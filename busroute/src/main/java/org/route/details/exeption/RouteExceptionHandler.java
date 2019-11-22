package org.route.details.exeption;

import org.route.details.constants.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "org.route.details")
public class RouteExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<Error> handleNotFoundException(Exception e, WebRequest request) {
		Error error = ((NotFoundException) e).getError();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.header(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(error);
	}

	@ExceptionHandler(ApplicationException.class)
	public final ResponseEntity<Error> handleApplicationException(Exception e, WebRequest request) {
		Error error = ((ApplicationException) e).getError();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.header(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(error);
	}

	@ExceptionHandler(SystemException.class)
	public final ResponseEntity<Error> handleSystemException(Exception e, WebRequest request) {
		Error error = ((SystemException) e).getError();
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				.header(Constants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(error);
	}
}
