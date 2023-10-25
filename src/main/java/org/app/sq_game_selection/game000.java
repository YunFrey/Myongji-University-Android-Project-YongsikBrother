package org.app.sq_game_selection;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class game000 extends AppCompatActivity {
    Button Btn_GS_backToMenu_ran, Btn_GS_gotoGame;
    Integer whatgamenumber;
    ConstraintLayout Game2, Game3, Game4, Game5, Game6;
    MediaPlayer gstartmus;

    String passname_team1_1 = "";
    String passname_team1_2 = "";
    String passname_team1_3 = "";
    String passname_team1_4 = "";
    String passname_team1_5 = "";

    String passname_team2_1 = "";
    String passname_team2_2 = "";
    String passname_team2_3 = "";
    String passname_team2_4 = "";
    String passname_team2_5 = "";

    String passname_team3_1 = "";
    String passname_team3_2 = "";
    String passname_team3_3 = "";
    String passname_team3_4 = "";
    String passname_team3_5 = "";

    String passname_team4_1 = "";
    String passname_team4_2 = "";
    String passname_team4_3 = "";
    String passname_team4_4 = "";
    String passname_team4_5 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game000);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        gstartmus = MediaPlayer.create(game000.this, R.raw.gamestart);

        //객체생성연결
        Button Btn_GS_backToMenu_ran = (Button) findViewById(R.id.btn_GS_backToMenu_ran);
        Button Btn_GS_gotoGame = (Button) findViewById(R.id.btn_GS_gotoGame);
        Game2 = (ConstraintLayout) findViewById(R.id.game2);
        Game3 = (ConstraintLayout) findViewById(R.id.game3);
        Game4 = (ConstraintLayout) findViewById(R.id.game4);
        Game5 = (ConstraintLayout) findViewById(R.id.game5);
        Game6 = (ConstraintLayout) findViewById(R.id.game6);


        //현재 인텐트 구하기
        Intent gamestartnum = getIntent();

        //이전 인텐트에서 랜덤게임 번호 받기
        whatgamenumber = gamestartnum.getIntExtra("gamenumber", 1);
        int gameaa = whatgamenumber;



        //임시 : 현재 인텐트 구해서 팀 집어넣기
        Intent aaaintent = getIntent();
        String passname_team1 = "";
        String passname_team2 = "";
        String passname_team3 = "";
        String passname_team4 = "";
        try{
            passname_team1 = aaaintent.getStringExtra("team1name");
            passname_team2 = aaaintent.getStringExtra("team2name");
            passname_team3 = aaaintent.getStringExtra("team3name");
            passname_team4 = aaaintent.getStringExtra("team4name");
        }
        catch(Exception e){
            Log.d("에러 :","팀명이 넘어오지 않음");
        }
        switch(whatgamenumber){
            case 2:
                Game2.setVisibility(View.VISIBLE);
                break;
            case 3:
                Game3.setVisibility(View.VISIBLE);
                break;
            case 4:
                Game4.setVisibility(View.VISIBLE);
                break;
            case 5:
                Game5.setVisibility(View.VISIBLE);
                break;
            case 6:
                Game6.setVisibility(View.VISIBLE);
                break;
        }



        //메인메뉴 돌아가기(랜덤게임 포기)
        Btn_GS_backToMenu_ran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomenu = new Intent(game000.this, sqgameselect.class);
                startActivity(backtomenu);
            }
        });

        //게임시작하기 랜덤으로
        String finalPassname_team1 = passname_team1;
        String finalPassname_team2 = passname_team2;
        String finalPassname_team3 = passname_team3;
        String finalPassname_team4 = passname_team4;
        Btn_GS_gotoGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //게임액티비티는 이후 game_2,3,4 로 구분할 예정

                //차후 게임 완성 시 sqgameselect 에 실행할 게임의 액티비티 집어넣기
                //메인화면에서 받은 팀 이름 정보 다시 각 게임에 나눠주기
                switch(whatgamenumber){
                    case 2:
                        Intent game2go = new Intent(game000.this, word_SubActivity.class);
                        game2go.putExtra("team1name", finalPassname_team1);
                        game2go.putExtra("team2name", finalPassname_team2);
                        game2go.putExtra("team3name", finalPassname_team3);
                        game2go.putExtra("team4name", finalPassname_team4); gstartmus.start();
                        startActivity(game2go);
                        break;
                    case 3:
                        Intent game3go = new Intent(game000.this, picpic_MainActivity.class);
                        game3go.putExtra("team1name", finalPassname_team1);
                        game3go.putExtra("team2name", finalPassname_team2);
                        game3go.putExtra("team3name", finalPassname_team3);
                        game3go.putExtra("team4name", finalPassname_team4); gstartmus.start();
                        startActivity(game3go);
                        break;
                    case 4:
                        Intent game4go = new Intent(game000.this, music_MainActivity.class);
                        game4go.putExtra("team1name", finalPassname_team1);
                        game4go.putExtra("team2name", finalPassname_team2);
                        game4go.putExtra("team3name", finalPassname_team3);
                        game4go.putExtra("team4name", finalPassname_team4); gstartmus.start();
                        startActivity(game4go);
                        break;
                    case 5:
                        Intent game5go = new Intent(game000.this, game005.class); gstartmus.start();
                        startActivity(game5go);
                        break;
                    case 6:
                        Intent game6go = new Intent(game000.this, game006.class);
                        game6go.putExtra("team1name", finalPassname_team1);
                        game6go.putExtra("team2name", finalPassname_team2);
                        game6go.putExtra("team3name", finalPassname_team3);
                        game6go.putExtra("team4name", finalPassname_team4); gstartmus.start();
                        startActivity(game6go);


                }

            }
        });



    }
}