package com.allissonjardel.projeto.model;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Administrador extends Usuario{

	public Administrador() {

	}
	
	public Administrador(String login, String senha) {
		super(login, senha);
	}



}
