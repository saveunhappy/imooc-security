package com.imooc.security.core.social.qq.api;
//这个就是服务提供商的API，是用来返回用户信息的，最起码得有一个返回用户信息的方法
public interface QQ {

    QQUserInfo getUserInfo();
}
