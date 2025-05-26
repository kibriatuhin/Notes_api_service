package com.notes_api_service.utils;

public class Constants {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static final String MOBILE_REGEX = "^(?:\\+880|01)[3-9]\\d{8}$";
    public static final String ROLE_ADMIN = "hasRole('ADMIN')";
    public static final String ROLE_USER = "hasRole('USER')";
    public static final String ROLE_ADMIN_USER = "hasAnyRole('USER','ADMIN')";
    public static final String DEFAULT_PAGE_NO = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
}
