<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script src="/js/test.js"></script>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판</title>
</head>
<body>

<%-- 작성 --%>
<div id="writetable">
    <table border="1" align="center">
        <caption>게시판 글쓰기</caption>
        <tr>
            <td>제목<input type="text" id="boardtitle" name="boardtitle"></td>
        </tr>
        <tr>
            <td>내용<input type="text" id="boardcontent" name="boardcontent"></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="button" value="작성" onclick="insert()">
                <input type="button" value="조회" onclick="boardList2()"></td>
        </tr>
    </table>
</div>

<%--게시판 목록--%>
<div id="listTable">
    <table border="1" align="center" style="margin-top : 50px">
        <thead>
        <tr>
            <td>게시글 번호</td>
            <td>제목</td>
            <td>내용</td>
            <td>조회수</td>
            <td>날짜</td>
        </tr>
        </thead>
        <tbody id="boardTable">

        </tbody>
    </table>

    <div align="center">
        <p id="pagetable"></p>
    </div>

    <div id="writeButton" align="right" style="margin-top: 50px; margin-bottom: 7px">
        <input type="button" value="글 작성" onclick="showWriteTable()">
    </div>

    <div id="recent">
        <h5>최근 검색어</h5>
        <p id="recentSearch"> </p>
    </div>

    <div id="search" align="right">
        <select name="searchtype" id="searchType">
            <option value="searchtitle">제목</option>
            <option value="searchcontent">내용</option>
            <option value="searchnum">번호</option>
        </select>
        <input type="text" placeholder="검색" id="searchtext">
        <button type="button" onclick="search()">검색</button>
    </div>

    <div align="right" style="margin-top: 5px">
        <select id="selectlistcount" onchange="selectlistcount()">
            <option value="10">10개씩 보기</option>
            <option value="20">20개씩 보기</option>
            <option value="30">30개씩 보기</option>
        </select>
    </div>

</div>

<%-- 상세 페이지 --%>
<div id="detailDiv">
    <table border="1" align="center" style="margin-top: 50px">
        <thead>
        <tr>
            <td>번호</td>
            <td>제목</td>
            <td>내용</td>
            <td>조회수</td>
            <td>날짜</td>
        </tr>
        </thead>
        <tbody id="detailBoard">

        </tbody>
    </table>

    <%-- 삭제, 수정, 목록 버튼 --%>
    <div id="boardButton" align="center" style="margin-top: 50px">
        <input type="button" value="목록" onclick="boardList2(page)">
        <input type="button" value="삭제"
               onclick="deleteBoard(document.getElementById('detailBoard_boardNum').textContent)">
        <input type="button" value="수정"
               onclick="modify(document.getElementById('detailBoard_boardNum').textContent, document.getElementById('detailBoard_boardTitle').value, document.getElementById('detailBoard_boardContent').value)">
    </div>
</div>

</body>
</html>