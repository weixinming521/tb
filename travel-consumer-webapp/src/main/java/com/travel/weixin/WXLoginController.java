package com.travel.weixin;

import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.core.utils.HttpClientUtil;
/**
 * 微信登录、微信支付接口
 * @author Administrator
 *
 */
@Controller
public class WXLoginController {
	
//	@Autowired
//	private RedisService redisService;
	
	/**
	 * 微信——公众号登录
	 * @return
	 */
	@RequestMapping("/wxLogin")
	public String wxLogin(){	
		
		StringBuilder sb = new StringBuilder();
		sb.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
		sb.append("appid=").append(WXConstant.AppID);
		sb.append("&redirect_uri=").append(URLEncoder.encode("http://www.baintime.com/wxLogin_callBack"));
		sb.append("&response_type=code");
		sb.append("&scope=snsapi_userinfo");
		sb.append("&state=STATE");
		sb.append("#wechat_redirect");		
		return "redirect:"+sb.toString();
	}

	/**
	 * 微信公众号登录——回调
	 * @return
	 */
	@RequestMapping("/wxLogin_callBack")
	public String callBack(String code){
		StringBuilder URL = new StringBuilder();
		URL.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
		URL.append("appid=").append(WXConstant.AppID);
		URL.append("&secret=").append(WXConstant.AppSecret);
		URL.append("&code=").append(code);
		URL.append("&grant_type=authorization_code");
		try {
			String result = HttpClientUtil.doGet(URL.toString());
			System.out.println(result);
			JSONObject jsonObject = new JSONObject(result);
			if (null != jsonObject) {  
			    String openId = jsonObject.getString("openid");  
			    String access_token = jsonObject.getString("access_token");
			    StringBuilder infoUrl = new StringBuilder("https://api.weixin.qq.com/sns/userinfo?");
			    infoUrl.append("access_token=").append(access_token);
			    infoUrl.append("&openid=").append(openId);
			    infoUrl.append("&lang=zh_CN");
			    String userInfo = HttpClientUtil.doGet(infoUrl.toString());
			    System.out.println(userInfo);
			    // 查询是否已经绑定用户，如果绑定用户则直接登录，如果未绑定用户创建绑定用户登录
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/user/account";
	}
	/**
	 * 微信扫码——登录
	 * @return
	 */
	@RequestMapping("/wxScanLogin")
	public String wxScanLogin(){	
		
		StringBuilder sb = new StringBuilder();
		sb.append("https://open.weixin.qq.com/connect/qrconnect?");
		sb.append("appid=").append(WXConstant.App_ID);
		sb.append("&redirect_uri=").append(URLEncoder.encode("http://www.baintime.com/wxScanLogin_callBack"));
		sb.append("&response_type=code");
		sb.append("&scope=snsapi_login");
		sb.append("&state=STATE");
		sb.append("#wechat_redirect");		
		return "redirect:"+sb.toString();
	}
	/**
	 * 微信公众号登录——回调
	 * @return
	 */
	@RequestMapping("/wxScanLogin_callBack")
	public String wxScanLogin_callBack(String code){
		StringBuilder URL = new StringBuilder();
		URL.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
		URL.append("appid=").append(WXConstant.App_ID);
		URL.append("&secret=").append(WXConstant.App_Secret);
		URL.append("&code=").append(code);
		URL.append("&grant_type=authorization_code");
		try {
			String result = HttpClientUtil.doGet(URL.toString());
			System.out.println(result);
			JSONObject jsonObject = new JSONObject(result);
			if (null != jsonObject) {  
			    String openId = jsonObject.getString("openid");  
			    // 使用openId查询是否已经绑定用户，如果绑定用户则直接登录，如果未绑定用户创建绑定用户登录
			    String access_token = jsonObject.getString("access_token");
			    StringBuilder infoUrl = new StringBuilder("https://api.weixin.qq.com/sns/userinfo?");
			    infoUrl.append("access_token=").append(access_token);
			    infoUrl.append("&openid=").append(openId);
			    infoUrl.append("&lang=zh_CN");
			    String userInfo = HttpClientUtil.doGet(infoUrl.toString());
			    System.out.println(userInfo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/user/account";
	}
}
