package kr.co.trands.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Controller;

import kr.co.trands.controller.dto.AccountDto;
import kr.co.trands.service.TokenService;
import kr.co.trands.util.StringUtil;
import graphql.GraphQLError;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@Controller
@EnableRedisRepositories
public class GqlInterceptor implements WebGraphQlInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {

        // graphql 대상 토큰 확인
        if("/graphql".equals(request.getUri().getPath())){

            // exclude operationName
            List<String> excludeOperationNameList = new ArrayList<String>(){
                {
                    add("UserLogin");
                    add("FindUserPassword");
                }
            };

            // token check 제외 대상 확인
            log.info("GQL operation : " + request.getOperationName());
            if(!StringUtil.isEmpty(request.getOperationName())
                    && excludeOperationNameList.contains(request.getOperationName())){
                //token check 제외
                log.info("exclude operationName : {}", request.getOperationName());
                return chain.next(request);
            }
            // 테스트 소스: Postman 등 테스트 용 token check 제외 대상 확인
            else {
                for(String excludeUri : excludeOperationNameList){
                    if(request.getDocument().contains(excludeUri)){
                        log.info("exclude document operationName : {}", excludeUri);
                        return chain.next(request);
                    }
                }
            }

            // access token 추출
            String authorization = request.getHeaders().getFirst("Authorization");
            if(StringUtil.isEmpty(authorization)){
                return chain.next(request).map(response -> {
                    return graphqlResponse(response, ErrorType.UNAUTHORIZED);
                });
            }
            String accessToken = authorization.replace("Bearer ", ""); // Bearer access token
            String refreshToken = request.getHeaders().getFirst("refresh_token"); // refresh token

            log.info("token : " + accessToken + " / " + refreshToken);
            try {
                AccountDto.TokenAccount account = tokenService.tokenCheck(accessToken, refreshToken, null);
                request.configureExecutionInput((executionInput, builder) ->
                        builder.graphQLContext(Collections.singletonMap("LoginAccount", account)).build());
                if(account.isNewAccessToken()) {
                    return chain.next(request).map(response -> {
                        response.getResponseHeaders().add("access_token", account.getAccessToken());
                        return response;
                    });
                }
            } catch (Exception e) {
                return chain.next(request).map(response -> {
                    return graphqlResponse(response, ErrorType.UNAUTHORIZED);
                });
            }
        }
        return chain.next(request);
    }

    private WebGraphQlResponse graphqlResponse(WebGraphQlResponse response, ErrorType type) {
        List<GraphQLError> errors = response.getErrors().stream()
                .map(error -> {
                    // error 재설정
                    return GraphQLError.newError()
                            .errorType(type)
                            .message(getMessage(type)).build();
                })
                .toList();
        return response.transform(builder -> builder.errors(errors).build());
    }

    private String getMessage(ErrorType type){
        if(ErrorType.UNAUTHORIZED.equals(type)){
            return ErrorType.UNAUTHORIZED.name();
        }else{
            return ErrorType.INTERNAL_ERROR.name();
        }
    }

}