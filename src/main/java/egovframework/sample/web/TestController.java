package egovframework.sample.web;


import egovframework.sample.vo.BoardVO;
import egovframework.sample.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

/*  @RequestMapping(value = "/test.do")
    @ResponseBody
    public Map<String, Object> selectAccidentName(@RequestParam Map<String, Object> commandMap, ModelMap model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        result = testService.selectExample();

        return result;
    }*/


    @RequestMapping(value = "/boardinsert.do")
    public String boardinsert(BoardVO board, Model model) {

        int result = testService.boardinsert(board);

        model.addAttribute("result", result);

        return "insertResult";
    }

    // JSTL로 리스트 출력
//    @RequestMapping(value = "/boardList.do")
//    public String boardList(Model model) {
//
//        List<BoardVO> boardList = testService.boardList();
//
//        model.addAttribute("boardList", boardList);
//        return "boardList";
//    }

    // 글 작성
    @RequestMapping(value = "/insert.do")
    @ResponseBody
    public String insert(String boardtitle, String boardcontent) {

        System.out.println(boardtitle);

        Map map = new HashMap();
        map.put("boardtitle", boardtitle);
        map.put("boardcontent", boardcontent);

        int result = testService.insert(map);

        System.out.println(result);

        if (result == 1) {
            return "Y";
        } else {
            return "N";
        }
    }

    // 게시판 목록 조회
    @RequestMapping(value = "/boardList2.do")
    @ResponseBody
    public Map<String, Object> boardList2(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "selectlist", defaultValue = "10") int selectlist) {

        Map<String, Object> result = new HashMap<>();

        int limit = selectlist;;

        int listcount = testService.countBoard(); // 총 게시물 갯수

        int maxpages = (int) Math.ceil((double) listcount / limit); // 총 페이지 갯수

        int startpage = ((pageNum - 1) / 10) * 10 + 1; // 시작 페이지수
        int endpage = startpage + 10 - 1; // 마지막 페이지수

        if (endpage > maxpages) {
            endpage = maxpages;
        }

        int offset = (pageNum - 1) * limit; // 목록 가져오기

        Map lioff = new HashMap();
        lioff.put("limit", limit);
        lioff.put("offset", offset);

        List<BoardVO> boardVOList = testService.boardList(lioff);

        result.put("boardVOList", boardVOList);
        result.put("pageNum", pageNum);
        result.put("startpage", startpage);
        result.put("endpage", endpage);

//        System.out.println(result);
//        System.out.println("result");

        return result;
    }

    // 상세 게시판
    @RequestMapping(value = "/moveDetail.do")
    @ResponseBody
    public Map<String, Object> moveDetail(int boardNum,String pageNum) {

        Map<String, Object> result = new HashMap<>();

        int rcount = testService.readcount(boardNum);

        BoardVO board = testService.detailBoard(boardNum);

        result.put("pageNum",pageNum);
        result.put("board",board);

        return result;
    }

    // 글 삭제
    @RequestMapping(value = "/deleteBoard.do")
    @ResponseBody
    public String deleteBoard(int boardNum) {

        int result = testService.deleteBoard(boardNum);

        if (result == 1) {
            return "Y";
        } else {
            return "N";
        }
    }

    // 글 수정
    @RequestMapping(value = "/modify.do")
    @ResponseBody
    public String modify(int boardNum, String boardTitle, String boardContent) {

        Map<String, Object> map = new HashMap();
        map.put("boardNum", boardNum);
        map.put("boardTitle", boardTitle);
        map.put("boardContent", boardContent);

        int result = testService.modify(map);

        if (result == 1) {
            return "Y";
        } else {
            return "N";
        }

    }

    // 검색
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public Map<String, Object> search(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "searchcontent") String searchcontent,
                                      @RequestParam(value = "searchtitle") String searchtitle,
                                      @RequestParam(value = "searchnum") String searchnum,
                                      @RequestParam(value = "selectlist", defaultValue = "10") int selectlist) {

        Map<String, Object> result = new HashMap();

        Map<String, Object> searchtext = new HashMap<>();
        searchtext.put("searchtitle", searchtitle);
        searchtext.put("searchcontent", searchcontent);
        searchtext.put("searchnum", searchnum);

        int limit = selectlist;

        int listcount = testService.countsearchBoard(searchtext); // 검색 총 갯수
        System.out.println("listcount : " + listcount);
        int startpage = ((pageNum - 1) / 10) * 10 + 1;
        int endpage = startpage + 10 - 1;

        int maxpage = (int) Math.ceil((double) listcount / limit);

        if (endpage > maxpage) {
            endpage = maxpage;
        }
        int offset = (pageNum - 1) * limit;

        Map<String, Object> map = new HashMap<>();
        map.put("offset", offset);
        map.put("searchtitle", searchtitle);
        map.put("searchcontent", searchcontent);
        map.put("searchnum", searchnum);
        map.put("limit", limit);

        List<BoardVO> searchedBoard = testService.search(map);

        System.out.println("searchedBoard" + searchedBoard);

        result.put("searchedBoard", searchedBoard);
        result.put("startpage", startpage);
        result.put("endpage", endpage);
        result.put("pageNum", pageNum);

        return result;
    }

    @RequestMapping(value = "/test.do")
    public String test() {
        return "test";
    }
}
