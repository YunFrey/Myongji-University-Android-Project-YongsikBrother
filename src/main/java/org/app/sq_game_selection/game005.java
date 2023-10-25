package org.app.sq_game_selection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.media.MediaPlayer;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import android.os.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class game005 extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Button Btn_sv,Btn_55_backToMenu, Presenter_clear, Presenter_viewres, Presenter_start;
    EditText Edit_Nickname;
    ConstraintLayout Presenter_select, Customer_select, Presenter_menu, Customer_menu;
    Chronometer chronometer;
    TextView Presenter_adminres, Presenter_customerres;
    Button Btn_pp_confirm;
    LinearLayout Cautionmenu_Layout, maxrankviewlayout;
    //시보음 음악의 상태 스위치 객체
    Switch switchsibo;
    Spinner memspinner;
    int memlimit = 3; //최대표시등수 : 3명까지임

    //시보음 음악은 매번 켜져있는 게 기본설정임
    boolean musicbbb = true;
    boolean issubmitted = false;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game005);

        //[객체 생성]
        Presenter_select = (ConstraintLayout) findViewById(R.id.presenter_select);
        Customer_select = (ConstraintLayout) findViewById(R.id.customer_select);
        Presenter_menu = (ConstraintLayout) findViewById(R.id.presenter_menu);
        Customer_menu = (ConstraintLayout) findViewById(R.id.customer_menu);
        Btn_55_backToMenu = (Button) findViewById(R.id.btn_55_backToMenu_ran);
        Presenter_clear = (Button) findViewById(R.id.presenter_clear);
        Presenter_viewres = (Button) findViewById(R.id.presenter_viewres);
        Presenter_start = (Button) findViewById(R.id.presenter_start);
        chronometer = (Chronometer)findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        Presenter_adminres = (TextView) findViewById(R.id.presenter_adminres);
        Presenter_customerres = (TextView) findViewById(R.id.presenter_customerres);
        switchsibo = (Switch) findViewById(R.id.switchsibo);
        Btn_pp_confirm = (Button) findViewById(R.id.btn_pp_Confirm);
        Cautionmenu_Layout = (LinearLayout) findViewById(R.id.cautionMenu_Layout);
        memspinner = (Spinner) findViewById(R.id.memspinner);
        maxrankviewlayout = (LinearLayout) findViewById(R.id.maxrankviewlayout);

        //[게임 시작]
        Presenter_select.setVisibility(View.VISIBLE);
        Customer_select.setVisibility(View.VISIBLE);
        maxrankviewlayout.setVisibility(View.VISIBLE);


        //크로노미터 10:00:00 로 보이게 초기화
        long inittime = 10 * 3600000 + 0 * 60000 + 0 * 1000;
        chronometer.setBase(SystemClock.elapsedRealtime()-(inittime));

        //시보음 배경음 토글 리스너
        switchsibo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    musicbbb = true;
                }else{
                    musicbbb = false;
                }
            }
        });

        //화면에서 방장 모드 선택 시
        Presenter_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setVisibility(View.VISIBLE);
                Presenter_clear.setVisibility(View.VISIBLE);
                Presenter_start.setVisibility(View.VISIBLE);
                Presenter_select.setVisibility(View.GONE);
                Customer_select.setVisibility(View.GONE);
                Presenter_menu.setVisibility(View.VISIBLE);
            }
        });

        //화면에서 참가자 모드 선택 시
        Customer_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Presenter_select.setVisibility(View.GONE);
                Customer_select.setVisibility(View.GONE);
                Customer_menu.setVisibility(View.VISIBLE);
            }
        });

        //뒤로가기
        Btn_55_backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //메뉴 정리
                chronometer.setVisibility(View.GONE);
                Presenter_clear.setVisibility(View.GONE);
                Presenter_select.setVisibility(View.VISIBLE);
                Customer_select.setVisibility(View.VISIBLE);
                Presenter_menu.setVisibility(View.GONE);
                Customer_menu.setVisibility(View.GONE);
                Presenter_viewres.setVisibility(View.GONE);
                Presenter_adminres.setVisibility(View.INVISIBLE);
                Presenter_customerres.setVisibility(View.INVISIBLE);
                //나갔다 다시 들어올 때 stack 에서 액티비티 제거
                //한 세션에 한번만 신청가능하게 제한
                issubmitted = false;

                //인텐트 뒤로가기메뉴
                Intent backtomenu = new Intent(game005.this, sqgameselect.class);
                //현재게임 스택에서 제외하기, 최적화 및 중복버그 방지
                backtomenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(backtomenu);
            }
        });

        //방장모드 시작 버튼 누를 시
        Presenter_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter_clear.setVisibility(View.GONE);
                Presenter_start.setVisibility(View.GONE);

                //포맷 초기화
                chronometer.setFormat("%s");
                //9:59:50부터 시작, 추후 옵션 버튼을 통해 시작시간 조정 가능
                long atime = 9 * 3600000 + 59 * 60000 + 50 * 1000;
                chronometer.setBase(SystemClock.elapsedRealtime()-(atime));
                //chronometer 가 59분 50초부터 실행되게 함
                chronometer.start();
                //미디어플레이어 객체에서 음악 시작
                MediaPlayer m;
                m = MediaPlayer.create(game005.this, R.raw.siboeum);
                if(musicbbb){
                    m.start();
                }


                //시작버튼 누르고 10초 기다리기

                //시작시간 조정시 음악은 항상 10초 전에 실행돼야하니 PostDelayed Handler 앞에 시간 추가하기
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Presenter_clear.setVisibility(View.VISIBLE);
                        Presenter_viewres.setVisibility(View.VISIBLE);

                        //10초후 DB에 방장의 타임값을 DB에 저장
                        String getGamerName = "Admin"+String.valueOf(System.currentTimeMillis());

                        //어드맨 권한으로 DB에 Admin 데이터베이스 설정, 이때 user(id(null, systime)) 으로 기록됨
                        writeNewAdmin(getGamerName);

                        //게임 종료 메세지출력
                        Toast.makeText(getApplicationContext(), getString(R.string.gameovertext), Toast.LENGTH_SHORT).show();

                        //users 하위에서 값 추가 이벤트 리스너 시작
                        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int counter = 0;
                                String winnerlist = "";

                                //스냅샷을 가져와 크기만큼 데이터 검색
                                for(DataSnapshot a : snapshot.getChildren()){
                                    counter+=1;
                                    winnerlist += counter+getString(R.string.rankformat)+a.getKey().toString().substring(14)+"\n";
                                    //winnerlist 에 데이터 지속추가
                                    Presenter_customerres.setText(winnerlist);

                                    //5명 등수까지만 보여줌
                                    /*
                                    * 버그수정필요 : 추후 몇명까지 보일지 지정가능 중복으로 보이는 경우 제외 필요함 버그임
                                    * 버그수정필요 : 마지막 행에 Admin 의 id 가 보임, 추후 필터로 제거필요
                                    */
                                    if(counter == memlimit){
                                        break;
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                //캔슬 시 딱히 할거 없음
                            }
                        });
                    }
                }, 10000); //상단 이벤트는 10초 지연 후 진행됨


            }
        });

        //게임 종료 후 결과보기창 실행
        Presenter_viewres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maxrankviewlayout.setVisibility(View.INVISIBLE);
                Presenter_adminres.setVisibility(View.VISIBLE);
                Presenter_customerres.setVisibility(View.VISIBLE);
                Presenter_clear.setVisibility(View.GONE);
                Presenter_viewres.setVisibility(View.GONE);
                chronometer.setVisibility(View.GONE);
                //readUser 클래스 통해 결과 불러오기
                readUser();

            }
        });

        //초기화 시 FireBase DB가 초기화됨, 추후 방을 만들게 되면 추가적인 DB구조 생성이 필요함
        Presenter_clear.setOnClickListener(new View.OnClickListener() {
            //초기화 버튼 누르면 모든 DB가 지워짐
            @Override
            public void onClick(View view) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //초기화 시 게임 시간 10:00:00 로 세팅됨
                long cleartime = 10 * 3600000 + 0 * 60000 + 0 * 1000;
                chronometer.setBase(SystemClock.elapsedRealtime()-(cleartime));
                chronometer.stop();
                Presenter_start.setVisibility(View.VISIBLE);
            }
        });
        //스피너 객체
        String[] mem_items = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        ArrayAdapter<String> lang_adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, mem_items);
        lang_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memspinner.setAdapter(lang_adapter);
        memspinner.setSelection(3);

        //스피너 액션
        memspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                memlimit = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //파이어베이스 객체 생성
        Btn_sv = (Button) findViewById(R.id.btn_sv);
        Edit_Nickname = (EditText) findViewById(R.id.edit_Nickname);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //파이어베이스 초기화
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //https://console.firebase.google.com/project/sugang-de804/database/sugang-de804-default-rtdb/data

        //수강신청 버튼 hashmap 은 정보를 database 에 추가하기 위한 임시구조
        Btn_sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!issubmitted){
                    String timestampid = String.valueOf(System.currentTimeMillis())+"+"+Edit_Nickname.getText().toString();;
                    //hashmap 만들기
                    HashMap result = new HashMap<>();
                    result.put("timestampid", "");
                    writeNewUser(timestampid);

                }
                else{
                    Cautionmenu_Layout.setVisibility(View.VISIBLE);
                    Btn_sv.setVisibility(View.INVISIBLE);
                }


            }
        });

        //알림창 끄는 확인 버튼
        Btn_pp_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cautionmenu_Layout.setVisibility(View.GONE);
                Btn_sv.setVisibility(View.VISIBLE);
            }
        });




    }

    //어드민 권한으로 DB에 추가하는 클래스, 제한 없이 생성가능
    private void writeNewAdmin(String timestampid) {
        User user = new User(timestampid, "");
        mDatabase.child("users").child(timestampid).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }

    //새로운정보입력, addListenerForSignleValueEvent 사용해 신청 버튼 한번 누를때만 실행, 단 방장의 DB가 안올라왔으면 못올림
    //방장의 DB는 10시 정각에 업로드됨
    //버그수정필요 : 클라이언트에서 DB에 데이터를 쓸지말지를 정하는 방식이기에 추후 구조상 알고리즘 변경 필요
    
    private void writeNewUser(String timestampid) {
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(User.class) == null){
                    //snapshot DB에 아무런 값이 없을 경우
                    Toast.makeText(getApplicationContext(), getString(R.string.serveropensat10pm), Toast.LENGTH_SHORT).show();
                }
                else {
                    //DB에 항목이 있을 경우(ex : 어드민이 서버에 admin 을 추가했을 경우)
                    writeNewAdmin(timestampid);
                    //성공창띄우기
                    issubmitted=true;
                    Toast.makeText(getApplicationContext(), getString(R.string.submitsuccess), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //데이터 변경 시마다 데이터를 계속 불러움, addValueEventListner 사용
    private void readUser(){
        mDatabase.child("users").child("Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(User.class) != null){
                    User post = dataSnapshot.getValue(User.class);
                    Presenter_adminres.setText(post.getGamerNick()+post.getTime());
                } else {
                    Toast.makeText(getApplicationContext(), "로그 : noData", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
}

}





