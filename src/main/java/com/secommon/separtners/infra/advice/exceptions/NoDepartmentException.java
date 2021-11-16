package com.secommon.separtners.infra.advice.exceptions;

/**
 * 미등록 부서 예외처리
 *
 */
public class NoDepartmentException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1291658484551919953L;

    public NoDepartmentException () {super("존재하는 부서가 없습니다.");}
    public NoDepartmentException ( String msg, Throwable t) {
        super(msg, t);
    }
     
    public NoDepartmentException ( String msg) {
        super(msg);
    }


}
