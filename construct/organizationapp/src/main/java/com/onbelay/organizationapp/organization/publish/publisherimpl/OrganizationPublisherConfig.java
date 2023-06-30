package com.onbelay.organizationapp.organization.publish.publisherimpl;

import com.onbelay.organizationapp.organization.publish.publisher.OrganizationPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class OrganizationPublisherConfig {

    @Bean
    @Profile("messaging")
    public OrganizationPublisher organizationPublisher() {
        return new OrganizationPublisherBean();
    }

    @Bean
    @Profile("!messaging")
    public OrganizationPublisher mockOrganizationPublisher() {
        return new OrganizationPublisherStub();
    }
}
