package kr.co.trands.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import jakarta.validation.Valid;
import kr.co.trands.controller.dto.AccountDto;
import kr.co.trands.controller.dto.JwtTokenDto;
import kr.co.trands.entity.Account;
import kr.co.trands.service.AccountService;
import kr.co.trands.service.TokenService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {

	@Autowired
	AccountService accountService;
	
	@Autowired
	TokenService tokenService;

	@QueryMapping
	public AccountDto.ResLogin login(@Valid @Argument("req") AccountDto.Login req) throws Exception {
		log.info("LoginController login");
		log.info("email: {}, passwd: {}", req.getEmail(), req.getPasswd());
		
		Account account = accountService.getIdPwLogin(req);
		if(account == null) {
			return null;
		}
		
		JwtTokenDto token = new JwtTokenDto();
		token.setAccountId(String.valueOf(account.getAccountId()));
		
		try {
			AccountDto.TokenAccount accountToken = tokenService.loginCreateToken(token, false, null);
			AccountDto.ResLogin resLogin = new AccountDto.ResLogin();
			resLogin.setAccount(account);
			resLogin.setToken(accountToken);
			return resLogin;
		} catch (Exception e) {
			return null;
		}
	}

}
