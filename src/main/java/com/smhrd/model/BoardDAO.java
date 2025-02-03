package com.smhrd.model;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.smhrd.database.SqlSessionManager;
import com.smhrd.model.BoardDTO;

public class BoardDAO {
	private final SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();

    // 게시글 목록 조회
    public List<BoardDTO> getAllPosts() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("BoardMapper.getAllPosts");
        }
    }

    // 특정 게시글 조회
    public BoardDTO getPostById(int postIdx) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("BoardMapper.getPostById", postIdx);
        }
    }

    // 게시글 작성
    public int insertPost(BoardDTO post) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return session.insert("BoardMapper.insertPost", post);
        }
    }

    // 게시글 수정
    public int updatePost(BoardDTO post) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return session.update("BoardMapper.updatePost", post);
        }
    }

    // 게시글 삭제
    public int deletePost(int postIdx) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return session.delete("BoardMapper.deletePost", postIdx);
        }
    }
}

