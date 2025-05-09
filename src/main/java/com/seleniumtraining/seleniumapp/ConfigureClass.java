package com.seleniumtraining.seleniumapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.seleniumtraining.seleniumapp.chromedriver.ChromeDriverOptions;

@Configuration
public class ConfigureClass {

    @Bean
    public ChromeDriverOptions chromeDriverOptions() {
        return new ChromeDriverOptions();
    }

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

}
