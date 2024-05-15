package kr.co.trands.dsl;

import static kr.co.trands.entity.QSample.sample;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.trands.controller.dto.SampleDto;
import kr.co.trands.entity.Sample;
import kr.co.trands.util.StringUtil;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SampleDSL {
	
	private final JPAQueryFactory queryFactory;
	
	private BooleanBuilder selectSampleWhere(SampleDto.SearchSample req) {
		BooleanBuilder builder = new BooleanBuilder()
				.and(sample.sampleName.eq(!StringUtil.isEmpty(req.getSampleName()) ? req.getSampleName() : null))
				.and(sample.sampleNumber.eq(req.getSampleNumber() > 0 ? req.getSampleNumber() : null))
				.and(sample.createdAt.between(
						req.getStartTime() != null ? req.getStartTime().toString() : null, 
						req.getEndTime() != null ? req.getEndTime().toString() : null));
		return builder;
	}
	
	public int selectSampleCount(SampleDto.SearchSample req){
        return queryFactory
        		.select(sample.count())
        		.from(sample)
        		.where(selectSampleWhere(req))
                .fetchFirst().intValue();
    }
	
	public List<Sample> selectSampleList(SampleDto.SearchSample req){
        return queryFactory
        		.selectFrom(sample)
        		.where(selectSampleWhere(req))
        		.offset((req.getPageNum() - 1)*req.getPageSize())
        		.limit(req.getPageSize())
                .fetch();
    }
	
}
