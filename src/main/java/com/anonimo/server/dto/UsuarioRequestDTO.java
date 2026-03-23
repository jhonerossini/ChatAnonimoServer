package com.anonimo.server.dto;

import java.time.LocalDateTime;

public class UsuarioRequestDTO{
	public String login;
	public String senha;
	
	public String cpf;
    public String nome;
    public String sobrenome;
    public String status;
    public String nick;
    public String mensagem;
    public String statusGlobal;
    public LocalDateTime dataCriacao;
    public LocalDateTime dataAtualizacao;
    public String usuarioCriacao;
    public String usuarioAtualizacao;
    
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	public String getUsuarioCriacao() {
		return usuarioCriacao;
	}
	public void setUsuarioCriacao(String usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	public String getUsuarioAtualizacao() {
		return usuarioAtualizacao;
	}
	public void setUsuarioAtualizacao(String usuarioAtualizacao) {
		this.usuarioAtualizacao = usuarioAtualizacao;
	}
	public String getStatusGlobal() {
		return statusGlobal;
	}
	public void setStatusGlobal(String statusGlobal) {
		this.statusGlobal = statusGlobal;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
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
}
