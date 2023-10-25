package org.app.sq_game_selection;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent initiate = new Intent(Splashscreen.this, sqgameselect.class);
                initiate.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(initiate);
                finish();
            }
        }, 1000); //스플래시스크린은 1초 동안 보임


    }
}