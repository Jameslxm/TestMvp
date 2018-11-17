package com.example.lxm.testmvp.common.constants;

public class HttpCode {

    public static int SERVICE_SUCCESS = 0;
    public static int SERVICE_FAIL = 500;
    public static int SERVICE_EMPTY = 404;


    public static int SERVICE_FAIL_JSON = 501;
    public static int SERVICE_FAIL_TIMEOUT = 502;
    public static int SERVICE_FAIL_CONNECTION = 503;


    public static int SERVICE_SUCCESS_LOAD_MORE = 2001;
    public static int SERVICE_FAIL_LOAD_MORE = 5001;
    public static int SERVICE_EMPTY_LOAD_MORE = 4001;


    public static int SERVICE_FAIL_JSON_LOAD_MORE = 5011;
    public static int SERVICE_FAIL_TIMEOUT_LOAD_MORE = 5021;
    public static int SERVICE_FAIL_CONNECTION_LOAD_MORE = 5031;

    public static boolean isRequest(String code){
        return "200".equals(code);
    }
}
