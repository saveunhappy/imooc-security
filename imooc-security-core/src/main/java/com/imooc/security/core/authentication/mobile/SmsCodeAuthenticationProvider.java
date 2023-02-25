package com.imooc.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        /**
         * SmsCodeAuthenticationToken extends AbstractAuthenticationToken,然后就实现了Authentication
         * 所以这个authentication就是SmsCodeAuthenticationToken
         *
         *             mobile = mobile.trim();
         *             SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
         *             this.setDetails(request, authRequest);
         *             return this.getAuthenticationManager().authenticate(authRequest);
         */


        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        //这里的authenticationToken.getPrincipal()就是SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);传进来的mobile
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if(user == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        //看 AbstractUserDetailsAuthenticationProvider
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user,user.getAuthorities());
        //之前在filter的时候有一个this.setDetails(request, authRequest);这里就是再把那个设置进去的details给放到这个新
        //创建的Token中，把原始的Details给保存起来。

        // Ensure we return the original credentials the user supplied,
        // so subsequent attempts are successful even with encoded passwords.
        // Also ensure we return the original getDetails(), so that future
        // authentication events after cache expiry contain the details

        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
