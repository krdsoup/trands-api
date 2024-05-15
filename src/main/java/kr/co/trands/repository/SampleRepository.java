package kr.co.trands.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.trands.entity.Sample;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {
    List<Sample> findBySampleName(String sampleName);
}
