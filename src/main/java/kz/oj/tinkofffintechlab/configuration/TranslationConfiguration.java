package kz.oj.tinkofffintechlab.configuration;

import com.google.cloud.translate.v3.TranslationServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class TranslationConfiguration {

    @Bean
    public TranslationServiceClient translationServiceClient() throws IOException {
        return TranslationServiceClient.create();
    }

}
