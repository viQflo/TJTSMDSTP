package com.smhrd.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.smhrd.database.SqlSessionManager;
import com.smhrd.model.BoardDTO;

public class BoardDAO {

    // SqlSessionFactory는 SqlSessionManager에서 얻습니다.
    private SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();

    
    public int insertBoard(BoardDTO board) {
        int result = 0;
        try (SqlSession sqlSession = SqlSessionManager.getSqlSession().openSession(true)) {
            result = sqlSession.insert("com.smhrd.model.BoardDAO.insertPost", board); // ✅ `insertPost`가 `BoardMapper.xml`과 일치해야 함!
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
 // 모든 게시글 가져오기
    public List<BoardDTO> getAllPosts() {
        List<BoardDTO> postList = null;
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            postList = sqlSession.selectList("com.smhrd.model.BoardDAO.getAllPosts");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(postList);
        return postList;
    }
    
 // 전체 게시글 개수 조회
    public int getTotalPostCount() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            return sqlSession.selectOne("com.smhrd.model.BoardDAO.getTotalPostCount");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 페이지별 게시글 리스트 조회
    public List<BoardDTO> getPostsByPage(int page, int pageSize) {
        List<BoardDTO> postList = null;
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            postList = sqlSession.selectList("com.smhrd.model.BoardDAO.getPostsByPage", 
                new PaginationParams(startRow, endRow));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(postList);
        return postList;
    }

    // 페이징 파라미터를 위한 내부 클래스
    public static class PaginationParams {
        private int startRow;
        private int endRow;

        public PaginationParams(int startRow, int endRow) {
            this.startRow = startRow;
            this.endRow = endRow;
        }

        public int getStartRow() {
            return startRow;
        }

        public int getEndRow() {
            return endRow;
        }
    }
    
    public BoardDTO getPostById(int postIdx) {
        BoardDTO post = null;
        SqlSession session = sqlSessionFactory.openSession(true);
        try {
            post = session.selectOne("com.smhrd.model.BoardDAO.getPostById", postIdx);
        } finally {
            session.close();
        }
        System.out.println(post);
        System.out.println("여기냐");
        return post;
    }
    
 // ✅ 조회수 1 증가
    public void incrementViewCount(int postIdx) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            session.update("com.smhrd.model.BoardDAO.incrementViewCount", postIdx);
        }
    }

    // ✅ 좋아요 1 증가
    public void incrementLikeCount(int postIdx) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            session.update("com.smhrd.model.BoardDAO.incrementLikeCount", postIdx);
        }
    }

    // ✅ 이전 게시글 번호 가져오기
    public Integer getPreviousPostIdx(int postIdx) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return session.selectOne("com.smhrd.model.BoardDAO.getPreviousPostIdx", postIdx);
        }
    }

    // ✅ 다음 게시글 번호 가져오기
    public Integer getNextPostIdx(int postIdx) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return session.selectOne("com.smhrd.model.BoardDAO.getNextPostIdx", postIdx);
        }
    }
    
    
}
