package com.allissonjardel.projeto.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;

import com.allissonjardel.projeto.utils.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Medico extends Usuario{

	@JsonView({Views.Paciente.class, Views.Consulta.class, Views.Medico.class})
	private Boolean status;
	
	@JsonView({Views.Medico.class, Views.Consulta.class, Views.Paciente.class})
	private String nome;
	@JsonView({Views.Medico.class, Views.Consulta.class, Views.Paciente.class})
	private Integer crm;
	@JsonView({Views.Medico.class, Views.Consulta.class, Views.Paciente.class})
	private String endereco;
	@JsonView({Views.Medico.class, Views.Consulta.class, Views.Paciente.class})
	private String telefone;
	@JsonView({Views.Medico.class, Views.Consulta.class, Views.Paciente.class})
	private String especialidade;
	@JsonView({Views.Medico.class, Views.Consulta.class, Views.Paciente.class})
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataNascimento;
	
	@JsonView({Views.Paciente.class, Views.Medico.class})
	@OneToMany(fetch=FetchType.EAGER, mappedBy="medico", cascade=CascadeType.REMOVE)	
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	private List<Consulta> consultas;
	
	public Medico() {
		this.status = true;
	}
	
	public Medico(Long id) {
		this.setId(id);
		this.status = true;
	}

	public Medico(String nome, Integer crm, String endereco, String telefone, String especialidade,
			LocalDate dataNascimento, String login, String senha) {
		super(login, senha);
		this.nome = nome;
		this.crm = crm;
		this.endereco = endereco;
		this.telefone = telefone;
		this.especialidade = especialidade;
		this.dataNascimento = dataNascimento;
		this.status = true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCrm() {
		return crm;
	}

	public void setCrm(Integer crm) {
		this.crm = crm;
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

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
}
