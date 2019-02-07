package com.witcher.rss.config;

import com.witcher.rss.api.mapper.JacksonObjectMapper;
import com.witcher.rss.api.rs.admin.AdminRS;
import com.witcher.rss.api.rs.client.ClientRS;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(JacksonObjectMapper.class);
        register(ClientRS.class);
        register(AdminRS.class);
    }
}
