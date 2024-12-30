package com.test.dao;

import com.test.pojo.Feedback;

import java.util.List;

public interface FeedbackDao {

    int addFeedback(Feedback feedback);

    List<Feedback> findFeedbackById(Integer id);

    int deleteFeedback(Integer id);

    List<Feedback> findFeedbackByType(String type);

    List<Feedback> findAllFeedback();

}
