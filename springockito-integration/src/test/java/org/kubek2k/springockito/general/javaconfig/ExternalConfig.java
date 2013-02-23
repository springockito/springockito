package org.kubek2k.springockito.general.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalConfig {

    @Bean
    SomeBean someBean() {
        return new SomeBean();
    }

    @Bean
    SpiedBean spiedBean(){
        return new SpiedBean();
    }
}
