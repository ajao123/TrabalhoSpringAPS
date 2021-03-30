package com.allissonjardel.projeto.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allissonjardel.projeto.model.Recepcionista;
import com.allissonjardel.projeto.repository.RecepcionistaRepository;


@Service
public class RecepcionistaService {

	@Autowired
	RecepcionistaRepository repository;
	
	public List<Recepcionista> getAll(){
		return repository.findAll();
	}
	
	public Recepcionista findById(Long id) {
		return repository.findById(id).get();
	}
	
	public void insert(Recepcionista entity) {
		repository.save(entity);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	public Recepcionista update(Long id, final Recepcionista obj) {
		Recepcionista objSave = repository.findById(id).get();			
		BeanUtils.copyProperties(obj, objSave, "id","login","senha");
		return repository.save(objSave);
		
	}
	
	public Recepcionista findByLoginSenha(String login, String senha) {
		return repository.getByLoginSenha(login, senha);
	}
	
}
