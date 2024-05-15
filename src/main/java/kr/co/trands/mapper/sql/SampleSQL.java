package kr.co.trands.mapper.sql;

import org.apache.ibatis.jdbc.SQL;

import kr.co.trands.controller.dto.SampleDto;
import kr.co.trands.util.StringUtil;

public class SampleSQL {
	
	public String selectSampleCount(SampleDto.SearchSample req) {
        SQL sql = new SQL();
        
        sql.SELECT("count(*) ");
        sql.FROM("cm_sample");
        if(!StringUtil.isEmpty(req.getSampleName())) {
        	sql.WHERE("sample_name = #{sampleName}");
        }
        if(req.getSampleNumber() > 0) {
        	sql.WHERE("sample_number = #{sampleNumber}");
        }
        if(req.getStartTime() != null && req.getEndTime() != null) {
        	sql.WHERE("created_at between #{startTime} and #{endTime}");
        }
        sql.WHERE("use_yn = 'Y'");

        return sql.toString();
    }

    public String selectSampleList(SampleDto.SearchSample req) {
        SQL sql = new SQL();
        
        sql.SELECT("* ");
        sql.FROM("cm_sample");
        if(!StringUtil.isEmpty(req.getSampleName())) {
        	sql.WHERE("sample_name = #{sampleName}");
        }
        if(req.getSampleNumber() > 0) {
        	sql.WHERE("sample_number = #{sampleNumber}");
        }
        if(req.getStartTime() != null && req.getEndTime() != null) {
        	sql.WHERE("created_at between #{startTime} and #{endTime}");
        }
        sql.WHERE("use_yn = 'Y'");
        sql.ORDER_BY("sample_id DESC");
        sql.OFFSET((req.getPageNum() - 1)*req.getPageSize());
        sql.LIMIT(req.getPageSize());

        return sql.toString();
    }

}
