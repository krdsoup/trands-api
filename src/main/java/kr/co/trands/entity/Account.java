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
@Table(name = "ac_account")
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private long accountId;
	@Column(name = "email")
	private String email;
	@Column(name = "passwd")
	private String passwd;
	@Column(name = "temp_password_yn")
	private String tempPasswordYn;
	@Column(name = "password_modify_dt")
	private String passwordModifyDt;
	@Column(name = "auth_code")
	private String authCode;
	@Column(name = "name")
	private String name;
	@Column(name = "phone")
	private String phone;
	@Column(name = "profile_url")
	private String profileUrl;
	@Column(name = "term_agree_yn")
	private String termAgreeYn;
	@Column(name = "term_agree_dt")
	private String termAgreeDt;
	@Column(name = "login_fail_cnt")
	private int loginFailCnt;
	@Column(name = "status_code")
	private String statusCode;
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
		return accountId;
	}
}
