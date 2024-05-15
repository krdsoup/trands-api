package kr.co.trands.controller.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.co.trands.util.DateUtil;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class SampleDto {

	@Data
	public static class UserLoginSample {
		@NotEmpty
		private String accountId;
	}
	
	@Data
    public static class SearchSample {
    	@NotEmpty
        private String sampleName;
    	@Min(value = 1)
        private int sampleNumber;
    	@NotNull
    	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME_FORMAT)
        private String startTime;
    	@NotNull
    	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME_FORMAT)
        private String endTime;
    	@Min(value = 1)
    	private int pageNum = 1;
    	@Min(value = 1)
    	private int pageSize = 10;
    }

    @Data
    public static class RegistSample {
    	private long sampleId;
    	@NotEmpty
        private String sampleName;
    	@Min(value = 1)
        private int sampleNumber;
    }
}
