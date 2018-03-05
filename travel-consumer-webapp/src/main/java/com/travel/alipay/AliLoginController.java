package com.travel.alipay;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.travel.weixin.WXConstant;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;

/**
 * 微信登录、微信支付接口
 * @author Administrator
 *
 */
@Controller
public class AliLoginController {
	
//	@Autowired
//	private RedisService redisService;
	
	/**
	 * 微信——公众号登录
	 * @return
	 */
	@RequestMapping("/aliLogin")
	public String wxLogin(){	
		
		StringBuilder sb = new StringBuilder();
		sb.append("https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?");
		sb.append("app_id=").append(AliConstant.APPID);
		sb.append("&scope=").append("auth_user");
		sb.append("&redirect_uri=").append(URLEncoder.encode("http://www.baintime.com/aliLogin_callBack"));
		return "redirect:"+sb.toString();
	}

	/**
	 * 支付宝登录——回调
	 * @return
	 */
	@RequestMapping("/aliLogin_callBack")
	public String callBack(String auth_code){
		AlipayClient alipayClient = new DefaultAlipayClient(AliConstant.URL, AliConstant.APPID, AliConstant.APP_PRIVATE_KEY, AliConstant.FORMAT, AliConstant.CHARSET, AliConstant.ALIPAY_PUBLIC_KEY, AliConstant.SIGN_TYPE); 
		AlipaySystemOauthTokenRequest alrequest = new AlipaySystemOauthTokenRequest();
		alrequest.setCode(auth_code);
		alrequest.setGrantType("authorization_code");
		try {
		    AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(alrequest);
		    String accessToken = oauthTokenResponse.getAccessToken();
		    String userId = oauthTokenResponse.getUserId();
		    // 判断用户是否已经绑定支付宝账号，如果绑定直接进行数据库查询，如果未绑定从支付宝获取用户信息并且进行绑定
		    AlipayUserInfoShareRequest userRequest = new AlipayUserInfoShareRequest();
		    try {
		        AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(userRequest, accessToken);
		        System.out.println(userinfoShareResponse.getBody());
		    } catch (AlipayApiException e) {
		        //处理异常
		        e.printStackTrace();
		    }
		} catch (AlipayApiException e) {
		    //处理异常
		    e.printStackTrace();
		}
		return "redirect:/user/account";
	}

}
