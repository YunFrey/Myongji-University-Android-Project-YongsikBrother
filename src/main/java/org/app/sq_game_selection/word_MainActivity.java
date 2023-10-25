package org.app.sq_game_selection;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class word_MainActivity extends AppCompatActivity {
    public static final int sub1=1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        Button start;

        start=(Button) findViewById(R.id.GameStart);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), word_SubActivity.class);
                startActivityForResult(intent,sub1);
            }
        });
    }
}