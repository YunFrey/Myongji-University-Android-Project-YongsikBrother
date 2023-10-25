package org.app.sq_game_selection;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;


public class music_MainActivity extends AppCompatActivity {
    // Widget 관련 변수
    ImageView imgGif;
    Button btnIntro, btnPlayAndPause, btnNext, btnTA, btnTB, btnTC, btnTD, btnTeam;
    TextView textAns;
    MediaPlayer mp = new MediaPlayer();
    //Initiate 하지 않으면 setDataResource 가 작동하지 않음(nullpointerException)
    Button btn_66_backToMenu_ran;

    int tAscore = 0;
    int tBscore = 0;
    int tCscore = 0;
    int tDscore = 0;
    boolean errorcnt = false;

    // Music 관련 변수 선언
    boolean isPlayed = false;

    /*
    int[] musics = {
            R.raw.asap, R.raw.celebrity, R.raw.fearless, R.raw.hype_boy, R.raw.jannabi,
            R.raw.lilac, R.raw.limousine, R.raw.love_dive, R.raw.psycho, R.raw.roller_coaster,
            R.raw.rollin, R.raw.russian, R.raw.step_back, R.raw.tomboy, R.raw.too_much, R.raw.two_beal
    };
    // 저작권 파일 배포 문제로 파일 스트리밍 형식으로 대체
     */

    String[] musics = {
            "http://218.48.47.14:7344/asap.mp3", "http://218.48.47.14:7344/celebrity.mp3", "http://218.48.47.14:7344/fearless.mp3", "http://218.48.47.14:7344/hype_boy.mp3", "http://218.48.47.14:7344/jannabi.mp3",
            "http://218.48.47.14:7344/lilac.mp3", "http://218.48.47.14:7344/limousine.mp3", "http://218.48.47.14:7344/love_dive.mp3", "http://218.48.47.14:7344/psycho.mp3", "http://218.48.47.14:7344/roller_coaster.mp3",
            "http://218.48.47.14:7344/rollin.mp3", "http://218.48.47.14:7344/russian.mp3", "http://218.48.47.14:7344/step_back.mp3", "http://218.48.47.14:7344/tomboy.mp3", "http://218.48.47.14:7344/too_much", "http://218.48.47.14:7344/two_beal.mp3"
    };


    String[] musicTitle = {
            "ASAP-STAYC", "Celebrity-IU", "fearless-르세라핌", "hype_boy-뉴진스",
            "사랑하긴 했었나요 스쳐가는 인연이었나요 짧지않은 우리 함께했던 시간들이 자꾸 내 마음을 가둬두네-잔나비","라일락(lilac)-IU", "리무진-비오",
            "Love dive-아이브", "Psycho-레드벨벳", "롤러코스터-청하","Rollin-브레이브걸스", "러시안룰렛-레드벨벳", "Step back-GOT the beat",
            "Tomboy-(여자)아이들","너무너무너무-아이오아이", "어떻게 이별까지 사랑하겠어 널 사랑하는 거지-악뮤"
    };

    boolean[] use_status = new boolean[musics.length];
    int current_music = (int)(Math.random() * musics.length);
    int music_cnt = 1;

    //추가 : 인터넷 연결 실패 시 값
    boolean isfailed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.music_activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Widget 바인딩
        imgGif = findViewById(R.id.imageViewCD);
        btnIntro = (Button) findViewById(R.id.buttonIntro);
        btnPlayAndPause = (Button) findViewById(R.id.buttonPlay);
        btnNext = (Button) findViewById(R.id.buttonNext);
    //    btnTeam = (Button) findViewById(R.id.buttonTeam);
        btnTA = (Button) findViewById(R.id.buttonTA);
        btnTB = (Button) findViewById(R.id.buttonTB);
        btnTC = (Button) findViewById(R.id.buttonTC);
        btnTD = (Button) findViewById(R.id.buttonTD);
        //버튼
        btn_66_backToMenu_ran = (Button) findViewById(R.id.btn_66_backToMenu_ran);

        //[중요] DB불러오기
        Intent aaaintent=getIntent();
        String passname_team1="";
        String passname_team2="";
        String passname_team3="";
        String passname_team4="";
        passname_team1=aaaintent.getStringExtra("team1name");
        passname_team2=aaaintent.getStringExtra("team2name");
        passname_team3=aaaintent.getStringExtra("team3name");
        passname_team4=aaaintent.getStringExtra("team4name");
        btnTA.setText(passname_team1);
        btnTB.setText(passname_team2);
        btnTC.setText(passname_team3);
        btnTD.setText(passname_team4);

        textAns = (TextView) findViewById(R.id.textViewTitle);
        textAns.setMovementMethod(new ScrollingMovementMethod());
        Glide.with(this).load(R.raw.album).into(imgGif);
        Intent IntentRe = new Intent(music_MainActivity.this, music_ResActivity.class);
        //Intent 에 팀값 집어넣기
        IntentRe.putExtra("team1name", passname_team1);
        IntentRe.putExtra("team2name", passname_team2);
        IntentRe.putExtra("team3name", passname_team3);
        IntentRe.putExtra("team4name", passname_team4);

