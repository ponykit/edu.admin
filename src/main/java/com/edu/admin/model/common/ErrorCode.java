package com.edu.admin.model.common;

/**
* 에러코드
* 
* TODO
4004        Missing Required Query Parameter            필수 Query Parameter 누락           
4005        Unsupported Query Parameter         지원되지 않는 Query Parameter         
4007        Invalid Path Variable           유효하지 않는 Path Variable           
4008        Malformed Body Content          잘못된 형식의 Body 구조         
4009        Missing Required Content Node           필수 Content Node 누락          
4010        Invalid Content Node Value          유효하지 않은 Content Node 값          
4101        Invalid API Key         유효하지 않은 API Key 정보          
4102        Invalid Access Key(Token, Auth ID..)            유효하지 않은 인증 정보           
4152        Access Denied           요청에 대한 접근 거부            
4201        Requested URI Not Exists            존재하지 않는 요청 URI          
4202        Data Not Found          존재하지 않는 데이터         
4221        Unsupported HTTP Method         지원하지 않는 HTTP Method         
4241        Not Acceptable          지원하지 않는 Accept Content-Type         
4261        Request Timeout         요청 시간 초과            
4281        Data Already Exists         데이터가 이미 존재 함            
4301        No Content Length           Content-Length Header 누락            
4321        Request Too Large           요청 Body 최대 크기 초과            
4341        Unsupported Media Type          지원하지 않는 Content-Type Header         
4342        Invalid Character Set           유효하지 않은 Character Set           
5201        Service Unavailable         서비스 이용 불가           

 */
public enum ErrorCode {

    /** 필수 Header 누락  */
    MISSING_REQUIRED_HEADER(4001, "필수 Header 누락"),
    /** 지원되지 않는 Header */
    UNSUPPORTED_HEADER(4002, "지원되지 않는 Header"),
    /** 유효하지 않는 Header 값   */
    INVALID_HEADER_VALUE(4003, "유효하지 않는 Header 값"),
    /** 유효하지 않는 Query Parameter 값    */
    INVALID_QUERY_PARAMETER_VALUE(4006, "유효하지 않는 Query Parameter 값"),
    /** 유효하지 않은 접근 권한    */
    PERMISSION_DENIED(4151, "유효하지 않은 접근 권한"),
    /** 내부 처리 오류    */
    INTERNAL_SERVER_ERROR(5001, "내부 처리 오류"),

    MISSING_REQUIRED_QUERY_PARAMETER(4004, "필수 Query Parameter 누락"),
    PASSWORD_MISMATCH(4291, "비밀번호 불일치"),
    PASSWORD_MISMATCH_LIMIT_REACHED(4292, "비밀번호 실패 허용 횟수 초과"),
    ACCOUNT_SUSPEND(4293, "계정 임시 정지 상태"),
    /** 유효하지 않은 접근 권한    */
    DATA_NOT_FOUND(4202, "존재하지 않는 데이터"),
    DATA_ALREADY_EXISTS(4281, "데이터가 이미 존재 함"),
    GOODS_MNG_INVALID_ERROR(9998, "입실기간을 줄일 수 없습니다.")
    ;

    private int code;
    
    private String msg;

    ErrorCode(int code) {
        this.code = code;
    }
    
    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
}


