package com.anonimo.server.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario", schema = "chatanonimo")
public class UsuarioEntity extends PanacheEntityBase {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "id_login")
	public LoginEntity loginEntity; 

    @Column(length = 11)
    public String cpf;

    @Column(length = 255)
    public String nome;
    
    @Column(length = 45)
    public String sobrenome;
    
    @Column(length = 2)
    public String status;
    
    @Column(length = 45)
    public String nick;
    
    @Column(length = 255)
    public String mensagem;
    
    @Column(name = "status_global", length = 2, updatable = false)
    public String statusGlobal;

    // Gerencia a data de criação automaticamente e impede que seja alterada em updates
    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    public LocalDateTime dataCriacao;

    // Atualiza a data automaticamente sempre que o registro for modificado
    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    public LocalDateTime dataAtualizacao;

    @Column(name = "usuario_criacao", length = 50)
    public String usuarioCriacao;

    @Column(name = "usuario_atualizacao", length = 50)
    public String usuarioAtualizacao;

}
