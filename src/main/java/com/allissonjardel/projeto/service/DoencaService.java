package com.allissonjardel.projeto.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allissonjardel.projeto.model.Doenca;
import com.allissonjardel.projeto.repository.DoencaRepository;

@Service
public class DoencaService {

	@Autowired
	DoencaRepository repository;
	
	public List<Doenca> getAll(){
		return repository.findAll();
	}
	
	public List<Doenca> getDoencasByPaciente(Long id){
		return repository.getDoencasByPaciente(id);
	}
	
	public Doenca findById(Long id) {
		return repository.findById(id).get();
	}
	
	public Doenca insert(Doenca entity) {
		return repository.save(entity);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	public Doenca update(Long id, final Doenca obj) {
		Doenca objSave = repository.findById(id).get();			
		BeanUtils.copyProperties(obj, objSave, "id");
		return repository.save(objSave);
		
	}
	
}
