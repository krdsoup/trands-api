package kr.co.trands.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import kr.co.trands.controller.dto.AccountDto;
import kr.co.trands.controller.dto.CommonDto;
import kr.co.trands.controller.dto.SampleDto;
import kr.co.trands.entity.Sample;
import kr.co.trands.service.SampleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/sample")
@Slf4j
public class SampleController {

	@Autowired
	SampleService sampleService;

	@GetMapping("")
	public CommonDto.ResultBody getSample(@Valid @RequestAttribute(name = "LoginAccount") AccountDto.TokenAccount account, @RequestParam(name = "id", required = true) long id) {
		log.info("SampleController getSample");
		log.info(account.getAccessToken());
		Sample sample = sampleService.getSample(id);
		if(sample == null) {
			return new CommonDto.ResultBody(CommonDto.NO_DATA, CommonDto.NO_DATA_MSG, null);
		}else {
			return new CommonDto.ResultBody(CommonDto.SUCCESS, CommonDto.SUCCESS_MSG, sample);
		}
	}

	@PostMapping("/list")
	public CommonDto.ResultListBody getSampleList(@Valid @RequestBody SampleDto.SearchSample req) {
		log.info("SampleController getSampleList");
		CommonDto.ResultList resultList = sampleService.getSampleList(req);
		if(resultList.getSize() == 0) {
			return new CommonDto.ResultListBody(CommonDto.NO_DATA, CommonDto.NO_DATA_MSG,
					new CommonDto.ResultList(0, 1, new ArrayList<>()));
		}else {
			return new CommonDto.ResultListBody(CommonDto.SUCCESS, CommonDto.SUCCESS_MSG, resultList);
		}
	}

	@PostMapping("/save")
	public CommonDto.ResultBody saveSample(@Valid @RequestBody SampleDto.RegistSample req) {
		log.info("SampleController saveSample");
		Sample sample = sampleService.saveSample(req);
//		return new CommonDto.CommonBody(CommonDto.SUCCESS, CommonDto.SUCCESS_MSG);
		return new CommonDto.ResultBody(CommonDto.SUCCESS, CommonDto.SUCCESS_MSG, sample.getId());
	}

	@PostMapping("/delete")
	public CommonDto.CommonBody deleteSample(@RequestParam(name = "id", required = true) long id) {
		log.info("SampleController deleteSample");
		Sample sample = sampleService.deleteSample(id);
		if(sample == null) {
			return new CommonDto.CommonBody(CommonDto.FAIL, CommonDto.FAIL_MSG);
		}else {
			return new CommonDto.CommonBody(CommonDto.SUCCESS, CommonDto.SUCCESS_MSG);
		}
	}

	@PostMapping("/file/list")
	public CommonDto.ResultBody getSampleFileList() {
		log.info("SampleController getSampleFileList");
		List<S3ObjectSummary> s3SampleFileList = sampleService.getSampleFileList();
		if(s3SampleFileList.size() == 0) {
			return new CommonDto.ResultBody(CommonDto.NO_DATA, CommonDto.NO_DATA_MSG,
					new CommonDto.ResultList(0, 1, new ArrayList<>()));
		}else {
			return new CommonDto.ResultBody(CommonDto.SUCCESS, CommonDto.SUCCESS_MSG, s3SampleFileList);
		}
	}

}
