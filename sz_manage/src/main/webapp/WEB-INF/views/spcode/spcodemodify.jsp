<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/zhrt/js/spcode.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/modify.js" ></script>
</head>

<body>
<form id="mainForm" method="post" action="/manager/zhrt/spcode">
<input type="hidden" name="id" id="id" value="${entity.id }" >
<input type="hidden" name="m" id="m" value="${m }" >

	<div class="btn-toolbar">
		<c:if test="${m != 'v' }">
			<a href="javascript:save();" data-toggle="modal" class="btn"<i class="icon-save"></i>> 保存 </a>
			<a href="javascript:clean();" data-toggle="modal" class="btn">清空</a>
		</c:if>
		<div class="btn-group"></div>
	</div>
<div style="color: red;text-align:center;">${errorInfo }</div>

	<div class="well">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">基本信息</a></li>
		  <!-- <li><a href="#profile" data-toggle="tab">Password</a></li> -->
		</ul>
	    <div id="myTabContent" class="tab-content">
	        
	        <div class="tab-pane active in" id="home">
		    <form id="tab">
		        <label><span style="color: red;">*</span>所属SP</label>
		        <label>
		        	<select id="spId" name="spId" class="input-xlarge" style="height: 40px;">
		        		<option value="">请选择</option>
		        		<c:forEach items="${spinfoList }"  var="sp">
		        			<option value="${sp.id }" <c:if test="${entity.spId eq sp.id}">selected="selected"</c:if>>${sp.name }</option>
		        		</c:forEach>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="spId_tip" style="color:red"></lable>
		        </label>
		        
		        
		        <label>通道名称</label>
		        <label>
		        	<input type="text" id="name" name="name" value="${entity.name }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="name_tip" style="color:red"></lable>&nbsp;&nbsp;
		        </label>
		        
		        <label>所属运营商</label>
		        <label>
		        	<select id="operatorType" name="operatorType" class="input-xlarge" style="height: 40px;">
		        		<option value="1" <c:if test="${entity.operatorType eq 1 }">selected</c:if>>中国移动</option>
		        		<option value="2" <c:if test="${entity.operatorType eq 2 }">selected</c:if>>中国联通</option>
		        		<option value="3" <c:if test="${entity.operatorType eq 3 }">selected</c:if>>中国电信</option>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="operatorType_tip" style="color:red"></lable>
		        </label>
		        
		        <label>计费通道类型</label>
		        <label>
		        	<select id="feeStatus" name="feeStatus" class="input-xlarge" style="height: 40px;">
		        		<option value="1" <c:if test="${entity.feeStatus eq 1 }">selected</c:if>>短信计费</option>
		        		<option value="2" <c:if test="${entity.feeStatus eq 2 }">selected</c:if>>wap</option>
		        		<option value="3" <c:if test="${entity.feeStatus eq 3 }">selected</c:if>>第三方SDK</option>
		        		<option value="4" <c:if test="${entity.feeStatus eq 4 }">selected</c:if>>PC联网</option>
		        	</select>
		        	&nbsp;&nbsp;
		        	<lable id="feeStatus_tip" style="color:red"></lable>
		        </label>
		        
		        <label>计费指令。如果是sms则填写sms计费指令，如果是wap则配置wap地址，如果是sdk则填写预先定义好的值</label>
		        <label>
		        	<input type="text" id="feeCode" name="feeCode" value="${entity.feeCode }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="feeCode_tip" style="color:red"></lable>&nbsp;&nbsp;
		        	<lable id="feeCode_remark">wap范例：http://xxx.xx.xxx/index.jsp?xxx=xxx&xxx=xxx[&imsi=imsi][&imei=imei][&phone=phone][&province=province]</lable>
		        </label>
		        
		        <label>短信计费端口号,wap和第三方sdk不需要填写</label>
		        <label>
		        	<input type="text" id="feeNumber" name="feeNumber" value="${entity.feeNumber }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="feeNumber_tip" style="color:red"></lable>
		        </label>
		        
		        <label>计费金额,单位元</label>
		        <label>
		        	<input type="text" id="chargeMoney" name="chargeMoney" value="${entity.chargeMoney }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="chargeMoney_tip" style="color:red"></lable>
		        </label>
		        
		        <label>拦截关键字<span style="color: red">*</span>    如果有多条下行短信（包括确认购买短信和扣费提醒短信），则将每条短信中挑选一个拦截关键字并以#分割</label>
		        <label>比如1条短信：“...谢谢购买《捕鱼》,资费10元...”，则为“谢谢购买《捕鱼》”</label>
		        <label>比如2条短信：“...即将购买《捕鱼》...”，“...谢谢购买《捕鱼》,资费10元...”， 则为“即将购买《捕鱼》#谢谢购买《捕鱼》”</label>
		        <label>比如3条短信：“...即将购买《捕鱼》...”，“...回复1234确认...”，“...谢谢购买《捕鱼》,资费10元...”，则为“即将购买《捕鱼》#确认#谢谢购买《捕鱼》”</label>
		        <label>
		        	<input type="text" id="interceptWord" name="interceptWord" value="${entity.interceptWord }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="interceptWord_tip" style="color:red"></lable>
		        </label>
		        
		        <label>回复类型 （0：不需要回复，1：需要回复）</label>
		        <label>
		        	<input type="text" id="replyType" name="replyType" value="${entity.replyType }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="replyType_tip" style="color:red"></lable>
		        </label>
		        <label>回复内容,如果回复类型replyType为0：则不写； 如果回复类型replyType为1：需要回复短信中的拦截关键字+“，”+验证码前文字+“#”+验证码后文字 </label>
		        <label>举例：当回复类型replyType为1时范例：假如包含回复内容的短信为：</label>
		        <label>“即将购买《捕鱼》，回复是确认”，设置的拦截关键字为“即将购买《捕鱼》”，那么填写“即将购买《捕鱼》，回复#确认” </label>
		        <label>“即将购买《捕鱼》，回复是”，设置的拦截关键字为“即将购买《捕鱼》”，那么填写“即将购买《捕鱼》，回复#” </label>
		        <label>“是确认”，设置的拦截关键字为“确认”，那么填写“确认，#确认” </label>
		        <label>
		        	<input type="text" id="replyContent" name="replyContent" value="${entity.replyContent }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="replyContent_tip" style="color:red"></lable>
		        </label>
		        
		        <label>计费次数<span style="color: red">*</span></label>
		        <label>
		        	<input type="text" id="chargeTimes" name="chargeTimes" value="${entity.chargeTimes }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="chargeTimes_tip" style="color:red"></lable>
		        </label>
		        
		        <label>开通省份</label>
		        <label>
		        	<c:forEach items="${latnList }" var="latn" varStatus="i">
		        		<c:if test="${i.index%10==0 && i.index != 0 }"><br/></c:if>
		        		<input name="chargeProvince" type="checkbox" value="${latn.latnId }" <c:if test="${okProvinceMap[latn.latnId] eq 1}">checked</c:if>>${latn.latnName }&nbsp;&nbsp;&nbsp;&nbsp;
		        	</c:forEach>
		        	<lable id="chargeProvince_tip" style="color:red"></lable>
		        </label>
		        
		        <label>日限条数<span style="color: red">*</span></label>
		        <label>
		        	<input type="text" id="limitDayNum" name="limitDayNum" value="${entity.limitDayNum }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="limitDayNum_tip" style="color:red"></lable>
		        </label>
		        
		        <label>月限条数<span style="color: red">*</span></label>
		        <label>
		        	<input type="text" id="limitMonthNum" name="limitMonthNum" value="${entity.limitMonthNum }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="limitMonthNum_tip" style="color:red"></lable>
		        </label>
		        
		        <label>计费时间段起</label>
		        <label>
		        	<input type="text" id="chargeTimeBegin" name="chargeTimeBegin" value="${entity.chargeTimeBegin }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="chargeTimeBegin_tip" style="color:red"></lable>
		        </label>
		        
		        <label>计费时间段止</label>
		        <label>
		        	<input type="text" id="chargeTimeEnd" name="chargeTimeEnd" value="${entity.chargeTimeEnd }" class="input-xlarge" style="height: 40px;">&nbsp;&nbsp;
		        	<lable id="chargeTimeEnd_tip" style="color:red"></lable>
		        </label>
		    </form>
	        </div>
						
		</div>
	</div>

</form>
                    
</body>
</html>