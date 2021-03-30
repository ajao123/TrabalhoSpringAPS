package com.allissonjardel.projeto.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;

import com.allissonjardel.projeto.entities.enums.TipoSanguineo;
import com.allissonjardel.projeto.utils.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Paciente extends Usuario{

	@JsonView({Views.Paciente.class, Views.Consulta.class, Views.Medico.class})
	private Boolean status;
	
	@JsonView({Views.Paciente.class, Views.Consulta.class, Views.Medico.class})
	private String nome;
	
	@JsonView({Views.Paciente.class, Views.Consulta.class, Views.Medico.class})
	private String endereco;
	
	@JsonView({Views.Paciente.class, Views.Consulta.class, Views.Medico.class})
	private String telefone;
	
	@JsonView({Views.Paciente.class, Views.Consulta.class, Views.Medico.class})
	private Integer tipoSanguineo;
	
	@JsonView({Views.Paciente.class, Views.Consulta.class, Views.Medico.class})
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="paciente", cascade=CascadeType.REMOVE)	
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<Consulta> consultas;
	
	
	@JsonView({Views.Paciente.class, Views.Consulta.class, Views.Medico.class})
	@OneToMany(fetch=FetchType.EAGER, mappedBy="paciente", cascade=CascadeType.REMOVE)	
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<Doenca> doencas;
	
	@JsonView({Views.Paciente.class, Views.Consulta.class, Views.Medico.class})
	@Column
	@ElementCollection(targetClass=String.class)
	private List<String> observacoes;
	
	@JsonView({Views.Paciente.class, Views.Consulta.class, Views.Medico.class})
	@Column
	@ElementCollection(targetClass=String.class)
	private List<String> historicoPaciente;
	
	public Paciente() {
		this.status = true;
		observacoes = new ArrayList<>();
	}
	
	public Paciente(Long id) {
		this.status = true;
		this.setId(id);
		observacoes = new ArrayList<>();
	}

	public Paciente(String nome, String endereco, String telefone, TipoSanguineo tipoSanguineo, LocalDate date, List<Doenca> doencas, List<String> historicoPaciente, String login, String senha) {
		super(login, senha);
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		setTipoSanguineo(tipoSanguineo);
		this.date = date;
		this.doencas = doencas; 
		this.observacoes = new ArrayList<>();
		this.historicoPaciente = historicoPaciente;
		this.status = true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public TipoSanguineo getTipoSanguineo() {
		return TipoSanguineo.valueOf(tipoSanguineo);
	}

	public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo.getCode();
	}
	
	public List<Doenca> getDoencas() {
		return doencas;
	}

	public void setDoencas(List<Doenca> doencas) {
		this.doencas = doencas;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public List<String> getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(List<String> observacoes) {
		this.observacoes = observacoes;
	}
	
	public List<String> getHistoricoPaciente() {
		return historicoPaciente;
	}

	public void setHistoricoPaciente(List<String> historicoPaciente) {
		this.historicoPaciente = historicoPaciente;
	}

	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

}
