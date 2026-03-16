package com.pokercircle;

public class DaoException extends Exception {
    public DaoException(String errMsg, Exception ex) {
        super(errMsg, ex);
    }
}
