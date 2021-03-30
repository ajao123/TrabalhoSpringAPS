package com.allissonjardel.projeto.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allissonjardel.projeto.model.Paciente;
import com.allissonjardel.projeto.repository.PacienteRepository;


@Service
public class PacienteService {

	@Autowired
	PacienteRepository repository;
	
	public List<Paciente> getAll(){
		return repository.findAll();
	}
	
	public Paciente findById(Long id) {
		return repository.findById(id).get();
	}
	
	public Paciente findByLoginSenha(String login, String senha) {
		return repository.getByLoginSenha(login, senha);
	}
	
	public Paciente insert(Paciente entity) {
		return repository.save(entity);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	public Paciente update(Long id, final Paciente obj) {
		Paciente objSave = repository.findById(id).get();			
		BeanUtils.copyProperties(obj, objSave, "id","login","senha",
				"doencas", "observacoes", "consultas", "historicoPaciente");
		return repository.save(objSave);
		
	}
	
	public void ativar(Long id) {
		Paciente objSave = repository.findById(id).get();
		objSave.setStatus(true);
		repository.save(objSave);	
	}
	
	public void desativar(Long id) {
		Paciente objSave = repository.findById(id).get();
		objSave.setStatus(false);
		repository.save(objSave);	
	}
	
	public List<Paciente> getAtivos(){
		return repository.getByAtivos();
	}
	
}
