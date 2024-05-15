package kr.co.trands.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import kr.co.trands.constants.Constants;
import kr.co.trands.controller.dto.AccountDto;
import kr.co.trands.controller.dto.JwtTokenDto;
import kr.co.trands.util.JWTUtil;
import kr.co.trands.util.StringUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenService {

	@Autowired
	private RedisTemplate<String, String> redis;

	@Autowired
	private JWTUtil jwtUtil;

	@Value("${app.token.policy}")
	private String tokenPolicy;

	public AccountDto.TokenAccount loginCreateToken(JwtTokenDto token, boolean autoLogin, String device) throws Exception {

		token.setExpireTime(Constants.ACCESS_EXP_TIME);
		String accessToken = jwtUtil.createJwtToken(token);

		long refreshExpiredTime = Constants.REFRESH_EXP_TIME;
		if(autoLogin) {
			refreshExpiredTime = Constants.AUTO_REFRESH_EXP_TIME;
		}
		token.setExpireTime(refreshExpiredTime);
		String refreshToken = jwtUtil.createJwtToken(token);

		AccountDto.TokenAccount tokenAccount = new AccountDto.TokenAccount();
		tokenAccount.setAccessToken(accessToken);
		tokenAccount.setRefreshToken(refreshToken);

		// token 정책별 redis 저장
		redis.opsForValue().set(getAccessTokenKey(token.getAccountId(), accessToken, device)
				, accessToken, Duration.ofMillis(Constants.ACCESS_EXP_TIME));

		redis.opsForValue().set(getRefreshTokenKey(token.getAccountId(), refreshToken, device)
				, refreshToken, Duration.ofMillis(refreshExpiredTime));

		return tokenAccount;
	}

	public AccountDto.TokenAccount tokenCheck(String accessToken, String refreshToken, String device) throws Exception {

		log.info("token check");

		AccountDto.TokenAccount tokenAccount = new AccountDto.TokenAccount();
		tokenAccount.setAccessToken(accessToken);
		tokenAccount.setRefreshToken(refreshToken);

		//접근 토큰 없음: 비정상
		if(StringUtil.isEmpty(accessToken)) {
			throw new Exception("access token empty");
		}

		//접근 토큰 오류
		JwtTokenDto accessTokenAccount;
		try {
			accessTokenAccount = jwtUtil.parseJwtToken(accessToken);

			//redis 확인 시 없음: 만료
			String accessTokenRedis = redis.opsForValue().get(
					getAccessTokenKey(accessTokenAccount.getAccountId(), accessToken, device));

			if(StringUtil.isEmpty(accessTokenRedis) || !accessToken.equals(accessTokenRedis)) {
				log.info("expired access token");
			}else {
				return tokenAccount;
			}
		} catch ( ExpiredJwtException eje ) {
			log.info("expired access token");
		} catch ( JwtException je ) {
			//접근 토큰 파싱 오류: 비정상
			throw new Exception("access token parse error");
		}

		//접근 토큰 만료
		//로그인 토큰 없음: 비정상
		if(StringUtil.isEmpty(refreshToken)) {
			throw new Exception("refresh token empty");
		}

		//로그인 토큰 오류
		JwtTokenDto refreshTokenAccount;
		try {
			refreshTokenAccount = jwtUtil.parseJwtToken(refreshToken);

			//redis 확인 시 없음: 만료
			String refreshTokenRedis = redis.opsForValue().get(
					getRefreshTokenKey(refreshTokenAccount.getAccountId(), refreshToken, device));
			if(StringUtil.isEmpty(refreshTokenRedis) || !refreshToken.equals(refreshTokenRedis)) {
				throw new Exception("refresh token expired");
			} else {
				//로그인 토큰 정상, 신규 accessToken 발급
				refreshTokenAccount.setExpireTime(Constants.ACCESS_EXP_TIME);
				tokenAccount.setAccessToken(jwtUtil.createJwtToken(refreshTokenAccount));
				redis.opsForValue().set(getAccessTokenKey(refreshTokenAccount.getAccountId(), tokenAccount.getAccessToken(), device)
						, tokenAccount.getAccessToken(), Duration.ofMillis(Constants.ACCESS_EXP_TIME));
				tokenAccount.setNewAccessToken(true);
				return tokenAccount;
			}

		} catch ( ExpiredJwtException eje ) {
			throw new Exception("refresh token expired");
		} catch ( JwtException je ) {
			//접근 토큰 파싱 오류: 비정상
			throw new Exception("refresh token parse error");
		}
	}

	private String getAccessTokenKey(String accountId, String accessToken, String device) {
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.ACCESS_TOKEN_REDIS_KEY);
		sb.append(":" + accountId);
		if(Constants.TOKEN_POLICY_MULTI.equals(tokenPolicy)) {
			sb.append(":" + accessToken);
		} else if(Constants.TOKEN_POLICY_PC_MOBILE.equals(tokenPolicy)) {
			if(Constants.TOKEN_POLICY_MOBILE_KEY.equals(device)) {
				sb.append(":" + Constants.TOKEN_POLICY_MOBILE_KEY);
			}else {
				sb.append(":" + Constants.TOKEN_POLICY_PC_KEY);
			}
		}
		return sb.toString();
	}

	private String getRefreshTokenKey(String accountId, String refreshToken, String device) {
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.REFRESH_TOKEN_REDIS_KEY);
		sb.append(":" + accountId);
		if(Constants.TOKEN_POLICY_MULTI.equals(tokenPolicy)) {
			sb.append(":" + refreshToken);
		} else if(Constants.TOKEN_POLICY_PC_MOBILE.equals(tokenPolicy)) {
			if(Constants.TOKEN_POLICY_MOBILE_KEY.equals(device)) {
				sb.append(":" + Constants.TOKEN_POLICY_MOBILE_KEY);
			}else {
				sb.append(":" + Constants.TOKEN_POLICY_PC_KEY);
			}
		}
		return sb.toString();
	}

}
