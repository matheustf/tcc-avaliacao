package com.puc.tcc.avaliacao.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Avaliacao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3588012881987809662L;

	private String id;
	
	@NotNull
	private String codigoDaAvaliacao;

	@NotNull
	private String idCliente;
	
	@NotNull
	private String idPedido;
	
	@NotNull
	private int notaDeSatisfacao;
	
	@NotNull
	private String titulo;

	@NotNull
	private String descricao;
	
	@NotNull
	private String dataDaAvaliacao;
	
	public Avaliacao update(Avaliacao avaliacao, Avaliacao detailsAvaliacao) {
		avaliacao.setTitulo(detailsAvaliacao.getTitulo());
		avaliacao.setNotaDeSatisfacao(avaliacao.getNotaDeSatisfacao());
		avaliacao.setTitulo(detailsAvaliacao.getDescricao());
		avaliacao.setDataDaAvaliacao(detailsAvaliacao.getDataDaAvaliacao());
		
		return avaliacao;
	}
	
}
