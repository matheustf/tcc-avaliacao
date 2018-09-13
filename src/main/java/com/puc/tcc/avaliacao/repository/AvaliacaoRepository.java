package com.puc.tcc.avaliacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.puc.tcc.avaliacao.model.Avaliacao;


@Repository
public interface AvaliacaoRepository extends MongoRepository<Avaliacao, String> {

	Optional<List<Avaliacao>> findByIdCliente(String idCliente);

	Optional<Avaliacao> findByIdCompra(String idCompra);

}
