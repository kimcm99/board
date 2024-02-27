<%--
  Created by IntelliJ IDEA.
  User: aphrosys
  Date: 2024-02-01
  Time: 오후 2:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>리스트</title>
</head>
<body>
<c:if test="${not empty boardList}">
    <table border="1" align="center">
        <tr>
            <td>게시글 번호</td>
            <td>제목</td>
            <td>날짜</td>
        </tr>
      <c:forEach items="${boardList}" var="item">
          <tr>
              <td>${item.boardNum}</td>
              <td>${item.boardTitle}</td>
              <td>${item.boardDate}</td>
          </tr>
      </c:forEach>
    </table>

    <button type="button" value="글 작성" onclick="location.href='/test.do'"></button>
</c:if>

<c:if test="${empty boardList}">
    <p> 작성된 글이 없습니다. </p>
</c:if>
</body>

</html>
