package com.arukione.curriculum_design.utils;

public class HTTPStatus {
    public static int OK = 200; //资源获取成功
    public static int Login = 201; //登陆成功
    public static int Success = 204; //处理成功

    public static int Failed = 406;
    public static int Unauthorized = 401; //没有登陆
    public static int NotAllowed = 403; //没有权限

}
