package br.com.sistematutoriais;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sistematutoriais.exceptions.PaginaNaoEncontrada;
import br.com.sistematutoriais.model.Tutorial;
import br.com.sistematutoriais.service.TutorialService;

/** @author carlos
 * 
 *  
 * 
 * Cada tutorial tem id, titulo, descricao, status.
 *   id : Long
 *   titulo : String 
 *   descricao : String
 *   status : String (PENDENTE, PUBLICADO)
 *   
 * Repositorio : TituloRepository
 * 
 * Servico : TituloService
 * 
 * O controller a api terá o nome  "api/tutorials" 
 * api deve  listar, recuperar, criar, atualizar, excluir tutoriais.
 * Também podemos encontrar tutoriais por título. 
 * 
 * api "api/tutorials"
 * post criar "/tutorials"
 * get listar "/tutorials", recuperar "/tutorials/{id}", encontrar tutoriais por título "/tutorials?titulo={}"
 * put atualizar "/tutorials/{id}"
 * delete excluir "/tutorials/{id}"
 *  
 *  
 *
 */
@AutoConfigureMockMvc
public class TestRestController extends SistemaTutoriaisApplicationTests {
	
	   @Autowired
	   private MockMvc mockitoMvc;
	   
	   @Autowired
	   private ObjectMapper objectMapper;
	   
	   // Servico
	   @Autowired
	   private TutorialService tutorialService;
	   
	   
	   @Test
	   public void test_criar_tutorial() throws JsonProcessingException, Exception {
		   Tutorial tutorial = new Tutorial("titulo01","descricao01","PENDENTE");
		    mockitoMvc.perform(// Dados para enviar.
		    		           post("/api/tutorials")  // url tipo POST.
		    		          .accept(MediaType.APPLICATION_JSON) // Formato json
				              .content(objectMapper.writeValueAsString(tutorial)) // Inserção Objeto para ser enviado.
				              .contentType(MediaType.APPLICATION_JSON) // Formato do objeto inserido				              
				              )// Retorno
		    				  .andDo(print())// Imprime no console os dados do processo
		                      .andExpect(status().is2xxSuccessful()) // Verifica se a requisição esta ok.
		    				  .andExpect(jsonPath("$.id").exists()) // Verifica os dados de retorno.
		    				  .andExpect(jsonPath("$.titulo").exists())
		    				  .andExpect(jsonPath("$.descricao").exists())
		    				  .andExpect(jsonPath("$.status").exists())
							  .andExpect(jsonPath("$.dtCriacao").exists())
							  .andExpect(jsonPath("$.dtAtualizacao").exists());
	   }
	   
