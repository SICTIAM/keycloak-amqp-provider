package com.mnemotix.keycloak.amqp;

public class AMQPClientException extends Exception {
    public AMQPClientException(String message) {
        super(message);
    }

    public AMQPClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
