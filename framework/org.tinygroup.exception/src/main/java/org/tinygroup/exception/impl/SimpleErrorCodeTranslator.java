package org.tinygroup.exception.impl;

import org.tinygroup.exception.Error;
import org.tinygroup.exception.ErrorCodeTranslator;

public class SimpleErrorCodeTranslator implements ErrorCodeTranslator {


    public String translate(Error error) {
        return error.getErrorMsg();
    }

    public String getErrorCode(Error error) {
        return error.getErrorCode().toString();
    }

}
