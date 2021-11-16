package com.secommon.separtners.infra.advice.exceptions;

import com.secommon.separtners.infra.commons.enums.ErrorMessage;

public class ExpiredTokenException extends RuntimeException {

    public ExpiredTokenException() {
        super( ErrorMessage.VALIDATE_REFRESH_TOKEN.getMessage() );
    }
    public ExpiredTokenException ( String s ) {
        super(s);
    }

}
