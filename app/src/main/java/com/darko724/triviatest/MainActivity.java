package com.darko724.triviatest;



import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.darko724.triviatest.Data.AnswerListtAsynResponse;
import com.darko724.triviatest.Data.Question_Bank;
import com.darko724.triviatest.Model.Question;
import com.darko724.triviatest.util.Prefs;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.darko724.triviatest.R.id.False;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private TextView counter , high_score;
private TextView Question,ScoreCard;
private Button True;
private Button False;
private ImageButton next;
private ImageButton previous;
private int current_question_index = 0;
private  List<Question> questionsList;
private CardView cardView;
private int score = 0;
private Score scoreObj;
private Prefs prefs;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = findViewById(R.id.counter);
        Question = findViewById(R.id.screen_for_question);
        True = findViewById(R.id.True);
        False = findViewById(R.id.False);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        cardView = findViewById(R.id.cardView);
        ScoreCard = findViewById(R.id.scoreCard);
        scoreObj = new Score();
        high_score = findViewById(R.id.highest_score);

        scoreObj.setScore(score);
        True.setOnClickListener(this);
        False.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        prefs = new Prefs(MainActivity.this);


        current_question_index = prefs.getState();
        high_score.setText("High Score: "+(prefs.getHighScore()));
    System.out.println(prefs.getHighScore());

   questionsList =  new Question_Bank().getQuestion(new AnswerListtAsynResponse() {
          @Override
          public void processFinished(ArrayList<Question> questionArrayList) {
           Question.setText(questionArrayList.get(current_question_index).getQuestion());
           counter.setText(
                   current_question_index+"/"+questionArrayList.size());

          }
      });
    }

    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.previous:
                if(current_question_index > 0){
                current_question_index = (current_question_index - 1)%questionsList.size();}
                updateQuestion();
                break;

            case R.id.next:
                prefs.saveHighScore(score);

                updateQuestion();
                break;

            case R.id.True:
            {checkAnswer(true);

                updateQuestion();}
                break;

            case R.id.False:
            {updateQuestion();
                current_question_index = (current_question_index + 1)%questionsList.size();
                checkAnswer(false);}
                break;
        }
    }

    private void checkAnswer(boolean b) {
        boolean check = questionsList.get(current_question_index).isAnswer();
        int toastMessageId = 0;
        if(check == b){
            toastMessageId = R.string.correct_ans;
            fade();
            score +=100;

        }
        else{
            toastMessageId = R.string.wrong_ans;
            shakeAnimation();
            if(score>0)
            {score -= 100;}
        }
        Toast.makeText(this, toastMessageId,Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion(){
        String question = questionsList.get(current_question_index).getQuestion();
        Question.setText(question);
        counter.setText(current_question_index+"/"+questionsList.size());
        ScoreCard.setText("Score  : "+score);
        }
    private void shakeAnimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }


            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                current_question_index = (current_question_index + 1)%questionsList.size();
                updateQuestion();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });}
    public void fade() {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f ,0.5f);
            alphaAnimation.setDuration(350);
            alphaAnimation.setRepeatCount(1);
            alphaAnimation.setRepeatMode(Animation.REVERSE);
            cardView.setAnimation(alphaAnimation);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    cardView.setCardBackgroundColor(Color.BLUE);

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    current_question_index = (current_question_index + 1)%questionsList.size();
                    updateQuestion();

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

    }

    @Override
    protected void onPause() {
prefs.saveHighScore(score);
prefs.setState(current_question_index);
        super.onPause();
    }
}


