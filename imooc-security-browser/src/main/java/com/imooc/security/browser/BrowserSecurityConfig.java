package com.imooc.security.browser;

import com.imooc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.SmsCodeFilter;
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
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//           http.httpBasic()就是弹出来一个框，让你输入账号密码。
        //   http.formLogin()就是一个登陆页面，不指定的话就是项目自带的一个登陆页面。
//        http.httpBasic()
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        //为什么不需要设置成功的处理器？因为自定义的过滤器成功之后，直接就走了，不是必须的
        //当然，如果你是需要设置一个成功之后的处理，那也行，只是这里没有涉及到而已。
        validateCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();

        http    //这两个过滤器都是去校验验证码的，一个是图形验证码，一个是短信验证码，这两个过了才去校验用户名和密码
                //所以是FilterBefore，
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                //这里原来是指定的html页面，也可以指定成对应的Controller
                .loginPage("/authentication/require")
                //这个是前端的那个表单提交的地址，然后SpringSecurity会拿到请求中带的username和password进行校验
                //原来是/login,现在既然换了，那么就是从这个中拿到了，而且这个原来是请求的/login请求，现在换成这个就是这个
                //由UsernamePasswordAuthenticationFilter来接收username和password了。
                //这个loginProcessingUrl对应的就是UsernamePasswordAuthenticationFilter
                .loginProcessingUrl("/authentication/form")
                .successHandler(imoocAuthenticationSuccessHandler)
                .failureHandler(imoocAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
//                .antMatchers("/imooc-signIn.html").permitAll()
                .antMatchers("/authentication/require",
                        securityProperties.getBrowser().getLoginPage()
                        , "/code/*").permitAll()

                .anyRequest()
                .authenticated()
                .and().csrf().disable()
                //这个是校验手机号的，别弄混了。
                .apply(smsCodeAuthenticationSecurityConfig);
    }


}
