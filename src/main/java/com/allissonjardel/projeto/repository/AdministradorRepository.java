package com.allissonjardel.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.allissonjardel.projeto.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long>{

	@Query(value = "SELECT * FROM ADMINISTRADOR WHERE LOGIN = ?1 AND SENHA = ?2", nativeQuery = true)
	Administrador getByLoginSenha(String login, String senha);
	
}
