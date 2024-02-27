"use strict"

let page = 1;
let searchText = '';
let searchType = '';
let selectlist = 10;

// 처음 List, Detail <div> hide
$(() => {
    $('#listTable').hide();
    $('#detailDiv').hide();
})

const selectlistcount = () => {
    selectlist = $('#selectlistcount').val();
    if (searchText == '' && searchText == null) {
        boardList2();
    } else {
        search();
    }
}

// 글 작성 폼을 보여주는 함수
const showWriteTable = () => {

    document.getElementById('boardtitle').value = '';
    document.getElementById('boardcontent').value = '';

    $('#writetable').show();
    $('#listTable').hide();
}

// 글 작성하는 함수
function insert() {

    let boardtitle = document.getElementById('boardtitle').value;
    let boardcontent = document.getElementById('boardcontent').value;

    $.ajax({
        type: 'POST',
        url: '/insert.do',
        data: {
            boardtitle: boardtitle,
            boardcontent: boardcontent
        },
        success: function (data) {
            if (data === 'Y') {
                alert("글 작성 성공");
                boardList2();
            } else {
                alert("글 작성 실패");
            }
        },
        error: function (error) {
            console.error('Error: ', error);
        }
    });

}

// 날짜 변환해주는 함수
const formattedDate = (boardDate) => {
    let timestamp = boardDate;
    let date = new Date(timestamp);
    let formattedDate = date.getFullYear() + '년' + ('0' + date.getMonth()).slice(-2) + '월' + ('0' + date.getDate()).slice(-2) + '일';
    return formattedDate;
}

