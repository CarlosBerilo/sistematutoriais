package br.com.sistematutoriais.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Tutorial implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String titulo;
	
	@Column
	private String descricao;
	
	@Column
	private String status;
	
	@Column
	private LocalDateTime dtCriacao;
	
	@Column
	private LocalDateTime dtPublicacao;
	
	@Column
	private LocalDateTime dtAtualizacao;

	public Tutorial() {
		super();
	}


	public Tutorial(String titulo, String descricao, String status) {
		super();
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
	}

	public Tutorial(Long id, String titulo, String descricao, String status) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
	}	
	
	public Tutorial(Long id, String titulo, String descricao, String status, LocalDateTime dtCriacao, LocalDateTime dtPublicacao, LocalDateTime dtAtualizacao) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
		this.dtCriacao = dtCriacao;
		this.dtPublicacao = dtPublicacao;
		this.dtAtualizacao = dtAtualizacao;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public LocalDateTime getDtCriacao() {
		return dtCriacao;
	}


	public void setDtCriacao(LocalDateTime dtCriacao) {
		this.dtCriacao = dtCriacao;
	}


	public LocalDateTime getDtPublicacao() {
		return dtPublicacao;
	}


	public void setDtPublicacao(LocalDateTime dtPublicacao) {
		this.dtPublicacao = dtPublicacao;
	}


	public LocalDateTime getDtAtualizacao() {
		return dtAtualizacao;
	}


	public void setDtAtualizacao(LocalDateTime dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}


	public String toString() {
		return "Tutorial [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", status=" + status
				+ ", dtCriacao=" + dtCriacao + ", dtPublicacao=" + dtPublicacao + ", dtAtualizacao=" + dtAtualizacao + "]";
	}
	
	
	

}
