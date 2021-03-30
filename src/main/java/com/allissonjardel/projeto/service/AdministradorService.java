package com.allissonjardel.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allissonjardel.projeto.model.Administrador;
import com.allissonjardel.projeto.repository.AdministradorRepository;


@Service
public class AdministradorService {

	@Autowired
	AdministradorRepository repository;
	
	
	
	public Administrador findByLoginSenha(String login, String senha) {
		return repository.getByLoginSenha(login, senha);
	}
	
	public Administrador findById(Long id) {
		return repository.getOne(id);
	}
	
	public void insert() {
		repository.save(new Administrador("admin", "admin"));
	}
	
	
	
}
