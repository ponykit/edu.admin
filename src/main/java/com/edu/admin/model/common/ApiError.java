/*
 * Interpark Tour Team, INTERPARK INC., SEOUL, KOREA
 * Copyright(c) 2018 by Interpark Inc.
 *
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of Interpark Inc.
*/
package com.edu.admin.model.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ApiError {
    
    /** 결과 상태(오류이므로 fail 고정 */
    private String status = "fail";
    
    /** 에러 메시지 */
    private ErrorMessage error;

    public ApiError(ErrorCode code) {
        error = new ErrorMessage(code, code.getMsg());
    }
    
    public ApiError(ErrorCode code, String message) {
        error = new ErrorMessage(code, message);
    }
    
}