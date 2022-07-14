package com.cbmachinery.aftercareserviceagent.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig {

	private static final String SECURITY_SCHEME = "bearer";
	private static final String BEARER_FORMAT = "JWT";
	private static final String SECURITY_SCHEME_NAME = "Authorization";

	@Bean
	public OpenAPI customOpenAPI(@Value("${spring.application.name}") final String appName,
			@Value("${info.version}") final String appVersion) {
		return new OpenAPI().info(new Info().title(appName + " API").version(appVersion))
				.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
				.components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME,
						new SecurityScheme().name(SECURITY_SCHEME_NAME).type(SecurityScheme.Type.HTTP)
								.in(SecurityScheme.In.HEADER).scheme(SECURITY_SCHEME).bearerFormat(BEARER_FORMAT)));
	}
}
