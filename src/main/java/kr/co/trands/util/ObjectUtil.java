package kr.co.trands.util;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ObjectUtil {
	
	public static boolean isEmpty(Object obj) {
		if(obj == null 
				|| (obj instanceof List && ((List)obj).size() == 0)) {
			return true;
		}else {
			return false;
		}
	}
	
}
