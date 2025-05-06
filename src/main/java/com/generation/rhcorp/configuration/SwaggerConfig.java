package com.generation.rhcorp.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI springProjetoIntegradorOpenAPI() {
		return new OpenAPI().info(new Info().title("Projeto Integrador - RH Corp")
				.description("Projeto Integrador - Projeto de Gerenciamento de RH feito pelo grupo 5").version("v0.0.1")
				.license(new License().name("JavaSon's Five").url(
						"https://github.com/Aplicativo-Sistema-de-RH/Aplicativo_Sistema_RH?tab=readme-ov-file#descri%C3%A7%C3%A3o-do-projeto"))
				.contact(new Contact().name("JavaSon's Five").url(
						"https://github.com/Aplicativo-Sistema-de-RH/Aplicativo_Sistema_RH?tab=readme-ov-file#descri%C3%A7%C3%A3o-do-projeto")
						.email("grupo_5_generation@proton.me")))
				.externalDocs(new ExternalDocumentation().description("JavaSon's Five").url(
						"https://github.com/Aplicativo-Sistema-de-RH/Aplicativo_Sistema_RH?tab=readme-ov-file#descri%C3%A7%C3%A3o-do-projeto"));
	}

	@Bean
	OpenApiCustomizer customerGlobalHeadOpenApiCustomizer() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
				ApiResponses apiResponses = operation.getResponses();
				apiResponses.addApiResponse("200", new ApiResponse().description("Sucesso!"));
				apiResponses.addApiResponse("201", new ApiResponse().description("Criado!"));
				apiResponses.addApiResponse("204", new ApiResponse().description("Sem Conteudo!"));
				apiResponses.addApiResponse("400", new ApiResponse().description("Erro na Requisição!"));
				apiResponses.addApiResponse("401", new ApiResponse().description("Não Autorizado!"));
				apiResponses.addApiResponse("403", new ApiResponse().description("Proibido!"));
				apiResponses.addApiResponse("404", new ApiResponse().description("Não Encontrado!"));
				apiResponses.addApiResponse("500", new ApiResponse().description("Erro na Aplicação!"));
			}));
		};
	}

}