package com.allissonjardel.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.allissonjardel.projeto.model.Doenca;


public interface DoencaRepository extends JpaRepository<Doenca, Long>{

	@Query(value = "SELECT * FROM DOENCA WHERE PACIENTE = ?1", nativeQuery = true)
	List<Doenca> getDoencasByPaciente(Long paciente);
}
