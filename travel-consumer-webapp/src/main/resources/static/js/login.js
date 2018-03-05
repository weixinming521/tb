$(function() {

	$(".thirdCoin").hover(function() {
		var curStyle = $(this).attr("data");
		$(this).addClass(curStyle);
	}, function() {
		var curStyle = $(this).attr("data");
		$(this).removeClass(curStyle);
	});

	//登陆注册切换
	$(".loginItems")
			.on(
					"click",
					function() {
						$(this).addClass("loginItemsChecked").siblings()
								.removeClass("loginItemsChecked");
						$(".loginSection").eq($(this).index()).show()
								.siblings().hide();
					})
})

//登录
function goodsLogin() {
	var loginPhone = $(".loginPhone").val();
	var loginPassword = $(".loginPassword").val();
	if (loginPhone == "") {
		warningInformation(".payWarnContent", "账号不能为空");
		return false;
	}
	if (loginPassword == "") {
		warningInformation(".payWarnContent", "密码不能为空");
		return false;
	}

	$.ajax({
		type : "POST",
		url : "/login",
		data : {
			telephone : loginPhone,
			password : loginPassword
		},
		dataType : "json",
		success : function(data) {
			var error = data.error;
			if (error == 0) {
				window.location.href="/user/account";
			} else {
				warningInformation(".payWarnContent", data.msg);
			}
		}
	});

}
//注册
function signCheck() {
	var signPhone = $(".signPhone").val();
	var signCheck = $(".signCheck").val();
	var signPassword = $(".signPassword").val();
	if (signPhone == "") {
		warningInformation(".payWarnContent", "手机号不能空");
		return false;
	}
	if (!MyRegExp(/^1[3|4|5|7|8]\d{9}$/, signPhone)) {
		warningInformation(".payWarnContent", "请输入11位正确的手机号");
		return false;
	}
	if (signCheck == "") {
		warningInformation(".payWarnContent", "验证码不能为空");
		return false;
	}
	if (!MyRegExp(/^\d{6}$/, signCheck)) {
		warningInformation(".payWarnContent", "输入6位数字的验证码");
		return false;
	}
	var passwordLength = signPassword.length;
	if (!MyRegExp(/^[u4E00-u9FA5]+$/, signPassword) || passwordLength < 6
			|| passwordLength > 20) {
		warningInformation(".payWarnContent", "密码6-20位由字母、下划线、数字等组成！");
		return false;
	}

	$.ajax({
		type : "POST",
		url : "/register",
		data : {
			telephone : signPhone,
			password : signPassword,
			checkCode : signCheck
		},
		dataType : "json",
		success : function(data) {
			var error = data.error;
			if (error == 0) {
				window.location.href="/user/account";
			} else {
				warningInformation(".payWarnContent", data.msg);
			}
		}
	});
};

//发送验证码验证---注册
function sendCodeRegist(obj) {
	var singPhone = $(".signPhone").val();
	if (singPhone == "") {
		warningInformation(".payWarnContent", "手机号不能空");
		return false;
	}
	if (!MyRegExp(/^1[3|4|5|7|8]\d{9}$/, singPhone)) {
		warningInformation(".payWarnContent", "填写正确的手机号");
		return false;
	}
	$.ajax({
		type : "POST",
		url : "/sms/registCheck",
		data : {
			telephone : singPhone
		},
		dataType : "json",
		success : function(data) {
			var error = data.error;
			if (error == 0) {
				settime(obj);
			} else {
				warningInformation(".payWarnContent", data.msg);
			}
		}
	});
}
//发送验证码验证---找回密码
function sendCodeBack(obj) {
	var singPhone = $(".signPhone").val();
	if (singPhone == "") {
		warningInformation(".payWarnContent", "手机号不能空");
		return false;
	}
	if (!MyRegExp(/^1[3|4|5|7|8]\d{9}$/, singPhone)) {
		warningInformation(".payWarnContent", "填写正确的手机号");
		return false;
	}
	$.ajax({
		type : "POST",
		url : "/sms/loginCheck",
		data : {
			telephone : singPhone
		},
		dataType : "json",
		success : function(data) {
			var error = data.error;
			if (error == 0) {
				settime(obj);
			} else {
				warningInformation(".payWarnContent", data.msg);
			}
		}
	});
}

//页面中全部的弹出框
function warningInformation(parentElement, spanContent) {
	$(parentElement).show(0).delay(1500).hide(0);
	$(parentElement).find(".payWarnDiv").text(spanContent);
}
//发送验证码
var countdown = 60;
function settime(obj) {
	if (countdown == 0) {
		obj.removeAttribute("disabled");
		obj.value = "获取验证码";
		countdown = 60;
		return;
	} else {
		obj.setAttribute("disabled", true);
		obj.value = "重新发送(" + countdown + ")";
		countdown--;
	}
	setTimeout(function() {
		settime(obj)
	}, 1000)
}
//正则表达式验证
function MyRegExp(regular, regularStr) {
	if (regular.test(regularStr)) {
		return true;
	} else {
		return false;
	}
}

//找回密码验证
function passwordBackCheck(){
	
  	var signPhone = $(".signPhone").val();
    var signCheck = $(".signCheck").val();
    if(signPhone == ""){
        warningInformation(".payWarnContent","手机号不能空");
        return false;
    };
    if(!MyRegExp(/^1[3|4|5|7|8]\d{9}$/,signPhone)){
        warningInformation(".payWarnContent","请输入11位正确的手机号");
        return false;
    };
    if(signCheck == ""){
        warningInformation(".payWarnContent","验证码不能为空");
        return false;
    };
    if(!MyRegExp(/^\d{6}$/,signCheck)){
        warningInformation(".payWarnContent","输入6位数字的验证码");
        return false;
    };
	$.ajax({
		type : "POST",
		url : "/sms/checkCode",
		data : {
			telephone:signPhone,
			code:signCheck
		},
		dataType : "json",
		success : function(data) {
			var error = data.error;
			if (error == 0) {
				$(".passwordNext").hide();
				$(".passwordSure").show();
			} else {
				warningInformation(".payWarnContent", data.msg);
			}
		}
	});
    
    
    
    
};
//修改密码完成
function editPassword(){
	 var passwordLength = $(".signPassword").val().length;
     var signPassword = $(".signPassword").val();
     var surePassword = $(".surePassword").val();
    		
     if(!MyRegExp(/^[u4E00-u9FA5]+$/,signPassword)||passwordLength<6||passwordLength>20){
        warningInformation(".payWarnContent","密码6-20位由字母、下划线、数字等组成！");
        return false;
    };
    if(signPassword!=surePassword){
    	 warningInformation(".payWarnContent","两次密码不一致！");
    };
  	var signPhone = $(".signPhone").val();
    var signCheck = $(".signCheck").val();
    
	$.ajax({
		type : "POST",
		url : "/updatePassword",
		data : {
			telephone:signPhone,
			checkCode:signCheck,
			password:signPassword
		},
		dataType : "json",
		success : function(data) {
			var error = data.error;
			if (error == 0) {
				// 修改成功
				window.location.href="/user/account";
			} else {
				warningInformation(".payWarnContent", data.msg);
			}
		}
	});
    
};

