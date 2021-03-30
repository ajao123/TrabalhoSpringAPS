package com.allissonjardel.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.allissonjardel.projeto.model.Recepcionista;


public interface RecepcionistaRepository extends JpaRepository<Recepcionista, Long>{

	@Query(value = "SELECT * FROM RECEPCIONISTA WHERE LOGIN = ?1 AND SENHA = ?2", nativeQuery = true)
	Recepcionista getByLoginSenha(String login, String senha);
	
}
