package com.asu.exception.handlers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 
 * @author ashutosh 
 * This class is for representing API errors and it has enough fields
 * to hold relevant information about errors that happen during REST calls.
 * this will be common error format in json that our application will generate
 *
 */
@Data
public class ApiError {

	/**
	 * This property holds the operation call status. 
	 * It will be anything from 4xx to signalize client errors
	 *  or 5xx to mean server errors example - 400 means BAD_REQUEST
	 */
	 private HttpStatus status;

	 /**
	  * java8 date and time format
	  * property holds the date-time instance of when the error happened.
	  */
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	 private LocalDateTime timestamp;

	 /**
	  * This property holds a user-friendly message about the error.
	  */
	 private String message;
	 
	 /**
	  * This property holds a system message describing the error in more detail.
	  */
	 private String debugMessage;
	 
	 /**
	  * subErrors property holds an array of sub-errors that happened.
	  * This is used for representing multiple errors in a single call.
	  * An example would be validation errors in which multiple fields
	  * have failed the validation. The ApiSubError class is used to encapsulate those.
	  */
	  private List<ApiSubError> subErrors;
	  
	  private ApiError() {
	       timestamp = LocalDateTime.now();
	   }
	  
	  ApiError(HttpStatus status) {
	       this();
	       this.status = status;
	   }
	  ApiError(HttpStatus status, Throwable ex) {
	       this();
	       this.status = status;
	       this.message = "Unexpected error";
	       this.debugMessage = ex.getLocalizedMessage();
	   }
	  ApiError(HttpStatus status, String message, Throwable ex) {
	       this();
	       this.status = status;
	       this.message = message;
	       this.debugMessage = ex.getLocalizedMessage();
	   }
}
