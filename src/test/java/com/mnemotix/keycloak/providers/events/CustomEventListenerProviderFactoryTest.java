package com.mnemotix.keycloak.providers.events;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomEventListenerProviderFactoryTest {

    @Test
    public void instantiateWithoutError() {
        try{
            CustomEventListenerProviderFactory factory = new CustomEventListenerProviderFactory();
            factory.create(null);
            factory.init(null);
            assertEquals(true, true);
        }catch(Throwable t){
            t.printStackTrace();
            assertEquals(true, false);
        }
    }
}
