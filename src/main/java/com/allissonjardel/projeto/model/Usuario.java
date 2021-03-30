package com.allissonjardel.projeto.model;

import java.io.Serializable;

import javax.persistence.*;

import com.allissonjardel.projeto.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;

@MappedSuperclass
public abstract class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonView({Views.Paciente.class, Views.Medico.class})
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String login;
	@Column(unique=true)
	private String senha;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}
