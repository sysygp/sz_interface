<%@page import="com.zhrt.util.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<link type="text/css" rel="stylesheet" href="${ctx }/webresources/common/css/style.css"/>
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
<form id="mainForm" method="post" action="/manager/zhrt/subcode">
<input type="hidden" name="pageNo" id="pageNo" value="" >
<input type="hidden" name="orderBy" id="orderBy" value="" >
<input type="hidden" name="order" id="order" value="" >

<input type="hidden" name="totalPages" id="totalPages" value="${page.totalPages}" >

<div class="btn-toolbar">
  <div class="btn-group"> </div>
</div>

<div class="well">
    <table class="table">
    
      <thead>
        <tr><th colspan="7" align="center" style="color: red;text-align: center;">${errorInfo }</th></tr>        
        <tr>
          <th><input type="checkbox" name="selectAll" id="selectAll" />全选</th>
          <th>万号段</th>
          <th>所属省份</th>
          <th>所属市</th>
          <th>用户类型编号</th>
          <th>运营商ID</th>
          <th style="width: 66px;">操作</th>
        </tr>
      </thead>
      
      
      <tbody>
        <c:forEach var="entity" items="${page.result}" varStatus="num">
        <tr>
          <td><input type="checkbox" name="ids" id="${entity.id }_${entity.subcode }" value="${entity.id}_${entity.subcode }"/></td>
          <td>${entity.subcode }</td>
          <td>${entity.latinProvince }</td>
          <td>${entity.latinCity }</td>
          <td>${entity.userType }</td>
          <td>
          	<c:choose>
          		<c:when test="${entity.operator==1 }">
          			移动
          		</c:when>
          		<c:when test="${entity.operator==2 }">
          			联通
          		</c:when>
          		<c:when test="${entity.operator==3 }">
          			电信
          		</c:when>
          	</c:choose>
          </td>
          <td>
              <a href="javascript:edit('v','${entity.id}_${entity.subcode }');">详情</a>&nbsp;&nbsp;
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