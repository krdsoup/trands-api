package kr.co.trands.controller.dto;

import lombok.Data;

@Data
public class JwtTokenDto {

	private String accountId;

	private Long expireTime;
}
