package com.allissonjardel.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.allissonjardel.projeto.model.Paciente;


public interface PacienteRepository extends JpaRepository<Paciente, Long>{

	@Query(value = "SELECT * FROM PACIENTE WHERE LOGIN = ?1 AND SENHA = ?2", nativeQuery = true)
	Paciente getByLoginSenha(String login, String senha);
	
	@Query(value = "SELECT * FROM PACIENTE WHERE STATUS = TRUE", nativeQuery = true)
	List<Paciente> getByAtivos();
	
}
