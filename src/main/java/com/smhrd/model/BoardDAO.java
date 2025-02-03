package com.smhrd.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.smhrd.database.SqlSessionManager;

public class BoardDAO {

    SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();

    // 전체 게시글 가져오는 메서드
    public List<BoardDTO> getList() {
        SqlSession session = SqlSessionManager.getSqlSession().openSession();
        List<BoardDTO> list = session.selectList("com.smhrd.database.BoardMapper.getList");
        session.close();
        return list;
    }

    // 게시글 작성하는 메서드
    public int write(BoardDTO dto) {
        SqlSession sqlSession = SqlSessionManager.getSqlSession().openSession(true);
        int result = sqlSession.insert("com.smhrd.database.BoardMapper.write", dto);
        sqlSession.close();
        return result;
    }


    // 게시글 상세보기 (게시글 번호로 조회)
    public BoardDTO getBoardContent(int idx) {
        if (sqlSessionFactory == null) {
            throw new RuntimeException("SqlSessionFactory is not initialized.");
        }

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return sqlSession.selectOne("com.smhrd.database.BoardMapper.getBoardContent", idx);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 게시글 삭제
    public int deleteBoard(int idx) {
        if (sqlSessionFactory == null) {
            throw new RuntimeException("SqlSessionFactory is not initialized.");
        }

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            int row = sqlSession.delete("com.smhrd.database.BoardMapper.deleteBoard", idx);
            sqlSession.commit(); // 커밋
            return row;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 게시글 수정
    public int updateBoard(BoardDTO dto) {
        SqlSession sqlSession = SqlSessionManager.getSqlSession().openSession(true);
        int result = sqlSession.update("com.smhrd.database.BoardMapper.updateBoard", dto);
        sqlSession.close();
        return result;
    }
}
