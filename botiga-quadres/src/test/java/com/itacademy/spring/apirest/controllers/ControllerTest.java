package com.itacademy.spring.apirest.controllers;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itacademy.spring.apirest.dao.BotigaDAO;
import com.itacademy.spring.apirest.dao.QuadreDAO;
import com.itacademy.spring.apirest.domain.Botiga;
import com.itacademy.spring.apirest.domain.Quadre;

@ComponentScan(basePackages = "com.itacademy.spring.apirest")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {ControllerTest.class})
class ControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Mock
	BotigaDAO botigaDAO;
	
	@InjectMocks
	Controller controller;
	
	@Mock
	QuadreDAO quadreDAO;
	
	List<Botiga> listaBotigas;
	List<Quadre> listaQuadres;
	Botiga botiga;
	Quadre quadre;
	
	@BeforeEach
	void setUp() {
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
				
	}
	
	@Test
	@DisplayName("Test getAllBotigas")	
	void getAllBotigasTest() throws Exception {
		
		listaBotigas = new ArrayList<Botiga>();
		listaBotigas.add(new Botiga(1, "MagicStore",5));
		listaBotigas.add(new Botiga(2, "Santalone",10));
		
		when(botigaDAO.findAll()).thenReturn(listaBotigas);	
		
		this.mockMvc.perform(get("/shops"))
			.andExpect(status().isOk())
			.andDo(print());
	}
	
	@Test
	@DisplayName("Test findBotigaById Success")
	void getBotigaByIdTest() throws Exception {				
		
		botiga = new Botiga(2, "Santalone",10);
		int botigaId = botiga.getId();
				
		when(botigaDAO.findById(botigaId)).thenReturn(Optional.of(botiga));
		
		this.mockMvc.perform(get("/shops/{id}", botigaId))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
			.andExpect(MockMvcResultMatchers.jsonPath(".nom").value("Santalone"))
			.andExpect(MockMvcResultMatchers.jsonPath(".capacitat_max").value(10))
			.andDo(print());	
	}
	
	@Test
	@DisplayName("Test findBotigaById Not Found")
	void getBotigaByIdNotFoundTest() throws Exception {	
		
		int botigaId = 5;
				
		when(botigaDAO.findById(botigaId)).thenReturn(Optional.empty());
		
		this.mockMvc.perform(get("/shops/{id}", botigaId))
			.andExpect(status().isNotFound())
			.andDo(print());			
	}
	
	@Test
	@DisplayName("Test createBotiga")
	void createBotigaTest() throws Exception {
		
		botiga = new Botiga(3,"Venus",8);
		
		when(botigaDAO.save(botiga)).thenReturn(botiga);
	
		ObjectMapper mapper = new ObjectMapper();
		String jsonbody = mapper.writeValueAsString(botiga);
		
		
		this.mockMvc.perform(post("/shops")
							.content(jsonbody)
							.contentType(MediaType.APPLICATION_JSON)
							)
					.andExpect(status().isOk())
					.andDo(print());			
	}	
	

	@Test
	@DisplayName("Test addQuadre Success")
	void addQuadreBotigaTest() throws Exception {
		
		botiga = new Botiga(2, "Santalone",10);
		int botigaId = 2;
		
		botiga.addQuadre(new Quadre(1,"Froid", "Jungla2", 500, Calendar.getInstance(), botiga));
		
		when(botigaDAO.findById(botigaId)).thenReturn(Optional.of(botiga));
		when(quadreDAO.save(quadre)).thenReturn(quadre);
		when(botigaDAO.save(botiga)).thenReturn(botiga);
		
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonbody = mapper.writeValueAsString(botiga);
		
		
		
		this.mockMvc.perform(post("/shops/{id}/quadres", botigaId)
							.content(jsonbody)
							.contentType(MediaType.APPLICATION_JSON)
							)
					.andExpect(status().isOk())
					.andDo(print());		
	}	

	
	@Test
	@DisplayName("Test getAllQuadres Success")
	void getQuadresTest() throws Exception {		
		
		botiga = new Botiga(2, "Santalone",10);		
		int botigaId = botiga.getId();
		botiga.addQuadre(new Quadre(1,"Froid", "Jungla2", 500, Calendar.getInstance(), botiga));
		
		listaQuadres = botiga.getQuadres();
		
		when(botigaDAO.findById(botigaId)).thenReturn(Optional.of(botiga));
		when(quadreDAO.findAll()).thenReturn(listaQuadres);
					
		this.mockMvc.perform(get("/shops/{id}/quadres",botigaId))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	@Test
	@DisplayName("Test getAllQuadres No Content")
	void getQuadresNoContentTest() throws Exception {	
		
		int botigaId = 2;
		
		when(botigaDAO.findById(botigaId)).thenReturn(Optional.empty());
		when(quadreDAO.findAll()).thenReturn(null);
					
		this.mockMvc.perform(get("/shops/{id}/quadres",botigaId))
		.andExpect(status().isNoContent())
		.andDo(print());
	}

	@Test
	@DisplayName("Test deleteBotiga Success")
	void deleteBotigaTest() throws Exception{	
		
		botiga = new Botiga(3,"Venus",8);
		int botigaId = 3;
		
		when(botigaDAO.findById(botigaId)).thenReturn(Optional.of(botiga));
		when(quadreDAO.deleteAllByBotiga(botiga)).thenReturn(botigaId);	
		
		this.mockMvc.perform(delete("/shops/{id}/quadres",botigaId))
		.andExpect(status().isOk())
		.andDo(print());
	}

	@Test
	@DisplayName("Test deleteBotiga Not Found")
	void deleteBotigaNotFoundTest() throws Exception {

		int botigaId = 5;

		when(botigaDAO.findById(botigaId)).thenReturn(Optional.empty());

		this.mockMvc.perform(delete("/shops/{id}/quadres", botigaId))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
}
