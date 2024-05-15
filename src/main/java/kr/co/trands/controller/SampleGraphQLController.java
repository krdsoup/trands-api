package kr.co.trands.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import kr.co.trands.controller.dto.AccountDto;
import kr.co.trands.controller.dto.CommonDto;
import kr.co.trands.controller.dto.JwtTokenDto;
import kr.co.trands.controller.dto.SampleDto;
import kr.co.trands.entity.Sample;
import kr.co.trands.service.SampleService;
import kr.co.trands.service.TokenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SampleGraphQLController {

	@Autowired
	TokenService tokenService;

	@Autowired
	SampleService sampleService;

	@QueryMapping
	public Sample getSample(@Valid @ContextValue("LoginAccount") AccountDto.TokenAccount account, @Argument("id") long id) throws Exception {
		log.info("SampleGraphQLController getSample");
//		if(true) {
//			throw new AuthException();
//		}
		Sample sample = sampleService.getSample(id);
		log.info(account.getAccessToken());
		return sample;
	}

	@QueryMapping
	public CommonDto.ResultList getSampleList(@Valid @Argument("req") SampleDto.SearchSample req) {
		log.info("SampleGraphQLController getSampleList");
		CommonDto.ResultList resultList = sampleService.getSampleList(req);
		return resultList;
	}

	@MutationMapping
	public AccountDto.TokenAccount userLogin(@Valid @Argument("req") SampleDto.UserLoginSample req) {
		log.info("SampleGraphQLController userLogin");
		JwtTokenDto token = new JwtTokenDto();
		token.setAccountId(req.getAccountId());

		try {
			return tokenService.loginCreateToken(token, false, null);
		} catch (Exception e) {
			return null;
		}
	}

}
