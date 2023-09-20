<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
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

          <!-- Begin Page Content -->
          <div class="container-fluid">
            <!-- page 내용 -->
            <h1>list page</h1>

            <div class="card shadow mb-4">
              <div class="card-body">
                <div class="table-responsive">
                  <table
                    class="table table-bordered"
                    id="dataTable"
                    width="100%"
                    cellspacing="0"
                  >
                    <thead>
                      <tr>
                        <th>No</th>
                        <th>Title</th>
                        <th>Writer</th>
                        <th>Date</th>
                        <th>Hit</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${list}" var="vo">
                        <tr>
                          <td>${vo.boardNo}</td>
                          <td>${vo.boardTitle}</td>
                          <td>${vo.boardWriter}</td>
                          <td>${vo.boardDate}</td>
                          <td>${vo.boardHit}</td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>

                  <div class="row">
                  <!-- 글작성버튼 -->
                    <div class="col-sm-12 col-md-5">
                      <div
                        class="dataTables_info"
                        id="dataTable_info"
                        role="status"
                        aria-live="polite"
                      >
                        <a href="./add" class="btn btn-primary btn-icon-split">
                            <span class="icon text-white-50">
                            	<i class="fas fa-flag"></i>
                            </span>
                        	<span class="text">글작성</span>
                      	</a>
                      </div>
                    </div>
                    <!-- 글작성버튼 끝-->
                    
                    <!-- 페이징처리 시작 -->
                    <div class="col-sm-12 col-md-7">
                      <div
                        class="dataTables_paginate paging_simple_numbers"
                        id="dataTable_paginate"
                      >
                        <ul class="pagination">
                          <li
                            class="paginate_button page-item previous disabled"
                            id="dataTable_previous"
                          >
                            <a
                              href="./list?page=${pager.page-1}"
                              aria-controls="dataTable"
                              data-dt-idx="0"
                              tabindex="0"
                              class="page-link"
                              >Previous</a
                            >
                          </li>
                          
                          <c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i">
	                          <li class="paginate_button page-item">
	                            <a
	                              href="./list?page=${i}"
	                              aria-controls="dataTable"
	                              data-dt-idx="1"
	                              tabindex="0"
	                              class="page-link"
	                              >${i}</a
	                            >
	                          </li>
                          </c:forEach>
                         
                          <li
                            class="paginate_button page-item next"
                            id="dataTable_next"
                          >
                            <a
                              href="./list?page=${pager.page+1}"
                              aria-controls="dataTable"
                              data-dt-idx="7"
                              tabindex="0"
                              class="page-link"
                              >Next</a
                            >
                          </li>
                        </ul>
                      </div>
                    </div>
                    <!-- 페이징처리 끝 -->
                    
                    
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Footer -->
        <c:import url="/WEB-INF/views/layout/footer.jsp"></c:import>
      </div>
    </div>

    <c:import url="/WEB-INF/views/layout/footjs.jsp"></c:import>
  </body>
</html>
