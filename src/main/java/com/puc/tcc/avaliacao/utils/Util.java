package com.puc.tcc.avaliacao.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import org.springframework.http.HttpStatus;

import com.puc.tcc.avaliacao.consts.Constants;
import com.puc.tcc.avaliacao.exceptions.AvaliacaoException;

public class Util {

	public static String gerarCodigo(int qtdCaracteres) {
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		return myRandom.substring(0, qtdCaracteres);
	}

	public static String gerarCodigo(String inicio, int qtdCaracteres) {
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		return inicio + "-" + myRandom.substring(0, qtdCaracteres);
	}

	public static String retirarPrefixo(String codigo) {
		String[] partes = codigo.split("-");

		return partes[1];
	}

	public static String dataNow() {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
				.withLocale(new Locale("pt", "br"));
		return agora.format(formatador);

	}

	public static String getPagameterToken(String token, String tokenParameter) throws AvaliacaoException {
		try {
			System.out.println(token);
			String[] pieces = token.split("\\.");

			String header = new String(DatatypeConverter.parseBase64Binary(pieces[1]), "UTF-8");

			return getParameter(header, tokenParameter);

		} catch (Exception e) {
			throw new AvaliacaoException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.SERVER_ERROR);
		}
	}

	private static String getParameter(String header, String parameter) {
		//TODO REFACTOR
		header.indexOf(parameter);

		int initial = header.lastIndexOf(parameter) + parameter.length() + 3;

		header = header.substring(initial);
		int next = header.indexOf("\"");
		
		return header.substring(0,next);
	}

}
