package kr.co.trands.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import kr.co.trands.controller.dto.SampleDto;
import kr.co.trands.entity.Sample;
import kr.co.trands.mapper.sql.SampleSQL;

@Mapper
public interface SampleMapper {
	
	@SelectProvider(type = SampleSQL.class, method = "selectSampleCount")
    public int selectSampleCount(SampleDto.SearchSample req);

    @SelectProvider(type = SampleSQL.class, method = "selectSampleList")
    @Results(value = {
            @Result(property = "sampleId", column = "sample_id"),
            @Result(property = "sampleName", column = "sample_name"),
            @Result(property = "sampleNumber", column = "sample_number"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "creatorId", column = "creator_id"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "updaterId", column = "updater_id"),
            @Result(property = "useYn", column = "use_yn")
    })
    public List<Sample> selectSampleList(SampleDto.SearchSample req);

}
