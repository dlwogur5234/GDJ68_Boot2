<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="/WEB-INF/views/layout/headCSS.jsp"></c:import>
</head>
<body id="page-top">
	<!-- Page Wrapper -->
    <div id="wrapper">
    
    	<!-- sidebar -->
    	<c:import url="/WEB-INF/views/layout/sidebar.jsp"></c:import>
    	
    	<div id="content-wrapper" class="d-flex flex-column">
    		<!-- topbar -->
    		<div id="content">
    			<c:import url="/WEB-INF/views/layout/topbar.jsp"></c:import>
    		</div>
    		
    		<!-- Begin Page Content -->
            <div class="container-fluid">
                <!-- page 내용 -->
                
                
            </div>
    		
    		<!-- Footer -->
    		<c:import url="/WEB-INF/views/layout/footer.jsp"></c:import>
    	</div>
    	
    </div>
    
<c:import url="./layout/footJs.jsp"></c:import>
</body>
</html>