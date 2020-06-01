package br.com.sistematutoriais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sistematutoriais.model.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long>{
	Tutorial getByTitulo(String titulo);
	List<Tutorial> findAllByStatus(String status);
}
