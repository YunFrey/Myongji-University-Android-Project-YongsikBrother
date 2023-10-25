package org.app.sq_game_selection;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class word_SubActivity extends AppCompatActivity {
    mySQLiteHelper mySQLiteHelper;

    SQLiteDatabase sqlDB;

    TextView text1; //앞 두글자 문제
    EditText text2; // 뒤 두글자 입력하는 칸
    Button check; //뒤에 두글자 입력후 맞는지 확인

    String passname_team1="";
    String passname_team2="";
    String passname_team3="";
    String passname_team4="";
    Button team1,team2,team3,team4; //팀1,2,3,4 누르면 점수 올라가도록
    Integer scoreTeam1=0,scoreTeam2=0,scoreTeam3=0,scoreTeam4=0; //각 팀의 점수

    EditText text3; //단어 입력 칸
    Button data1,data2; //데이터 입력,데이터 입력완료버튼

    Button checkData,checkScore; //저장된 데이터,점수 확인하는 버튼
    TextView text4; //저장된 데이터 보여주기
    TextView text5,text6,text7,text8; //점수 확인

    TextView timer;
    CountDownTimer countdowntimer;

    Button back; //뒤로 가기 버튼

    Button start; //문제 출력시작
    Button pass; //문제 패스 버튼
    int passnumber=1; //패스하는 문제 갯수
    int correctnumber=0,wrongnumber=0; //맞힌 갯수, 틀린 갯수

    Button gameEnd; //메인 메뉴로 돌아가기 버튼

    ImageView image; //정답인지 오답인지 애니메이션으로 보여주기

    //랜덤한 단어가 나오게 하기 위해 필요한 변수
    Random rnd =new Random();

    //저장된 단어들의 갯수와 문제 번호 변수, 지금 현재 내고있는 문제번호를 저장하는 변수
    int cnt;
    int questionNumber[];
    int number=0;

    //문제출제를 위해 필요한 임시 변수와 문제 변수
    String tmp="";
    String realquestion="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.word_activity_sub);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        text1=(TextView) findViewById(R.id.textView);
        text2=(EditText) findViewById(R.id.textView2);
        check=(Button) findViewById(R.id.Check);
        Intent aaaintent=getIntent();
        passname_team1=aaaintent.getStringExtra("team1name");
        passname_team2=aaaintent.getStringExtra("team2name");
        passname_team3=aaaintent.getStringExtra("team3name");
        passname_team4=aaaintent.getStringExtra("team4name");
        team1=(Button) findViewById(R.id.team1);
        team2=(Button) findViewById(R.id.team2);
        team3=(Button) findViewById(R.id.team3);
        team4=(Button) findViewById(R.id.team4);
        text3=(EditText) findViewById(R.id.textView3);
        data1=(Button) findViewById(R.id.insertData1);
        data2=(Button) findViewById(R.id.insertData2);
        text4=(TextView) findViewById(R.id.textView4);
        text4.setMovementMethod(new ScrollingMovementMethod());
        checkData=(Button) findViewById(R.id.checkData);
        text5=(TextView) findViewById(R.id.textView5);
        text6=(TextView) findViewById(R.id.textView6);
        text7=(TextView) findViewById(R.id.textView7);
        text8=(TextView) findViewById(R.id.textView8);
        checkScore=(Button) findViewById(R.id.checkScore);
        back=(Button) findViewById(R.id.buttonBack);
        start=(Button) findViewById(R.id.gameStart2);
        image=(ImageView) findViewById(R.id.imageView);
        pass=(Button) findViewById(R.id.buttonPass);
        timer=(TextView) findViewById(R.id.textView9);
        gameEnd=(Button) findViewById(R.id.buttonEnd);

        //DB생성
        mySQLiteHelper = new mySQLiteHelper(this);
        sqlDB = mySQLiteHelper.getWritableDatabase();
        mySQLiteHelper.onUpgrade(sqlDB,1,2);
        sqlDB.close();

        //단어 50개 넣기
        sqlDB = mySQLiteHelper.getWritableDatabase();
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '도원결의');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '누네띠네');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '스파게티');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '업데이트');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '스타워즈');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '요구르트');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '오토바이');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '파인애플');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '스타벅스');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '사자성어');"); //10개
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '비밀번호');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '생년월일');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '알레르기');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '현장학습');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '호랑나비');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '주의사항');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '탄수화물');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '비트코인');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '비트박스');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '미끄럼틀');"); //20개
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '하모니카');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '신용카드');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '아카시아');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '샌드위치');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '샌드박스');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '와이파이');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '플라스틱');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '텔레비전');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '스테이크');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '스마트폰');"); //30개
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '아이패드');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '브로콜리');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '고슴도치');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '어벤져스');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '피라미드');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '포스트잇');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '동서남북');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '모나리자');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '전라남도');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '전라북도');"); //40개
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '카카오톡');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '박테리아');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '페이스북');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '아디다스');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '일기예보');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '삼각김밥');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '페브리즈');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '크레파스');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '홈페이지');");
        sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '멜로디언');"); //50개
        sqlDB.close();

        //팀 점수 막 입력하지 못하도록 비활성화 해놓기,처음부터 pass 못 누르도록 설정
        team1.setEnabled(false);
        team2.setEnabled(false);
        team3.setEnabled(false);
        team4.setEnabled(false);
        pass.setEnabled(false);

        //문제 40개 정하기
        sqlDB = mySQLiteHelper.getReadableDatabase();
        Cursor cursor;
        cnt=0; //단어들의 갯수를 구하는 변수
        questionNumber=new int[40];//문제 갯수는 40개로 제한+ 중복되지 않게 하기 위해 필요한 변수
        cursor= sqlDB.rawQuery("SELECT * FROM groupTBL ",null);
        while(cursor.moveToNext()){
            cnt=cursor.getInt(0);
        }
        for(int i=0;i<questionNumber.length;i++) {
            questionNumber[i]=rnd.nextInt(cnt)+1;
            for(int s=0;s<i;s++){
                if(questionNumber[s]==questionNumber[i]){
                    i--;
                }
            }
        }
        sqlDB.close();

        team1.setText(passname_team1);
        team2.setText(passname_team2);
        team3.setText(passname_team3);
        team4.setText(passname_team4);

        //입력한 답이 맞는지 확인
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer="",tmp1="";
                answer=text2.getText().toString();
                tmp1=tmp.substring(2,4);
                if(answer.equals(tmp1)){
                    image.setImageResource(R.drawable.imagecorrect);
                    image.bringToFront();
                    Animation animFadein= AnimationUtils.loadAnimation(getApplication(),R.anim.fadein);
                    Animation animFadeout=AnimationUtils.loadAnimation(getApplication(),R.anim.fadeout);
                    image.startAnimation(animFadein);
                    image.startAnimation(animFadeout);
                    start.setEnabled(false);
                    pass.setEnabled(false);
                    team1.setEnabled(true);
                    team2.setEnabled(true);
                    team3.setEnabled(true);
                    team4.setEnabled(true);
                    checkData.setEnabled(true);
                    countdowntimer.cancel(); //타이머 멈추기
                    correctnumber++;
                    }else{
                    image.setImageResource(R.drawable.imagefalse);
                    image.bringToFront();
                    Animation animFadein= AnimationUtils.loadAnimation(getApplication(),R.anim.fadein);
                    Animation animFadeout=AnimationUtils.loadAnimation(getApplication(),R.anim.fadeout);
                    image.startAnimation(animFadein);
                    image.startAnimation(animFadeout);
                    wrongnumber++;
                }
            }
        });

        //데이터 입력 버튼 누르기
        data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1.setVisibility(View.INVISIBLE);
                text2.setVisibility(View.INVISIBLE);
                check.setVisibility(View.INVISIBLE);
                team1.setVisibility(View.INVISIBLE);
                team2.setVisibility(View.INVISIBLE);
                team3.setVisibility(View.INVISIBLE);
                team4.setVisibility(View.INVISIBLE);
                text3.setVisibility(View.VISIBLE);
                data1.setVisibility(View.INVISIBLE);
                data2.setVisibility(View.VISIBLE);
                checkScore.setVisibility(View.INVISIBLE);
                checkData.setVisibility(View.INVISIBLE);
                back.setVisibility(View.VISIBLE);
                start.setVisibility(View.INVISIBLE);
                timer.setVisibility(View.INVISIBLE);
                pass.setVisibility(View.INVISIBLE);
                gameEnd.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"4글자만 입력하시오.",Toast.LENGTH_SHORT).show();
            }
        });

        //데이터 입력
        data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                check.setVisibility(View.VISIBLE);
                team1.setVisibility(View.VISIBLE);
                team2.setVisibility(View.VISIBLE);
                team3.setVisibility(View.VISIBLE);
                team4.setVisibility(View.VISIBLE);
                text3.setVisibility(View.INVISIBLE);
                data1.setVisibility(View.VISIBLE);
                data2.setVisibility(View.INVISIBLE);
                checkScore.setVisibility(View.VISIBLE);
                checkData.setVisibility(View.VISIBLE);
                back.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                timer.setVisibility(View.VISIBLE);
                pass.setVisibility(View.VISIBLE);
                gameEnd.setVisibility(View.VISIBLE);
                tmp=text3.getText().toString();
                if(tmp.length()==4) {
                    sqlDB = mySQLiteHelper.getWritableDatabase();
                    sqlDB.execSQL("INSERT INTO groupTBL('gName') VALUES ( '" + text3.getText().toString() + "');");
                    sqlDB.close();
                    Toast.makeText(getApplicationContext(),"입력되었습니다.",Toast.LENGTH_SHORT).show();
                    text3.setText(null);
                }else{
                    Toast.makeText(getApplicationContext(),"입력 실패!4글자만 입력하시오.",Toast.LENGTH_SHORT).show();
                    text3.setText(null);
                }
            }
        });

        //단어들이 입력이 되었는지, 입력된 단어들이 무엇이 있는지 확인
        checkData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1.setVisibility(View.INVISIBLE);
                text2.setVisibility(View.INVISIBLE);
                check.setVisibility(View.INVISIBLE);
                team1.setVisibility(View.INVISIBLE);
                team2.setVisibility(View.INVISIBLE);
                team3.setVisibility(View.INVISIBLE);
                team4.setVisibility(View.INVISIBLE);
                data1.setVisibility(View.INVISIBLE);
                text4.setVisibility(View.VISIBLE);
                checkData.setVisibility(View.INVISIBLE);
                checkScore.setVisibility(View.INVISIBLE);
                back.setVisibility(View.VISIBLE);
                start.setVisibility(View.INVISIBLE);
                timer.setVisibility(View.INVISIBLE);
                pass.setVisibility(View.INVISIBLE);
                gameEnd.setVisibility(View.INVISIBLE);

                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursor;
                //오름차순으로 보여주기
                cursor = sqlDB.rawQuery("SELECT * FROM groupTBL order by gName asc;",null);

                String data="단어"+"\r\n"+"\r\n";

                while(cursor.moveToNext()){
                    data+=cursor.getString(1)+"  ";
                }

                text4.setText(data);

                cursor.close();
                sqlDB.close();
            }
        });

        //점수 확인 하기 버튼
        checkScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1.setVisibility(View.INVISIBLE);
                text2.setVisibility(View.INVISIBLE);
                check.setVisibility(View.INVISIBLE);
                team1.setVisibility(View.INVISIBLE);
                team2.setVisibility(View.INVISIBLE);
                team3.setVisibility(View.INVISIBLE);
                team4.setVisibility(View.INVISIBLE);
                data1.setVisibility(View.INVISIBLE);
                text5.setVisibility(View.VISIBLE);
                text6.setVisibility(View.VISIBLE);
                text7.setVisibility(View.VISIBLE);
                text8.setVisibility(View.VISIBLE);
                checkData.setVisibility(View.INVISIBLE);
                checkScore.setVisibility(View.INVISIBLE);
                back.setVisibility(View.VISIBLE);
                start.setVisibility(View.INVISIBLE);
                timer.setVisibility(View.INVISIBLE);
                pass.setVisibility(View.INVISIBLE);
                gameEnd.setVisibility(View.INVISIBLE);

                String temp1,temp2,temp3,temp4;
                temp1=passname_team1+"팀점수:"+scoreTeam1;
                temp2=passname_team2+"팀점수:"+scoreTeam2;
                temp3=passname_team3+"팀점수:"+scoreTeam3;
                temp4=passname_team4+"팀점수:"+scoreTeam4;
                text5.setText(temp1);
                text6.setText(temp2);
                text7.setText(temp3);
                text8.setText(temp4);
            }
        });

        //뒤로가기 버튼
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
                check.setVisibility(View.VISIBLE);
                team1.setVisibility(View.VISIBLE);
                team2.setVisibility(View.VISIBLE);
                team3.setVisibility(View.VISIBLE);
                team4.setVisibility(View.VISIBLE);
                text3.setVisibility(View.INVISIBLE);
                text4.setVisibility(View.INVISIBLE);
                text5.setVisibility(View.INVISIBLE);
                text6.setVisibility(View.INVISIBLE);
                text7.setVisibility(View.INVISIBLE);
                text8.setVisibility(View.INVISIBLE);
                data1.setVisibility(View.VISIBLE);
                data2.setVisibility(View.INVISIBLE);
                checkScore.setVisibility(View.VISIBLE);
                checkData.setVisibility(View.VISIBLE);
                back.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                timer.setVisibility(View.VISIBLE);
                pass.setVisibility(View.VISIBLE);
                gameEnd.setVisibility(View.VISIBLE);
            }
        });

        //게임시작 버튼,단어들이 나오기 시작하는 버튼
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check.setEnabled(true);

                ////원래 false 였던 걸 통합 시 true 로 바꿈
                team1.setEnabled(true);
                team2.setEnabled(true);
                team3.setEnabled(true);
                team4.setEnabled(true);
                //

                checkData.setEnabled(false);
                data1.setEnabled(false); //시작하고 나서는 문제 추가 불가
                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor questionCursor;

                text2.setText(null);

                if (number != 40) {
                    questionCursor = sqlDB.rawQuery("SELECT gName FROM groupTBL WHERE charId='" + questionNumber[number] + "'", null);
                    number++;
                    while (questionCursor.moveToNext()) {
                        tmp = questionCursor.getString(0);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"40문제 끝",Toast.LENGTH_SHORT).show();
                    check.setEnabled(false);
                    pass.setEnabled(false);
                    timer.setText("맞힌 갯수 : "+Integer.toString(correctnumber)+"개 PASS : "+passnumber+"개");
                    tmp="    ";  //문제 출력칸 비우기 위해서
                }

                realquestion=tmp.substring(0,2);

                text1.setText(realquestion);
                sqlDB.close();

                start.setEnabled(false);

                //타이머, 시간제한 바꾸려면 millisInFuture를 변경, 7000이 7초
                countdowntimer=new CountDownTimer(7000,1000){
                    public void onTick(long MILLISINFUTURE){
                        timer.setText("제한시간 : "+MILLISINFUTURE/1000+"초");
                    }
                    public void onFinish(){
                        timer.setText("시간 초과");
                        check.setEnabled(false);
                        checkData.setEnabled(true);
                        pass.setEnabled(true);
                        text2.setText(tmp.substring(2,4));
                    }
                }.start();
            }
        });

        //pass 버튼
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check.setEnabled(true);
                //원래 false 였던 걸 통합 시 true 로 바꿈

                team1.setEnabled(true);
                team2.setEnabled(true);
                team3.setEnabled(true);
                team4.setEnabled(true);


                pass.setEnabled(false);
                checkData.setEnabled(false);
                countdowntimer.cancel();
                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor questionCursor;

                text2.setText(null);

                if (number != 40) {
                    questionCursor = sqlDB.rawQuery("SELECT gName FROM groupTBL WHERE charId='" + questionNumber[number] + "'", null);
                    number++;
                    while (questionCursor.moveToNext()) {
                        tmp = questionCursor.getString(0);
                    }
                    Toast.makeText(getApplicationContext(),Integer.toString(passnumber)+"문제 패스",Toast.LENGTH_SHORT).show();
                    passnumber++;
                    countdowntimer=new CountDownTimer(7000,1000){
                        public void onTick(long MILLISINFUTURE){
                            timer.setText("제한시간 : "+MILLISINFUTURE/1000+"초");
                        }
                        public void onFinish(){
                            timer.setText("시간 초과");
                            check.setEnabled(false);
                            checkData.setEnabled(true);
                            pass.setEnabled(true);
                            text2.setText(tmp.substring(2,4));
                        }
                    }.start();
                }else{
                    Toast.makeText(getApplicationContext(),"40문제 끝",Toast.LENGTH_SHORT).show();
                    check.setEnabled(false);
                    pass.setEnabled(false);
                    timer.setText("맞힌 갯수 : "+Integer.toString(correctnumber)+"개 PASS : "+passnumber+"개");
                    tmp="    ";  //문제 출력칸 비우기 위해서
                }

                realquestion=tmp.substring(0,2);

                text1.setText(realquestion);
                sqlDB.close();

                start.setEnabled(false);

            }
        });

        //게임 종료하고 메인메뉴로 가는 버튼
       gameEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomenu=new Intent(word_SubActivity.this,sqgameselect.class);
                backtomenu.putExtra("teamscore_a",scoreTeam1);
                backtomenu.putExtra("teamscore_b",scoreTeam2);
                backtomenu.putExtra("teamscore_c",scoreTeam3);
                backtomenu.putExtra("teamscore_d",scoreTeam4);
                //현재게임 스택에서 제외하기, 최적화 및 중복버그 방지
                backtomenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(backtomenu);
            }
        });

        //팀 점수 올리기,점수는 한번에 한팀만 그리고 1점씩만 올릴 수 있도록 설정
        team1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //통합 시 맞춘걸로 하기 위해 count 추가
                correctnumber+=1;
                //4팀의 점수 합이 맞힌 갯수를 넘기면 안되기
                if((scoreTeam1+scoreTeam2+scoreTeam3+scoreTeam4)<correctnumber) {
                    scoreTeam1++;
                    check.setEnabled(false); text2.setText(tmp.substring(2,4));
                    team1.setEnabled(false);
                    team2.setEnabled(false);
                    team3.setEnabled(false);
                    team4.setEnabled(false);
                }else{
                    Toast.makeText(getApplicationContext(),"40문제 종료",Toast.LENGTH_SHORT).show();
                }
            }
        });
        team2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //통합 시 맞춘걸로 하기 위해 count 추가
                correctnumber+=1;
                if((scoreTeam1+scoreTeam2+scoreTeam3+scoreTeam4)<correctnumber) {
                    scoreTeam2++;
                    check.setEnabled(false); text2.setText(tmp.substring(2,4));
                    team1.setEnabled(false);
                    team2.setEnabled(false);
                    team3.setEnabled(false);
                    team4.setEnabled(false);
                }else{
                    Toast.makeText(getApplicationContext(),"40문제 종료",Toast.LENGTH_SHORT).show();
                }
            }
        });
        team3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //통합 시 맞춘걸로 하기 위해 count 추가
                correctnumber+=1;
                if((scoreTeam1+scoreTeam2+scoreTeam3+scoreTeam4)<correctnumber) {
                    scoreTeam3++;
                    check.setEnabled(false); text2.setText(tmp.substring(2,4));
                    team1.setEnabled(false);
                    team2.setEnabled(false);
                    team3.setEnabled(false);
                    team4.setEnabled(false);
                }else{
                    Toast.makeText(getApplicationContext(),"40문제 종료",Toast.LENGTH_SHORT).show();
                }
            }
        });
        team4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //통합 시 맞춘걸로 하기 위해 count 추가
                correctnumber+=1;
                if((scoreTeam1+scoreTeam2+scoreTeam3+scoreTeam4)<correctnumber) {
                    scoreTeam4++;
                    check.setEnabled(false); text2.setText(tmp.substring(2,4));
                    team1.setEnabled(false);
                    team2.setEnabled(false);
                    team3.setEnabled(false);
                    team4.setEnabled(false);
                }else{
                    Toast.makeText(getApplicationContext(),"40문제 종료",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class mySQLiteHelper extends SQLiteOpenHelper {
        public mySQLiteHelper(Context context) {
            super(context, "groupDB", null, 1);
        }
        @Override
        //charID는 각 단어들에게 아이디를 줘서 위에있는 random을 이용해서 계속 다른 단어가 나오도록 하기 위해 필요한 변수
        //AUTOINCREMENT 를 이용해서 1부터 시작해서 자동으로 늘어나도록 지정
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE groupTBL (charID INTEGER PRIMARY KEY AUTOINCREMENT, gName CHAR(20));");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL");
            onCreate(db);
        }
    }
}