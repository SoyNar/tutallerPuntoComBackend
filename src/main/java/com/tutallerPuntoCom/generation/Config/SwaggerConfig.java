    package com.tutallerPuntoCom.generation.Config;

    import io.swagger.v3.oas.models.Components;
    import io.swagger.v3.oas.models.OpenAPI;
    import io.swagger.v3.oas.models.info.Info;
    import io.swagger.v3.oas.models.security.SecurityRequirement;
    import io.swagger.v3.oas.models.security.SecurityScheme;
    import org.springframework.context.annotation.Bean;

    public class SwaggerConfig {


        private static final String SECURITY_SCHEME = "bearerAuth";

        @Bean
        public OpenAPI openAPI() {
            return new OpenAPI()
                    .info(new Info()
                            .title("Tu Taller API")
                            .version("1.0")
                            .description("API para gestión de talleres, servicios y citas"))
                    .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME))
                    .components(new Components()
                            .addSecuritySchemes(SECURITY_SCHEME,
                                    new SecurityScheme()
                                            .name(SECURITY_SCHEME)
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("bearer")
                                            .bearerFormat("JWT")));
        }
    }