// 게시판 목록을 보여주는 함수
function boardList2(pageNum = 1) {

    $('#detailDiv').hide();
    $('#writetable').hide();

    $.ajax({
        url: '/boardList2.do',
        type: 'POST',
        data: {
            pageNum: pageNum,
            selectlist: selectlist
        },
        dataType: 'json',
        success: function (result) {

            let data = result.boardVOList;
            let startpage = result.startpage;
            let endpage = result.endpage;
            page = result.pageNum;

            $('#listTable').show();

            let tableHtml = ''
            $.each(data, function (index, item) {
                tableHtml += `
                    <tr onclick="moveDetail(${item.boardNum}, '${page}')" style="cursor:pointer;">
                        <td>${item.boardNum}</td>
                        <td>${item.boardTitle}</td>
                        <td>${item.boardContent}</td>
                        <td>${item.board_readcount}</td>
                        <td>${formattedDate(item.boardDate)}</td>
                    </tr>
                `;
                // tableHtml += '<td>' + item.boardNum + '</td>' +
                //     '<td>' + item.boardTitle + '</td>' +
                //     '<td>' + item.boardContent + '</td>' +
                //     '<td>' + formattedDate(item.boardDate) + '</td>' +
                //     '</tr>';
            });

            let pagehtml = '';

            for (var num = startpage; num <= endpage; num++) {
                pagehtml += '<a href="#" onclick="boardList2(' + num + ')">' + num + '</a>' + '&nbsp';
            }

            $('#boardTable').html(tableHtml);
            $('#pagetable').html(pagehtml);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

// 상세페이지 보여주는 함수
const moveDetail = (boardNum,pageNum) => {

    $('#listTable').hide();
    let detailhtml = '';

    $.ajax({
        type: 'POST',
        url: '/moveDetail.do',
        data : {
            'boardNum' : boardNum,
            'pageNum' : pageNum
        },
        datatype: 'json',
        success: function (result) {

            let detailBoard = result.board;
            page = result.pageNum;

            detailhtml += '<tr>' +
                '<td id="detailBoard_boardNum">' + detailBoard.boardNum + '</td>' +
                '<td>' + '<input type="text" id="detailBoard_boardTitle" value="' + detailBoard.boardTitle + '">' + '</td>' +
                '<td>' + '<input type="text" id="detailBoard_boardContent" value="' + detailBoard.boardContent + '">' + '</td>' +
                '<td>' + detailBoard.board_readcount + '</td>' +
                '<td>' + formattedDate(detailBoard.boardDate) + '</td>' +
                '</tr>';

            $('#detailBoard').html(detailhtml);
            $('#detailDiv').show();
        },
        error: function (error) {
            console.error('Error', error);
        }
    });
}

// 글 삭제하는 함수
const deleteBoard = (boardNum) => {
    let deletecheck = confirm('삭제하시겠습니까?');

    if (deletecheck) {
        $.ajax({
            url: '/deleteBoard.do',
            type: 'POST',
            data: {boardNum: boardNum},
            dataType: 'text',
            success: function (result) {
                if (result === 'Y') {
                    alert('삭제 완료');
                    boardList2();
                } else {
                    alert('삭제 실패');
                }
            },
            error: function (error) {
                console.error('Error : ', error);
                alert('오류 발생');
            }
        });
    }
}

// 글 수정하는 함수
const modify = (boardNum, boardTitle, boardContent) => {
    let modifycheck = confirm('수정하시겠습니까?');

    if (modifycheck) {
        $.ajax({
            url: '/modify.do',
            type: 'POST',
            data: {
                boardNum: boardNum,
                boardTitle: boardTitle,
                boardContent: boardContent
            },
            dataType: 'text',
            success: function (result) {
                if (result === 'Y') {
                    alert('수정 완료');
                    moveDetail(boardNum);
                } else {
                    alert('수정 실패');
                }
            },
            error: function (error) {
                console.error('Error : ', error);
            }
        });
    }
}

// 최근 검색어 띄우는 함수
const pcookie = () => {
    let recenthtml = '';
    let words = getCookie('recentSearch');

    recenthtml += words;

    $('#recentSearch').html(recenthtml);
}

// 검색하는 함수
const search = (pageNum) => {

    if (page == null) {
        page = 1;
    }
    searchText = $("#searchtext").val();

    setCookie("recentSearch",searchText,1);

    let data = {
        'selectlist': selectlist,
        'pageNum': pageNum,
        'searchcontent': '',
        'searchtitle': '',
        'searchnum': '',
    }

    data[$("#searchType").val()] = searchText

    $.ajax({
        url: '/search.do',
        type: 'POST',
        data: data,
        dataType: 'json',
        success: function (result) {
            let tablehtml = '';

            let searchedBoard = result.searchedBoard;
            let startpage = result.startpage;
            let endpage = result.endpage;
            pageNum = result.pageNum;

            pagination(startpage, endpage);

            $.each(searchedBoard, function (index, item) {
                tablehtml += '<tr onclick="moveDetail(' + item.boardNum + ',' + pageNum + ')">' +
                    '<td>' + item.boardNum + '</td>' +
                    '<td>' + item.boardTitle + '</td>' +
                    '<td>' + item.boardContent + '</td>' +
                    '<td>' + item.board_readcount + '</td>' +
                    '<td>' + formattedDate(item.boardDate) + '</td>' +
                    '</tr>';
            });

            pcookie();
            $('#boardTable').html(tablehtml);

        }

    });
}

const pagination = (startpage, endpage, text) => {
    let pagehtml = '';

    for (var num = startpage; num <= endpage; num++) {
        pagehtml += `<span onclick="search(${num})" style="margin-right:10px; cursor:pointer;">${num}</span>`;
        // pagehtml += `<a href="javascript:void(0);" onclick="event.preventDefault(); search('${text}',${num})" style="margin-right:10px">${num}</a>`;
    }

    $('#pagetable').html(pagehtml);
}

const setCookie = (name, value, exp) => {
    let date = new Date();
    date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);
    document.cookie = `${name} = ${value}; expires= ${date.toUTCString()}; path=/`;
};

const getCookie = (name) => {
    let value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value ? value[2] : null;
};

const deleteCookie = (name) => {
    document.cookie = name + '=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
}

// const pagination = (page,totalCount) => {
//     totalCount = 74;
//     page = 3;
//     let blockCount = 5; // 블럭의 갯수 (1,2,3,4,5)
//     let preLimit = 10; // 한 페이지에 보여지는 리스트 갯수
//     let totalPage = totalCount % preLimit != 0 ? totalCount / preLimit + 1 : totalCount / preLimit; // 총 페이지의 갯수
//     let startBlock = (parseInt(page / blockCount) * blockCount) + 1;
//     let endBlock = startBlock + 4 > totalPage ? totalPage : startBlock + 4;
//
//     let html = ``;
//
//     if(page !== 1){
//         html += '<a href="#" onclick="pageMove(\'back\')">' + num + '</a>' + '&nbsp;';
//     }
//
//     for(let i = startBlock; i <= endBlock; i++){
//         html += '<a href="#" onclick="search(' + num + ')">' + num + '</a>' + '&nbsp;';
//     }
//
//     if(page !== endBlock){
//         html += '<a href="#" onclick="pageMove(\'front\')">' + num + '</a>' + '&nbsp;';
//     }
//
//     $('#pagetable').html(html);
// }
//
// const searchTxtChg = () => {
//     searchText = $("#searchtext").val();
//     search();
// }
//
// const pageMove = (clickPage) => {
//     if(clickPage === 'back'){
//         page = page-1;
//     }else if(clickPage === 'front'){
//         page = page+1;
//     }else {
//         page = clickPage;
//     }
//     search();
// }