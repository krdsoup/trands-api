package kr.co.trands.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		log.info("[{}] start", req.getRequestURI());
		// AWS 로깅 등 추가

		try {
			chain.doFilter(request, response);
		} finally {
			log.info("[{}] end", req.getRequestURI());
			// AWS 로깅 등 추가
		}
	}

}