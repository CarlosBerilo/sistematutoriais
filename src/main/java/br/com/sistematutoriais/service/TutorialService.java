package br.com.sistematutoriais.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sistematutoriais.model.Tutorial;
import br.com.sistematutoriais.repository.TutorialRepository;

@Service
public class TutorialService {
	
	@Autowired
	private TutorialRepository tutorialRepository;
	
	public List<Tutorial> getAll() {
		return tutorialRepository.findAll();
	}
	
	public List<Tutorial> getAllByStatus(String status) {
		return tutorialRepository.findAllByStatus(status);
	}
	
	public Tutorial getById(Long id) {
		Optional<Tutorial> tutorial = tutorialRepository.findById(id);
		if(tutorial.isPresent()){
			return tutorial.get();
		}else {
			return null;
		}
	}
	
	public Tutorial add(Tutorial tutorial) {
		tutorial.setDtCriacao(LocalDateTime.now());
		tutorial.setDtAtualizacao(LocalDateTime.now());
		return tutorialRepository.save(tutorial);
	}
	
	public Tutorial update(Tutorial tutorial) {
		if(tutorial.getStatus().equals("PUBLICADO")) {
			tutorial.setDtPublicacao(LocalDateTime.now());
		}
		tutorial.setDtAtualizacao(LocalDateTime.now());
		return tutorialRepository.save(tutorial);
	}
	
	public void delete(Long id) {
		tutorialRepository.deleteById(id);
	}
	
	public Tutorial getByTitulo(String titulo) {
		return tutorialRepository.getByTitulo(titulo);
	}
	
}
