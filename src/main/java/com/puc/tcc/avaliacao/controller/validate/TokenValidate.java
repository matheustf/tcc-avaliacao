package com.puc.tcc.avaliacao.controller.validate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.puc.tcc.avaliacao.consts.Constants;
import com.puc.tcc.avaliacao.exceptions.AvaliacaoException;
import com.puc.tcc.avaliacao.utils.Util;

@Component
public class TokenValidate {

	public void tokenValidate(String token) throws AvaliacaoException {
		String idCadastro = Util.getPagameterToken(token, "idCadastro");

		if (StringUtils.isBlank(idCadastro)) {
			throw new AvaliacaoException(HttpStatus.UNAUTHORIZED, Constants.UNAUTHORIZED);
		}

	}
	
	public void tokenSimpleValidate(String token) throws AvaliacaoException {
		if (StringUtils.isBlank(token)) {
			throw new AvaliacaoException(HttpStatus.UNAUTHORIZED, Constants.UNAUTHORIZED);
		}
	}

	public void tokenValidateCliente(String token, String idCliente) throws AvaliacaoException {
		String idCadastro = Util.getPagameterToken(token, "idCadastro");

		if (!isTokenCorreto(idCadastro, idCliente)) {
			throw new AvaliacaoException(HttpStatus.UNAUTHORIZED, Constants.UNAUTHORIZED);
		}

	}

	private boolean isTokenCorreto(String idCadastro, String idCliente) {
		return StringUtils.isNotBlank(idCadastro) && idCadastro.equals(idCliente);
	}

}