	   @Test
	   public void test_atualizar_tutorial_para_publicado() throws JsonProcessingException, Exception {
		   Tutorial tutorialRest01 = tutorialService.add(new Tutorial("tutorialRest01", "descricaoRest01", "PENDENTE"));
		   Tutorial tutorialRest01PUBLICADO = new Tutorial(tutorialRest01.getId(),"tutorialRest01PUBLICADO", "tutorialRest01PUBLICADO", "PUBLICADO",tutorialRest01.getDtCriacao(), null,tutorialRest01.getDtAtualizacao());
		   mockitoMvc.perform(// Dados para enviar.
 		              put("/api/tutorials/{id}", tutorialRest01PUBLICADO.getId())  // url tipo POST.
 		           	  .accept(MediaType.APPLICATION_JSON) // Formato json
		              .content(objectMapper.writeValueAsString(tutorialRest01PUBLICADO)) // Inserção Objeto para ser enviado.
		              .contentType(MediaType.APPLICATION_JSON) // Formato do objeto inserido				              
			          )// Retorno  
	 				  .andDo(print())// Imprime no console os dados do processo
	                  .andExpect(status().is2xxSuccessful()) // Verifica se a requisição esta ok.
	 				  .andExpect(jsonPath("$.id").exists()) // Verifica os dados de retorno.
	 				  .andExpect(jsonPath("$.titulo").value(tutorialRest01PUBLICADO.getTitulo()))
	 				  .andExpect(jsonPath("$.descricao").value(tutorialRest01PUBLICADO.getDescricao()))
	 				  .andExpect(jsonPath("$.status").value(tutorialRest01PUBLICADO.getStatus()))
					  .andExpect(jsonPath("$.dtCriacao").exists())
					  .andExpect(jsonPath("$.dtAtualizacao").exists())
					  .andExpect(jsonPath("$.dtPublicacao").exists());
	   }
	   
	   
	   @Test
	   public void test_bucar_tutoriais() throws Exception {
		   Tutorial tutorial01 = tutorialService.add(new Tutorial("tutorialRest01", "descricao10", "PENDENTE"));
		   Tutorial tutorial02 = tutorialService.add(new Tutorial("tutorialRest02", "descricao10", "PENDENTE"));
		   mockitoMvc.perform(// Dados do envio
	   				get("/api/tutorials")  // url
	   				.accept(MediaType.APPLICATION_JSON) // Tipo json
	   			  )// Retorno
					  .andDo(print()) // Imprime no console os dados do processo
					  .andExpect(status().is2xxSuccessful()) // Verifica se a requisição esta ok.
					  .andExpect(jsonPath("$").isArray()) // Verifica os dados de retorno estão em formato de array.
					  .andExpect(jsonPath("$[0].id").exists())// Verifica os dados no array.
					  .andExpect(jsonPath("$[0].titulo").exists())
					  .andExpect(jsonPath("$[0].descricao").exists())
					  .andExpect(jsonPath("$[0].status").exists())
					  .andExpect(jsonPath("$[0].dtCriacao").exists())
					  .andExpect(jsonPath("$[0].dtAtualizacao").exists())
					  .andExpect(jsonPath("$[1].id").exists())
					  .andExpect(jsonPath("$[1].titulo").exists())
					  .andExpect(jsonPath("$[1].descricao").exists())
					  .andExpect(jsonPath("$[1].status").exists())
					  .andExpect(jsonPath("$[1].dtCriacao").exists())
					  .andExpect(jsonPath("$[0].dtAtualizacao").exists());
	   }
	   
	   
	   @Test
	   public void test_bucar_tutoriais_publicados() throws Exception {
		   Tutorial tutorialRest01 = tutorialService.add(new Tutorial("tutorialRest01", "descricao10", "PUBLICADO"));
		   Tutorial tutorialRest02 = tutorialService.add(new Tutorial("tutorialRest02", "descricao10", "PUBLICADO"));
		   Tutorial tutorialRest01Atualizado = tutorialService.update(tutorialRest01);
		   Tutorial tutorialRest02Atualizado = tutorialService.update(tutorialRest02);
		   mockitoMvc.perform(// Dados do envio
	   				get("/api/tutorials/status/PUBLICADO")  // url
	   				.accept(MediaType.APPLICATION_JSON) // Tipo json
	   			  )// Retorno
					  .andDo(print()) // Imprime no console os dados do processo
					  .andExpect(status().is2xxSuccessful()) // Verifica se a requisição esta ok.
					  .andExpect(jsonPath("$").isArray()) // Verifica os dados de retorno estão em formato de array.
					  .andExpect(jsonPath("$[0].id").exists())// Verifica os dados no array.
					  .andExpect(jsonPath("$[0].titulo").exists())
					  .andExpect(jsonPath("$[0].descricao").exists())
					  .andExpect(jsonPath("$[0].status").value("PUBLICADO"))
					  .andExpect(jsonPath("$[0].dtCriacao").exists())
					  .andExpect(jsonPath("$[0].dtAtualizacao").exists())
					  .andExpect(jsonPath("$[0].dtPublicacao").exists())
					  .andExpect(jsonPath("$[1].id").exists())
					  .andExpect(jsonPath("$[1].titulo").exists())
					  .andExpect(jsonPath("$[1].descricao").exists())
					  .andExpect(jsonPath("$[1].status").value("PUBLICADO"))
					  .andExpect(jsonPath("$[1].dtCriacao").exists())
					  .andExpect(jsonPath("$[0].dtAtualizacao").exists())
					  .andExpect(jsonPath("$[1].dtPublicacao").exists());
	   }

	   @Test
	   public void test_bucar_tutoriais_pendentes() throws Exception {		   
		   Tutorial tutorialRest01 = tutorialService.add(new Tutorial("tutorialRest01", "descricao10", "PENDENTE"));
		   Tutorial tutorialRest02 = tutorialService.add(new Tutorial("tutorialRest02", "descricao10", "PENDENTE"));
		   mockitoMvc.perform(// Dados do envio
   				get("/api/tutorials/status/PENDENTE")  // url
   				.accept(MediaType.APPLICATION_JSON) // Tipo json
   			    )// Retorno
				  .andDo(print()) // Imprime no console os dados do processo
				  .andExpect(status().is2xxSuccessful()) // Verifica se a requisição esta ok.
				  .andExpect(jsonPath("$").isArray()) // Verifica os dados de retorno estão em formato de array.
				  .andExpect(jsonPath("$[0].id").exists())// Verifica os dados no array.
				  .andExpect(jsonPath("$[0].titulo").exists())
				  .andExpect(jsonPath("$[0].descricao").exists())
				  .andExpect(jsonPath("$[0].status").value("PENDENTE"))
				  .andExpect(jsonPath("$[0].dtCriacao").exists())
				  .andExpect(jsonPath("$[0].dtAtualizacao").exists())
				  .andExpect(jsonPath("$[0].dtPublicacao").doesNotExist())
				  .andExpect(jsonPath("$[1].id").exists())
				  .andExpect(jsonPath("$[1].titulo").exists())
				  .andExpect(jsonPath("$[1].descricao").exists())
				  .andExpect(jsonPath("$[1].status").value("PENDENTE"))
				  .andExpect(jsonPath("$[1].dtCriacao").exists())
				  .andExpect(jsonPath("$[0].dtAtualizacao").exists())
				  .andExpect(jsonPath("$[1].dtPublicacao").doesNotExist());
		   }
	   
