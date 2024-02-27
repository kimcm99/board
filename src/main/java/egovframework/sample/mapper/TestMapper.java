package egovframework.sample.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.sample.vo.BoardVO;

import java.util.List;
import java.util.Map;

@Mapper("TestMapper")
public interface TestMapper {

    List<EgovMap> selectExamples();

    String makeGeom(String type);

    int boardinsert(BoardVO board);

    List<BoardVO> boardList(Map lioff);

    int insert(Map map);

    BoardVO detailBoard(int boardNum);

    int deleteBoard(int boardNum);

    int modify(Map map);

    int countBoard();

    List<BoardVO> search(Map map);

    int countsearchBoard(Map<String, Object> searchtext);

    int readcount(int boardNum);
}
