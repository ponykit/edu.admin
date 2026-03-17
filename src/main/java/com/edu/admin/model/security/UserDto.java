package com.edu.admin.model.security;


import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long adminSeq;
    private String admId;
    private String admPwd;
    private String admName;
    private String admEmail;
    private Integer pwdFailCnt;
    private String regDt;

    private List<Authority> authorities = new ArrayList<>();
}
