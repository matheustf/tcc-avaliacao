package com.puc.tcc.avaliacao.rabbitmq;

import com.puc.tcc.avaliacao.model.Avaliacao;

public interface RabbitMQComponent {

	void sendAvaliacao(Avaliacao avaliacao);

}