        // Musics setting
        for (int i=0; i < use_status.length; i++) use_status[i] = false;
        try {
            mp.setDataSource(getApplicationContext(), Uri.parse(musics[current_music]));


        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "1)연결 실패 : "+musics[current_music], Toast.LENGTH_SHORT).show();
        }
        mp.prepareAsync();

        //mp = MediaPlayer.create(this, musics[current_music]);

        // Play & Pause Button
        btnPlayAndPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPlayed) {
                    //인터넷 음원 로드 실패했는데 플레이 시
                    if(isfailed){
                        int picked = (int)(Math.random() * musics.length);
                        try {
                            mp.setDataSource(getApplicationContext(), Uri.parse(musics[picked]));
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "4)연결 실패 : "+musics[current_music], Toast.LENGTH_SHORT).show();
                            isfailed = true;
                        }
                        mp.prepareAsync(); //재로드, 만약 문제생기면 다시 여기로 옴
                        isfailed=false;
                    }
                    else{
                        mp.start();
                        btnPlayAndPause.setText("PAUSE");
                        isPlayed = true;
                    }

                } else {
                    mp.pause();
                    btnPlayAndPause.setText("PLAY");
                    isPlayed = false;
                }
            }
        });

        //BackTomenu button
        btn_66_backToMenu_ran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPlayed = false;
                mp.stop();
                Intent backtomenu = new Intent(music_MainActivity.this, sqgameselect.class);
                //현재게임 스택에서 제외하기, 최적화 및 중복버그 방지
                backtomenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(backtomenu);
            }
        });

        // Intro Button
        btnIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPlayed = false;
                btnPlayAndPause.setText("PLAY");
                //처음으로 돌아가기
                mp.seekTo(0);
                mp.pause();
            }
        });

        //버퍼링 실패 시 알림
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                //메세지 무한출력 방지용 errorcnt flag
                if(!errorcnt){
                    errorcnt = true;
                    Toast.makeText(getApplicationContext(), "2)연결 실패 : "+musics[current_music], Toast.LENGTH_SHORT).show();
                    isfailed=true;
                    //초기화
                    mp.pause();
                    btnPlayAndPause.setText("PLAY");
                    isPlayed = false;
                }
                else{

                }


                return false;
            }
        });
        // Next Button



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //음악이 재생되는 중에 넥스트 누른 경우
                mp.pause();
                if (isPlayed) {
                    isPlayed = false;
                    btnPlayAndPause.setText("PLAY");
                }

                //음악이 재생된 경우 true로 바꿔줌
                use_status[current_music] = true;
                //음악 재생 횟수 +
                music_cnt++;

                Log.d("prev", current_music+"");

                textAns.setText(getString(R.string.whatisthissongname));


                //음악이 다 재생된 경우
                if (music_cnt==musics.length) {
                    textAns.setText(getString(R.string.gameovertext));
                    IntentRe.putExtra("a", tAscore);
                    IntentRe.putExtra("b", tBscore);
                    IntentRe.putExtra("c", tCscore);
                    IntentRe.putExtra("d", tDscore);

                    startActivity(IntentRe);

                }

                while(true) {
                    //랜덤 재생
                    int picked = (int)(Math.random() * musics.length);

                    if(!use_status[picked]) {
                        current_music = picked;
                        //illegalState 에러 방지, 기존 재생중인 음악 플레이어를 초기화
                        mp.reset();
                        //이후 인터넷서버에서 다시 음원파일을 받아와 버퍼링


                        try {
                            mp.setDataSource(getApplicationContext(), Uri.parse(musics[picked]));
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "3)연결 실패 : "+musics[current_music], Toast.LENGTH_SHORT).show();
                            isfailed = true;
                        }
                        mp.prepareAsync(); //비동기버퍼링

                        //mp = MediaPlayer.create(getBaseContext(), musics[picked]);
                        //로컬음악재생용 Deprecated
                        break;
                    }
                }
                Log.d("next", current_music+"");
                Log.d("cnt", music_cnt+"");

            }
        });

        btnTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tAscore+=3;
                //제목 출력
                if(music_cnt!=musics.length){
                    textAns.setText(musicTitle[current_music]);}
            }
        });

        btnTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tBscore+=3;
                //제목 출력
                if(music_cnt!=musics.length){
                    textAns.setText(musicTitle[current_music]);}
            }
        });

        btnTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tCscore+=3;
                //제목 출력
                if(music_cnt!=musics.length){
                    textAns.setText(musicTitle[current_music]);}
            }
        });

        btnTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tDscore+=3;
                //제목 출력
                if(music_cnt!=musics.length){
                    textAns.setText(musicTitle[current_music]);}
            }
        });



    /*    btnTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //제목 출력
                if(music_cnt!=musics.length){
                    textAns.setText(musicTitle[current_music]);}
            }
        }); */

    }
}
