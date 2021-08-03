package com.gdutelc.snp.dao;

import com.gdutelc.snp.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author kid
 */

@Component
public interface ICommentDao {



    /**
     * 通过comment类插入comment的信息
     *
     * @param comment comment类
     * @return  插入是否成功
     */
    Integer insertCommnet(Comment comment);

    /**
     * 通过aid获得comment
     *
     * @param aid 管理员id
     * @return  comment
     */
    List<Comment> getCommentByAid(Integer aid);

    /**
     * 通过uid获得comment
     *
     * @param uid 用户id
     * @return  comment
     */
    List<Comment> getCommentByUid(Integer uid);

//    /**
//     * 通过aid删除comment
//     *
//     * @param aid 管理员id
//     * @return  成功条数
//     */
//    Integer deleteCommentByAid(Integer aid);
//
//    /**
//     * 通过uid删除comment
//     *
//     * @param uid 用户id
//     * @return  成功条数
//     */
//    Integer deleteCommentByUid(Integer uid);



}
