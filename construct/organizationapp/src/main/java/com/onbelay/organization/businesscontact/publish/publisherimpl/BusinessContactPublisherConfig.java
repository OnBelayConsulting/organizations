package com.onbelay.organization.businesscontact.publish.publisherimpl;

import com.onbelay.organization.businesscontact.publish.publisher.BusinessContactPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class BusinessContactPublisherConfig {

    @Bean
    @Profile("messaging")
    public BusinessContactPublisher businessContactPublisher() {
        return new BusinessContactPublisherBean();
    }

    @Bean
    @Profile("!messaging")
    public BusinessContactPublisher mockBusinessContactPublisher() {
        return new BusinessContactPublisherStub();
    }
}
