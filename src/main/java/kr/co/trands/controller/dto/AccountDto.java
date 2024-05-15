package kr.co.trands.controller.dto;

import kr.co.trands.entity.Account;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

public class AccountDto {

	@Data
	public static class TokenAccount {
		private String accessToken;
		private String refreshToken;
		private boolean newAccessToken;
	}
	
	@Data
	public static class Login {
		@NotEmpty
		private String email;
		@NotEmpty
		private String passwd;
	}
	
	@Data
	public static class TokenLogin {
		@NotEmpty
		private String accessToken;
		@NotEmpty
		private String refreshToken;
	}
	
	@Data
	public static class ResLogin {
		private Account account;
		private TokenAccount token;
	}
}
