package com.mnemotix.keycloak.amqp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AMQPClientTest {

    @Test
    public void connectWithoutError() {
        AMQPClient client = new AMQPClient();
        try {
            client.connect();
            assertEquals(true, true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertEquals(true, false);
        }
    }
}
