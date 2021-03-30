package com.allissonjardel.projeto.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.allissonjardel.projeto.entities.enums.StatusConsulta;
import com.allissonjardel.projeto.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table
public class Consulta implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonView({Views.Medico.class, Views.Consulta.class, Views.Paciente.class})
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView({Views.Medico.class, Views.Consulta.class, Views.Paciente.class})
	private LocalDateTime data;	
	
	@JsonView({Views.Medico.class, Views.Consulta.class, Views.Paciente.class})
	private Integer statusConsulta;
	
	@JsonView({Views.Medico.class, Views.Consulta.class, Views.Paciente.class})
	@Column
	@ElementCollection(targetClass=String.class)
	private List<String> observacoes;
	
	@JsonView({Views.Medico.class, Views.Consulta.class})
	@ManyToOne
	@JoinColumn(name="paciente")
	private Paciente paciente;
	
	@JsonView({Views.Consulta.class, Views.Paciente.class})
	@ManyToOne
	@JoinColumn(name="medico")
	private Medico medico;
	
	public Consulta() {
		observacoes = new ArrayList<>();
	}

	public Consulta(LocalDateTime data, StatusConsulta statusConsulta, Paciente paciente,
			Medico medico) {
		super();
		this.data = data;
		setStatusConsulta(statusConsulta);
		this.observacoes = new ArrayList<>();
		this.paciente = paciente;
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public StatusConsulta getStatusConsulta() {
		return StatusConsulta.valueOf(statusConsulta);
	}

	public void setStatusConsulta(StatusConsulta statusConsulta) {
		this.statusConsulta = statusConsulta.getCode();
	}

	public List<String> getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(List<String> observacoes) {
		this.observacoes = observacoes;
	}

}
