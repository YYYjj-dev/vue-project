package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.FeedbackDao;
import com.test.pojo.Feedback;

import java.util.List;

public class FeedbackImpl extends BaseDao implements FeedbackDao {


    @Override
    public int addFeedback(Feedback feedback) {
        String sql = "insert into feedback values(DEFAULT,?,?,?,?)";
        return baseUpdate(sql,feedback.getContent(),feedback.getType(),feedback.getEmail(),feedback.getPhone());
    }

    @Override
    public List<Feedback> findFeedbackById(Integer id) {
        String sql = "select * from feedback where id = ?";
        return baseQuery(Feedback.class, sql, id);
    }

    @Override
    public int deleteFeedback(Integer id) {
        String sql = "delete from feedback where id = ?";
        return baseUpdate(sql, id);
    }
}
