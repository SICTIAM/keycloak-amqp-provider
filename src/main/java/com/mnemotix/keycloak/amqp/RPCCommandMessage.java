package com.mnemotix.keycloak.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RPCCommandMessage {

    Logger logger = LoggerFactory.getLogger(RPCCommandMessage.class);

    private String command;
    private String sender;
    private Long date;
    private ObjectNode body;

    public RPCCommandMessage(String command, ObjectNode body) {
        this.command = command;
        this.date = System.currentTimeMillis();
        this.body = body;
    }

    public boolean hasSender() {
        return this.sender != null;
    }

    public String getCommand() {
        return command;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public ObjectNode toNode() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("command", command);
        node.put("sender", sender);
        node.put("date", date);
        node.set("body", body);
        return node;
    }

    public String prettyPrint() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(toNode());
        } catch (JsonProcessingException e) {
            logger.error(e.toString());
            return null;
        }
    }
}
