package com.allissonjardel.projeto.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allissonjardel.projeto.model.Consulta;
import com.allissonjardel.projeto.repository.ConsultaRepository;


@Service
public class ConsultaService {

	@Autowired
	ConsultaRepository repository;
	
	public List<Consulta> getAll(){
		return repository.findAll();
	}
	
	public List<Consulta> getByStatus(Integer statusConsulta){
		return repository.getByStatus(statusConsulta);
	}
	
	public List<Consulta> getByPaciente(Long id){
		return repository.getByPaciente(id);
	}
	
	public List<Consulta> getByPacienteMarcada(Long id){
		return repository.getByPacienteMarcada(id);
	}
	
	public List<Consulta> getByPacienteRealizada(Long id){
		return repository.getByPacienteRealizada(id);
	}
	
	public List<Consulta> getByPacienteIndeferida(Long id){
		return repository.getByPacienteIndeferida(id);
	}
	
	public List<Consulta> getByMedicoMarcada(Long id){
		return repository.getByMedicoMarcada(id);
	}
	
	public List<Consulta> getByMedicoRealizada(Long id){
		return repository.getByMedicoRealizada(id);
	}
	
	public List<Consulta> getByMedicoCancelada(Long id){
		return repository.getByMedicoCancelada(id);
	}
	
	public List<Consulta> getByMarcada(){
		return repository.getByMarcada();
	}
	
	public List<Consulta> getByRealizada(){
		return repository.getByRealizada();
	}
	
	public List<Consulta> getByCancelada(){
		return repository.getByCancelada();
	}
	
	public List<Consulta> getByIndeferida(){
		return repository.getByIndeferida();
	}
	
	
	public Consulta findById(Long id) {
		return repository.findById(id).get();
	}
	
	public Consulta insert(Consulta entity) {
		return repository.save(entity);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	public Consulta update(Long id, final Consulta obj) {
		Consulta objSave = repository.findById(id).get();			
		BeanUtils.copyProperties(obj, objSave, "id", "paciente", "medico", "observacoes");
		return repository.save(objSave);
		
	}
	
}
