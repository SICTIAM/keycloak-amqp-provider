package com.mnemotix.keycloak.amqp;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AMQPClient {

    Logger logger = LoggerFactory.getLogger(AMQPClient.class);

    private AMQPClientConfig conf;
    private Connection connection;
    private Channel channel;

    private static final ConnectionFactory factory = new ConnectionFactory();
    private static final String nodeId = UUID.randomUUID().toString();

    public AMQPClient() {

        // Load connection parameters from configuration file
        conf = new AMQPClientConfig();

        logger.debug("AMQP client loaded with parameters :\n" + conf.toString());

        factory.setHost(conf.getHost());
        factory.setPort(conf.getPort());
        factory.setUsername(conf.getUser());
        factory.setPassword(conf.getPassword());
        factory.setConnectionTimeout(conf.getTimeout());
    }

    public void init() throws AMQPClientException {
        postStatus("STARTING", "Module is starting up");
        connect();
        postStatus("CONNECTED", "Module is connected to broker.");
    }

    public void close() throws AMQPClientException {
        postStatus("DISCONNECTING", "Module is closing.");
        disconnect();
    }

    /**
     * Opens a connection to the broker
     * @return the connection handler
     * @throws AMQPClientException if the broker did not respond
     */
    public Connection connect() throws AMQPClientException {
        try {
            connection = factory.newConnection();
            createChannel();
            return connection;
        } catch (TimeoutException tex) {
            throw new AMQPClientException("The connection to broker timed out.");
        } catch (IOException iex) {
            throw new AMQPClientException("IO Error while connecting to the broker.");
        }
    }

    /**
     * Closes the connection to the broker
     * @throws AMQPClientException if the broker did not respond
     */
    public void disconnect() throws AMQPClientException {
        try {
            channel.close();
            connection.close();
        } catch (TimeoutException tex) {
            throw new AMQPClientException("The connection to broker timed out.");
        } catch (IOException iex) {
            throw new AMQPClientException("IO Error while connecting to the broker.");
        }
    }

    /**
     * Creates a channel (with a topic name loaded from configuration) to exchange messages with remote consumers
     * @return the channel handler
     * @throws AMQPClientException if the broker did not respond
     */
    public Channel createChannel() throws AMQPClientException {
        try {
            channel = connection.createChannel();
            if (conf.isFairDispatch()) channel.basicQos(conf.getPrefetchCount());
            channel.exchangeDeclare(conf.getExchangeName(), "topic");
            return channel;
        } catch (IOException iex) {
            throw new AMQPClientException("IO Error while creating an AMQP channel.");
        }
    }

    /**
     * Method called to post AMQP messages to keep consumers up to date about the plugin status
     */
    private void postStatus(String status, String message) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("module", conf.getModuleId());
        node.put("status", status);
        node.put("message", message);
        try {
            logger.debug("Sending status update message");
            postMessage(new RPCCommandMessage("status.update", node));
        } catch (AMQPClientException e) {
            logger.error(e.toString());
        }
    }

    /**
     * Method called to post AMQP messages on the open channel
     * @throws AMQPClientException if a problems occurs with the communication with the broker
     */
    public void postMessage(RPCCommandMessage cmd) throws AMQPClientException {
        try {
            if (!cmd.hasSender()) cmd.setSender(nodeId);
            if (connection == null) connect();
            if (channel == null) createChannel();
            channel.basicPublish(conf.getExchangeName(), cmd.getCommand(), null, cmd.toNode().toString().getBytes());
            logger.debug(cmd.prettyPrint());
        } catch (IOException iex) {
            throw new AMQPClientException("IO Error while posting an AMQP message.", iex);
        }
    }

}
