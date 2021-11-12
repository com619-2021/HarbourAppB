package com.devops.groupb.harbourmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.PathSelectors;

@Configuration
@SpringBootApplication
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class HarbourMaster {
	public static void main(String[] args) {
		SpringApplication.run(HarbourMaster.class, args);
	}

	@Bean
	public Docket harbourMasterAPI() {
		return new Docket(DocumentationType.SWAGGER_2).select()
			.apis(RequestHandlerSelectors.basePackage("com.devops.groupb.harbourmaster.controller"))
			.paths(PathSelectors.any()).build();
	}
}
