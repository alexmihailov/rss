package com.witcher.rss.config;

import com.witcher.rss.api.rs.client.ClientRS;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ClientRS.class);
    }
}
