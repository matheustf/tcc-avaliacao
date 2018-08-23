package com.puc.tcc.avaliacao.dtos;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.puc.tcc.avaliacao.consts.Constants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {
	
	private Integer code = HttpStatus.OK.value();
	
	private String returnMessage = Constants.SUCESSFULLY;
	
	private LocalDateTime requestDateTime = LocalDateTime.now();
	
	public BaseDTO(Integer code,String returnMessage){
		this.code = code;
		this.returnMessage = returnMessage;
	}
}