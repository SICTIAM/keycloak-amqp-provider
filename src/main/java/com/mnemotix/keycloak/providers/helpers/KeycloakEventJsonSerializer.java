package com.mnemotix.keycloak.providers.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;

import java.util.Map;

public class KeycloakEventJsonSerializer {

    public static String prettyPrint(Object event) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                    (event instanceof AdminEvent) ? toNode((AdminEvent) event) : toNode((Event) event)
            );
            return indented;
        } catch (JsonProcessingException jpe) {
            return null;
        }
    }

    public static ObjectNode toNode(Event event) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("eventType", event.getType().toString());
        node.put("realmId", event.getRealmId());
        node.put("sessionId", event.getSessionId());
        node.put("clientId", event.getClientId());
        node.put("userId", event.getUserId());
        node.put("ipAddress", event.getIpAddress());
        node.put("time", event.getTime());
        node.put("date", System.currentTimeMillis());

        if (event.getError() != null) node.put("error", event.getError());

        if (event.getDetails() != null) {
            ObjectNode details = node.putObject("details");
            for (Map.Entry<String, String> e : event.getDetails().entrySet()) {
                if (e.getValue() != null) {
                    details.put(e.getKey(), e.getValue());
                }
            }
        }
        return node;
    }

    public static ObjectNode toNode(AdminEvent event) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("operationType", event.getOperationType().toString());
        node.put("realmId", event.getRealmId());
        node.put("authRealmId", event.getAuthDetails().getRealmId());
        node.put("clientId", event.getAuthDetails().getClientId());
        node.put("userId", event.getAuthDetails().getUserId());
        node.put("ipAddress", event.getAuthDetails().getIpAddress());
        node.put("time", event.getTime());
        node.put("date", System.currentTimeMillis());
        node.put("resourcePath", event.getResourcePath());

        if (event.getError() != null) node.put("error", event.getError());
        return node;
    }


}
