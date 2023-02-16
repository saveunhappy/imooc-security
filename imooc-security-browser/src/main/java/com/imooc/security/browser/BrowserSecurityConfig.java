package com.imooc.security.browser;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
//           http.httpBasic()就是弹出来一个框，让你输入账号密码。
        //   http.formLogin()就是一个登陆页面，不指定的话就是项目自带的一个登陆页面。
//        http.httpBasic()
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);


        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                //这里原来是指定的html页面，也可以指定成对应的Controller
                .loginPage("/authentication/require")
                //这个是前端的那个表单提交的地址，然后SpringSecurity会拿到请求中带的username和password进行校验
                //原来是/login,现在既然换了，那么就是从这个中拿到了，而且这个原来是请求的/login请求，现在换成这个就是这个
                //由UsernamePasswordAuthenticationFilter来接收username和password了。
                .loginProcessingUrl("/authentication/form")
                .successHandler(imoocAuthenticationSuccessHandler)
                .failureHandler(imoocAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
//                .antMatchers("/imooc-signIn.html").permitAll()
                .antMatchers("/authentication/require",
                        securityProperties.getBrowser().getLoginPage()
                ,"/code/image").permitAll()

                .anyRequest()
                .authenticated()
        .and().csrf().disable();
    }


}
