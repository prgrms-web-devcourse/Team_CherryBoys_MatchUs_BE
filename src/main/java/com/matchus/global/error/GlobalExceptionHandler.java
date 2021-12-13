package com.matchus.global.error;

import com.matchus.domains.common.exception.AgeGroupNotFoundException;
import com.matchus.domains.sports.exception.SportsNotFoundException;
import com.matchus.domains.team.exception.GradeNotFoundException;
import com.matchus.domains.user.exception.RoleNotFoundException;
import com.matchus.domains.user.exception.UserNotFoundException;
import com.matchus.global.error.exception.BusinessException;
import com.matchus.global.error.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.error("handleEntityNotFoundException", e);
		ErrorResponse errorResponse = ErrorResponse.of(
			ErrorCode.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
		MethodArgumentNotValidException e
	) {
		ErrorResponse errorResponse = ErrorResponse.of(
			ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(
		{
			SportsNotFoundException.class,
			UserNotFoundException.class,
			RoleNotFoundException.class,
		}
	)
	public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException exception) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AgeGroupNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAgeGroupNotFound(BusinessException exception) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.AGEGROUP_NOT_FOUND);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(GradeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleGradeNotFound(BusinessException exception) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.GRADE_NOT_FOUND);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
