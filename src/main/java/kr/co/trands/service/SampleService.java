package kr.co.trands.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import kr.co.trands.constants.SampleConstants;
import kr.co.trands.controller.dto.CommonDto;
import kr.co.trands.controller.dto.SampleDto;
import kr.co.trands.dsl.SampleDSL;
import kr.co.trands.entity.Sample;
import kr.co.trands.mapper.SampleMapper;
import kr.co.trands.repository.SampleRepository;
import kr.co.trands.util.AwsUtil;

@Service
public class SampleService {

	@Autowired
	private SampleRepository sampleRepository;

	@Autowired
	private SampleDSL sampleDSL;
	
	@Autowired
	private SampleMapper sampleMapper;
	
	@Autowired
	private AwsUtil awsUtil;

	public Sample getSample(long id) {
		Optional<Sample> sampleOpt = sampleRepository.findById(id);
		if (sampleOpt.isPresent()) {
			return sampleOpt.get();
		} else {
			return null;
		}
	}

	public CommonDto.ResultList getSampleList(SampleDto.SearchSample req) {
		
		//Mapper Sample
//		int count = 
		sampleMapper.selectSampleCount(req);
//		List<Sample> list = 
		sampleMapper.selectSampleList(req);
		
		//DSL Sample
		int count = sampleDSL.selectSampleCount(req);
		List<Sample> list = sampleDSL.selectSampleList(req);
		
		return new CommonDto.ResultList(count, req.getPageSize(), list);
//		return new CommonDto.ResultList(0, req.getPageSize(), new ArrayList());
	}

	public Sample saveSample(SampleDto.RegistSample req) {
		Optional<Sample> sampleOpt = sampleRepository.findById(req.getSampleId());
		Sample sample;
		if (sampleOpt.isPresent()) {
			sample = sampleOpt.get();
		} else {
			sample = new Sample();
		}
		sample.setSampleName(req.getSampleName());
		sample.setSampleNumber(req.getSampleNumber());
		return sampleRepository.save(sample);
	}

	public Sample deleteSample(long id) {
		Optional<Sample> sampleOpt = sampleRepository.findById(id);
		if (sampleOpt.isPresent()) {
			sampleOpt.get().setUseYn(SampleConstants.USE_YN_N);
			sampleRepository.save(sampleOpt.get());
			return sampleOpt.get();
		} else {
			return null;
		}
	}
	
	public List<S3ObjectSummary> getSampleFileList() {
		return awsUtil.getS3ObjectSummaryList("plkconnected", "faceid/2024/04/22/", ".json");
	}
}
