package com.mlorenzo.sfgrestdocsexample.web.controllers;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlorenzo.sfgrestdocsexample.domain.Beer;
import com.mlorenzo.sfgrestdocsexample.repositories.BeerRepository;
import com.mlorenzo.sfgrestdocsexample.web.models.BeerDto;
import com.mlorenzo.sfgrestdocsexample.web.models.BeerStyleEnum;

// Nota: Para realizar los tests y generar la documentación con REST Docs, se debe usar las utilidades del paquete
// "org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders" en lugar del paquete
// "org.springframework.test.web.servlet.request.MockMvcRequestBuilders"(Este paquete se usa en tests sin REST Docs)

@ExtendWith(RestDocumentationExtension.class)
// Por defecto, esta anotación utiliza el esquema http, el host localhost y el puerto 8080 para documentar los clientes
// http como, por ejemplo, curl. Podemos modificar estos valores por defecto usando los atributos "uriScheme", "uriHost"
// y "uriPort" de esta anotación.
@AutoConfigureRestDocs//(uriScheme = "https", uriHost = "test.host", uriPort = 80)
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.mlorenzo.sfgrestdocsexample.web.mappers")
class BeerControllerTest {
	
	@MockBean
	BeerRepository beerRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	void getBeerByIdTest() throws Exception {
		//given
		Beer beer = Beer.builder()
				.id(UUID.randomUUID())
				.beerName("Beer1")
				.build();
		given(beerRepository.findById(any(UUID.class))).willReturn(Optional.of(beer));
		//when
		mockMvc.perform(get("/api/v1/beer/{beerId}", beer.getId())
				// Pasamos a la url de la petición http el parámetro "isCold" con valor "yes". Realmente, el método
				// handler del controlador asociado a esta petición http no maneja ningún parámetro de la url, es decir,
				// se trata de un ejemplo para mostrar cómo debe documentarse parámetros de la url de una petición http
				// con REST Docs
				.param("isCold","yes")
				.accept(MediaType.APPLICATION_JSON))
				//then
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(beer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(beer.getBeerName())))
				.andDo(document("v1/beer-get",
						// Para documentar variables(Path Parameters) que viajan en la url de la petición http
						pathParameters(
								parameterWithName("beerId").description("UUID for desired beer to get")
						),
						// Para documentar parámetros(Query Parameters) que viajan en la url de la petición http
						requestParameters(
								parameterWithName("isCold").description("Is Beer Cold Query param")
						),
						// Para documentar la respuesta de la petición http, debe documentarse todas las propiedades
						// o campos de la entidad asociada a la respuesta, ya que, de no hacerlo, el test fallará
						responseFields(
								fieldWithPath("id").description("Id of Beer").type(UUID.class),
	                            fieldWithPath("version").description("Version number"),
	                            fieldWithPath("createdDate").description("Date Created").type(OffsetDateTime.class),
	                            fieldWithPath("lastModifiedDate").description("Date Updated").type(OffsetDateTime.class),
	                            fieldWithPath("beerName").description("Beer Name"),
	                            fieldWithPath("beerStyle").description("Beer Style"),
	                            fieldWithPath("upc").description("UPC of Beer"),
	                            fieldWithPath("price").description("Price"),
	                            fieldWithPath("quantityOnHand").description("Quantity On hand")
						)));
	}
	
	@Test
	void saveNewBeerTest() throws Exception {
		//given
		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		// Creamos una instancia de la clase "ConstrainedFields" para poder documentar correctamente las validaciones
		// de las propiedades o campos de la entidad "BeerDto" que se envía en la petición http de esta prueba
		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
		given(beerRepository.save(any())).willReturn(Beer.builder().id(UUID.randomUUID()).build());
		//when
		mockMvc.perform(post("/api/v1/beer/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
				//then
				.andExpect(status().isCreated())
				.andDo(document("v1/beer-new",
						// Para documentar la petición de la petición http, debe documentarse todas las propiedades o
						// campos de la entidad asociada a la petición, ya que, de no hacerlo, el test fallará.
						// Usamos el método "ignored" en aquellas propiedades que no queremos que aparezcan en la
						// documentación generada. En este caso, aplicamos dicho método en aquellas propiedades cuyos
						// valores son generados por la propia aplicación y no son introducidos por el usuario o cliente.
						requestFields(
								// Sustituimos el método "fieldWithPath" por el método "withPath" de la instancia
								// "fields" para que se documenten las validaciones de las propiedades o campos de la
								// entidad que se envía en la petición http de esta prueba. Si la entidad no tuviera
								// validaciones de propiedades o campos, entonces podríamosseguir usando el método
								// "fieldWithPath" en cada una de esas propiedades o campos.
								fields.withPath("id").ignored(),
								fields.withPath("version").ignored(),
								fields.withPath("createdDate").ignored(),
								fields.withPath("lastModifiedDate").ignored(),
								fields.withPath("beerName").description("Name of the beer"),
								fields.withPath("beerStyle").description("Style of Beer"),
								fields.withPath("upc").description("Beer UPC").attributes(),
								fields.withPath("price").description("Beer Price"),
								fields.withPath("quantityOnHand").ignored()
						)));
	}
	
	@Test
	void updateBeerByIdTest() throws Exception {
		//given
		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		//when
		mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
				//then
				.andExpect(status().isNoContent());
	}
	
	BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("Nice Ale")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("9.99"))
                .upc(123123123123L)
                .build();
    }
	
	// Esta clase es necesaria para poder documentar las validaciones de las propiedades o campos de las entidades que
	// se envían en algunas peticiones http
	private static class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;
        
        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }
        
        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

}
