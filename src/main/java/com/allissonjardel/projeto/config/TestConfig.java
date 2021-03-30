package com.allissonjardel.projeto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.allissonjardel.projeto.model.Administrador;
import com.allissonjardel.projeto.repository.AdministradorRepository;


@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	private AdministradorRepository administradorRepository;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		if(administradorRepository.findAll().isEmpty()) {
			administradorRepository.save(new Administrador("admin", "admin"));
		}
	}
	
	
	
}
