package com.allissonjardel.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.allissonjardel.projeto.model.Medico;



public interface MedicoRepository extends JpaRepository<Medico, Long>{
	
	@Query(value = "SELECT * FROM MEDICO WHERE ESPECIALIDADE = ?1", nativeQuery = true)
	List<Medico> getByEspecialidade(String especialidade);
	
	@Query(value = "SELECT * FROM MEDICO WHERE LOGIN = ?1 AND SENHA = ?2", nativeQuery = true)
	Medico getByLoginSenha(String login, String senha);
	
	@Query(value = "SELECT * FROM MEDICO WHERE STATUS = TRUE", nativeQuery = true)
	List<Medico> getByAtivos();
	
}
