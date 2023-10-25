package org.app.sq_game_selection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class picpic_quizpopup extends AppCompatActivity {
    Button q5,q10,q20;
    int quizp;
    int quiz =0;
    public static Context context_quiz;


    public boolean onTouchEvent(MotionEvent evente) {
        if( evente.getAction() == MotionEvent.ACTION_OUTSIDE ) {
            return false;
        }
        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picpic_activity_pop1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        q5 = (Button) findViewById(R.id.button5);
        q10 = (Button) findViewById(R.id.button10);
        q20 = (Button) findViewById(R.id.button20);
        context_quiz =this;
        Intent intent = new Intent(picpic_quizpopup.this, picpic_MainActivity.class);

        q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quiz=8;
                intent.putExtra("int",8);
                finish();
            }
        });

        q10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quiz=4;
                intent.putExtra("int",4);
                finish();

            }
        });

        q20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quiz=2;
                intent.putExtra("int",2);

                finish();
            }
        });
    }

}
