<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<script type="text/javascript" src="${ctx }/webresources/common/js/page.js" ></script>
	<script type="text/javascript" src="${ctx }/webresources/common/js/list.js" ></script>
</head>

<body>
<div>
   <c:set var="funMap" value="<%=Constant.funcMap %>"></c:set>
   <ul class="breadcrumb">
     <li>Home<span class="divider">/</span></li>
     <li class="active">${funMap[funcId]}</li>
   </ul>
</div>
<form id="mainForm" method="post" action="/manager/zhrt/spcode">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
    <a href="javascript:edit('a','0');" class="btn"> 新增 </a>
   <!--  <a href="javascript:delmul();" class="btn"> 批量删除 </a> -->
  <div class="btn-group"> </div>
</div>


<div class="well">
    <table class="table">
    
      <thead>
        <tr><th colspan="8" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>
        <tr>
          <th width="8%"><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
          <th width="15%">sp名称</th>
          <th width="15%">通道名称</th>
          <th width="15%">计费指令</th>
          <th width="10%">金额</th>
          <th width="10%">日限条数</th>
          <th width="10%">月限条数</th>
          <th width="30%" >操作</th>
        </tr>
      </thead>
      
      
      <tbody>
      	<c:set var="spMap" value="<%=Constant.spMap %>"></c:set>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td ><input type="checkbox" name="ids" id="${entity.id }" value="${entity.id }"/></td>
          <td >${spMap[entity.spId].name }</td>
          <td >${entity.name }</td>
          <td ><input type="text" value="${entity.feeCode }" size="60"/></td>
          <td >${entity.chargeMoney }</td>
          <td >${entity.limitDayNum }</td>
          <td >${entity.limitMonthNum }</td>
          <td >
              <a href="javascript:edit('e','${entity.id }');">编辑</a>&nbsp;&nbsp;
              <a href="javascript:edit('v','${entity.id }');">详情</a>&nbsp;&nbsp;
              <a href="javascript:del('${entity.id }');" >删除</a>
          </td>
        </tr>
        </c:forEach>
      </tbody>
      
      
    </table>
</div>


<jsp:include page="../common/page.jsp" />

</form>
                    
<!-- <footer>
    <hr>

    Purchase a site license to remove this link from the footer: http://www.portnine.com/bootstrap-themes
    <p class="pull-right">A <a href="http://www.portnine.com/bootstrap-themes" target="_blank">Free Bootstrap Theme</a> by <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>

    <p>&copy; 2012 <a href="http://www.mycodes.net/" title="源码之家" target="_blank">源码之家</a></p>
</footer> -->
</body>
</html>