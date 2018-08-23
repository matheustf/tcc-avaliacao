package com.puc.tcc.avaliacao.dtos;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AvaliacaoDTO {

	private String id;

	private String codigoDaAvaliacao;
	
	private String idCliente;
	
	@NotNull()
	private String idPedido;
	
	@NotNull()
	private int notaDeSatisfacao;
	
	@NotNull()
	private String titulo;

	@NotNull()
	private String descricao;
	
	private String dataDaAvaliacao;
	
}