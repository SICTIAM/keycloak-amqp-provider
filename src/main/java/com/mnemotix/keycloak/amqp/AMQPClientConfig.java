package com.mnemotix.keycloak.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AMQPClientConfig {

    Logger logger = LoggerFactory.getLogger(AMQPClientConfig.class);

    private Config conf;
    private String user, password, host, exchangeName, moduleId;
    private int port, timeout, prefetchCount;
    private boolean durable, fairDispatch;

    public AMQPClientConfig() {
        this.conf = ConfigFactory.load().getConfig("amqp");
        this.user = conf.getString("user");
        this.password = conf.getString("password");
        this.host = conf.getString("broker.host");
        this.port = conf.getInt("broker.port");
        this.durable = conf.getBoolean("durable");
        this.timeout = conf.getInt("connection.timeout");
        this.exchangeName = conf.getString("exchange.name");
        this.fairDispatch = conf.getBoolean("qos.fairdispatch");
        this.prefetchCount = conf.getInt("qos.prefetch.count");
        this.moduleId = conf.getString("module.id");

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getPrefetchCount() {
        return prefetchCount;
    }

    public boolean isDurable() {
        return durable;
    }

    public boolean isFairDispatch() {
        return fairDispatch;
    }

    public String getModuleId() {
        return moduleId;
    }

    public String toString() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("user", this.user);
        node.put("host", this.host);
        node.put("port", this.port);
        node.put("durable", this.durable);
        node.put("timeout", this.timeout);
        node.put("exchangeName", this.exchangeName);
        node.put("fairDispatch", this.fairDispatch);
        node.put("prefetchCount", this.prefetchCount);
        node.put("moduleId", this.moduleId);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (JsonProcessingException e) {
            logger.error(e.toString());
            return null;
        }
    }
}
