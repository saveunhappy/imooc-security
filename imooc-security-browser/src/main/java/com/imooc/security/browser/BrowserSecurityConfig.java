package com.imooc.security.browser;

import com.imooc.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.httpBasic()
        http.formLogin()
                //这里原来是指定的html页面，也可以指定成对应的Controller
                .loginPage("/authentication/require")
                //这个是前端的那个表单提交的地址，然后SpringSecurity会拿到请求中带的username和password进行校验
                //原来是/login,现在既然换了，那么就是从这个中拿到了。
                .loginProcessingUrl("/authentication/form")
                .successHandler(imoocAuthenticationSuccessHandler)
                .failureHandler(imoocAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
//                .antMatchers("/imooc-signIn.html").permitAll()
                .antMatchers("/authentication/require",securityProperties.getBrowser().getLoginPage()).permitAll()

                .anyRequest()
                .authenticated()
        .and().csrf().disable();
    }


}
