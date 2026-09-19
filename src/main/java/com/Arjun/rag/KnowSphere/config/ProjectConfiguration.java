package com.Arjun.rag.KnowSphere.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfiguration {

    @Bean
    public OpenAPI openAPI(){

        return new OpenAPI()
                .info(
                        new Info()
                                .title("KnowSphere - AI document intelligence. A RAG based backend")
                                .description("Rest api for KnowSphere where documents of multiple formats can be uploaded for context creation to cater the user queries.")
                                .version("1.0.0")
                                .contact( new Contact()
                                        .name("Arjun Technocrats")
                                        .email("arjun.12narang@gmail.com")
                                        .url("abc.com")
                                )


                );
    }
}
