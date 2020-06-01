package br.com.sistematutoriais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistematutoriais.model.Tutorial;
import br.com.sistematutoriais.service.TutorialService;

@RestController
@RequestMapping(path = "/api")
public class TutorialController {
	
	@Autowired
	TutorialService tutorialService;
	
	@GetMapping("/tutorials")
	public List<Tutorial> getAll(){
		return tutorialService.getAll();
	}
	
	@PostMapping("/tutorials")
	public Tutorial getAll(@RequestBody Tutorial tutorial) {
		return tutorialService.add(tutorial);
	}
	
	@GetMapping("/tutorials/{id}")
	public Tutorial getById(@PathVariable("id")Long id) {
		return tutorialService.getById(id);
	}

	
	@GetMapping("/tutorials/status/{status}")
	public List<Tutorial> getAllByStatus(@PathVariable("status") String status) {
		return tutorialService.getAllByStatus(status);
	}
	
	@GetMapping("/tutorials/titulo/{titulo}")
	public Tutorial getByTitulo(@PathVariable("titulo") String titulo) {
		return tutorialService.getByTitulo(titulo);
	}
	
	@PutMapping("/tutorials/{id}")
	public Tutorial update(@PathVariable("id")Long id,@RequestBody Tutorial tutorial) {
		return tutorialService.update(tutorial);
	}
	
	@DeleteMapping("/tutorials/{id}")
	public void delete(@PathVariable("id")Long id) {
		tutorialService.delete(id);
	}
	

	
}
