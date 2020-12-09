package com.example.quizkro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class questionsactivity extends AppCompatActivity {
    private TextView question,no_indicator;
    private FloatingActionButton bookmark_btn;
    private LinearLayout options_container;
    private Button share_btn,next_btn;
    private int count = 0;
    private List<QuestionModel> list;
    private int position = 0;
    private int score = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionsactivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        question = findViewById(R.id.question);
        no_indicator = findViewById(R.id.no_indicator);
        bookmark_btn = findViewById(R.id.bookmark_btn);
        options_container = findViewById(R.id.options_container);
        share_btn = findViewById(R.id.share_btn);
        next_btn = findViewById(R.id.next_btn);

        list = new ArrayList<>();
        list.add(new QuestionModel("question 1","a","b","c","d","d"));
        list.add(new QuestionModel("question 2","a","b","c","d","d"));
        list.add(new QuestionModel("question 3","a","b","c","d","c"));
        list.add(new QuestionModel("question 4","a","b","c","d","d"));
        list.add(new QuestionModel("question 5","a","b","c","d","c"));
        list.add(new QuestionModel("question 6","a","b","c","d","b"));
        list.add(new QuestionModel("question 7","a","b","c","d","d"));
        list.add(new QuestionModel("question 8","a","b","c","d","a"));

        for (int i = 0 ; i < 4 ; i++) {
            options_container.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer((Button) view);

                }
            });
        }
        playanim(question,0,list.get(position).getQuestion());
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next_btn.setEnabled(false);
                next_btn.setAlpha(0.7f);
                enableoption(true);
                position++;
                if (position == list.size()){
                    return;
                }
                count = 0;
                playanim(question,0,list.get(position).getQuestion());
            }
        });
    }
    private void playanim(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animator)
            {
                if (value == 0 && count < 4 )
                {
                    String option = "";
                    if (count == 0){
                        option = list.get(position).getOptionA();
                    }
                    else if(count == 1){
                        option = list.get(position).getOptionB();
                    }
                    else if(count == 2){
                        option = list.get(position).getOptionC();
                    }
                    else if(count == 3){
                        option = list.get(position).getOptionD();
                    }
                    playanim(options_container.getChildAt(count),0,option);
                    count++;
                }
            }

            ////@SuppressLint("SetTextI18n")
            @Override
            public void onAnimationEnd(Animator animator) {
                if (value == 0){
                    try {
                        ((TextView) view).setText(data);
                        no_indicator.setText(position+1+"/"+list.size());

                    }catch (ClassCastException ex){
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);
                    playanim(view,1,data);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    private void checkAnswer(Button selectedOption){
        enableoption(false);
        next_btn.setEnabled(true);
        next_btn.setAlpha(1);
        if (selectedOption.getText().toString().equals(list.get(position).getCorrectANS())){
            //correct
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }else{
            //incorrect
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctoption = (Button) options_container.findViewWithTag(list.get(position).getCorrectANS());
            correctoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
    }
    private void enableoption(boolean enable){
        for (int i = 0 ; i < 4 ; i++)
        {
            options_container.getChildAt(i).setEnabled(enable);
            if (enable){
                options_container.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));

            }
        }
    }

}