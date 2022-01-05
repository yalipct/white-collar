package com.itacademy.spring.apirest.controllers;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itacademy.spring.apirest.dao.BotigaDAO;
import com.itacademy.spring.apirest.dao.QuadreDAO;
import com.itacademy.spring.apirest.domain.Botiga;
import com.itacademy.spring.apirest.domain.Quadre;

/**
 * @author Aliuvys
 *
 */
@RestController
@RequestMapping("shops")
public class Controller {

	@Autowired
	private BotigaDAO botigaDAO;
	
	@Autowired
	private QuadreDAO quadreDAO;

	// CREATE NEW SHOP
	@PostMapping
	public ResponseEntity<Botiga> createBotiga(@RequestBody Botiga botiga) {
		Botiga newBotiga = botigaDAO.save(botiga);
		return ResponseEntity.ok(newBotiga);
	}

	// GET A SHOP LIST
	@GetMapping
	public ResponseEntity<List<Botiga>> getAllBotigas() {
		List<Botiga> botigas = botigaDAO.findAll();
		return ResponseEntity.ok(botigas);
	}

	// GET SHOP BY ID
	@RequestMapping(value = "{id}")
	public ResponseEntity<Botiga> getBotigaById(@PathVariable("id") int id) {

		Optional<Botiga> botiga = botigaDAO.findById(id);
		if(botiga.isPresent()) {
			return ResponseEntity.ok(botiga.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// 3) ADD PICTURE (quadre): li donarem el nom del quadre i el del autor (POST/shops/{ID}/pictures)
	@PostMapping(value = "{id}/quadres") 
	public ResponseEntity<String> addQuadreBotiga(@PathVariable("id") int id, 
			@RequestBody Quadre quadre) 
	{		
		Optional<Botiga> botiga = botigaDAO.findById(id);
		if(botiga.isPresent()) {
			Botiga botiga1 = botiga.get();
			int capacitatBotiga = botiga1.getQuadres().size();
			
			if(capacitatBotiga < botiga1.getCapacitat_max()) {
				quadre.setBotiga(botiga1);
				//quadre.setData_registre(Calendar.getInstance());
				quadreDAO.save(quadre);
				botiga1.addQuadre(quadre);
				botigaDAO.save(botiga1);
				return ResponseEntity.ok("Cuadro insertado con éxito");
			}else {
				return ResponseEntity.ok("No se ha insertado el cuadro. Superado el máximo de cuadros de la tienda");	
			}			
			
		}
		return ResponseEntity.noContent().build();		
		
	}
	
	// GET A QUADRE LIST
	@RequestMapping(value = "{id}/quadres")
	public ResponseEntity<List<Quadre>> getQuadres(@PathVariable("id") int id) {
		Optional<Botiga> botiga = botigaDAO.findById(id);
		if (botiga.isPresent()) {
			Botiga botiga1 = botiga.get();
			List<Quadre> quadres = botiga1.getQuadres();
			return ResponseEntity.ok(quadres);
		}
		return ResponseEntity.noContent().build();
	}

	// DELETE SHOP
	@Transactional
	@DeleteMapping(value = "{id}/quadres")
	public ResponseEntity<String> deleteBotiga(@PathVariable("id") int id){
		Optional<Botiga> botiga = botigaDAO.findById(id);
		if(botiga.isPresent()) {
			quadreDAO.deleteAllByBotiga(botiga.get());
			botigaDAO.deleteById(id);
			return ResponseEntity.ok("Tienda borrada correctamente");
		}		
		
		return ResponseEntity.ok("La tienda que intenta eliminar, no existe!");		
		
	}
	
}
