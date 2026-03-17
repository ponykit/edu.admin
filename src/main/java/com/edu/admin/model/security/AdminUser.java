package com.edu.admin.model.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.ZonedDateTime;
import java.util.Collection;

@Getter
@EqualsAndHashCode(of="memberNo", callSuper=false)
@ToString
public class AdminUser extends User {
	
	private static final long serialVersionUID = 1L;
	
	private Long memberNo;
	private String memberId;
	private String memberName;

	@Setter
	private String admEmail;
	@Setter
	private Integer pwdFailCnt;
	@Setter
	private ZonedDateTime regDt;

	public AdminUser(String memberId, Long memberNo, String memberName, String password, Collection<? extends GrantedAuthority> authorities) {
		super(memberId, password, authorities);
	
		this.memberId = memberId;
		this.memberNo = memberNo;
		this.memberName = memberName;
	}
}
