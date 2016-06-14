<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%
	String path = request.getContextPath();
%>
 <!-- #CSS Links -->
     <!-- Basic Styles -->
     <link rel="shortcut icon" href="<%=path %>/images/favicon.ico">
    <link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/css/select.dataTables.min.css">
    <link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/css/font-awesome.min.css">

    <!-- SmartAdmin Styles : Caution! DO NOT change the order -->
    <link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/css/smartadmin-production-plugins.min.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/css/smartadmin-production.min.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/css/smartadmin-skins.min.css"/>

    <link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/a2z.css">
     <link href="<%=path%>/css/multiple-select.css" rel="stylesheet" /> <!--引入多选框-->
  
    <style>
    	.widget-body {
		    border-top:1px solid #aaa;
		}
    </style>