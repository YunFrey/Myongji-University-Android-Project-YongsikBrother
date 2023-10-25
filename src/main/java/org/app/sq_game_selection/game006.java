package org.app.sq_game_selection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class game006 extends AppCompatActivity {
    TextView Player1_1, Player2_1, Player3_1, Player4_1;
    Button Btn_66_rundice, Btn_66_gameStart, Btn_66_backToMenu_ran;
    ImageView pin1_1;

    String passname_team1 = "";
    String passname_team2 = "";
    String passname_team3 = "";
    String passname_team4 = "";

    //객체추가노가다
    TextView mark_88,mark_68,mark_48,mark_28,mark_08,mark_06,mark_04,mark_02,mark_00,mark_80,mark_60,mark_40,mark_20,mark_86,mark_84,
            mark_82,mark_66,mark_55,mark_44,mark_33,mark_22,mark_62,mark_53,mark_35,mark_26;


    int currentwho = 1; //첫 시작은 항상 처음 플레이어가


    // 방향, y값, x값
    boolean isanyonewin = false;
    char[] t1_player1_pos = {'u','8','8'}; //88은 y8, x8, 시작위치, u 는 현재 방향
    char[] t2_player1_pos = {'u','8','8'}; //88은 시작위치
    char[] t3_player1_pos = {'u','8','8'}; //88은 시작위치
    char[] t4_player1_pos = {'u','8','8'}; //88은 시작위치

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game006);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mark_88 = (TextView)findViewById(R.id.mark_88);
        mark_68 = (TextView)findViewById(R.id.mark_68);
        mark_48 = (TextView)findViewById(R.id.mark_48);
        mark_28 = (TextView)findViewById(R.id.mark_28);
        mark_08 = (TextView)findViewById(R.id.mark_08);
        mark_06 = (TextView)findViewById(R.id.mark_06);
        mark_04 = (TextView)findViewById(R.id.mark_04);
        mark_02 = (TextView)findViewById(R.id.mark_02);
        mark_00 = (TextView)findViewById(R.id.mark_00);
        mark_80 = (TextView)findViewById(R.id.mark_80);
        mark_60 = (TextView)findViewById(R.id.mark_60);
        mark_40 = (TextView)findViewById(R.id.mark_40);
        mark_20 = (TextView)findViewById(R.id.mark_20);
        mark_86 = (TextView)findViewById(R.id.mark_86);
        mark_84 = (TextView)findViewById(R.id.mark_84);
        mark_82 = (TextView)findViewById(R.id.mark_82);
        mark_66 = (TextView)findViewById(R.id.mark_66);
        mark_55 = (TextView)findViewById(R.id.mark_55);
        mark_44 = (TextView)findViewById(R.id.mark_44);
        mark_33 = (TextView)findViewById(R.id.mark_33);
        mark_22 = (TextView)findViewById(R.id.mark_22);
        mark_62 = (TextView)findViewById(R.id.mark_62);
        mark_53 = (TextView)findViewById(R.id.mark_53);
        mark_35 = (TextView)findViewById(R.id.mark_35);
        mark_26 = (TextView)findViewById(R.id.mark_26);





        Player1_1 = (TextView)findViewById(R.id.player1_1);
        Player2_1 = (TextView)findViewById(R.id.player2_1);
        Player3_1 = (TextView)findViewById(R.id.player3_1);
        Player4_1 = (TextView)findViewById(R.id.player4_1);
        Btn_66_rundice = (Button)findViewById(R.id.btn_66_rundice);
        Btn_66_gameStart = (Button)findViewById(R.id.btn_66_gameStart);
        Btn_66_backToMenu_ran = (Button)findViewById(R.id.btn_66_backToMenu_ran);
        Btn_66_gameStart.setVisibility(View.VISIBLE);


        Intent aaaintent = getIntent();

        passname_team1 = aaaintent.getStringExtra("team1name");
        passname_team2 = aaaintent.getStringExtra("team2name");
        passname_team3 = aaaintent.getStringExtra("team3name");
        passname_team4 = aaaintent.getStringExtra("team4name");


        //초기값
        int[][] yutarray ={
                // 윷놀이판을 배열로 구현
                //중간부분을 띄워서 구현
                //보기편하게 숫자로 구분, 추후 기능을 위해
                //위로 : u, 왼쪽 : l, 아래 : d, 오른쪽 : r,
                //밑좌 : a, 밑우 : s, 위우 : w
                {5,0,1,0,1,0,1,0,4},
                {0,0,0,0,0,0,0,0,0},
                {1,0,2,0,0,0,2,0,1},
                {0,0,0,2,0,2,0,0,0},
                {1,0,0,0,7,0,0,0,1},
                {0,0,0,2,0,2,0,0,0},
                {1,0,3,0,0,0,3,0,1},
                {0,0,0,0,0,0,0,0,0},
                {6,0,1,0,1,0,1,0,7}
        };

        Player1_1.setText(passname_team1+"팀\n 1번말 "+"현재위치" + t1_player1_pos[0]+t1_player1_pos[1]+t1_player1_pos[2]);
        Player2_1.setText(passname_team2+"팀\n 1번말 "+"현재위치" + t2_player1_pos[0]+t2_player1_pos[1]+t2_player1_pos[2]);
        Player3_1.setText(passname_team3+"팀\n 1번말 "+"현재위치" + t3_player1_pos[0]+t3_player1_pos[1]+t3_player1_pos[2]);
        Player4_1.setText(passname_team4+"팀\n 1번말 "+"현재위치" + t4_player1_pos[0]+t4_player1_pos[1]+t4_player1_pos[2]);
        //초기 플레이어 누군지 알려줌
        switch(currentwho){
            case 1 :
                Player1_1.setTextColor(Color.parseColor("#FF0000"));
                break;
            case 2:
                Player2_1.setTextColor(Color.parseColor("#FF0000"));
                break;
            case 3:
                Player3_1.setTextColor(Color.parseColor("#FF0000"));
                break;
            case 4:
                Player4_1.setTextColor(Color.parseColor("#FF0000"));
                break;
            default :
                break;
        }

        //초기값 설정 끝


        //버튼들구현

        //메인메뉴 돌아가기(랜덤게임 포기)
        Btn_66_backToMenu_ran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomenu = new Intent(game006.this, sqgameselect.class);
                //현재게임 스택에서 제외하기, 최적화 및 중복버그 방지
                backtomenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(backtomenu);
            }
        });

        //주사위굴리기
        Btn_66_rundice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        char[] current_player = {};
                        //플레이어수만큼 현재플레이어가 돌아가게하기
                        switch(currentwho){
                            case 1 :
                                current_player = t1_player1_pos;
                                break;
                            case 2:
                                current_player = t2_player1_pos;
                                break;
                            case 3:
                                current_player = t3_player1_pos;
                                break;
                            case 4:
                                current_player = t4_player1_pos;
                                break;
                            default :
                                //unreach함 여기는
                                current_player = t1_player1_pos;
                                break;
                        }


                        //비주얼 구현 시작
                        String input = String.valueOf(current_player[1])+String.valueOf(current_player[2]);
                        switch(input) {
                            case "88":
                                mark_88.setVisibility(View.VISIBLE);
                                break;
                            case "68":
                                mark_68.setVisibility(View.VISIBLE);
                                break;
                            case "48":
                                mark_48.setVisibility(View.VISIBLE);
                                break;
                            case "28":
                                mark_28.setVisibility(View.VISIBLE);
                                break;
                            case "08":
                                mark_08.setVisibility(View.VISIBLE);
                                break;
                            case "06":
                                mark_06.setVisibility(View.VISIBLE);
                                break;
                            case "04":
                                mark_04.setVisibility(View.VISIBLE);
                                break;
                            case "02":
                                mark_02.setVisibility(View.VISIBLE);
                                break;
                            case "00":
                                mark_00.setVisibility(View.VISIBLE);
                                break;
                            case "20":
                                mark_20.setVisibility(View.VISIBLE);
                                break;
                            case "40":
                                mark_40.setVisibility(View.VISIBLE);
                                break;
                            case "60":
                                mark_60.setVisibility(View.VISIBLE);
                                break;
                            case "80":
                                mark_80.setVisibility(View.VISIBLE);
                                break;
                            case "82":
                                mark_82.setVisibility(View.VISIBLE);
                                break;
                            case "84":
                                mark_84.setVisibility(View.VISIBLE);
                                break;
                            case "86":
                                mark_86.setVisibility(View.VISIBLE);
                                break;
                            case "66":
                                mark_66.setVisibility(View.VISIBLE);
                                break;
                            case "55":
                                mark_55.setVisibility(View.VISIBLE);
                                break;
                            case "44":
                                mark_44.setVisibility(View.VISIBLE);
                                break;
                            case "33":
                                mark_33.setVisibility(View.VISIBLE);
                                break;
                            case "22":
                                mark_22.setVisibility(View.VISIBLE);
                                break;
                            case "62":
                                mark_62.setVisibility(View.VISIBLE);
                                break;
                            case "53":
                                mark_53.setVisibility(View.VISIBLE);
                                break;
                            case "35":
                                mark_35.setVisibility(View.VISIBLE);
                                break;
                            case "26":
                                mark_26.setVisibility(View.VISIBLE);
                                break;
                        }
                            //비주얼 구현 끝





                        int gocount = dice();
                        switch(gocount){
                            case 1:
                                Toast.makeText(getApplicationContext(), "도",Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(getApplicationContext(), "개",Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Toast.makeText(getApplicationContext(), "걸",Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                Toast.makeText(getApplicationContext(), "윷",Toast.LENGTH_SHORT).show();
                                break;
                            case 5:
                                Toast.makeText(getApplicationContext(), "모",Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(getApplicationContext(), "뺵도: 미구현",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        move(gocount, current_player);

                        switch(currentwho){
                            case 1 : Player1_1.setText(passname_team1+"팀\n 1번말 "+"현재위치" + current_player[0]+current_player[1]+current_player[2]);
                                break;
                            case 2 : Player2_1.setText(passname_team2+"팀\n 1번말 "+"현재위치" + current_player[0]+current_player[1]+current_player[2]);
                                break;
                            case 3 : Player3_1.setText(passname_team3+"팀\n 1번말 "+"현재위치" + current_player[0]+current_player[1]+current_player[2]);
                                break;
                            case 4 :  Player4_1.setText(passname_team4+"팀\n 1번말 "+"현재위치" + current_player[0]+current_player[1]+current_player[2]);
                                break;
                        }



                        if(current_player[0] == 'f') {
                            Toast.makeText(getApplicationContext(), "게임 끝 : 도착", Toast.LENGTH_SHORT).show();
                            Btn_66_rundice.setVisibility(View.INVISIBLE);
                        }
                        //별 일 없을경우 여기서 currentwho +=1; 해야함

                        switch(currentwho){
                            case 1:
                                t1_player1_pos = current_player;
                                currentwho = 2;
                                break;
                            case 2:
                                t2_player1_pos = current_player;
                                currentwho = 3;
                                break;
                            case 3:
                                t3_player1_pos = current_player;
                                currentwho = 4;
                                break;
                            case 4:
                                t4_player1_pos = current_player;
                                currentwho = 1;
                                break;
                        }


                    }
                }, 3500); //상단 이벤트는 3.5초 지연 후 진행됨
                //현재 플레이어 색변경
                Player1_1.setTextColor(Color.parseColor("#FFFFFF"));
                Player2_1.setTextColor(Color.parseColor("#FFFFFF"));
                Player3_1.setTextColor(Color.parseColor("#FFFFFF"));
                Player4_1.setTextColor(Color.parseColor("#FFFFFF"));
                switch(currentwho){
                    case 1 :
                        Player1_1.setTextColor(Color.parseColor("#FF0000"));
                        break;
                    case 2:
                        Player2_1.setTextColor(Color.parseColor("#FF0000"));
                        break;
                    case 3:
                        Player3_1.setTextColor(Color.parseColor("#FF0000"));
                        break;
                    case 4:
                        Player4_1.setTextColor(Color.parseColor("#FF0000"));
                        break;
                    default :
                        break;
                }
                Toast.makeText(getApplicationContext(), "또르르(소리),윷굴러가유", Toast.LENGTH_SHORT).show();

            }
        });

        //게임시작버튼
        Btn_66_gameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Btn_66_gameStart.setVisibility(View.INVISIBLE);
                Btn_66_rundice.setVisibility(View.VISIBLE);
                //이클립스에서 구현해서 가져왔기에 비호환코드 제거필요
                //게임 시작, 게임이 끝나는 조건은 승리자가 1명이 나오는 것

            /*
            //윷값 생성
            //0은 빽도/ 1은 도/ 2는 개/ 3은 걸/ 4는 윷/ 5는 모
            int gocount = dice();
            Toast.makeText(getApplicationContext(), String.valueOf(gocount), Toast.LENGTH_SHORT).show();

            //말이 가는과정
            current_player = move(gocount, current_player);
            Player1_1.setText("1번말 "+"현재위치" + current_player[0]+current_player[1]+current_player[2]);

             */

                    //
                    //여기에 빽도일경우 추가필요, 개발기간상 추가못함 ㅠㅠ
                    //

                    //게임종료

                    //현재 플레이어값을 기존 플레이어값에 저장

                    //말겹치는지테스트

                    //현재위치 출력
                    //게임 끝 정산필요
                    //현재 플레이어 누구?
                }

            });
        }




    /*


    // 여기서부터는 기능구현 클래스


     */



    static char[] move(int gocount, char[] current_player) {
        while (gocount > 0 || current_player[0] == 'f') {                        //아직 갈 카운트가 남아있을 때까지 반복
            //도는 누군가 도착했을때까지
            switch (current_player[0]) {
                case 'u':
                    System.out.println("위" + gocount + current_player[0] + current_player[1] + current_player[2]);
                    if (current_player[1] == '0') {        //가는 길이 막히면 스탑
                        current_player[0] = 'l';            //말 방향전환
                        break;
                    }
                    current_player[1] = (char) (current_player[1] - 2); //y값이동
                    current_player[2] = (char) (current_player[2]); //x값이동
                    gocount -= 1;
                    Log.d("Move : ",String.valueOf(current_player[0])+String.valueOf(current_player[1])+String.valueOf(current_player[2]));
                    break;


                case 'l':
                    System.out.println("왼" + gocount + current_player[0] + current_player[1] + current_player[2]);
                    if (current_player[2] == '0') {        //가는 길이 막히면 스탑
                        current_player[0] = 'd';            //말 방향전환
                        break;
                    }
                    current_player[1] = (char) (current_player[1]); //y값이동
                    current_player[2] = (char) (current_player[2] - 2); //x값이동
                    gocount -= 1;
                    Log.d("Move : ",String.valueOf(current_player[0])+String.valueOf(current_player[1])+String.valueOf(current_player[2]));
                    break;

                case 'd':
                    System.out.println("밑" + gocount + current_player[0] + current_player[1] + current_player[2]);
                    if (current_player[1] == '8') {        //가는 길이 막히면 스탑
                        current_player[0] = 'r';            //말 방향전환
                        break;
                    }
                    current_player[1] = (char) (current_player[1] + 2); //y값이동
                    current_player[2] = (char) (current_player[2]); //x값이동
                    gocount -= 1;
                    Log.d("Move : ",String.valueOf(current_player[0])+String.valueOf(current_player[1])+String.valueOf(current_player[2]));
                    break;

                case 'r':
                    System.out.println("우" + gocount + current_player[0] + current_player[1] + current_player[2]);
                    if (current_player[2] == '8') {        //가는 길이 막히면 스탑
                        current_player[0] = 'f';            //승리플래그 f
                        break;
                    }
                    current_player[1] = (char) (current_player[1]); //y값이동
                    current_player[2] = (char) (current_player[2] + 2); //x값이동
                    gocount -= 1;
                    Log.d("Move : ",String.valueOf(current_player[0])+String.valueOf(current_player[1])+String.valueOf(current_player[2]));
                    break;

                case 'a':
                    System.out.println("좌밑" + gocount + current_player[0] + current_player[1] + current_player[2]);
                    if (current_player[2] == '2' && current_player[1] == '6') {
                        current_player[1] = '8'; //y값이동
                        current_player[2] = '0'; //x값이동
                        current_player[0] = 'r'; //방향전환 우측
                        gocount -= 1;
                        break;
                    }
                    if (current_player[1] == '0' && current_player[2] == '8') {
                        current_player[1] = '2'; //y값이동
                        current_player[2] = '6'; //x값이동
                        current_player[0] = 'a'; //방향전환 우측
                        gocount -= 1;
                        break;
                    }
                    current_player[1] = (char) (current_player[1] + 1); //y값이동
                    current_player[2] = (char) (current_player[2] - 1); //x값이동
                    gocount -= 1;
                    Log.d("Move : ",String.valueOf(current_player[0])+String.valueOf(current_player[1])+String.valueOf(current_player[2]));
                    break;

                case 's':
                    System.out.println("우밑" + gocount + current_player[0] + current_player[1] + current_player[2]);
                    if (current_player[2] == '6' && current_player[1] == '6') {
                        current_player[1] = '8'; //y값이동
                        current_player[2] = '8'; //x값이동
                        current_player[0] = 'f'; //승리플래그 f
                        break;
                    }
                    if (current_player[1] == '0' && current_player[2] == '0') {
                        current_player[1] = '2'; //y값이동
                        current_player[2] = '2'; //x값이동
                        current_player[0] = 's'; //방향전환 우측
                        gocount -= 1;
                        break;
                    }
                    current_player[1] = (char) (current_player[1] + 1); //y값이동
                    current_player[2] = (char) (current_player[2] + 1); //x값이동
                    gocount -= 1;
                    Log.d("Move : ",String.valueOf(current_player[0])+String.valueOf(current_player[1])+String.valueOf(current_player[2]));
                    break;

                case 'w':
                    System.out.println("우위" + gocount + current_player[0] + current_player[1] + current_player[2]);
                    if (current_player[2] == '4' && current_player[1] == '4') {
                        current_player[1] = '8'; //y값이동
                        current_player[2] = '0'; //x값이동
                        current_player[0] = 's'; //방향전환 우측
                        gocount -= 1;
                        break;
                    }
                    if (current_player[1] == '8' && current_player[2] == '0') {
                        current_player[1] = '6'; //y값이동
                        current_player[2] = '2'; //x값이동
                        current_player[0] = 'w'; //방향전환 우측
                        gocount -= 1;
                        break;
                    }
                    current_player[1] = (char) (current_player[1] - 1); //y값이동
                    current_player[2] = (char) (current_player[2] - 1); //x값이동
                    gocount -= 1;
                    Log.d("Move : ",String.valueOf(current_player[0])+String.valueOf(current_player[1])+String.valueOf(current_player[2]));
                    break;

                case 'f':
                    current_player[0] = 'f'; //승리플래그 f
                    System.out.println("게임 끝");
                    gocount = 0;
                    break;
            }
        }
        //도착 후 방향전환이 필요할 경우
        if(current_player[1] == '0' && current_player[2] == '8') {
            current_player[0] = 'a';
        }
        else if(current_player[1] == '0' && current_player[2] == '0'){
            current_player[0] = 's';
        }
        else if(current_player[1] == '8' && current_player[2] == '0'){
            current_player[0] = 'w';
        }
        else if(current_player[1] == '4' && current_player[2] == '4'){
            current_player[0] = 's';
        }
        return current_player;
    }



    static int dice() {
        // 도 = 20%, 개 = 31%, 걸 = 30%, 윷 = 12%, 모 = 7%, 실제론 던지는 방식에 의해 모도 나오기에 현실반영
        // 빽도는 개발기간 부족으로 구현X
        int gocount = (int)(Math.random()*100); //0~99까지 나옴
        if(gocount >=93) {
            gocount = 5;
        }
        else if(gocount >=81) {
            gocount = 4;
        }
        else if(gocount >=51) {
            gocount = 3;
        }
        else if(gocount >=20) {
            gocount = 2;
        }
        else {
            gocount = 1;
        }
        return gocount;
    }

}