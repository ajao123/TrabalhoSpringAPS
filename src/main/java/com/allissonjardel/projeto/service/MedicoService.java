package com.allissonjardel.projeto.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allissonjardel.projeto.model.Medico;
import com.allissonjardel.projeto.repository.MedicoRepository;


@Service
public class MedicoService {

	@Autowired
	MedicoRepository repository;
	
	public List<Medico> getAll(){
		return repository.findAll();
	}
	
	public List<Medico> getByEspecialidade(String especialidade){
		return repository.getByEspecialidade(especialidade);
	}
	
	public Medico findById(Long id) {
		return repository.findById(id).get();
	}
	
	public Medico insert(Medico entity) {
		return repository.save(entity);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	public Medico update(Long id, final Medico obj) {
		Medico objSave = repository.findById(id).get();			
		BeanUtils.copyProperties(obj, objSave, "id", "login","senha", "consultas");
		return repository.save(objSave);
		
	}
	
	public void ativar(Long id) {
		Medico objSave = repository.findById(id).get();
		objSave.setStatus(true);
		repository.save(objSave);	
	}
	
	public Medico findByLoginSenha(String login, String senha) {
		return repository.getByLoginSenha(login, senha);
	}
	
	public void desativar(Long id) {
		Medico objSave = repository.findById(id).get();
		objSave.setStatus(false);
		repository.save(objSave);	
	}
	
	public List<Medico> getAtivos(){
		return repository.getByAtivos();
	}
	
	
}
