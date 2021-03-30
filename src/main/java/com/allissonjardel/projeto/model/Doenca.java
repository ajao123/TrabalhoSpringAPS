package com.allissonjardel.projeto.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.allissonjardel.projeto.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Doenca implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonView({Views.Doenca.class, Views.Paciente.class, Views.Consulta.class})
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonView({Views.Doenca.class, Views.Paciente.class, Views.Consulta.class})
	@Column
	private String nome;
	
	@JsonView({Views.Doenca.class, Views.Paciente.class, Views.Consulta.class})
	@Column
	@ElementCollection(targetClass=String.class)
	private List<String> observacoes;
	
	@JsonView({Views.Doenca.class, Views.Paciente.class, Views.Consulta.class})
	@Column
	@ElementCollection(targetClass=String.class)
	private List<String> sintomas;
	
	@JsonView({Views.Doenca.class, Views.Paciente.class, Views.Consulta.class})
	private String estagio;
	
	@JsonView({Views.Doenca.class, Views.Paciente.class, Views.Consulta.class})
	@ManyToOne
	@JoinColumn(name="paciente")
	private Paciente paciente;
	
	public Doenca() {
		// TODO Auto-generated constructor stub
	}

	public Doenca(Long id, String nome, List<String> observacoes, List<String> sintomas, String estagio, Paciente paciente) {
		super();
		this.id = id;
		this.observacoes = observacoes;
		this.sintomas = sintomas;
		this.estagio = estagio;
		this.paciente = paciente;
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(List<String> observacoes) {
		this.observacoes = observacoes;
	}

	public List<String> getSintomas() {
		return sintomas;
	}

	public void setSintomas(List<String> sintomas) {
		this.sintomas = sintomas;
	}

	public String getEstagio() {
		return estagio;
	}

	public void setEstagio(String estagio) {
		this.estagio = estagio;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
