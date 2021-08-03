package com.gdutelc.snp;


import com.gdutelc.snp.dao.ICommentDao;
import com.gdutelc.snp.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class CommentDaoTests {

    @Autowired
    private ICommentDao iCommentDao;

    @Test
    @DisplayName("插入comment信息测试")
    void insertCommentTest(){
        Date date = new Date();
        Comment comment = new Comment(null,1,3,"hentai",date);
        Integer judge = iCommentDao.insertCommnet(comment);
        System.out.println(judge);

    }

    @Test
    @DisplayName("通过aid获得comment")
    void getCommentByAidTest(){
        List<Comment> comment = iCommentDao.getCommentByAid(1);
        System.out.println(comment.get(0).getComment());
        System.out.println(comment.get(1).getComment());
    }
    @Test
    @DisplayName("通过uid获得comment")
    void getCommentByUidTest(){
        List<Comment> comment = iCommentDao.getCommentByUid(3);
        System.out.println(comment.get(0).getComment());

    }

}
