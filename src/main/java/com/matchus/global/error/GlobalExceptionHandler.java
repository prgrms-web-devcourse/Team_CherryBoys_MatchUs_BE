package com.matchus.global.error;

import com.matchus.domains.common.exception.AgeGroupNotFoundException;
import com.matchus.domains.hire.exception.HireApplicationNotFoundException;
import com.matchus.domains.match.exception.TeamWaitingNotFoundException;
import com.matchus.domains.sports.exception.SportsNotFoundException;
import com.matchus.domains.team.exception.GradeNotFoundException;
import com.matchus.domains.team.exception.TeamInvitationAlreadyExistsException;
import com.matchus.domains.team.exception.TeamNotFoundException;
import com.matchus.domains.team.exception.TeamUserNotFoundException;
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
			HireApplicationNotFoundException.class,
			TeamNotFoundException.class,
			TeamUserNotFoundException.class,
			TeamWaitingNotFoundException.class,
		}
	)
	public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException e) {
		log.error("handleEntityNotFoundException", e);
		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AgeGroupNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAgeGroupNotFound(BusinessException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(GradeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleGradeNotFound(BusinessException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TeamInvitationAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleAlreadyExists(BusinessException e) {
		ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
