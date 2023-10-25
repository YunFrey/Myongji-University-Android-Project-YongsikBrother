package org.app.sq_game_selection;

import static java.lang.Math.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class penaltygame extends AppCompatActivity {
    int selectedteam = 1; //spinner에서 선택된 팀 기본설정은 1번팀임
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
    //드럼소리 실행준비
    MediaPlayer mp;
    MediaPlayer mp_symb;

    TextView penaltylist, penaltylistwho, teampenaltylist, teampenaltylistwho;
    ArrayList<String> penaltyarray = new ArrayList<String>();
    ArrayList<String> teampenaltyarray = new ArrayList<>();


    ConstraintLayout rulletselect, rulletmenu, penaltymenu, penaltyselect;

    Button btn_pn_backToMenu, penaltystart, gorandom;

    LinearLayout penaltyselectcontainter;

    // 주기반복용 핸들러 생성
    Handler handler = new Handler(Looper.getMainLooper());

//랜덤값 입력
    int rnd;
    int memrnd;
    int counterlimit = 0;

    //팀 이름 입력받는 객체
    String passname_team1 = "";
    String passname_team2 = "";
    String passname_team3 = "";
    String passname_team4 = "";

    Spinner teamspinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penaltygame);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        //[개인 벌칙 추가] 추후 이곳에 벌칙 추가 및 제거 기능 추가가능
        penaltyarray.add("카톡 친구중에 아무나한테 전화하기");
        penaltyarray.add("카톡 친구중에 5번째 사람한테 박진영 남친짤 보내기");
        penaltyarray.add("편의점 가서 숙취해소제 사오기");
        penaltyarray.add("교수님한테 카톡 날려서 30분 내에 답장 받기");
        penaltyarray.add("친구한테 웃긴 카톡을 날려서 ㅋ 10개 이상 답장받기");
        penaltyarray.add("팀원하고 셀카 찍기");
        penaltyarray.add("자기소개서 낭독");
        penaltyarray.add("편의점 가서 최애 음식 하나 사오기");
        penaltyarray.add("친구한테 카톡으로 발 사진 보내기");

        //[팀 벌칙 추가] 추후 이곳에 벌칙 추가 및 제거 기능 추가가능
        teampenaltyarray.add("1시간 동안 영어만 사용하기");
        teampenaltyarray.add("1등 팀에게 원하는 호칭으로 불러주기");
        teampenaltyarray.add("포스트잇 붙이고 안면으로 떼기");
        teampenaltyarray.add("눈치게임으로 한명만 몰아주기 > 지금 바로 시작!");
        teampenaltyarray.add("30분 동안 ㅇ 받침 무조건 쓰기 (예 : 앙녕항셍용)");
        teampenaltyarray.add("1시간 동안 한본어만 사용하기");
        teampenaltyarray.add("1시간 동안 영어만 사용하기");


        //[객체 생성]
        teamspinner = (Spinner) findViewById(R.id.teamspinner);
        penaltyselect = (ConstraintLayout) findViewById(R.id.penalty_select);
        rulletselect = (ConstraintLayout) findViewById(R.id.rullet_select);
        penaltymenu = (ConstraintLayout) findViewById(R.id.penalty_menu);
        rulletmenu = (ConstraintLayout) findViewById(R.id.rullet_menu);
        btn_pn_backToMenu = (Button) findViewById(R.id.btn_pn_backToMenu_ran);
        penaltylist = (TextView) findViewById(R.id.penaltylist);
        penaltylistwho = (TextView) findViewById(R.id.penaltylistwho);
        teampenaltylist = (TextView) findViewById(R.id.penaltylist2);
        teampenaltylistwho = (TextView) findViewById(R.id.penaltylistwho2);
        penaltystart = (Button) findViewById(R.id.penalty_start);
        gorandom = (Button) findViewById(R.id.btn_gorandom);
        penaltyselectcontainter = (LinearLayout) findViewById(R.id.penaltyselectteamcontainer);


        //플레이어 준비
        mp = MediaPlayer.create(penaltygame.this, R.raw.mp_drumsound);
        mp_symb = MediaPlayer.create(penaltygame.this, R.raw.mp_symber);

        //임시 : 현재 인텐트 구해서 팀 집어넣기
        Intent aaaintent = getIntent();
        try{
            passname_team1_1 = aaaintent.getStringExtra("passname_team1_1");
            passname_team1_2 = aaaintent.getStringExtra("passname_team1_2");
            passname_team1_3 = aaaintent.getStringExtra("passname_team1_3");
            passname_team1_4 = aaaintent.getStringExtra("passname_team1_4");
            passname_team1_5 = aaaintent.getStringExtra("passname_team1_5");

            passname_team2_1 = aaaintent.getStringExtra("passname_team2_1");
            passname_team2_2 = aaaintent.getStringExtra("passname_team2_2");
            passname_team2_3 = aaaintent.getStringExtra("passname_team2_3");
            passname_team2_4 = aaaintent.getStringExtra("passname_team2_4");
            passname_team2_5 = aaaintent.getStringExtra("passname_team2_5");

            passname_team3_1 = aaaintent.getStringExtra("passname_team3_1");
            passname_team3_2 = aaaintent.getStringExtra("passname_team3_2");
            passname_team3_3 = aaaintent.getStringExtra("passname_team3_3");
            passname_team3_4 = aaaintent.getStringExtra("passname_team3_4");
            passname_team3_5 = aaaintent.getStringExtra("passname_team3_5");

            passname_team4_1 = aaaintent.getStringExtra("passname_team4_1");
            passname_team4_2 = aaaintent.getStringExtra("passname_team4_2");
            passname_team4_3 = aaaintent.getStringExtra("passname_team4_3");
            passname_team4_4 = aaaintent.getStringExtra("passname_team4_4");
            passname_team4_5 = aaaintent.getStringExtra("passname_team4_5");
        }
        catch(Exception e){
            Log.d("에러 :","팀 멤버명단이 넘어오지 않음");
        }

        //[호출] 단체게임 랜덤돌리기 시작버튼
        gorandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                //핸들러에 재귀함수 적용해서 지연반복 구현
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        teampenaltylistwho.setVisibility(View.VISIBLE);
                        gorandom.setVisibility(View.GONE);
                        teampenaltylist.setVisibility(View.VISIBLE);
                        //counterlimit 가 20 이하일 경우
                        if(counterlimit<43){
                            rnd = (int) (Math.random()*teampenaltyarray.size());
                            teampenaltylist.setText(teampenaltyarray.get(rnd));
                            counterlimit+=1;
                            handler.postDelayed(this,100);
                        }
                        //counterlimit 가 20이상일 경우
                        else{
                            memrnd = (int) ((Math.random() * 4) + 1);
                            //심벌즈 울리고
                            mp_symb.start();
                            //핸들러 탈출
                        }
                    }
                }, 100); //상단 이벤트는 3초 지연 후 진행됨
                counterlimit = 0;
            }
        });


        //[호출]
        //1명만 걸려라 실행!
        penaltystart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                penaltystart.setVisibility(View.INVISIBLE);
                penaltylist.setVisibility(View.VISIBLE);
                penaltyselectcontainter.setVisibility(View.GONE);
                mp.start();
                    //핸들러에 재귀함수 적용해서 지연반복 구현
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //counterlimit 가 20 이하일 경우
                            if(counterlimit<43){
                                rnd = (int) (Math.random()*penaltyarray.size());
                                penaltylist.setText(penaltyarray.get(rnd));
                                counterlimit+=1;
                                handler.postDelayed(this,100);
                            }
                            //counterlimit 가 20이상일 경우
                            else{
                                penaltylistwho.setVisibility(View.VISIBLE);
                                memrnd = (int) ((Math.random() * 4) + 1);
                                switch(selectedteam){
                                    case 1:
                                        switch(memrnd){
                                            case 1:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team1_1);
                                                break;
                                            case 2:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team1_2);
                                                break;
                                            case 3:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team1_3);
                                                break;
                                            case 4:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team1_4);
                                                break;
                                        }
                                        break;
                                    case 2:
                                        switch(memrnd){
                                            case 1:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team2_1);
                                                break;
                                            case 2:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team2_2);
                                                break;
                                            case 3:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team2_3);
                                                break;
                                            case 4:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team2_4);
                                                break;
                                        }
                                        break;
                                    case 3:
                                        switch(memrnd){
                                            case 1:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team3_1);
                                                break;
                                            case 2:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team3_2);
                                                break;
                                            case 3:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team3_3);
                                                break;
                                            case 4:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team3_4);
                                                break;
                                        }
                                        break;
                                    case 4:
                                        switch(memrnd){
                                            case 1:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team4_1);
                                                break;
                                            case 2:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team4_2);
                                                break;
                                            case 3:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team4_3);
                                                break;
                                            case 4:
                                                penaltylistwho.setText(getString(R.string.atariman)+passname_team4_4);
                                                break;
                                        }
                                        break;
                                }




                                //심벌즈 울리고
                                mp_symb.start();
                                //핸들러 탈출
                            }
                        }
                    }, 100); //상단 이벤트는 3초 지연 후 진행됨
                counterlimit = 0;
                



            }
        });

        //개인벌칙메뉴 진입
        penaltyselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                penaltyselect.setVisibility(View.GONE);
                rulletselect.setVisibility(View.GONE);
                penaltymenu.setVisibility(View.VISIBLE);
            }
        });
        //단체벌칙메뉴 진입
        rulletselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rulletselect.setVisibility(View.GONE);
                penaltyselect.setVisibility(View.GONE);
                rulletmenu.setVisibility(View.VISIBLE);
            }
        });
        //메인메뉴 돌아가기 버튼
        btn_pn_backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //인텐트 뒤로가기메뉴
                Intent backtomenu = new Intent(penaltygame.this, sqgameselect.class);
                //현재게임 스택에서 제외하기, 최적화 및 중복버그 방지
                backtomenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mp.reset();
                startActivity(backtomenu);
            }
        });






        //스피너 객체
        String[] mem_items = {aaaintent.getStringExtra("passname_team1"),
                aaaintent.getStringExtra("passname_team2"),
                aaaintent.getStringExtra("passname_team3"),
                aaaintent.getStringExtra("passname_team4")
        };

        ArrayAdapter<String> member_adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, mem_items);
        member_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamspinner.setAdapter(member_adapter);
        teamspinner.setSelection(0);

        //스피너 액션
        teamspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedteam = i+1;
                teamspinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

    });
}
    //기존 뒤로가기 버튼 메소드 오버라이드
    @Override
    public void onBackPressed() {
        mp.reset();
        finish();
    }


}

