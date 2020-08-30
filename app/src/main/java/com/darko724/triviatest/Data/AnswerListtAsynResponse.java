package com.darko724.triviatest.Data;

import com.darko724.triviatest.Model.Question;

import java.util.ArrayList;

public interface AnswerListtAsynResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
