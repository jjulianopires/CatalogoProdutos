package com.api.products.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestControllerAdvice
public class ValidationErrorHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus (code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler (MethodArgumentNotValidException.class)
	public JsonNode handle(MethodArgumentNotValidException exception){
		JsonNode jsonNode = null;
		String message = exception.getBindingResult().getFieldError().getDefaultMessage();
		String json = "{ \"status_code\" : "+HttpStatus.BAD_REQUEST.value()+", \"message\" : \""+message+"\" }";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jsonNode = objectMapper.readTree(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonNode;
	}
	
	
	
}
