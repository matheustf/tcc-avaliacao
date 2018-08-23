package com.puc.tcc.avaliacao.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Compra implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1880890978192714607L;

	@Id
	private Long id;
	
	private String codigoDoProduto;
	
	private BigDecimal valorDaCompra;
	
	private int quantidade;
	
	public Compra update(Compra compra, Compra detailsCompra) {
		compra.setCodigoDoProduto(detailsCompra.getCodigoDoProduto());
		compra.setQuantidade(detailsCompra.getQuantidade());
		
		return compra;
	}
	

}
