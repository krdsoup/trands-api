package kr.co.trands.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trands.constants.Constants;
import kr.co.trands.controller.dto.AccountDto;
import kr.co.trands.entity.Account;
import kr.co.trands.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public Account getIdPwLogin(AccountDto.Login req) {
		
		//passwd 암호화
		String passwd = req.getPasswd();
		
		Optional<Account> accountOpt = accountRepository.findByEmailAndPasswdAndUseYn(req.getEmail(), passwd, Constants.USE_YN_Y);
		if (accountOpt.isPresent()) {
			Account account = accountOpt.get();
			if("N".equals(account.getTermAgreeYn())) {
				//미동의
			}
			else if("N".equals(account.getTempPasswordYn())) {
				//비밀번호 초기화 대상
			}
			return accountOpt.get();
		} else {
			return null;
		}
	}

}
