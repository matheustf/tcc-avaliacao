package com.puc.tcc.avaliacao.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.puc.tcc.avaliacao.consts.Constants;
import com.puc.tcc.avaliacao.dtos.AvaliacaoDTO;
import com.puc.tcc.avaliacao.exceptions.AvaliacaoException;
import com.puc.tcc.avaliacao.model.Avaliacao;
import com.puc.tcc.avaliacao.rabbitmq.RabbitMQComponent;
import com.puc.tcc.avaliacao.repository.AvaliacaoRepository;
import com.puc.tcc.avaliacao.utils.Util;

@Service
public class AvaliacaoServiceImpl implements AvaliacaoService {

	AvaliacaoRepository avaliacaoRepository;
	
	RabbitMQComponent rabbitMQComponent;

	@Autowired
	public AvaliacaoServiceImpl(AvaliacaoRepository avaliacaoRepository, RabbitMQComponent rabbitMQComponent) {
		this.avaliacaoRepository = avaliacaoRepository;
		this.rabbitMQComponent = rabbitMQComponent;
	}

	@Override
	public AvaliacaoDTO consultar(String id) throws AvaliacaoException {
		
		Optional<Avaliacao> optional = avaliacaoRepository.findById(id);
		Avaliacao avaliacao = validarAvaliacao(optional);
		
		AvaliacaoDTO avaliacaoDTO = modelMapper().map(avaliacao, AvaliacaoDTO.class);
		
		return avaliacaoDTO;
	}
	
	@Override
	public AvaliacaoDTO consultarPorIdCompra(String idCompra) throws AvaliacaoException {
		
		Optional<Avaliacao> optional = avaliacaoRepository.findByIdCompra(idCompra);
		Avaliacao avaliacao = validarAvaliacao(optional);
		
		AvaliacaoDTO avaliacaoDTO = modelMapper().map(avaliacao, AvaliacaoDTO.class);
		
		return avaliacaoDTO;
	}

	@Override
	public List<AvaliacaoDTO> buscarTodos() {

		List<Avaliacao> avaliacoes = (List<Avaliacao>) avaliacaoRepository.findAll();

		Type listType = new TypeToken<List<AvaliacaoDTO>>(){}.getType();
		List<AvaliacaoDTO> avaliacoesDTO = modelMapper().map(avaliacoes, listType);

		return avaliacoesDTO;
	}

	@Override
	public AvaliacaoDTO incluir(AvaliacaoDTO avaliacaoDTO, String token) throws AvaliacaoException {
		Avaliacao avaliacao = modelMapper().map(avaliacaoDTO, Avaliacao.class);
		
		verificarSeAvaliacaoJaExiste(avaliacao.getIdCompra());
		
		//avaliacao.setIdCliente(Util.getPagameterToken(token, "idCadastro"));
		avaliacao.setDataDaAvaliacao(Util.dataNow());
		avaliacao.setCodigoDaAvaliacao(Util.gerarCodigo("AVALIACAO",5).toUpperCase());
		
		avaliacaoRepository.save(avaliacao);
		rabbitMQComponent.sendAvaliacao(avaliacao);
		
		return modelMapper().map(avaliacao, AvaliacaoDTO.class);
	}

	private void verificarSeAvaliacaoJaExiste(String idCompra) throws AvaliacaoException {
		Optional<Avaliacao> optional = avaliacaoRepository.findByIdCompra(idCompra);
		if (optional.isPresent()) {
			throw new AvaliacaoException(HttpStatus.CONFLICT, Constants.AVALIACAO_EXISTENTE);
		}
	}

	@Override
	public AvaliacaoDTO atualizar(String idCompra, AvaliacaoDTO avaliacaoDTODetails) throws AvaliacaoException {
		
		Optional<Avaliacao> optional = avaliacaoRepository.findByIdCompra(idCompra);
		Avaliacao avaliacao = validarAvaliacao(optional);
		
		Avaliacao avaliacaoDetails = modelMapper().map(avaliacaoDTODetails, Avaliacao.class);

		avaliacaoDetails.setDataDaAvaliacao(Util.dataNow());
		
		avaliacao = avaliacao.update(avaliacao, avaliacaoDetails);
		avaliacaoRepository.save(avaliacao);

		AvaliacaoDTO avaliacaoDTO = modelMapper().map(avaliacao, AvaliacaoDTO.class);

		return avaliacaoDTO;
	}

	@Override
	public ResponseEntity<AvaliacaoDTO> deletar(String id) throws AvaliacaoException {
		
		Optional<Avaliacao> optional = avaliacaoRepository.findById(id);
		validarAvaliacao(optional);
		
		avaliacaoRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	private Avaliacao validarAvaliacao(Optional<Avaliacao> optional) throws AvaliacaoException {
		return Optional.ofNullable(optional).get()
		.orElseThrow(() -> new AvaliacaoException(HttpStatus.NOT_FOUND, Constants.ITEM_NOT_FOUND));
	}
	
	private List<Avaliacao> validarAvaliacaoList(Optional<List<Avaliacao>> optional) throws AvaliacaoException {
		return Optional.ofNullable(optional).get()
		.orElseThrow(() -> new AvaliacaoException(HttpStatus.NOT_FOUND, Constants.ITEM_NOT_FOUND));
	}

	@Override
	public List<AvaliacaoDTO> consultarPorCliente(String token) throws AvaliacaoException {
		String idCliente = Util.getPagameterToken(token, "idCadastro");

		Optional<List<Avaliacao>> optional = avaliacaoRepository.findByIdCliente(idCliente);
		List<Avaliacao> avaliacoes = validarAvaliacaoList(optional);
		
		Type listType = new TypeToken<List<AvaliacaoDTO>>(){}.getType();
		List<AvaliacaoDTO> avaliacoesDTO = modelMapper().map(avaliacoes, listType);

		return avaliacoesDTO;
	}
	
}
