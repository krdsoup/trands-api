package kr.co.trands.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import kr.co.trands.controller.dto.CommonDto;
import kr.co.trands.controller.dto.CommonDto.BadRequestBody;
import kr.co.trands.controller.dto.CommonDto.CommonBody;
import jakarta.security.auth.message.AuthException;

@ControllerAdvice
@RestController
public class ErrorResponse {

	@ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonDto.BadRequestBody processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        Map<String, String> map = new HashMap<String, String>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
        	map.put(fieldError.getField(), fieldError.getCode());
        }

        return new CommonDto.BadRequestBody(CommonDto.BAD_REQUEST, CommonDto.BAD_REQUEST_MSG, map);
    }
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonDto.CommonBody processValidationError(HttpMessageNotReadableException exception) {
        return new CommonDto.CommonBody(CommonDto.BAD_REQUEST, CommonDto.BAD_REQUEST_MSG);
    }
	
	@ExceptionHandler(AuthException.class)
    public CommonDto.CommonBody processValidationError(AuthException exception) {
        return new CommonDto.CommonBody(CommonDto.AUTH_ERROR, CommonDto.AUTH_ERROR_MSG);
    }
	
	@ExceptionHandler(SQLException.class)
    public CommonDto.CommonBody sqlError(SQLException exception) {
        return new CommonDto.CommonBody(CommonDto.SQL_ERROR, CommonDto.SQL_ERROR_MSG);
    }

	@ExceptionHandler(Exception.class)
    public CommonDto.CommonBody serverError(Exception exception) {
        return new CommonDto.CommonBody(CommonDto.SERVER_ERROR, CommonDto.SERVER_ERROR_MSG);
    }

}
