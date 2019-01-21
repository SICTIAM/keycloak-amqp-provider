package com.mnemotix.keycloak.providers.events;

import com.mnemotix.keycloak.amqp.AMQPClientException;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.OperationType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class CustomEventListenerProviderFactory implements EventListenerProviderFactory {

    Logger logger = LoggerFactory.getLogger(CustomEventListenerProviderFactory.class);

    CustomEventListenerProvider provider;
    private Set<EventType> excludedEvents;
    private Set<OperationType> excludedAdminOperations;

    public EventListenerProvider create(KeycloakSession keycloakSession) {
        // initialize event listener
        provider = new CustomEventListenerProvider();
        return provider;
    }

    public void init(Config.Scope config) {

        String user = config.get("rabbitmq.user");
        String password = config.get("rabbitmq.password");
        String host = config.get("rabbitmq.host");
        String port = config.get("rabbitmq.port");
        String durable = config.get("rabbitmq.durable");
        String timeout = config.get("rabbitmq.timeout");
        String exchange = config.get("rabbitmq.exchange");
        String fairdispatch = config.get("rabbitmq.fairdispatch");
        String prefetch = config.get("rabbitmq.prefetch");
        String moduleId = config.get("module.id");

        StringBuilder sb = new StringBuilder();
        sb.append("\nKeycloak AMQP plugin initializing with following parameters").append("\n");
        sb.append("\n\t- User: ").append(user).append("\n");
//        sb.append("\n\t- Password: ").append(password).append("\n");
        sb.append("\n\t- Host: ").append(host).append("\n");
        sb.append("\n\t- Port: ").append(port).append("\n");
        sb.append("\n\t- Durable: ").append(durable).append("\n");
        sb.append("\n\t- Timeout: ").append(timeout).append("\n");
        sb.append("\n\t- Exchange: ").append(exchange).append("\n");
        sb.append("\n\t- Fairdispatch: ").append(fairdispatch).append("\n");
        sb.append("\n\t- Prefetch: ").append(prefetch).append("\n");
        sb.append("\n\t- ModuleId: ").append(moduleId).append("\n");
        System.out.println(sb.toString());

        String[] excludeEvents = config.getArray("exclude.events");
        String[] excludeOperations = config.getArray("exclude.operations");

        if (excludeEvents != null) {
            excludedEvents = new HashSet<EventType>();
            for (String e : excludeEvents) {
                excludedEvents.add(EventType.valueOf(e));
            }
        }
        if (excludeOperations != null) {
            excludedAdminOperations = new HashSet<OperationType>();
            for (String e : excludeOperations) {
                excludedAdminOperations.add(OperationType.valueOf(e));
            }
        }

/*
        conf.setUser(config.get("broker_user"));
        conf.setPassword(config.get("broker_pwd"));
        conf.setHost(config.get("broker_host"));
        conf.setPort(config.getInt("broker_port"));
        conf.setDurable(config.getBoolean("durable_messages"));
        conf.setTimeout(config.getInt("connection_timeout"));
        conf.setExchangeName(config.get("exchange_name"));
        conf.setFairDispatch(config.getBoolean("qos_fairdispatch"));
        conf.setPrefetchCount(config.getInt("qos_prefetch_count"));
        conf.setDebugMode(config.getBoolean("debug_mode"));

        String[] excludes = config.getArray("excludes");
        if (excludes != null) {
            excludedEvents = new HashSet<>();
            for (String e : excludes) {
                excludedEvents.add(EventType.valueOf(e));
            }
        }

        String[] excludesOperations = config.getArray("excludesOperations");
        if (excludesOperations != null) {
            excludedAdminOperations = new HashSet<>();
            for (String e : excludesOperations) {
                excludedAdminOperations.add(OperationType.valueOf(e));
            }
        }

 */
//        try {
//            provider.init();
//        } catch (AMQPClientException amex) {
//            logger.error("Unable to connect to broker, retrying...");
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                init(config);
//            }
//            init(config);
//        }
    }

    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
    }

    public void close() {
        logger.info("Keycloak AMQP plugin shutting down.");
        provider.close();
    }

    public String getId() {
        return "keycloak-amqp";
    }

    public int order() {
        return 0;
    }


}
