package egovframework.sample.service.impl;

import egovframework.sample.mapper.TestMapper;
import egovframework.sample.vo.BoardVO;
import egovframework.sample.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("TestService")
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public  int insert(Map map){
        return testMapper.insert(map);
    }

    @Override
    public int boardinsert(BoardVO board){
        return testMapper.boardinsert(board);
    }

    @Override
    public List<BoardVO> boardList(Map lioff){
        return testMapper.boardList(lioff);
    }

    @Override
    public BoardVO detailBoard(int boardNum) {return testMapper.detailBoard(boardNum);}

    @Override
    public int deleteBoard(int boardNum){return testMapper.deleteBoard(boardNum);}

    @Override
    public int modify(Map map) {
        return testMapper.modify(map);
    }

    @Override
    public int countBoard() {
        return testMapper.countBoard();
    }

    @Override
    public List<BoardVO> search(Map map) {
        return testMapper.search(map);
    }

    @Override
    public int countsearchBoard(Map<String, Object> searchtext) {
        return testMapper.countsearchBoard(searchtext);
    }

    @Override
    public int readcount(int boardNum) {
        return testMapper.readcount(boardNum);
    }

    @Override
    public Map<String, Object> selectExample() {
        Map<String,Object> result = new HashMap<>();

        result.put("list", testMapper.selectExamples());
        return null;
    }


    @Override
    public String makeGeom(String type){
        return testMapper.makeGeom(type);
    }
}
