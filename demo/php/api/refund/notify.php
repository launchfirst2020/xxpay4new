<?php
	
	require("../_config.php");  //公共配置文件
	require("../_utils.php");  //工具类

	if(!isset($_REQUEST["sign"]) ){
		echo "fail(sign not exists)";
		exit;
	}
	
	$resSign = $_REQUEST["sign"] ;
	
	$paramArray = array();
	
	if(isset($_REQUEST["refundOrderId"]) ){
		$paramArray["refundOrderId"] = $_REQUEST["refundOrderId"];
	}

	if(isset($_REQUEST["mchId"]) ){
		$paramArray["mchId"] = $_REQUEST["mchId"];
	}
	
	if(isset($_REQUEST["appId"]) ){
		$paramArray["appId"] = $_REQUEST["appId"];
	}

	if(isset($_REQUEST["mchRefundNo"]) ){
		$paramArray["mchRefundNo"] = $_REQUEST["mchRefundNo"];
	}
	
	if(isset($_REQUEST["refundAmount"]) ){
		$paramArray["refundAmount"] = $_REQUEST["refundAmount"];
	}
	
	if(isset($_REQUEST["status"]) ){
		$paramArray["status"] = $_REQUEST["status"];
	}
	
	if(isset($_REQUEST["channelOrderNo"]) ){
		$paramArray["channelOrderNo"] = $_REQUEST["channelOrderNo"];
	}
	
	if(isset($_REQUEST["param1"]) ){
		$paramArray["param1"] = $_REQUEST["param1"];
	}
	
	if(isset($_REQUEST["param2"]) ){
		$paramArray["param2"] = $_REQUEST["param2"];
	}
	
	if(isset($_REQUEST["refundSuccTime"]) ){
		$paramArray["refundSuccTime"] = $_REQUEST["refundSuccTime"];
	}
	
	if(isset($_REQUEST["backType"]) ){
		$paramArray["backType"] = $_REQUEST["backType"];
	}

	
	$sign = paramArraySign($paramArray, $mchKey);  //签名
	
	if($resSign != $sign){  //验签失败
		echo "fail(verify fail)";
		exit;
	}
	
	//处理业务
	
	
	
	echo "success";
	exit;
