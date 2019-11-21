package com.asu.exception.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author ashutosh
 * ApiValidationError is a class that extends ApiSubError and 
 * expresses validation problems encountered during the REST call.
 */
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class ApiValidationError extends ApiSubError {

	private String object;
	private String field;
	private Object rejectedValue;
	private String message;
	ApiValidationError(String object, String message) {
	       this.object = object;
	       this.message = message;
	   }
}
