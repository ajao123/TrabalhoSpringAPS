package com.allissonjardel.projeto.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Recepcionista extends Usuario{

	private String nome;

	private String endereco;

	private String telefone;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataNascimento;
	
	public Recepcionista() {
		// TODO Auto-generated constructor stub
	}

	public Recepcionista(String nome, String endereco, String telefone, LocalDate dataNascimento, String login, String senha) {
		super(login, senha);
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
}
