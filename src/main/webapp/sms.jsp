<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>短息接口测试</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <script src="js/jquery-1.12.4.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
        window.onload = function(){
            var getsms = document.getElementById("phonenumbutton");

            phonenuminput.oninput = function(){
                var phonenum = document.getElementById("phonenuminput").value;
                isphonenum = /^1[34578]\d{9}$/;
                if(phonenum != ''){
                    //alert(isphonenum.test(phonenum))
                    if(isphonenum.test(phonenum)){
                        document.getElementById("phonenumbutton").disabled = false;
                        getsms.addEventListener("click",getphonenum);
                    }else{
                        document.getElementById("phonenumbutton").disabled = true;
                    }
                }
            }
            function getphonenum(){
                var phonenum = document.getElementById("phonenuminput").value;
                phonenuminput = document.getElementById("phonenuminput");
                //alert(phonenum)
                $.post('sendsms',{'phonenum':phonenum},function(data){
                    if(data.result != 0){
                        alert("发送失败")
                    }else{
                        alert("发送成功,请勿重复发送!")
                        document.getElementById("phonenumbutton").disabled = true;
                    }
                },'json');
            }

            var getcode = document.getElementById("phonecodebutton");

            phonecodeinput.oninput = function(){
                var phonecode = document.getElementById("phonecodeinput").value;
                isphonecode = /^\d{4}$/;
                if(phonecode != ''){
                    //alert(isphonenum.test(phonenum))
                    if(isphonecode.test(phonecode)){
                        document.getElementById("phonecodebutton").disabled = false;
                        getcode.addEventListener("click",getcodenum);
                    }else{
                        document.getElementById("phonecodebutton").disabled = true;
                    }
                }
            }
            function getcodenum(){
                var phonecode = document.getElementById("phonecodeinput").value;
                //alert(phonecode)
                $.post('getcode',{'phonecode':phonecode},function(data){
                    if(data != 1){
                        alert("验证码不符")
                    }else{
                        window.location.href = "index.jsp"
                    }
                },'json');
                document.getElementById("phonecodebutton").disabled = true;
            }
        }
    </script>
</head>
<body>
<div class="container">
    <form class="form-inline" role="form" id="phonenumform" style="margin-top: 50px;font-size: 20px;">
        <div class="form-group">
            <label for="phonenuminput">请输入手机号码:</label>
            <input class="form-control" id="phonenuminput" type="text" value="" required="required" maxlength="11" size="9" style="font-size: 20px;"/>
            <button type="button" class="btn btn-primary" id="phonenumbutton" disabled="disabled">获取短信验证码</button>
        </div>
    </form>
    <form class="form-inline" role="form" id="phonecodeform" style="margin-top: 50px;font-size: 20px;">
        <div class="form-group">
            <label for="phonecodeinput">请输入验证码:</label>
            <input class="form-control" id="phonecodeinput" type="text" value="" required="required" maxlength="4" size="1" style="font-size: 20px;"/>
            <button type="button" class="btn btn-success" id="phonecodebutton" disabled="disabled">提交短信验证码</button>
        </div>
    </form>
</div>
</body>
</html>

