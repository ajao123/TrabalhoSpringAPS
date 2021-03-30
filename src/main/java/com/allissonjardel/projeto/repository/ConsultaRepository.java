package com.allissonjardel.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.allissonjardel.projeto.model.Consulta;



public interface ConsultaRepository extends JpaRepository<Consulta, Long>{

	@Query(value = "SELECT * FROM CONSULTA WHERE STATUS_CONSULTA = ?1", nativeQuery = true)
	List<Consulta> getByStatus(Integer statusConsulta);
	
	@Query(value = "SELECT * FROM CONSULTA WHERE PACIENTE = ?1 AND STATUS_CONSULTA != 3", nativeQuery = true)
	List<Consulta> getByPaciente(Long id);
	
	@Query(value = "SELECT * FROM CONSULTA WHERE PACIENTE = ?1 AND STATUS_CONSULTA = 1", nativeQuery = true)
	List<Consulta> getByPacienteMarcada(Long id);
	
	@Query(value = "SELECT * FROM CONSULTA WHERE PACIENTE = ?1 AND STATUS_CONSULTA = 2", nativeQuery = true)
	List<Consulta> getByPacienteRealizada(Long id);
	
	@Query(value = "SELECT * FROM CONSULTA WHERE PACIENTE = ?1 AND STATUS_CONSULTA = 4", nativeQuery = true)
	List<Consulta> getByPacienteIndeferida(Long id);
	
	@Query(value = "SELECT * FROM CONSULTA WHERE MEDICO = ?1 AND STATUS_CONSULTA = 1", nativeQuery = true)
	List<Consulta> getByMedicoMarcada(Long id);
	
	@Query(value = "SELECT * FROM CONSULTA WHERE MEDICO = ?1 AND STATUS_CONSULTA = 2", nativeQuery = true)
	List<Consulta> getByMedicoRealizada(Long id);
	
	@Query(value = "SELECT * FROM CONSULTA WHERE MEDICO = ?1 AND STATUS_CONSULTA = 3", nativeQuery = true)
	List<Consulta> getByMedicoCancelada(Long id);
	
	
	@Query(value = "SELECT * FROM CONSULTA WHERE STATUS_CONSULTA = 1", nativeQuery = true)
	List<Consulta> getByMarcada();
	
	@Query(value = "SELECT * FROM CONSULTA WHERE STATUS_CONSULTA = 2", nativeQuery = true)
	List<Consulta> getByRealizada();
	
	@Query(value = "SELECT * FROM CONSULTA WHERE STATUS_CONSULTA = 3", nativeQuery = true)
	List<Consulta> getByCancelada();
	
	@Query(value = "SELECT * FROM CONSULTA WHERE STATUS_CONSULTA = 4", nativeQuery = true)
	List<Consulta> getByIndeferida();
	
}
