package egovframework.sample.vo;

import java.util.Date;

public class BoardVO {

    private int boardNum;
    private String boardTitle;
    private String boardContent;
    private Date boardDate;
    private int board_readcount;

    public int getBoardNum() {
        return boardNum;
    }

    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public Date getBoardDate() {
        return boardDate;
    }

    public void setBoardDate(Date boardDate) {
        this.boardDate = boardDate;
    }
    public int getBoard_readcount() {
        return board_readcount;
    }

    public void setBoard_readcount(int board_readcount) {
        this.board_readcount = board_readcount;
    }

}
