package com.mnemotix.keycloak.amqp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AMQPClientConfigTest {
    @Test
    public void loadConf() {
        AMQPClientConfig conf = new AMQPClientConfig();
        assertEquals(conf.getUser(), "admin");
        assertEquals(conf.getPassword(), "Pa55w0rd");
        assertEquals(conf.getHost(), "localhost");
        assertEquals(conf.getPort(), 5672);
        assertEquals(conf.isDurable(), true);
        assertEquals(conf.getTimeout(), 10000);
        assertEquals(conf.getExchangeName(), "default");
        assertEquals(conf.isFairDispatch(), true);
        assertEquals(conf.getPrefetchCount(), 5);
    }

}
