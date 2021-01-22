<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>授权提示</title>
    <link rel="stylesheet" href="/layui/css/layui.css">
    <style>
        .mainDiv1 {color:lightseagreen; text-align:center; margin-top: 50px;}
        .mainDiv2 {text-align:center; margin-top: 10px;}
        .mainDiv3 {text-align:center; margin-top: 200px;}
        .mainDivTitle {text-align:center; margin-top: 20px; color:orangered }
    </style>
</head>
<body>


<#if isSuccess?? && isSuccess>

    <div class="mainDiv1 layui-fluid">
        <i class="layui-icon" style="font-size:100px;">&#x1005;</i>
    </div>
    <div class="mainDiv2 layui-fluid">
        <span style="font-size:16px">授权成功</span>
    </div>

    <#if authType?? && authType == 'alipayInner'>
        <div class="mainDivTitle layui-fluid">
            <span style="font-size:14px">提示： 请联系您的 [服务商]
                登录支付宝管理平台获取 [<span style="font-style: oblique">授权Token</span>] 进行下一步配置！</span>
        </div>
    </#if>

<#else>

    <div class="mainDiv1 layui-fluid">
        <i class="layui-icon" style="font-size:100px; color:orangered">&#x1007;</i>
    </div>
    <div class="mainDiv2 layui-fluid">
        <span style="font-size:16px; ">授权失败</span>
        <div class="mainDivTitle layui-fluid">
            <span style="font-size:14px">错误提示：${errMsg!''}
            </span>
        </div>
    </div>
</#if>

<div class="mainDiv3 layui-fluid">
    <a class="layui-btn layui-btn-primary closeBtn">关闭页面</a>
</div>

<script src="/layui/layui.js"></script>
<script>
    layui.use(['jquery'], function(){

        layui.$(".closeBtn").click(function(){

            var ua = navigator.userAgent.toLowerCase();
            if(ua.match(/MicroMessenger/i)=="micromessenger") {
                WeixinJSBridge.call('closeWindow');
            } else if(ua.indexOf("alipay")!=-1){
                AlipayJSBridge.call('closeWebview');
            }
            else{
                window.close();
            } 
        });
    });







</script>
</body>
</html>