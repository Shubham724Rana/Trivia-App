package com.darko724.triviatest.Data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.darko724.triviatest.Controller.AppController;
import com.darko724.triviatest.Model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Question_Bank {
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
            private ArrayList<Question > MQuestion = new ArrayList<Question>();
    public List<Question> getQuestion(final AnswerListtAsynResponse callBack)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("JSON STUFF", "onResponse: "+response);
                for (int i = 0 ; i<=response.length();i++){
                    try {
                       Question question = new Question();
                       question.setQuestion(response.getJSONArray(i).get(0).toString());
                       question.setAnswer(response.getJSONArray(i).getBoolean(1));
                        MQuestion.add(question);
                        Log.d("question1", "onResponse: "+question);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if(null!= callBack){
                    callBack.processFinished(MQuestion);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return MQuestion;
    }
}