	   @Test
	   public void test_excluir_tutorial() throws Exception {
		      Tutorial tutorialRestParaDeletar = tutorialService.add(new Tutorial("tutorialRestParaDeletar", "descricao10", "PENDENTE"));
			  mockitoMvc.perform(// Dados de envio
			             delete("/api/tutorials/{id}", tutorialRestParaDeletar.getId()) // url
			             .accept(MediaType.APPLICATION_JSON) // Formato json
			             )// Retorno
	                      .andDo(print())// Imprime no console os dados do processo
	                      .andExpect(status().is2xxSuccessful()// Verificar se a requisição esta ok.
	                     ); 
	   }
	   
	   
	   @Test
	   public void test_bucar_tutorial_por_titulo() throws Exception {
		   Tutorial tutorialRestByTitulo = tutorialService.add(new Tutorial("tutorialRestByTitulo", "descricaoRestByTitulo", "PENDENTE"));
		   mockitoMvc.perform(// Dados da requisição
	                 get("/api/tutorials/titulo/{titulo}",tutorialRestByTitulo.getTitulo()) // url
	                 .accept(MediaType.APPLICATION_JSON) // Formato tipo json
	                 )// Retorno;
                   .andDo(print()) // Imprime no console os dados do processo
                   .andExpect(status().is2xxSuccessful()) // Verificar se a requisição esta ok.
                   .andExpect(jsonPath("$.id").exists()) // Verificar os dados do objeto de retorno.
                   .andExpect(jsonPath("$.titulo").value(tutorialRestByTitulo.getTitulo()))
                   .andExpect(jsonPath("$.descricao").value(tutorialRestByTitulo.getDescricao()))
                   .andExpect(jsonPath("$.status").value(tutorialRestByTitulo.getStatus()))
                   .andExpect(jsonPath("$.dtCriacao").exists())
                   .andExpect(jsonPath("$.dtAtualizacao").exists())
 				   .andExpect(jsonPath("$.dtPublicacao").doesNotExist());
	   }

	   @Test
	   public void test_bucar_tutorial_por_id() throws Exception {
		   Tutorial tutorialById = tutorialService.add(new Tutorial("tutorialById", "descricaoById", "PENDENTE"));
		    mockitoMvc.perform(// Dados do envio
	  				get("/api/tutorials/{id}",tutorialById.getId()) // url
	  				.accept(MediaType.APPLICATION_JSON)	// Formato json			  			
	  			)// Retorno
				  .andDo(print())// Imprime no console os dados do processo
		          .andExpect(status().is2xxSuccessful()) // Verifica se a requisição esta ok.
				  .andExpect(jsonPath("$.id").exists()) // Verifica os dados de retorno.
				  .andExpect(jsonPath("$.titulo").value(tutorialById.getTitulo()))
				  .andExpect(jsonPath("$.descricao").value(tutorialById.getDescricao()))
				  .andExpect(jsonPath("$.status").value(tutorialById.getStatus()))
				  .andExpect(jsonPath("$.dtCriacao").exists())
				  .andExpect(jsonPath("$.dtAtualizacao").exists())
				  .andExpect(jsonPath("$.dtPublicacao").doesNotExist());
	   }
	   
	   @Test
	   public void test_500_e_404() throws Exception{
		  
				    mockitoMvc.perform(// Dados do envio
						get("/api/tutorialsssss") // url
						.accept(MediaType.APPLICATION_JSON)	// Formato json			  			
					)// Retorno
					  .andDo(print())// Imprime no console os dados do processo
				      .andExpect(status().is4xxClientError())
				      .andExpect(jsonPath("$.code").value("404"))
				      .andExpect(jsonPath("$.message").value("Pagina nao encontrata."));
				    
				    mockitoMvc.perform(// Dados do envio
						get("/api/tutorials/12AAA") // url
						.accept(MediaType.APPLICATION_JSON)	// Formato json			  			
					)// Retorno
					  .andDo(print())// Imprime no console os dados do processo
				      .andExpect(status().is5xxServerError())
				      .andExpect(jsonPath("$.code").value("500"))
				      .andExpect(jsonPath("$.message").value("Dados incorretos, verifique e tente novamente.")); 
			
	   } 
	   

}














