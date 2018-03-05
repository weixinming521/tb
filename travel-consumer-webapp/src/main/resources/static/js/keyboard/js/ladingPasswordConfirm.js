$(function(){
    var check_pass_word='';var passwords = $('#password').get(0);
    $(function(){
        var div = '\
        <div id="key" style="width:100%; overflow:hidden;">\
            <ul id="keyboard" class="clearfix" style="font-size:20px;margin:2px -2px 1px 2px">\
                <li class="symbol"><span class="off longwidth">1</span></li>\
                <li class="symbol"><span class="off">2</span></li>\
                <li class="symbol btn_number_ threeRight"><span class="off longwidth">3</span></li>\
                <li class="tab"><span class="off">4</span></li>\
                <li class="symbol"><span class="off">5</span></li>\
                <li class="symbol btn_number_ threeRight"><span class="off longwidth">6</span></li>\
                <li class="tab"><span class="off longwidth">7</span></li>\
                <li class="symbol"><span class="off">8</span></li>\
                <li class="symbol btn_number_ threeRight"><span class="off longwidth">9</span></li>\
                <li class="cancle btn_number_ longwidth"></li>\
                <li class="symbol zeroLi"><span class="off">0</span></li>\
                <li class="delete lastitem threeRight longwidth">删除</li>\
            </ul>\
        </div>\
        ';
        var character,index=0;  $("input.pass").attr("disabled",true);  $("#password").attr("disabled",true);$("#keyboardDIV").html(div);
        $('#keyboard li').click(function(){
            if ($(this).hasClass('delete')) {
                $(passwords.elements[--index%6]).val('');
                if($(passwords.elements[0]).val()==''){
                    index = 0;
                }
                /*for(var i= 0,len=passwords.elements.length-1;len>=i;len--){
                    if($(passwords.elements[len]).val()!=''){
                        $(passwords.elements[len]).val('');
                        break;
                    }
                }*/
                return false;
            }
            if ($(this).hasClass('cancle')) {
                parentDialog.close();
                return false;
            }
            if ($(this).hasClass('symbol') || $(this).hasClass('tab')){
                character = $(this).text();
                $(passwords.elements[index++%6]).val(character);
                if($(passwords.elements[5]).val()!=''){
                    index = 0;
                }
            /*for(var i= 0,len=passwords.elements.length;i<len;i++){
                if($(passwords.elements[i]).val()== null ||$(passwords.elements[i]).val()==undefined||$(passwords.elements[i]).val()==''){
                    $(passwords.elements[i]).val(character);
                    break;
                }
            }*/
            if($(passwords.elements[5]).val()!='') {
                var temp_rePass_word = '';
                for (var i = 0; i < passwords.elements.length; i++) {
                    temp_rePass_word += $(passwords.elements[i]).val();
                }
                check_pass_word = temp_rePass_word;
                /*$("#key").hide();*/
               $(".cardCollect").hide();
                $(".cardkey").hide();
                var action = 'modify';
                    $.ajax({
                        url:'http://www.lanrenzhijia.com',
                        type:'post',
                        data:{userName:'heboy18',ladingPassword:check_pass_word},
                        dataType:'json',
                        success:function(data){
                            var result=JSON.stringify(data);
                            if(result=="\"验证通过\""){
                                if(action=="modify"){
                                    window.parent.document.getElementById("modify_div").style.display='';
                                    window.parent.document.getElementById("modify_ladingPassword").disabled='disabled';
                                    window.parent.document.getElementById("oldLadingPassword").value=check_pass_word;
                                    parentDialog.close();//如果验证成功关闭当前窗口并跳转到提单券号列表
                                }else if(action=="set"){
                                    var ladingId = parent.window.document.getElementById("ladingId").value;
                                    var ladingType =parent.window.document.getElementById("ladingType").value;
                                    parent.window.location.href="indexSeting.do";
                                    //parentDialog.close();//如果验证成功关闭当前窗口并跳转到提单券号列表
                                }
                            }else{
                                var result_text='\
                                    <span>提货密码</span>\
                                    <span style="color: red;">验证失败</span>\
                                    ';
                                $("#set_text").html(result_text);
                                /*$("#key").show();*/
                                $("#cardkey").show();
                                for (var i = 0; i < passwords.elements.length; i++) {
                                    $(passwords.elements[i]).val('');
                                }
                                /*var t=1;
                                var t1 = window.setInterval(function(){
                                    t--;
                                    if(t==0){
                                        window.clearInterval(t1);
                                        if('set'==action){
                                            parent.window.location.href="queryLadingDetailWeixin.pfv?rm="+rm;
                                        }else if('modify'==action)
                                            parent.window.location.href="weixinLading.pfv?info=2";
                                    }
                                },1000);*/
                            }
                        },
                        error:function(data){
                            var result_text='\
                               <span>网络异常</span>\
                               <span style="color: red;">验证失败</span>\
                               ';
                            $("#set_text").html(result_text);
                            var t=1;
                                var t1 = window.setInterval(function(){
                                    t--;
                                    if(t==0){
                                        window.clearInterval(t1);
                                        if('set'==action){
                                            parent.window.location.href="queryLadingDetailWeixin.do";
                                        }else if('modify'==action)
                                            parent.window.location.href="#";
                                    }
                                },1000);
                        }
                    });
                }
                        }
            return false;
        });
    });
    (function () {
        function tabForward(e) {
            e = e || window.event;
            var target = e.target || e.srcElement;
            if (target.value.length === target.maxLength) {
                var form = target.form;
                for (var i = 0, len = form.elements.length-1; i < len; i++) {
                    if (form.elements[i] === target) {
                        if (form.elements[i++]) {
                            form.elements[i++].focus();
                        }
                        break;
                    }
                }
            }
        }
        var form = document.getElementById("password");
        form.addEventListener("keyup", tabForward, false);
        var set_text='\
        <span>请输入</span>\
        <span style="color: red;">提货密码</span>\
        <span>，验证本次操作</span>\
        ';
        $("#set_text").html(set_text);
    })();
})