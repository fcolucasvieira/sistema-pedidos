package com.fcolucasvieira.sistema_pedidos.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema Pedidos API")
                        .version("1.0.0")
                        .description("API REST para gerenciamento de pedidos, produtos e usuários desenvolvida com Java 21 + Spring Boot 4 e DDD")
                        .contact(new Contact()
                                .name("Lucas Vieira")
                                .url("https://github.com/fcolucasvieira")
                        )
                );

    }
}
