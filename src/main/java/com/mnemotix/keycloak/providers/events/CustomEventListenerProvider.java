package com.mnemotix.keycloak.providers.events;

import com.mnemotix.keycloak.amqp.AMQPClientException;
import com.mnemotix.keycloak.providers.helpers.KeycloakEventJsonSerializer;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustomEventListenerProvider implements EventListenerProvider {

    Logger logger = LoggerFactory.getLogger(CustomEventListenerProvider.class);

//    private AMQPClient amqpClient;
//    private Config conf;
//    private Set<EventType> excludedEvents = new HashSet<EventType>();
//    private Set<OperationType> excludedAdminOperations;



    public CustomEventListenerProvider() {
//        amqpClient = new AMQPClient();
//
//        // initialize excluded events set from conf
//        this.conf = ConfigFactory.load().getConfig("keycloak");
//        List<String> excludes = conf.getStringList("exclude.events");
//        if (excludes != null) {
//            for (String e : excludes) {
//                excludedEvents.add(EventType.valueOf(e));
//            }
//        }
//
//        List<String> excludedOperations = conf.getStringList("exclude.operations");
//        if (excludedOperations != null) {
//            excludedAdminOperations = new HashSet<OperationType>();
//            for (String e : excludedOperations) {
//                excludedAdminOperations.add(OperationType.valueOf(e));
//            }
//        }
    }

    public void init() throws AMQPClientException {
//        amqpClient.init();
    }

    public void onEvent(Event event) {
        logger.debug(KeycloakEventJsonSerializer.prettyPrint(event));
//        // Ignore excluded events
//        if (excludedEvents != null && excludedEvents.contains(event.getType())) return;
//        else {
//            try {
//                amqpClient.postMessage(
//                        new RPCCommandMessage(
//                                "sso.event." + event.getType().toString().toLowerCase(),
//                                KeycloakEventJsonSerializer.toNode(event)
//                        )
//                );
//            } catch (AMQPClientException e) {
//                logger.error(e.toString());
//            }
//        }
    }

    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        logger.debug(KeycloakEventJsonSerializer.prettyPrint(event));
//        // Ignore excluded operations
//        if (excludedAdminOperations != null && excludedAdminOperations.contains(event.getOperationType())) return;
//        else {
//            try {
//                amqpClient.postMessage(
//                        new RPCCommandMessage(
//                                "sso.admin." + event.getOperationType().toString().toLowerCase(),
//                                KeycloakEventJsonSerializer.toNode(event)
//                        )
//                );
//            } catch (AMQPClientException e) {
//                logger.error(e.toString());
//            }
//        }

    }

    public void close() {
//        try {
//            amqpClient.close();
//        } catch (AMQPClientException e) {
//            logger.error(e.toString());
//        }
    }
}
