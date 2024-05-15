package kr.co.trands.util;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StringUtil {
	
	public static boolean isEmpty(String str) {
		if(str == null || "".equals(str)) {
			return true;
		}else {
			return false;
		}
	}
	
}
