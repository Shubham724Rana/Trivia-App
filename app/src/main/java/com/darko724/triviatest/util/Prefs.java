package com.darko724.triviatest.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Prefs {
private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(Context.MODE_PRIVATE);
    }
    public void saveHighScore(int score){
        int CurrentScore = score;
      int Last_score  =   preferences.getInt("high_score",0);
        if(Last_score<CurrentScore){
          preferences.edit().putInt("HighestScore", CurrentScore).apply();

        }


    }
    public  int getHighScore(){
           return  preferences.getInt("HighestScore", 0);

    }
    public void setState(int index){
        preferences.edit().putInt( "index_state", index).apply();
    }

    public int getState(){
        return preferences.getInt("index_state",0);
    }
}
