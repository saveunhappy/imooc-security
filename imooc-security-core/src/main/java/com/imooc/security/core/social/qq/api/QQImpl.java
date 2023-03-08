package com.imooc.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
                                                                    // access_token在父类中会处理，所以这里就不用传了。
//    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?access_token=YOUR_ACCESS_TOKEN&oauth_consumer_key=YOUR_APP_ID&openid=YOUR_OPENID";
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();


    public QQImpl(String accessToken, String appId) {
        //TokenStrategy.ACCESS_TOKEN_PARAMETER就是把access_token放到请求栏中，如果是默认的话，那就是放到请求头中去了。

        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        String url = String.format(URL_GET_OPENID, accessToken);
        // 文档上写的返回值是这样的：callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );

        String result = getRestTemplate().getForObject(url, String.class);

        System.out.println(result);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    /* (non-Javadoc)
     * @see com.imooc.security.core.social.qq.api.QQ#getUserInfo()
     */
    @Override
    public QQUserInfo getUserInfo() {

        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);

        System.out.println(result);

        QQUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }

}
