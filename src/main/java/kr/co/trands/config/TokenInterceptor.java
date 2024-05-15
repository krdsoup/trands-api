package kr.co.trands.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.trands.controller.dto.AccountDto;
import kr.co.trands.service.TokenService;
import kr.co.trands.util.StringUtil;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // get header token
        // redis access token 체크


        // access token 정상: 정상 동작
        // user 정보 조회하여 user setting
//    	DeviceInfo device = new DeviceInfo();
//    	device.setDriverId("1");
//    	device.setDriverName("홍길동");
//    	request.setAttribute("tokenUser", device);

        log.info(request.getHeader("Authorization"));
        
        // access token 추출
        String authorization = request.getHeader("Authorization");
        if(StringUtil.isEmpty(authorization)){
        	throw new AuthException();
        }
        String accessToken = authorization.replace("Bearer ", ""); // Bearer access token
        String refreshToken = request.getHeader("refresh_token"); // refresh token

        try {
        	AccountDto.TokenAccount account = tokenService.tokenCheck(accessToken, refreshToken, null);
        	request.setAttribute("LoginAccount", account);
        	if(account.isNewAccessToken()) {
        		response.addHeader("access_token", account.getAccessToken());
        	}
        }catch(Exception e) {
        	throw new AuthException();
        }

        log.info("Token pre handler");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("Token post handler");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}