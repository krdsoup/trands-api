package kr.co.trands.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CommonDto {

	// success
    public static final int SUCCESS = 1000; // 성공
    public static final String SUCCESS_MSG = "success";

    public static final int NO_DATA = 1001; // 조회 된 데이타가 없음
    public static final String NO_DATA_MSG = "no data";

    public static final int NO_REGISTER_DEVICE = 1002; // 등록된 디바이스가 없음
    public static final String NO_REGISTER_DEVICE_MSG = "no register device";

    public static final int WRONG_DEVICE = 1003; // 잘못된 디바이스가 없음
    public static final String WRONG_DEVICE_MSG = "wrong device";

    public static final int ALREADY_CMD = 1004; // 이미 커맨드 요청이 있음.
    public static final String ALREADY_CMD_MSG = "already command";

    public static final int RESULT_NONE_VIDEO_REPORT = 1006; // 이미 커맨드 요청이 있음.
    public static final String RESULT_NONE_VIDEO_REPORT_MSG = "none video report";

    // fail
    public static final int FAIL = 9000; // 실패
    public static final String FAIL_MSG = "fail";
    
    public static final int BAD_REQUEST = 9001; // 잘못된 요청
    public static final String BAD_REQUEST_MSG = "bad request"; // Error Response 별도 생성
    
    public static final int INVALID_ACCESS = 9002; // 유효하지 않은 AccessToken 으로 요청
    public static final String INVALID_ACCESS_MSG = "invalid access";
    
    public static final int SQL_ERROR = 9004; // 쿼리 오류
    public static final String SQL_ERROR_MSG = "sql error";
    
    public static final int AUTH_ERROR = 9005; // 토큰 오류
    public static final String AUTH_ERROR_MSG = "UNAUTHORIZED";
    
    public static final int SERVER_ERROR = 9999; // 앱 서버 에러
    public static final String SERVER_ERROR_MSG = "server error";

    @Data
    @AllArgsConstructor
    public static class BadRequestBody {
        private int code;
        private String message;
        private Object param;
    }
    
    @Data
    @AllArgsConstructor
    public static class CommonBody {
        private int code;
        private String message;
    }
    
    @Data
    @AllArgsConstructor
    public static class ResultBody {
        private int code;
        private String message;
        private Object result;
    }
    
    @Data
    @AllArgsConstructor
    public static class ResultListBody {
        private int code;
        private String message;
        private ResultList result;
    }
    
    @Data
    public static class ResultList {
    	private int size;
    	private int totalPage;
        private Object list;
        
        public ResultList(int size, int pageSize, Object list) {
        	this.size = size;
        	this.totalPage = (int) Math.ceil(size*1.0f/pageSize);
        	this.list = list;
        }
    }

}
