package kr.co.trands.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "cm_sample")
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class Sample {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sample_id")
	private long sampleId;
	@Column(name = "sample_name")
	private String sampleName;
	@Column(name = "sample_number")
	private Integer sampleNumber;
	@Column(name = "created_at", insertable = false, updatable = false)
	private String createdAt;
	@Column(name = "creator_id")
	private long creatorId;
	@Column(name = "updated_at", insertable = false, updatable = false)
	private String updatedAt;
	@Column(name = "updater_id")
	private long updaterId;
	@Column(name = "use_yn", insertable = false)
	private String useYn;
	
	public long getId() {
		return sampleId;
	}
}
