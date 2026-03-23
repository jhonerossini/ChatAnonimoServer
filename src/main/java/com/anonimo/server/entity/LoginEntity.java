package com.anonimo.server.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "login", schema = "chatanonimo")
public class LoginEntity extends PanacheEntityBase {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(length = 45)
    public String login;

    @Column(length = 255)
    public String senha;
    
    @Column(length = 45)
    public String role;

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
