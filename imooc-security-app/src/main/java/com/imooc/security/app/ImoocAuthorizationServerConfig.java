package com.imooc.security.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class ImoocAuthorizationServerConfig {
//    http://localhost:8060/oauth/authorize?response_type=code&client_id=2a28a81d-24a1-42d8-a838-23e815f2cf6b&redirect_uri=http://example.com&scope=all
}
