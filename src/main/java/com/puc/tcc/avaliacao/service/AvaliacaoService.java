package com.puc.tcc.avaliacao.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.puc.tcc.avaliacao.dtos.AvaliacaoDTO;
import com.puc.tcc.avaliacao.exceptions.AvaliacaoException;

public interface AvaliacaoService {
	
	AvaliacaoDTO consultar(String id) throws AvaliacaoException;
	
	AvaliacaoDTO incluir(AvaliacaoDTO avaliacaoDTO, String token) throws AvaliacaoException;
	
	AvaliacaoDTO atualizar(String id, AvaliacaoDTO avaliacaoDTODetails) throws AvaliacaoException;
	
	ResponseEntity<AvaliacaoDTO> deletar(String id) throws AvaliacaoException;

	List<AvaliacaoDTO> buscarTodos();

	List<AvaliacaoDTO> consultarPorCliente(String token) throws AvaliacaoException;

}
