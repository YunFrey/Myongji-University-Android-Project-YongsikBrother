package org.app.sq_game_selection;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class picpic_MainActivity extends AppCompatActivity {
    Button Ta, Tb, Tc, Td, Pass,q5,q10,q20, btn_66_backToMenu_ran;
    int Gamep=1;
    int Tap = 0;
    int Tbp = 0;
    int Tcp = 0;
    int Tdp = 0;
    int Passp = 0;
    int GameOver = 0;
    int arras = 1;
    boolean Youcangonow = false;
    TextView txt;
    ImageView PicGameIV;
    TextView game004_title2;
    int[] array;
    Handler gTimer = new Handler();
    Random rand = new Random();


    public void Cpr(int i) { //cpr은 정수를 받아 해당 숫자에 맞는 사진으로 이미지뷰 변경
            switch (i) {
                case 1:
                    PicGameIV.setImageResource(R.drawable.h01);
                    break;
                case 2:
                    PicGameIV.setImageResource(R.drawable.h02);
                    break;
                case 3:
                    PicGameIV.setImageResource(R.drawable.h03);
                    break;
                case 4:
                    PicGameIV.setImageResource(R.drawable.h04);
                    break;
                case 5:
                    PicGameIV.setImageResource(R.drawable.h05);
                    break;
                case 6:
                    PicGameIV.setImageResource(R.drawable.h06);
                    break;
                case 7:
                    PicGameIV.setImageResource(R.drawable.h07);
                    break;
                case 8:
                    PicGameIV.setImageResource(R.drawable.h08);
                    break;
                case 9:
                    PicGameIV.setImageResource(R.drawable.h09);
                    break;
                case 10:
                    PicGameIV.setImageResource(R.drawable.h10);
                    break;
                case 11:
                    PicGameIV.setImageResource(R.drawable.h11);
                    break;
                case 12:
                    PicGameIV.setImageResource(R.drawable.h12);
                    break;
                case 13:
                    PicGameIV.setImageResource(R.drawable.h13);
                    break;
                case 14:
                    PicGameIV.setImageResource(R.drawable.h14);
                    break;
                case 15:
                    PicGameIV.setImageResource(R.drawable.h15);
                    break;
                case 16:
                    PicGameIV.setImageResource(R.drawable.h16);
                    break;
                case 17:
                    PicGameIV.setImageResource(R.drawable.h17);
                    break;
                case 18:
                    PicGameIV.setImageResource(R.drawable.h18);
                    break;
                case 19:
                    PicGameIV.setImageResource(R.drawable.h19);
                    break;
                case 20:
                    PicGameIV.setImageResource(R.drawable.h20);
                    break;


            }
        }
    public void Popend(){
        Ta.setVisibility(View.VISIBLE);
        Tb.setVisibility(View.VISIBLE);
        Tc.setVisibility(View.VISIBLE);
        Td.setVisibility(View.VISIBLE);
        Pass.setVisibility(View.VISIBLE);
        game004_title2.setVisibility(View.GONE);
        q5.setVisibility(View.GONE);
        q10.setVisibility(View.GONE);
        q20.setVisibility(View.GONE);
        PicGameIV.setVisibility(View.VISIBLE);// 보여주기
    }
    public int[] randarray(int N){
        int[] randArray = new int[N+1]; //중복없는 int배열 생성(5,10,20)
        for(int i =0;i<randArray.length;i++){
            randArray[i] = rand.nextInt(20)+1;
            for(int j=0; j<i;j++){ //중복제거
                if(randArray[i]==randArray[j]){
                    i--;
                    break;
                }
            }
        }
        Cpr(randArray[0]);
        return randArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picpic_activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Ta = (Button) findViewById(R.id.ButTA);
        Tb = (Button) findViewById(R.id.ButTB);
        Tc = (Button) findViewById(R.id.ButTC);
        Td = (Button) findViewById(R.id.ButTD);
        game004_title2 = (TextView) findViewById(R.id.game004_title2);
        game004_title2.setVisibility(View.VISIBLE);
        Pass = (Button) findViewById(R.id.ButP);
        q5 = (Button) findViewById(R.id.button5);
        q10 = (Button) findViewById(R.id.button10);
        q20 = (Button) findViewById(R.id.button20);
        PicGameIV = (ImageView) findViewById(R.id.imageViewC);
        btn_66_backToMenu_ran = (Button) findViewById(R.id.btn_66_backToMenu_ran);


        ////[중요]팀 DB불러오기
        Intent aaaintent=getIntent();
        String passname_team1="";
        String passname_team2="";
        String passname_team3="";
        String passname_team4="";
        passname_team1=aaaintent.getStringExtra("team1name");
        passname_team2=aaaintent.getStringExtra("team2name");
        passname_team3=aaaintent.getStringExtra("team3name");
        passname_team4=aaaintent.getStringExtra("team4name");

        Intent IntendRes = new Intent(picpic_MainActivity.this, picpic_ResultActivity.class);

        IntendRes.putExtra("team1name", passname_team1);
        IntendRes.putExtra("team2name", passname_team2);
        IntendRes.putExtra("team3name", passname_team3);
        IntendRes.putExtra("team4name", passname_team4);
        Ta.setText(" "+passname_team1+" ");
        Tb.setText(" "+passname_team2+" ");
        Tc.setText(" "+passname_team3+" ");
        Td.setText(" "+passname_team4+" ");

        //BackTomenu button
        btn_66_backToMenu_ran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomenu = new Intent(picpic_MainActivity.this, sqgameselect.class);
                //현재게임 스택에서 제외하기, 최적화 및 중복버그 방지
                backtomenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(backtomenu);
            }
        });


        //팝업
        q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gamep = 8;
                array = randarray(5);
                Popend();
                Youcangonow = true;
            }
        });

        q10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gamep = 4;
                array = randarray(10);
                Popend();
                Youcangonow = true;
            }
        });

        q20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gamep = 3;
                array = randarray(15);
                Popend();
                Youcangonow = true;
            }
        });

        if (Youcangonow=true) {
            Ta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (arras < array.length - 1) {
                        Tap += Gamep;
                        GameOver += Gamep;
                        makeText(picpic_MainActivity.this, aaaintent.getStringExtra("team1name")+getString(R.string.picpic_ifanswercorrect), Toast.LENGTH_SHORT).show();
                        gTimer.postDelayed(new Runnable() {
                            public void run() {// 시간 지난 후 실행할 코딩
                                Cpr(array[arras]);
                            }
                        }, 3000);//3000밀리초(3초)
                        ++arras;//배열 넘기기
                    } else {

                        IntendRes.putExtra("a", Tap);
                        IntendRes.putExtra("b", Tbp);
                        IntendRes.putExtra("c", Tcp);
                        IntendRes.putExtra("d", Tdp);
                        Ta.setEnabled(false);
                        Tb.setEnabled(false);
                        Tc.setEnabled(false);
                        Td.setEnabled(false);
                        startActivity(IntendRes); //끝나는조건(패스포함 40점)
                    }
                }
            });
            Tb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (arras < array.length - 1) {
                        Tbp += Gamep;
                        GameOver += Gamep;
                        makeText(picpic_MainActivity.this, aaaintent.getStringExtra("team2name")+getString(R.string.picpic_ifanswercorrect), Toast.LENGTH_SHORT).show();
                        gTimer.postDelayed(new Runnable() {
                            public void run() {// 시간 지난 후 실행할 코딩
                                Cpr(array[arras]);
                            }
                        }, 3000);//3000밀리초(3초)
                        ++arras;//배열 넘기기
                    } else {
                        IntendRes.putExtra("a", Tap);
                        IntendRes.putExtra("b", Tbp);
                        IntendRes.putExtra("c", Tcp);
                        IntendRes.putExtra("d", Tdp);
                        Ta.setEnabled(false);
                        Tb.setEnabled(false);
                        Tc.setEnabled(false);
                        Td.setEnabled(false);
                        startActivity(IntendRes); //끝나는조건(패스포함 40점)
                    }
                }
            });


            Tc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (arras < array.length - 1) {
                        Tcp += Gamep;
                        GameOver += Gamep;
                        makeText(picpic_MainActivity.this, aaaintent.getStringExtra("team3name")+getString(R.string.picpic_ifanswercorrect), Toast.LENGTH_SHORT).show();
                        gTimer.postDelayed(new Runnable() {
                            public void run() {// 시간 지난 후 실행할 코딩
                                Cpr(array[arras]);
                            }
                        }, 3000);//3000밀리초(3초)
                        ++arras;//배열 넘기기
                    } else {
                        IntendRes.putExtra("a", Tap);
                        IntendRes.putExtra("b", Tbp);
                        IntendRes.putExtra("c", Tcp);
                        IntendRes.putExtra("d", Tdp);
                        Ta.setEnabled(false);
                        Tb.setEnabled(false);
                        Tc.setEnabled(false);
                        Td.setEnabled(false);
                        startActivity(IntendRes); //끝나는조건(패스포함 40점)
                    }
                }
            });

            Td.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (arras < array.length - 1) {
                        Tdp += Gamep;
                        GameOver += Gamep;
                        makeText(picpic_MainActivity.this, aaaintent.getStringExtra("team4name")+getString(R.string.picpic_ifanswercorrect), Toast.LENGTH_SHORT).show();
                        gTimer.postDelayed(new Runnable() {
                            public void run() {// 시간 지난 후 실행할 코딩
                                Cpr(array[arras]);
                            }
                        }, 3000);//3000밀리초(3초)
                        ++arras;//배열 넘기기
                    } else {
                        IntendRes.putExtra("a", Tap);
                        IntendRes.putExtra("b", Tbp);
                        IntendRes.putExtra("c", Tcp);
                        IntendRes.putExtra("d", Tdp);
                        Ta.setEnabled(false);
                        Tb.setEnabled(false);
                        Tc.setEnabled(false);
                        Td.setEnabled(false);
                        startActivity(IntendRes); //끝나는조건(패스포함 40점)
                    }
                }
            });

            Pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (arras < array.length - 1) {
                        Passp += Gamep;
                        GameOver += Gamep;
                        makeText(picpic_MainActivity.this, getString(R.string.picpic_ifpass), Toast.LENGTH_SHORT).show();
                        Ta.setEnabled(false);
                        Tb.setEnabled(false);
                        Tc.setEnabled(false);
                        Td.setEnabled(false);
                        gTimer.postDelayed(new Runnable() {
                            public void run() {// 시간 지난 후 실행할 코딩
                                Cpr(array[arras]);
                            }
                        }, 3000);//3000밀리초(3초)
                        ++arras;//배열 넘기기
                        Ta.setEnabled(true);
                        Tb.setEnabled(true);
                        Tc.setEnabled(true);
                        Td.setEnabled(true);
                    } else {
                        IntendRes.putExtra("a", Tap);
                        IntendRes.putExtra("b", Tbp);
                        IntendRes.putExtra("c", Tcp);
                        IntendRes.putExtra("d", Tdp);
                        startActivity(IntendRes); //끝나는조건(패스포함 40점)
                    }
                }
            });

        }
    }
}






