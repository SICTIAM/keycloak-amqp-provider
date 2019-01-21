package com.mnemotix.keycloak.providers.events;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomEventListenerProviderTest {
    CustomEventListenerProvider provider = new CustomEventListenerProvider();

    @Test
    public void initWithoutError() {
        try{
            provider.init();
            assertEquals(true, true);
        }catch(Throwable t){
            t.printStackTrace();
            assertEquals(true, false);
        }
    }

    @Test
    public void closeWithoutError() {
        try{
            provider.close();
            assertEquals(true, true);
        }catch(Throwable t){
            t.printStackTrace();
            assertEquals(true, false);
        }
    }
}
