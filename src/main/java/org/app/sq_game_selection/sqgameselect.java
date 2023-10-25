package org.app.sq_game_selection;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Stack;

public class sqgameselect extends AppCompatActivity {

    //글로벌 파라미터
    Configuration config = new Configuration();
    SharedPreferences preferences;
    String locale;
    int locale_number;
    private final long finishstamp = 1000;
    private long pressedstamp = 0;

    //다른 액티비티에서 팀 점수 받을 때 저장할 Integer 변수 초기화
    Integer scorepass_team1 =0, scorepass_team2=0, scorepass_team3=0, scorepass_team4=0;

    //SQLite 관련 참조변수 생성
    mySQLiteHelper mySQLiteHelper;
    SQLiteDatabase sqlDB;

    //Mediaplayer
    MediaPlayer mpmariotiring, errorpip, sheek, dudungtak, huuk;


    //메인메뉴 화면
    Button Btn_GameSelect, Btn_TeamCreate, Btn_Rullet, Btn_BonusMenu, Btn_TeamCheck, Btn_RankCheck, Btn_Settings, Btn_ScoresetMenu;
    LinearLayout Main_MenuGrid;

    //팀 만들기 화면
    Button Btn_TC_Quit, Btn_TC_DBClear;
    ConstraintLayout TeamCreate_ConstLayout;
    ImageButton TC_Team1_Btn, TC_Team2_Btn, TC_Team3_Btn, TC_Team4_Btn;
    TextView TC_Team1_Text, TC_Team2_Text, TC_Team3_Text, TC_Team4_Text;
    EditText EditTextTeamName, TeamCreate_Member1, TeamCreate_Member2, TeamCreate_Member3, TeamCreate_Member4, TeamCreate_Member5;
    TextView TeamCreate_helptext, EditTextTeamNameInfo;
    int currentTeam = 0; //현재 수정중인 팀 지정 변수 초기화

    //게임 선택 화면
    ImageButton Btn_GS_BackToMenu;
    ConstraintLayout GameSelect_ConstLayout;
    ConstraintLayout Game1; //랜덤게임 객체
    ConstraintLayout Game2; //이어말하기 객체
    ConstraintLayout Game3; //사진맞추기 객체
    ConstraintLayout Game4; //박세미 객체
    ConstraintLayout Game5; //수강신청 객체
    ConstraintLayout Game6; //윷놀이 객체
    Button game2_help, game3_help, game4_help, game5_help, game6_help;




    //설정 화면
    LinearLayout SettingMenu_layout;
    TextView Text_st_title, Text_st_languagetitle;
    Spinner Spinner_st_lang;
    Switch Switchfordark;
    Button Btn_st_Confirm, Btn_st_Cancel;

    //점수 변경 화면
    LinearLayout ScoresetMenu_Layout;
    TextView Text_SS_BonusText, Restext_SS_SelectTeam;
    Spinner Spinner_SS_SelectTeam;
    EditText EditText_SS_Bonus;
    Button Btn_SS_Confirm, Btn_SS_Cancel;

    //보너스 추가 화면
    LinearLayout BonusMenu_Layout;
    TextView Text_BM_BonusText, Restext_BM_SelectTeam;
    Spinner Spinner_BM_SelectTeam;
    EditText EditText_BM_Bonus;
    Button Btn_BM_Confirm, Btn_BM_Cancel;

    //나중에 팀 멤버 명단 액티비티에 넘기기 위한 변수
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


    //팀 배정 조회 화면
    ImageButton Btn_TS_backToMenu;
    ConstraintLayout TeamShow_ConstLayout;
    LinearLayout TS_Team1, TS_Team2, TS_Team3, TS_Team4;
    TextView TS_Team1_Name, TS_Team1_List, TS_Team2_Name, TS_Team2_List, TS_Team3_Name, TS_Team3_List, TS_Team4_Name, TS_Team4_List;
    Button Btn_TC_DBSave;

    //현재 순위 화면
    ConstraintLayout TeamBoard_ConstLayout;
    ImageButton Btn_TB_backToMenu;
    TextView TB_teamBoard_Topteam_Score, TB_Topteam_Name, TB_teamBoard_Middle_CurrentTop;
    LinearLayout TB_Team1, TB_Team2, TB_Team3, TB_Team4;
    TextView TB_Team1_Name, TB_Team1_Score, TB_Team2_Name, TB_Team2_Score, TB_Team3_Name, TB_Team3_Score, TB_Team4_Name, TB_Team4_Score;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //음악 불러오기
        mpmariotiring = MediaPlayer.create(sqgameselect.this, R.raw.mariotiring);
        errorpip = MediaPlayer.create(sqgameselect.this, R.raw.errorpip);
        sheek = MediaPlayer.create(sqgameselect.this, R.raw.sheek);
        dudungtak = MediaPlayer.create(sqgameselect.this, R.raw.dudungtak);
        huuk = MediaPlayer.create(sqgameselect.this, R.raw.huuk);

        //설정화면용 시스템 언어 설정 불러오기
        preferences = getSharedPreferences("shared",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //버전 확인후 sharedpreferences에 locale키 값의 value값을 가져옴
        //값이 없을 경우 기본언어 설정
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = preferences.getString("locale",getResources().getConfiguration().getLocales().get(0).getLanguage());
            //불러온 값이 어떻게 생겼지?
            //ko.en.jp 로 보임
        }else{
            locale = preferences.getString("locale",Resources.getSystem().getConfiguration().locale.getLanguage());
        }

        //언어설정값 DB에서 불러오기, 초기기동 시 기본값이 없으면 한국어설정
        switch(preferences.getString("locale", "ko")){
            case "ko":
                config.locale = Locale.KOREAN;
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                getApplicationContext().getPackageManager().getLaunchIntentForPackage(getPackageName());
                break;
            case "en":
                config.locale = Locale.ENGLISH;
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                getApplicationContext().getPackageManager().getLaunchIntentForPackage(getPackageName());
                break;
            case "jp":
                config.locale = Locale.JAPANESE;
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                getApplicationContext().getPackageManager().getLaunchIntentForPackage(getPackageName());
                break;
        }
        //해보니까 xml 에서 작업해야해서 취소


        setContentView(R.layout.sqgameselect);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        preferences.getBoolean("ISDARKMODE", true); //다크모드 설정값 DB에서 불러오기, 초기기동시에 기본값이 없으면 켜져있는 게 기본설정임
        //여기에 다크모드값 전역적용하기


        //SQLite Helper 객체 선언
        mySQLiteHelper = new mySQLiteHelper(getApplicationContext()); // 헬퍼 객체 선언

        /* 객체 연결 시작 */

        //[객체 연결] - 메인메뉴 화면

        Main_MenuGrid = (LinearLayout) findViewById(R.id.main_MenuGrid);

        Btn_GameSelect = (Button) findViewById(R.id.btn_GameSelect);
        Btn_TeamCreate = (Button) findViewById(R.id.btn_TeamCreate);
        Btn_Rullet = (Button) findViewById(R.id.btn_Rullet);
        Btn_BonusMenu = (Button) findViewById(R.id.btn_BonusMenu);
        Btn_TeamCheck = (Button) findViewById(R.id.btn_TeamCheck);
        Btn_RankCheck = (Button) findViewById(R.id.btn_RankCheck);
        Btn_Settings = (Button) findViewById(R.id.btn_Settings);
        Btn_ScoresetMenu = (Button) findViewById(R.id.btn_ScoresetMenu);

        //[객체 연결] - 팀 만들기 화면
        TeamCreate_ConstLayout = (ConstraintLayout) findViewById(R.id.teamCreate_ConstLayout);
        Btn_TC_Quit = (Button) findViewById(R.id.btn_TC_quit);
        Btn_TC_DBClear = (Button) findViewById(R.id.btn_TC_DBClear);
        TC_Team1_Btn = (ImageButton) findViewById(R.id.tC_Team1_Btn);
        TC_Team2_Btn = (ImageButton) findViewById(R.id.tC_Team2_Btn);
        TC_Team3_Btn = (ImageButton) findViewById(R.id.tc_Team3_Btn);
        TC_Team4_Btn = (ImageButton) findViewById(R.id.tC_Team4_Btn);
        TC_Team1_Text = (TextView) findViewById(R.id.tC_Team1_Text);
        TC_Team2_Text = (TextView) findViewById(R.id.tC_Team2_Text);
        TC_Team3_Text = (TextView) findViewById(R.id.tC_Team3_Text);
        TC_Team4_Text = (TextView) findViewById(R.id.tC_Team4_Text);
        EditTextTeamName = (EditText) findViewById(R.id.editTextTeamName);
        TeamCreate_Member1 = (EditText) findViewById(R.id.teamCreate_Member1);
        TeamCreate_Member2 = (EditText) findViewById(R.id.teamCreate_Member2);
        TeamCreate_Member3 = (EditText) findViewById(R.id.teamCreate_Member3);
        TeamCreate_Member4 = (EditText) findViewById(R.id.teamCreate_Member4);
        TeamCreate_Member5 = (EditText) findViewById(R.id.teamCreate_Member5);
        Btn_TC_DBSave = (Button) findViewById(R.id.btn_TC_DBSave);
        TeamCreate_helptext = (TextView) findViewById(R.id.teamCreate_helptext);
        EditTextTeamNameInfo = (TextView) findViewById(R.id.editTextTeamNameInfo);


        //[객체 연결] - 게임 선택 화면
        GameSelect_ConstLayout = (ConstraintLayout) findViewById(R.id.gameSelect_ConstLayout);
        Btn_GS_BackToMenu = (ImageButton) findViewById(R.id.btn_GS_backToMenu);
        Game1 = (ConstraintLayout) findViewById(R.id.game1);
        Game2 = (ConstraintLayout) findViewById(R.id.game2);
        Game3 = (ConstraintLayout) findViewById(R.id.game3);
        Game4 = (ConstraintLayout) findViewById(R.id.game4);
        Game5 = (ConstraintLayout) findViewById(R.id.game5);
        Game6 = (ConstraintLayout) findViewById(R.id.game6);
        game2_help = (Button) findViewById(R.id.game2_help);
        game3_help = (Button) findViewById(R.id.game3_help);
        game4_help = (Button) findViewById(R.id.game4_help);
        game5_help = (Button) findViewById(R.id.game5_help);
        game6_help = (Button) findViewById(R.id.game6_help);


        //[객체 연결] - 보너스 추가 화면
        BonusMenu_Layout = (LinearLayout) findViewById(R.id.bonusMenu_Layout);
        Spinner_BM_SelectTeam = (Spinner) findViewById(R.id.spinner_BM_SelectTeam);
        Text_BM_BonusText = (TextView) findViewById(R.id.text_BM_BonusText);
        EditText_BM_Bonus = (EditText) findViewById(R.id.editText_BM_Bonus);
        Btn_BM_Confirm = (Button) findViewById(R.id.btn_BM_Confirm);
        Btn_BM_Cancel = (Button) findViewById(R.id.btn_BM_Cancel);
        Restext_BM_SelectTeam = (TextView) findViewById(R.id.restext_BM_SelectTeam);

        //[객체 연결] - 점수 설정 화면
        ScoresetMenu_Layout = (LinearLayout) findViewById(R.id.scoresetMenu_Layout);
        Spinner_SS_SelectTeam = (Spinner) findViewById(R.id.spinner_SS_SelectTeam);
        Text_SS_BonusText = (TextView) findViewById(R.id.text_SS_BonusText);
        EditText_SS_Bonus = (EditText) findViewById(R.id.editText_SS_Bonus);
        Btn_SS_Confirm = (Button) findViewById(R.id.btn_SS_Confirm);
        Btn_SS_Cancel = (Button) findViewById(R.id.btn_SS_Cancel);
        Restext_SS_SelectTeam = (TextView) findViewById(R.id.restext_SS_SelectTeam);

        //[객체 연결] - 팀 배정 조회 화면
        TeamShow_ConstLayout = (ConstraintLayout) findViewById(R.id.teamShow_ConstLayout);
        TS_Team1 = (LinearLayout) findViewById(R.id.tS_Team1);
        TS_Team2 = (LinearLayout) findViewById(R.id.tS_Team2);
        TS_Team3 = (LinearLayout) findViewById(R.id.tS_Team3);
        TS_Team4 = (LinearLayout) findViewById(R.id.tS_Team4);
        TS_Team1_List = (TextView) findViewById(R.id.tS_Team1_List);
        TS_Team1_Name = (TextView) findViewById(R.id.tS_Team1_Name);
        TS_Team2_List = (TextView) findViewById(R.id.tS_Team2_List);
        TS_Team2_Name = (TextView) findViewById(R.id.tS_Team2_Name);
        TS_Team3_List = (TextView) findViewById(R.id.tS_Team3_List);
        TS_Team3_Name = (TextView) findViewById(R.id.tS_Team3_Name);
        TS_Team4_List = (TextView) findViewById(R.id.tS_Team4_List);
        TS_Team4_Name = (TextView) findViewById(R.id.tS_Team4_Name);
        Btn_TS_backToMenu = (ImageButton) findViewById(R.id.btn_TS_backToMenu);

        //[객체 연결] - 현재 순위 화면
        TeamBoard_ConstLayout = (ConstraintLayout) findViewById(R.id.teamBoard_ConstLayout);
        Btn_TB_backToMenu = (ImageButton) findViewById(R.id.btn_TB_backToMenu);
        TB_teamBoard_Topteam_Score = (TextView) findViewById(R.id.tB_teamBoard_Topteam_Score);
        TB_Topteam_Name = (TextView) findViewById(R.id.tB_Topteam_Name);
        TB_Team1 = (LinearLayout) findViewById(R.id.tB_Team1);
        TB_Team2 = (LinearLayout) findViewById(R.id.tB_Team2);
        TB_Team3 = (LinearLayout) findViewById(R.id.tB_Team3);
        TB_Team4 = (LinearLayout) findViewById(R.id.tB_Team4);
        TB_Team1_Name = (TextView) findViewById(R.id.tB_Team1_Name);
        TB_Team1_Score = (TextView) findViewById(R.id.tB_Team1_Score);
        TB_Team2_Name = (TextView) findViewById(R.id.tB_Team2_Name);
        TB_Team2_Score = (TextView) findViewById(R.id.tB_Team2_Score);
        TB_Team3_Name = (TextView) findViewById(R.id.tB_Team3_Name);
        TB_Team3_Score = (TextView) findViewById(R.id.tB_Team3_Score);
        TB_Team4_Name = (TextView) findViewById(R.id.tB_Team4_Name);
        TB_Team4_Score = (TextView) findViewById(R.id.tB_Team4_Score);
        TB_teamBoard_Middle_CurrentTop = (TextView) findViewById(R.id.tB_teamBoard_Middle_CurrentTop);

        //[객체 연결] - 설정 메뉴
        SettingMenu_layout = (LinearLayout) findViewById(R.id.settingMenu_Layout);
        Text_st_title = (TextView) findViewById(R.id.text_st_languagetitle);
        Text_st_languagetitle = (TextView) findViewById(R.id.text_st_languagetitle);
        Spinner_st_lang = (Spinner) findViewById(R.id.spinner_st_lang);
        Switchfordark = (Switch) findViewById(R.id.switchfordark);
        Btn_st_Confirm = (Button) findViewById(R.id.btn_st_Confirm);
        Btn_st_Cancel = (Button) findViewById(R.id.btn_st_Cancel);


        /* 객체 연결 끝 */

        //이전 게임에서 점수 받기
        Intent gintent = getIntent();
        try{
            sqlDB = mySQLiteHelper.getWritableDatabase();
            //A팀 점수
            scorepass_team1 = gintent.getIntExtra("teamscore_a", 0);
            sqlDB.execSQL("UPDATE TeamTable SET teamScore = teamScore +"+scorepass_team1+" WHERE teamid = "+1+";");
            //B팀 점수
            scorepass_team2 = gintent.getIntExtra("teamscore_b", 0);
            sqlDB.execSQL("UPDATE TeamTable SET teamScore = teamScore + "+scorepass_team2+" WHERE teamid = "+2+";");
            //C팀 점수
            scorepass_team3 = gintent.getIntExtra("teamscore_c", 0);
            sqlDB.execSQL("UPDATE TeamTable SET teamScore = teamScore + "+scorepass_team3+" WHERE teamid = "+3+";");
            //D팀 점수
            scorepass_team4 = gintent.getIntExtra("teamscore_d",0 );
            sqlDB.execSQL("UPDATE TeamTable SET teamScore = teamScore + "+scorepass_team4+" WHERE teamid = "+4+";");
            if(scorepass_team1 == 0 && scorepass_team2 == 0 && scorepass_team3 == 0 && scorepass_team4 == 0){ //점수추가된 게 없을경우
            }
            else{
            }
            GameSelect_ConstLayout.setVisibility(View.INVISIBLE); //메인화면 돌아갈 시 게임선택 화면 안보이게 하기 위해
            sqlDB.close();
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"오류, 이전 게임 점수정보 찾기실패",Toast.LENGTH_SHORT).show();
        }



        /* 메인메뉴에서의 호출 버튼 시작 */



        //[호출] 벌칙게임 화면
        Btn_Rullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                huuk.start();
                //먼저 Intent 인스턴스 만들어두기
                //여기다가 추가정보 넣을예정
                Intent rulletgo = new Intent(sqgameselect.this, penaltygame.class);
                //[DB 사용 시작]
                sqlDB = mySQLiteHelper.getReadableDatabase();
                //DB 에서 팀명 가져오기
                //DB 에서 팀 멤버목록 가져오기(1번팀)
                Cursor cursorformem = sqlDB.rawQuery("SELECT * FROM TeamMember WHERE teamid = "+1+";",null);
                //더 이상 데이터가 없을 때까지 Cursor 이동
                try { //멤버가 있을 때
                    cursorformem.moveToNext();
                    passname_team1_1 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team1_2 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team1_3 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team1_4 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team1_5 = cursorformem.getString(2);
                    cursorformem.close();
                } //멤버가 없을 때
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "로그 : 팀 멤버 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
                //DB 에서 팀 멤버목록 가져오기(1번팀)
                cursorformem = sqlDB.rawQuery("SELECT * FROM TeamMember WHERE teamid = "+2+";",null);
                //더 이상 데이터가 없을 때까지 Cursor 이동
                try { //멤버가 있을 때
                    cursorformem.moveToNext();
                    passname_team2_1 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team2_2 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team2_3 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team2_4 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team2_5 = cursorformem.getString(2);
                    cursorformem.close();
                } //멤버가 없을 때
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "로그 : 팀 멤버 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
                //DB 에서 팀 멤버목록 가져오기(1번팀)
                cursorformem = sqlDB.rawQuery("SELECT * FROM TeamMember WHERE teamid = "+3+";",null);
                //더 이상 데이터가 없을 때까지 Cursor 이동
                try { //멤버가 있을 때
                    cursorformem.moveToNext();
                    passname_team3_1 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team3_2 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team3_3 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team3_4 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team3_5 = cursorformem.getString(2);
                    cursorformem.close();
                } //멤버가 없을 때
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "로그 : 팀 멤버 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
                //DB 에서 팀 멤버목록 가져오기(1번팀)
                cursorformem = sqlDB.rawQuery("SELECT * FROM TeamMember WHERE teamid = "+4+";",null);
                //더 이상 데이터가 없을 때까지 Cursor 이동
                try { //멤버가 있을 때
                    cursorformem.moveToNext();
                    passname_team4_1 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team4_2 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team4_3 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team4_4 = cursorformem.getString(2);
                    cursorformem.moveToNext();
                    passname_team4_5 = cursorformem.getString(2);
                    cursorformem.close();
                } //멤버가 없을 때
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "로그 : 팀 멤버 불러오기 실패", Toast.LENGTH_SHORT).show();
                }

                Cursor cursorforteamscorename = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                String team1name = "";
                String team2name = "";
                String team3name = "";
                String team4name = "";
                try {
                    cursorforteamscorename.moveToNext();
                    team1name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team2name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team3name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team4name = cursorforteamscorename.getString(1);
                    //마지막으로 팀명도 집어넣기
                    rulletgo.putExtra("passname_team1", team1name);
                    rulletgo.putExtra("passname_team2",team2name);
                    rulletgo.putExtra("passname_team3", team3name);
                    rulletgo.putExtra("passname_team4", team4name);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),getString(R.string.pleasecreateteam),Toast.LENGTH_SHORT).show();
                }

                //DB Close
                cursorforteamscorename.close();
                sqlDB.close();

                //[DB 사용 종료]

                rulletgo.putExtra("passname_team1_1", passname_team1_1);
                rulletgo.putExtra("passname_team1_2", passname_team1_2);
                rulletgo.putExtra("passname_team1_3", passname_team1_3);
                rulletgo.putExtra("passname_team1_4", passname_team1_4);
                rulletgo.putExtra("passname_team1_5", passname_team1_5);

                rulletgo.putExtra("passname_team2_1", passname_team2_1);
                rulletgo.putExtra("passname_team2_2", passname_team2_2);
                rulletgo.putExtra("passname_team2_3", passname_team2_3);
                rulletgo.putExtra("passname_team2_4", passname_team2_4);
                rulletgo.putExtra("passname_team2_5", passname_team2_5);

                rulletgo.putExtra("passname_team3_1", passname_team3_1);
                rulletgo.putExtra("passname_team3_2", passname_team3_2);
                rulletgo.putExtra("passname_team3_3", passname_team3_3);
                rulletgo.putExtra("passname_team3_4", passname_team3_4);
                rulletgo.putExtra("passname_team3_5", passname_team3_5);

                rulletgo.putExtra("passname_team4_1", passname_team4_1);
                rulletgo.putExtra("passname_team4_2", passname_team4_2);
                rulletgo.putExtra("passname_team4_3", passname_team4_3);
                rulletgo.putExtra("passname_team4_4", passname_team4_4);
                rulletgo.putExtra("passname_team4_5", passname_team4_5);

                startActivity(rulletgo);
            }
        });

        //[호출] 팀 만들기 화면
        Btn_TeamCreate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                huuk.start();
                TeamCreate_ConstLayout.setVisibility(View.VISIBLE);
                Main_MenuGrid.setVisibility(View.INVISIBLE);
                //팀 선택하세요 메세지 보이기
                TeamCreate_helptext.setVisibility(View.VISIBLE);

                //DB저장 시 팀 뱃지도 바뀌게 하기
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforbadge = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                String badge = "";
                try {
                    cursorforbadge.moveToNext();
                    TC_Team1_Text.setText(badge+cursorforbadge.getString(1));
                    cursorforbadge.moveToNext();
                    TC_Team2_Text.setText(badge+cursorforbadge.getString(1));
                    cursorforbadge.moveToNext();
                    TC_Team3_Text.setText(badge+cursorforbadge.getString(1));
                    cursorforbadge.moveToNext();
                    TC_Team4_Text.setText(badge+cursorforbadge.getString(1));

                } catch (Exception e) {
                    TC_Team1_Text.setText(getString(R.string.tC_team1_text));
                    TC_Team2_Text.setText(getString(R.string.tC_team2_text));
                    TC_Team3_Text.setText(getString(R.string.tC_team3_text));
                    TC_Team4_Text.setText(getString(R.string.tC_team4_text));
                }
                cursorforbadge.close();
                sqlDB.close();

            }
        });

        //[호출] 게임 선택 화면
        Btn_GameSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                huuk.start();
                GameSelect_ConstLayout.setVisibility(View.VISIBLE);
                Main_MenuGrid.setVisibility(View.INVISIBLE);
                Game2.setVisibility(View.VISIBLE);
                Game3.setVisibility(View.VISIBLE);
                Game4.setVisibility(View.VISIBLE);
                Game5.setVisibility(View.VISIBLE);
            }
        });

        Game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ran_game_choose = (int)Math.round(Math.random()*2+2); //게임 넘버 랜덤 (1~3), 게임 추가 시 변경 필요
                Intent Game1Intent = new Intent(sqgameselect.this, game000.class);

                String team1name = "";
                String team2name = "";
                String team3name = "";
                String team4name = "";
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforteamscorename = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                try {
                    cursorforteamscorename.moveToNext();
                    team1name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team2name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team3name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team4name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.close();
                    sqlDB.close();

                    Game1Intent.putExtra("team1name", team1name);
                    Game1Intent.putExtra("team2name", team2name);
                    Game1Intent.putExtra("team3name", team3name);
                    Game1Intent.putExtra("team4name", team4name);
                    Game1Intent.putExtra("gamenumber", ran_game_choose); //저쪽 Intent 에 무슨 게임 넘버가 선택됐는지 알리기
                    dudungtak.start();
                    Game1Intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(Game1Intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),getString(R.string.pleasecreateteam),Toast.LENGTH_SHORT).show();
                    cursorforteamscorename.close();
                    sqlDB.close();
                }

            }
        });

        Game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Game2Intent = new Intent(sqgameselect.this, game000.class);

                String team1name = "";
                String team2name = "";
                String team3name = "";
                String team4name = "";
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforteamscorename = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                try {
                    cursorforteamscorename.moveToNext();
                    team1name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team2name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team3name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team4name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.close();
                    sqlDB.close();

                    Game2Intent.putExtra("team1name", team1name);
                    Game2Intent.putExtra("team2name", team2name);
                    Game2Intent.putExtra("team3name", team3name);
                    Game2Intent.putExtra("team4name", team4name);
                    dudungtak.start();
                    Game2Intent.putExtra("gamenumber", 2); //저쪽 Intent 에 무슨 게임 넘버가 선택됐는지 알리기
                    Game2Intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(Game2Intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),getString(R.string.pleasecreateteam),Toast.LENGTH_SHORT).show();
                    cursorforteamscorename.close();
                    sqlDB.close();
                }

            }
        });

        Game3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Game3Intent = new Intent(sqgameselect.this, game000.class);
                String team1name = "";
                String team2name = "";
                String team3name = "";
                String team4name = "";
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforteamscorename = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                try {
                    cursorforteamscorename.moveToNext();
                    team1name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team2name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team3name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team4name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.close();
                    sqlDB.close();

                    Game3Intent.putExtra("team1name", team1name);
                    Game3Intent.putExtra("team2name", team2name);
                    Game3Intent.putExtra("team3name", team3name);
                    Game3Intent.putExtra("team4name", team4name);
                    dudungtak.start();
                    Game3Intent.putExtra("gamenumber", 3); //저쪽 Intent 에 무슨 게임 넘버가 선택됐는지 알리기
                    Game3Intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(Game3Intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),getString(R.string.pleasecreateteam),Toast.LENGTH_SHORT).show();
                    cursorforteamscorename.close();
                    sqlDB.close();
                }

            }
        });

        Game4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Game4Intent = new Intent(sqgameselect.this, game000.class);
                String team1name = "";
                String team2name = "";
                String team3name = "";
                String team4name = "";
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforteamscorename = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                try {
                    cursorforteamscorename.moveToNext();
                    team1name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team2name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team3name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team4name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.close();
                    sqlDB.close();

                    Game4Intent.putExtra("team1name", team1name);
                    Game4Intent.putExtra("team2name", team2name);
                    Game4Intent.putExtra("team3name", team3name);
                    Game4Intent.putExtra("team4name", team4name);
                    dudungtak.start();
                    Game4Intent.putExtra("gamenumber", 4); //저쪽 Intent 에 무슨 게임 넘버가 선택됐는지 알리기
                    Game4Intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(Game4Intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),getString(R.string.pleasecreateteam),Toast.LENGTH_SHORT).show();
                    cursorforteamscorename.close();
                    sqlDB.close();
                }

            }
        });

        Game5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Game5Intent = new Intent(sqgameselect.this, game000.class);
                String team1name = "";
                String team2name = "";
                String team3name = "";
                String team4name = "";
                dudungtak.start();
                Game5Intent.putExtra("gamenumber", 5); //저쪽 Intent 에 무슨 게임 넘버가 선택됐는지 알리기
                Game5Intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(Game5Intent);

            }
        });

        Game6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Game6Intent = new Intent(sqgameselect.this, game006.class);

                String team1name = "";
                String team2name = "";
                String team3name = "";
                String team4name = "";
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforteamscorename = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                try {
                    cursorforteamscorename.moveToNext();
                    team1name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team2name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team3name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    team4name = cursorforteamscorename.getString(1);
                    cursorforteamscorename.close();
                    sqlDB.close();

                    Game6Intent.putExtra("team1name", team1name);
                    Game6Intent.putExtra("team2name", team2name);
                    Game6Intent.putExtra("team3name", team3name);
                    Game6Intent.putExtra("team4name", team4name);
                    dudungtak.start();
                    Game6Intent.putExtra("gamenumber", 6); //저쪽 Intent 에 무슨 게임 넘버가 선택됐는지 알리기
                    Game6Intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(Game6Intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),getString(R.string.pleasecreateteam),Toast.LENGTH_SHORT).show();
                    cursorforteamscorename.close();
                    sqlDB.close();
                }

            }
        });

        //[호출] - 보너스 추가 화면
        Btn_BonusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                huuk.start();
                //DB가 생성되어 있지 않을 시 실행안되게 해야함 if문 사용
                //보너스 추가 화면 호출 이후 뒤에 버튼이 안눌리게 조정할 필요 있음

                //팀 DB불러오기
                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor cursorforbonus = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                //더 이상 데이터가 없을 때까지 Cursor 이동
                String bonus_a = "";
                String bonus_b = "";
                String bonus_c = "";
                String bonus_d = "";
                try { //멤버가 있을 때
                    cursorforbonus.moveToNext();
                    bonus_a = cursorforbonus.getString(1);
                    cursorforbonus.moveToNext();
                    bonus_b = cursorforbonus.getString(1);
                    cursorforbonus.moveToNext();
                    bonus_c = cursorforbonus.getString(1);
                    cursorforbonus.moveToNext();
                    bonus_d = cursorforbonus.getString(1);
                    cursorforbonus.close();
                    sqlDB.close();
                } //멤버가 없을 때
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), getString(R.string.pleasecreateteam), Toast.LENGTH_SHORT).show();
                }

                //스피너 구성
                BonusMenu_Layout.setVisibility(View.VISIBLE);
                Restext_BM_SelectTeam.setText("0");
                String[] bonus_items = {bonus_a,bonus_b,bonus_c,bonus_d};
                ArrayAdapter<String> bonus_adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item, bonus_items);
                bonus_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner_BM_SelectTeam.setAdapter(bonus_adapter);
                //스피너 액션
                Spinner_BM_SelectTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Restext_BM_SelectTeam.setText(bonus_items[i]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Restext_BM_SelectTeam.setText("");
                    }
                });
            }
        });

        game2_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=iY_4C6BEm8E"));
                startActivity(netintent);
            }
        });

        game3_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=Nz2uKK-7dVQ"));
                startActivity(netintent);
            }
        });

        game4_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=CC0fHNOEP20"));
                startActivity(netintent);
            }
        });

        game5_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/"));
                startActivity(netintent);
            }
        });

        game6_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent netintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/"));
                startActivity(netintent);
            }
        });









        //[호출] - 점수 설정 화면
        Btn_ScoresetMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                huuk.start();
                //DB가 생성되어 있지 않을 시 실행안되게 해야함 if문 사용
                //보너스 추가 화면 호출 이후 뒤에 버튼이 안눌리게 조정할 필요 있음
                //팀 DB불러오기
                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor cursorforscore = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                //더 이상 데이터가 없을 때까지 Cursor 이동
                String sc_a = "";
                String sc_b = "";
                String sc_c = "";
                String sc_d = "";
                try { //멤버가 있을 때
                    cursorforscore.moveToNext();
                    sc_a = cursorforscore.getString(1);
                    cursorforscore.moveToNext();
                    sc_b = cursorforscore.getString(1);
                    cursorforscore.moveToNext();
                    sc_c = cursorforscore.getString(1);
                    cursorforscore.moveToNext();
                    sc_d = cursorforscore.getString(1);
                    cursorforscore.close();
                    sqlDB.close();
                } //멤버가 없을 때
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), getString(R.string.pleasecreateteam), Toast.LENGTH_SHORT).show();
                }

                //스피너 구성
                ScoresetMenu_Layout.setVisibility(View.VISIBLE);
                Restext_SS_SelectTeam.setText("0");
                String[] score_items = {sc_a,sc_b,sc_c,sc_d};
                ArrayAdapter<String> sc_adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item, score_items);
                sc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner_SS_SelectTeam.setAdapter(sc_adapter);
                //스피너 액션
                Spinner_SS_SelectTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Restext_SS_SelectTeam.setText(score_items[i]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Restext_BM_SelectTeam.setText("");
                    }
                });
            }
        });

        //[호출] 팀 배정 조회 화면
        Btn_TeamCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeamShow_ConstLayout.setVisibility(View.VISIBLE);
                Main_MenuGrid.setVisibility(View.INVISIBLE);
                huuk.start();
                //[DB사용 시작]
                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor cursorfortst = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                String tsteamname = ""; //데이터가 없을 때에는 공백 불러오기
                //더 이상 데이터가 없을 때까지 Cursor 이동
                try { //멤버가 있을 때
                    cursorfortst.moveToNext();
                    TS_Team1_Name.setText(tsteamname+cursorfortst.getString(1));
                    cursorfortst.moveToNext();
                    TS_Team2_Name.setText(tsteamname+cursorfortst.getString(1));
                    cursorfortst.moveToNext();
                    TS_Team3_Name.setText(tsteamname+cursorfortst.getString(1));
                    cursorfortst.moveToNext();
                    TS_Team4_Name.setText(tsteamname+cursorfortst.getString(1));
                    cursorfortst.close();
                    sqlDB.close();
                } //멤버가 없을 때
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), getString(R.string.pleasecreateteam), Toast.LENGTH_SHORT).show();
                }

                //[팀 소속 멤버 표시용 DB사용 시작]
                //4개의 팀을 각자 DB 객체를 사용해검색

                //1번팀 불러오기
                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor cursorfortsmem1 = sqlDB.rawQuery("SELECT * FROM TeamMember WHERE teamid = 1;",null);
                String tsmem1 = ""; //데이터가 없을 때에는 공백 불러오기
                try { //멤버가 있을 때
                    //더 이상 데이터가 없을 때까지 Cursor 이동
                    while(cursorfortsmem1.moveToNext()){
                        tsmem1 += cursorfortsmem1.getString(2)+"\r\n";
                    }
                    TS_Team1_List.setText(tsmem1);
                    cursorfortsmem1.close();
                    sqlDB.close();
                } //멤버가 없을 때
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "에러.", Toast.LENGTH_SHORT).show();
                }

                //2번팀 불러오기
                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor cursorfortsmem2 = sqlDB.rawQuery("SELECT * FROM TeamMember WHERE teamid = 2;",null);
                String tsmem2 = ""; //데이터가 없을 때에는 공백 불러오기
                try { //멤버가 있을 때
                    //더 이상 데이터가 없을 때까지 Cursor 이동
                    while(cursorfortsmem2.moveToNext()){
                        tsmem2 += cursorfortsmem2.getString(2)+"\r\n";
                    }
                    TS_Team2_List.setText(tsmem2);
                    cursorfortsmem2.close();
                    sqlDB.close();
                } //멤버가 없을 때
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "에러.", Toast.LENGTH_SHORT).show();
                }

                //3번팀 불러오기
                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor cursorfortsmem3 = sqlDB.rawQuery("SELECT * FROM TeamMember WHERE teamid = 3;",null);
                String tsmem3 = ""; //데이터가 없을 때에는 공백 불러오기
                try { //멤버가 있을 때
                    //더 이상 데이터가 없을 때까지 Cursor 이동
                    while(cursorfortsmem3.moveToNext()){
                        tsmem3 += cursorfortsmem3.getString(2)+"\r\n";
                    }
                    TS_Team3_List.setText(tsmem3);
                    cursorfortsmem3.close();
                    sqlDB.close();
                } //멤버가 없을 때
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "에러.", Toast.LENGTH_SHORT).show();
                }

                //4번팀 불러오기
                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor cursorfortsmem4 = sqlDB.rawQuery("SELECT * FROM TeamMember WHERE teamid = 4;",null);
                String tsmem4 = ""; //데이터가 없을 때에는 공백 불러오기
                try { //멤버가 있을 때
                    //더 이상 데이터가 없을 때까지 Cursor 이동
                    while(cursorfortsmem4.moveToNext()){
                        tsmem4 += cursorfortsmem4.getString(2)+"\r\n";
                    }
                    TS_Team4_List.setText(tsmem4);
                    cursorfortsmem4.close();
                    sqlDB.close();
                } //멤버가 없을 때
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "에러.", Toast.LENGTH_SHORT).show();
                }




                //DB Close
                sqlDB.close();
                //[팀 소속 멤버 표시용 DB사용 종료]

            }
        });

        //[호출] 현재 순위 화면
        Btn_RankCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                huuk.start();
                TeamBoard_ConstLayout.setVisibility(View.VISIBLE);
                Main_MenuGrid.setVisibility(View.INVISIBLE);
                //점수 넣을 팀별 변수 초기화
                int team1sc =0, team2sc =0, team3sc =0, team4sc =0;
                String team1nm ="", team2nm ="", team3nm ="", team4nm ="";
                //팀 점수배지에 점수 넣기
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforteamscore = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                try {
                    cursorforteamscore.moveToNext();
                    TB_Team1_Score.setText(cursorforteamscore.getString(2));
                    team1sc = Integer.parseInt(cursorforteamscore.getString(2));
                    cursorforteamscore.moveToNext();
                    TB_Team2_Score.setText(cursorforteamscore.getString(2));
                    team2sc = Integer.parseInt(cursorforteamscore.getString(2));
                    cursorforteamscore.moveToNext();
                    TB_Team3_Score.setText(cursorforteamscore.getString(2));
                    team3sc = Integer.parseInt(cursorforteamscore.getString(2));
                    cursorforteamscore.moveToNext();
                    TB_Team4_Score.setText(cursorforteamscore.getString(2));
                    team4sc = Integer.parseInt(cursorforteamscore.getString(2));

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),getString(R.string.pleasecreateteam),Toast.LENGTH_SHORT).show();
                }
                cursorforteamscore.close();
                sqlDB.close();

                //팀 이름배지에 이름 넣기
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforteamscorename = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                try {
                    cursorforteamscorename.moveToNext();
                    TB_Team1_Name.setText(cursorforteamscorename.getString(1));
                    team1nm = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    TB_Team2_Name.setText(cursorforteamscorename.getString(1));
                    team2nm = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    TB_Team3_Name.setText(cursorforteamscorename.getString(1));
                    team3nm = cursorforteamscorename.getString(1);
                    cursorforteamscorename.moveToNext();
                    TB_Team4_Name.setText(cursorforteamscorename.getString(1));
                    team4nm = cursorforteamscorename.getString(1);

                } catch (Exception e) {
                    Log.d("Err","에러발생?TeamScoreName");
                    TB_Team1_Name.setText("");
                    TB_Team2_Name.setText("");
                    TB_Team3_Name.setText("");
                    TB_Team4_Name.setText("");
                    TB_Team1_Score.setText("");
                    TB_Team2_Score.setText("");
                    TB_Team3_Score.setText("");
                    TB_Team4_Score.setText("");
                }
                cursorforteamscorename.close();
                sqlDB.close();

                //여기부턴 최고팀 점수 넣기, 나중에 공동 1위도 만들어야 함
                int res_score = 0;
                int maxteamno = 0;
                Stack<Integer> teamfirstlist = new Stack<Integer>();

                int[] score_ar = {team1sc, team2sc, team3sc, team4sc};

                for(int i=0; i<score_ar.length; i++){
                    if(res_score <= score_ar[i]){
                        res_score = score_ar[i];
                    }
                }
                    //최고점수 저장 완료
                    for(int j=score_ar.length-1; j>=0; j--){
                        if(res_score == score_ar[j]){
                            teamfirstlist.push(j+1); //이긴 팀의 번호가 스택에 저장됨
                            maxteamno = j+1;
                        }
                    }

                if(res_score == 0){
                    teamfirstlist.clear(); //공동승리가 아니므로 stack초기화
                    TB_Topteam_Name.setVisibility(View.INVISIBLE);
                    TB_teamBoard_Topteam_Score.setVisibility(View.INVISIBLE);
                    TB_teamBoard_Middle_CurrentTop.setVisibility(View.INVISIBLE);
                }
                else{
                    if(teamfirstlist.size()>1){
                        String outlettext = getString(R.string.common1st)+"\n";
                        while(teamfirstlist.size() > 0){
                            switch(teamfirstlist.pop()){
                                case 1 :
                                    outlettext += team1nm+"\n";
                                    break;
                                case 2 :
                                    outlettext += team2nm+"\n";
                                    break;
                                case 3 :
                                    outlettext += team3nm+"\n";
                                    break;
                                case 4 :
                                    outlettext += team4nm+"\n";
                                    break;
                                default :
                                    outlettext = "로그 : 팀불러오기 오류";
                                    break;
                            }

                        }
                        TB_teamBoard_Middle_CurrentTop.setText(outlettext);
                        TB_Topteam_Name.setVisibility(View.INVISIBLE);
                        teamfirstlist.clear();

                    }
                    else{
                        switch(maxteamno){
                            case 1:
                                TB_Topteam_Name.setText(team1nm);
                                TB_Topteam_Name.setBackgroundResource(R.drawable.teamflagbgbadgegreen);
                                break;
                            case 2:
                                TB_Topteam_Name.setText(team2nm);
                                TB_Topteam_Name.setBackgroundResource(R.drawable.teamflagbgbadgeblue);
                                break;
                            case 3:
                                TB_Topteam_Name.setText(team3nm);
                                TB_Topteam_Name.setBackgroundResource(R.drawable.teamflagbgbadgered);
                                break;
                            case 4:
                                TB_Topteam_Name.setText(team4nm);
                                TB_Topteam_Name.setBackgroundResource(R.drawable.teamflagbgbadgeyellow);
                                break;
                            default :
                                Toast.makeText(getApplicationContext(), "Exception : 공동우승자", Toast.LENGTH_SHORT).show();
                        }
                    }




                    TB_teamBoard_Topteam_Score.setText(String.valueOf(res_score)+getString(R.string.scoreformat));
                }



            }
        });

        //[호출] 설정 화면
        Btn_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                huuk.start();
                SettingMenu_layout.setVisibility(View.VISIBLE);

                //언어 불러오기

                switch(locale){
                    case "ko":
                        locale_number = 0;
                        break;
                    case "en":
                        locale_number = 1;
                        break;
                    case "jp":
                        locale_number = 2;
                        break;
                }
                //스피너 구성
                //Text_st_languagetitle.setText(getStringByLocal(this,R.string.korean,locale));
                Restext_BM_SelectTeam.setText("0");
                String korlang = "한국어";
                String englang = "English";
                String jpnlang = "日本語";

                String[] lang_items = {korlang, englang, jpnlang};
                String[] lang_loc = {"ko", "en", "jp"};
                ArrayAdapter<String> lang_adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item, lang_items);
                lang_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner_st_lang.setAdapter(lang_adapter);

                //설정값의 locale 테이블에서 값 가져오기, 만약 값 없으면 한국어 가져옴(기본설정)
                switch(preferences.getString("locale","ko")){ //혹시나 오류 발생시엔 ko 값 가져옴
                    case "ko":
                        Spinner_st_lang.setSelection(0);
                        break;
                    case "en":
                        Spinner_st_lang.setSelection(1);
                        break;
                    case "jp":
                        Spinner_st_lang.setSelection(2);
                        break;
                }

                //스피너 액션
                Spinner_st_lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Restext_BM_SelectTeam.setText(lang_items[i]);
                        editor.putString("locale", lang_loc[i]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                //switch 액션(다크모드)
                if(preferences.getBoolean("ISDARKMODE",true)){
                    Switchfordark.setChecked(true); //설정값이 on 이면 다크모드로 스위치 세팅
                }
                else{
                    Switchfordark.setChecked(false); //설정값이 false 이면 화이트모드로 스위치 세팅
                }
                Switchfordark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b) //b가 true 면 다크모드 설정값 변경
                            editor.putBoolean("ISDARKMODE", true);
                        else
                            editor.putBoolean("ISDARKMODE", false);
                    }
                });





            }
        });

        /* 메인메뉴에서의 호출 버튼 끝 */





        /* 보너스 추가 메뉴에서의 메소드 시작*/

        //확인 버튼을 눌러 창 닫고 DB 업데이트
        Btn_BM_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                while(true){ //오류픽스 : 보너스 점수가 없을 때 팅김방지
                    try{
                        String bonus_res = Restext_BM_SelectTeam.getText().toString();
                        int bonus_sum = Integer.parseInt(EditText_BM_Bonus.getText().toString());
                        //[DB사용 시작]
                        sqlDB = mySQLiteHelper.getWritableDatabase();
                        sqlDB.execSQL("UPDATE TeamTable SET teamScore = teamScore+"+bonus_sum+" WHERE teamNAME = '"+bonus_res+"';");
                        Toast.makeText(getApplicationContext(),"보너스 추가 완료",Toast.LENGTH_SHORT).show();
                        mpmariotiring.start();
                        sqlDB.close();
                        BonusMenu_Layout.setVisibility(View.GONE);
                        break;
                        //[DB사용 종료]
                    }
                    catch(Exception e){
                        Toast.makeText(getApplicationContext(), "추가할 점수를 입력하세요.", Toast.LENGTH_SHORT).show();
                        errorpip.start();
                        break;
                    }
                }

                //EditText_BM_Bonus.setText("");

            }
        });

        //취소 버튼을 눌러 창 닫기
        Btn_BM_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BonusMenu_Layout.setVisibility(View.GONE);
            }
        });

        /* 보너스 추가 메뉴에서의 메소드 끝*/



        /* 점수 설정 메뉴에서의 메소드 시작*/

        //확인 버튼을 눌러 창 닫고 DB 업데이트
        Btn_SS_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                while(true){ //오류픽스 : 설정 점수가 없을 때 팅김방지
                    try{
                        String sscore_res = Restext_SS_SelectTeam.getText().toString();
                        int score_sum = Integer.parseInt(EditText_SS_Bonus.getText().toString());
                        //[DB사용 시작]
                        sqlDB = mySQLiteHelper.getWritableDatabase();
                        sqlDB.execSQL("UPDATE TeamTable SET teamScore = "+score_sum+" WHERE teamNAME = '"+sscore_res+"';");
                        Toast.makeText(getApplicationContext(),getString(R.string.setscorecompleted),Toast.LENGTH_SHORT).show();
                        sqlDB.close();
                        ScoresetMenu_Layout.setVisibility(View.GONE);
                        mpmariotiring.start();
                        break;
                        //[DB사용 종료]
                    }
                    catch(Exception e){
                        Toast.makeText(getApplicationContext(), getString(R.string.pleaseenterscore), Toast.LENGTH_SHORT).show();
                        errorpip.start();
                        break;
                    }
                }

                //EditText_BM_Bonus.setText("");

            }
        });

        //취소 버튼을 눌러 창 닫기
        Btn_SS_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScoresetMenu_Layout.setVisibility(View.GONE);
            }
        });

        /* 점수 설정 메뉴에서의 메소드 끝*/





        /* 팀 만들기 메뉴에서의 메소드 시작*/

        // DB 초기화 버튼
        Btn_TC_DBClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = mySQLiteHelper.getWritableDatabase();
                mySQLiteHelper.onUpgrade(sqlDB,1,2);
                Toast.makeText(getApplicationContext(), getString(R.string.DBinitiatesuccess), Toast.LENGTH_SHORT).show();
                sqlDB.close(); //DB닫고 끝
                sheek.start();
                TC_Team1_Text.setText(getString(R.string.tC_team1_text));
                TC_Team2_Text.setText(getString(R.string.tC_team2_text));
                TC_Team3_Text.setText(getString(R.string.tC_team3_text));
                TC_Team4_Text.setText(getString(R.string.tC_team4_text));
            }
        });
        // DB 저장 버튼
        Btn_TC_DBSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sqlDB = mySQLiteHelper.getWritableDatabase();

                ////DB중복기입 시 팅김방지를 위해 TryCatch문 사용
                try{//처음 DB에 입력시
                    //DB : teamName, teamScore
                    sqlDB.execSQL("INSERT INTO TeamTable VALUES ("+currentTeam+",'"+EditTextTeamName.getText()+"',0);");
                    //DB : ID, teamid, teamMember, memCount(팀내 해당 멤버의 순서)
                    sqlDB.execSQL("INSERT INTO TeamMember (teamid, teamMember, memCount) VALUES ("+currentTeam+",'"+TeamCreate_Member1.getText()+"',1);");
                    sqlDB.execSQL("INSERT INTO TeamMember (teamid, teamMember, memCount) VALUES ("+currentTeam+",'"+TeamCreate_Member2.getText()+"',2);");
                    sqlDB.execSQL("INSERT INTO TeamMember (teamid, teamMember, memCount) VALUES ("+currentTeam+",'"+TeamCreate_Member3.getText()+"',3);");
                    sqlDB.execSQL("INSERT INTO TeamMember (teamid, teamMember, memCount) VALUES ("+currentTeam+",'"+TeamCreate_Member4.getText()+"',4);");
                    sqlDB.execSQL("INSERT INTO TeamMember (teamid, teamMember, memCount) VALUES ("+currentTeam+",'"+TeamCreate_Member5.getText()+"',5);");
                    Toast.makeText(getApplicationContext(),EditTextTeamName.getText()+""+getString(R.string.teamsavecompleted), Toast.LENGTH_SHORT).show();
                    mpmariotiring.start();

                }catch(Exception e){
                    //입력된 정보가 이미 있을 땐 INSERT 대신 UPDATE
                    sqlDB.execSQL("UPDATE TeamTable SET teamName = '"+EditTextTeamName.getText()+"' WHERE teamid = "+currentTeam+";");
                    sqlDB.execSQL("UPDATE TeamMember SET teamMember = '"+TeamCreate_Member1.getText()+"' WHERE teamid = "+currentTeam+" AND memCount = 1;");
                    sqlDB.execSQL("UPDATE TeamMember SET teamMember = '"+TeamCreate_Member2.getText()+"' WHERE teamid = "+currentTeam+" AND memCount = 2;");
                    sqlDB.execSQL("UPDATE TeamMember SET teamMember = '"+TeamCreate_Member3.getText()+"' WHERE teamid = "+currentTeam+" AND memCount = 3;");
                    sqlDB.execSQL("UPDATE TeamMember SET teamMember = '"+TeamCreate_Member4.getText()+"' WHERE teamid = "+currentTeam+" AND memCount = 4;");
                    sqlDB.execSQL("UPDATE TeamMember SET teamMember = '"+TeamCreate_Member5.getText()+"' WHERE teamid = "+currentTeam+" AND memCount = 5;");
                    Toast.makeText(getApplicationContext(),EditTextTeamName.getText()+""+getString(R.string.teamupdatesuccess), Toast.LENGTH_SHORT).show();
                    mpmariotiring.start();
                }

                sqlDB.close();

                //DB저장 시 팀 뱃지도 바뀌게 하기
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforbadge = sqlDB.rawQuery("SELECT * FROM TeamTable;",null);
                String badge = "";
                try {
                    cursorforbadge.moveToNext();
                    TC_Team1_Text.setText(badge+cursorforbadge.getString(1));
                    cursorforbadge.moveToNext();
                    TC_Team2_Text.setText(badge+cursorforbadge.getString(1));
                    cursorforbadge.moveToNext();
                    TC_Team3_Text.setText(badge+cursorforbadge.getString(1));
                    cursorforbadge.moveToNext();
                    TC_Team4_Text.setText(badge+cursorforbadge.getString(1));

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),getString(R.string.pleasecreateteammember),Toast.LENGTH_SHORT).show();
                }
                cursorforbadge.close();
                sqlDB.close();
            }
        });
        // DB 팀1 불러오기
        TC_Team1_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Btn_TC_DBSave.setVisibility(View.VISIBLE);
                //팀 선택하세요 안내메시지 제거
                TeamCreate_helptext.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), getString(R.string.teamloadsuccessful), Toast.LENGTH_SHORT).show();
                sqlDB = mySQLiteHelper.getReadableDatabase();
                //팀 내용 수정 항목 보이게 하기
                EditTextTeamNameInfo.setVisibility(View.VISIBLE);
                EditTextTeamName.setVisibility(View.VISIBLE);
                EditTextTeamName.setVisibility(View.VISIBLE);
                TeamCreate_Member1.setVisibility(View.VISIBLE);
                TeamCreate_Member2.setVisibility(View.VISIBLE);
                TeamCreate_Member3.setVisibility(View.VISIBLE);
                TeamCreate_Member4.setVisibility(View.VISIBLE);
                TeamCreate_Member5.setVisibility(View.VISIBLE);
                currentTeam = 1; //현재 수정중인 팀은 1임.

                //[DB 사용 시작]
                //DB 에서 팀명 가져오기
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforteam = sqlDB.rawQuery("SELECT * FROM TeamTable WHERE teamid = "+currentTeam+";",null);
                String tname = ""; //데이터가 없을 때에는 공백 불러오기
                //더 이상 데이터가 없을 때까지 Cursor 이동
                while(cursorforteam.moveToNext()){
                    tname += cursorforteam.getString(1);
                }
                EditTextTeamName.setText(tname);
                cursorforteam.close();

                //DB 에서 팀 멤버목록 가져오기
                Cursor cursorformem = sqlDB.rawQuery("SELECT * FROM TeamMember WHERE teamid = "+currentTeam+";",null);
                String mname = ""; //데이터가 없을 때에는 공백 불러오기
                //더 이상 데이터가 없을 때까지 Cursor 이동
                try { //멤버가 있을 때
                    cursorformem.moveToNext();
                    TeamCreate_Member1.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member2.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member3.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member4.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member5.setText(mname+cursorformem.getString(2));
                    cursorformem.close();
                    huuk.start();
                } //멤버가 없을 때
                catch(Exception e){
                    //각 항목 공백으로 변경하기
                    TeamCreate_Member1.setText("");
                    TeamCreate_Member2.setText("");
                    TeamCreate_Member3.setText("");
                    TeamCreate_Member4.setText("");
                    TeamCreate_Member5.setText("");
                    Toast.makeText(getApplicationContext(), getString(R.string.addteammemberhint), Toast.LENGTH_SHORT).show();
                }
                //DB Close
                sqlDB.close();
                //[DB 사용 종료]

            }
        });
        // DB 팀2 불러오기
        TC_Team2_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Btn_TC_DBSave.setVisibility(View.VISIBLE);
                TeamCreate_helptext.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), getString(R.string.teamloadsuccessful), Toast.LENGTH_SHORT).show();
                currentTeam = 2;
                sqlDB = mySQLiteHelper.getReadableDatabase();
                //팀 내용 수정 항목 보이게 하기
                EditTextTeamNameInfo.setVisibility(View.VISIBLE);
                EditTextTeamName.setVisibility(View.VISIBLE);
                EditTextTeamName.setVisibility(View.VISIBLE);
                TeamCreate_Member1.setVisibility(View.VISIBLE);
                TeamCreate_Member2.setVisibility(View.VISIBLE);
                TeamCreate_Member3.setVisibility(View.VISIBLE);
                TeamCreate_Member4.setVisibility(View.VISIBLE);
                TeamCreate_Member5.setVisibility(View.VISIBLE);
                currentTeam = 2; //현재 수정중인 팀은 2임.

                //[DB 사용 시작]
                //DB 에서 팀명 가져오기
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforteam = sqlDB.rawQuery("SELECT * FROM TeamTable WHERE teamid = "+currentTeam+";",null);
                String tname = ""; //데이터가 없을 때에는 공백 불러오기
                //더 이상 데이터가 없을 때까지 Cursor 이동
                while(cursorforteam.moveToNext()){
                    tname += cursorforteam.getString(1);
                }
                EditTextTeamName.setText(tname);
                cursorforteam.close();

                //DB 에서 팀 멤버목록 가져오기
                Cursor cursorformem = sqlDB.rawQuery("SELECT * FROM TeamMember WHERE teamid = "+currentTeam+";",null);
                String mname = ""; //데이터가 없을 때에는 공백 불러오기
                //더 이상 데이터가 없을 때까지 Cursor 이동
                try { //멤버가 있을 때
                    cursorformem.moveToNext();
                    TeamCreate_Member1.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member2.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member3.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member4.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member5.setText(mname+cursorformem.getString(2));
                    cursorformem.close();
                    huuk.start();
                } //멤버가 없을 때
                catch(Exception e){
                    //각 항목 공백으로 변경하기
                    TeamCreate_Member1.setText("");
                    TeamCreate_Member2.setText("");
                    TeamCreate_Member3.setText("");
                    TeamCreate_Member4.setText("");
                    TeamCreate_Member5.setText("");
                    Toast.makeText(getApplicationContext(), getString(R.string.addteammemberhint), Toast.LENGTH_SHORT).show();
                }
                //DB Close
                sqlDB.close();
                //[DB 사용 종료]
            }
        });
        // DB 팀3 불러오기
        TC_Team3_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Btn_TC_DBSave.setVisibility(View.VISIBLE);
                TeamCreate_helptext.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), getString(R.string.teamloadsuccessful), Toast.LENGTH_SHORT).show();
                sqlDB = mySQLiteHelper.getReadableDatabase();
                //팀 내용 수정 항목 보이게 하기
                EditTextTeamNameInfo.setVisibility(View.VISIBLE);
                EditTextTeamName.setVisibility(View.VISIBLE);
                EditTextTeamName.setVisibility(View.VISIBLE);
                TeamCreate_Member1.setVisibility(View.VISIBLE);
                TeamCreate_Member2.setVisibility(View.VISIBLE);
                TeamCreate_Member3.setVisibility(View.VISIBLE);
                TeamCreate_Member4.setVisibility(View.VISIBLE);
                TeamCreate_Member5.setVisibility(View.VISIBLE);
                currentTeam = 3; //현재 수정중인 팀은 3임.

                //[DB 사용 시작]
                //DB 에서 팀명 가져오기
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforteam = sqlDB.rawQuery("SELECT * FROM TeamTable WHERE teamid = "+currentTeam+";",null);
                String tname = ""; //데이터가 없을 때에는 공백 불러오기
                //더 이상 데이터가 없을 때까지 Cursor 이동
                while(cursorforteam.moveToNext()){
                    tname += cursorforteam.getString(1);
                }
                EditTextTeamName.setText(tname);
                cursorforteam.close();

                //DB 에서 팀 멤버목록 가져오기
                Cursor cursorformem = sqlDB.rawQuery("SELECT * FROM TeamMember WHERE teamid = "+currentTeam+";",null);
                String mname = ""; //데이터가 없을 때에는 공백 불러오기
                //더 이상 데이터가 없을 때까지 Cursor 이동
                try { //멤버가 있을 때
                    cursorformem.moveToNext();
                    TeamCreate_Member1.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member2.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member3.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member4.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member5.setText(mname+cursorformem.getString(2));
                    cursorformem.close();
                    huuk.start();
                } //멤버가 없을 때
                catch(Exception e){
                    //각 항목 공백으로 변경하기
                    TeamCreate_Member1.setText("");
                    TeamCreate_Member2.setText("");
                    TeamCreate_Member3.setText("");
                    TeamCreate_Member4.setText("");
                    TeamCreate_Member5.setText("");
                    Toast.makeText(getApplicationContext(), getString(R.string.addteammemberhint), Toast.LENGTH_SHORT).show();
                }
                //DB Close
                sqlDB.close();
                //[DB 사용 종료]
            }
        });
        // DB 팀4 불러오기
        TC_Team4_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Btn_TC_DBSave.setVisibility(View.VISIBLE);
                TeamCreate_helptext.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), getString(R.string.teamloadsuccessful), Toast.LENGTH_SHORT).show();
                sqlDB = mySQLiteHelper.getReadableDatabase();
                //팀 내용 수정 항목 보이게 하기
                EditTextTeamNameInfo.setVisibility(View.VISIBLE);
                EditTextTeamName.setVisibility(View.VISIBLE);
                EditTextTeamName.setVisibility(View.VISIBLE);
                TeamCreate_Member1.setVisibility(View.VISIBLE);
                TeamCreate_Member2.setVisibility(View.VISIBLE);
                TeamCreate_Member3.setVisibility(View.VISIBLE);
                TeamCreate_Member4.setVisibility(View.VISIBLE);
                TeamCreate_Member5.setVisibility(View.VISIBLE);
                currentTeam = 4; //현재 수정중인 팀은 4임.

                //[DB 사용 시작]
                //DB 에서 팀명 가져오기
                sqlDB= mySQLiteHelper.getReadableDatabase();
                Cursor cursorforteam = sqlDB.rawQuery("SELECT * FROM TeamTable WHERE teamid = "+currentTeam+";",null);
                String tname = ""; //데이터가 없을 때에는 공백 불러오기
                //더 이상 데이터가 없을 때까지 Cursor 이동
                while(cursorforteam.moveToNext()){
                    tname += cursorforteam.getString(1);
                }
                EditTextTeamName.setText(tname);
                cursorforteam.close();

                //DB 에서 팀 멤버목록 가져오기
                Cursor cursorformem = sqlDB.rawQuery("SELECT * FROM TeamMember WHERE teamid = "+currentTeam+";",null);
                String mname = ""; //데이터가 없을 때에는 공백 불러오기
                //더 이상 데이터가 없을 때까지 Cursor 이동
                try { //멤버가 있을 때
                    cursorformem.moveToNext();
                    TeamCreate_Member1.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member2.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member3.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member4.setText(mname+cursorformem.getString(2));
                    cursorformem.moveToNext();
                    TeamCreate_Member5.setText(mname+cursorformem.getString(2));
                    cursorformem.close();
                    huuk.start();
                } //멤버가 없을 때
                catch(Exception e){
                    //각 항목 공백으로 변경하기
                    TeamCreate_Member1.setText("");
                    TeamCreate_Member2.setText("");
                    TeamCreate_Member3.setText("");
                    TeamCreate_Member4.setText("");
                    TeamCreate_Member5.setText("");
                    Toast.makeText(getApplicationContext(), getString(R.string.addteammemberhint), Toast.LENGTH_SHORT).show();
                }
                //DB Close
                sqlDB.close();
                //[DB 사용 종료]
            }
        });
        // 팀 만들기 메뉴 창 닫기
        Btn_TC_Quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeamCreate_ConstLayout.setVisibility(View.GONE);
                Main_MenuGrid.setVisibility(View.VISIBLE);
                TeamCreate_helptext.setVisibility(View.GONE);
                TeamCreate_Member1.setVisibility(View.INVISIBLE);
                TeamCreate_Member2.setVisibility(View.INVISIBLE);
                TeamCreate_Member3.setVisibility(View.INVISIBLE);
                TeamCreate_Member4.setVisibility(View.INVISIBLE);
                TeamCreate_Member5.setVisibility(View.INVISIBLE);
                EditTextTeamNameInfo.setVisibility(View.INVISIBLE);
                EditTextTeamName.setVisibility(View.INVISIBLE);
            }
        });


        /* 팀 만들기 메뉴에서의 메소드 끝*/





        /* 게임 시작(선택) 메뉴에서의 메소드 시작*/
        Btn_GS_BackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameSelect_ConstLayout.setVisibility(View.GONE);
                Main_MenuGrid.setVisibility(View.VISIBLE);

            }
        });
        /* 게임 시작(선택) 메뉴에서의 메소드 끝*/





        /* 팀 배정 조회 화면에서의 메소드 시작*/
        // 뒤로가기 버튼을 눌러 메인메뉴 복귀
        Btn_TS_backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeamShow_ConstLayout.setVisibility(View.GONE);
                Main_MenuGrid.setVisibility(View.VISIBLE);
            }
        });
        /* 팀 배정 조회 화면에서의 메소드 종료*/






        /* 현재 순위 화면에서의 메소드 시작*/
        //뒤로가기 버튼을 눌러 메인메뉴 돌아가기
        Btn_TB_backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeamBoard_ConstLayout.setVisibility(View.GONE);
                Main_MenuGrid.setVisibility(View.VISIBLE);

                TB_Topteam_Name.setVisibility(View.VISIBLE);
                TB_teamBoard_Topteam_Score.setVisibility(View.VISIBLE);
                TB_teamBoard_Middle_CurrentTop.setVisibility(View.VISIBLE);
                //버그픽스, 처음 공동1등이였다가 나중에 단독 1등으로 바뀔 경우 텍스트가 바뀌지 않는 현상 수정
                TB_teamBoard_Middle_CurrentTop.setText(getString(R.string.current1st));
            }
        });
        /* 현재 순위 화면에서의 메소드 종료*/






        /* 설정 화면에서의 메소드 시작 */


        //확인버튼 눌러 값 GlobalPref 에 저장하기
        Btn_st_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpmariotiring.start();
                SettingMenu_layout.setVisibility(View.INVISIBLE);
                editor.commit();// 설정값 sharedpreferences 에 저장.
                //언어 적용
                switch(preferences.getString("locale", "ko")){
                    case "ko":
                        Log.d("Locale set : ","ko");
                        config.locale = Locale.KOREAN;
                        break;
                    case "en":
                        Log.d("Locale set : ","en");
                        config.locale = Locale.ENGLISH;
                        break;
                    case "jp":
                        Log.d("Locale set : ","jp");
                        config.locale = Locale.JAPANESE;
                        break;
                }
                //config 의 lang설정 가져와서
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                Intent refresh = getApplicationContext().getPackageManager().getLaunchIntentForPackage(getPackageName());
                //스택 초기화
                refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(refresh);
            }
        });
        // 뒤로가기 버튼을 눌러 메인메뉴 돌아가기
        Btn_st_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingMenu_layout.setVisibility(View.INVISIBLE);
                if(preferences.getBoolean("ISDARKMODE",true)){
                    Switchfordark.setChecked(true); //설정값이 on 이면 다크모드로 스위치 세팅
                }
                else{
                    Switchfordark.setChecked(false); //설정값이 false 이면 화이트모드로 스위치 세팅
                }
            }
            //나중에 설정 창 다시 불렀을때 스위치 애니메이션이 보이는 현상 수정
        });

        /* 설정 화면에서의 메소드 종료 */


    }

    //[호출] 뒤로가기 물리버튼 눌렀을 때

    //제거 시 스플래쉬 화면으로 돌아가므로 삭제 금지
    //뒤로가기 물리버튼 한번 눌렀을 때는 종료 안되고

    //기존 뒤로가기 버튼 메소드 오버라이드
    @Override
    public void onBackPressed() {
        Main_MenuGrid.setVisibility(View.VISIBLE);
        TeamCreate_ConstLayout.setVisibility(View.GONE);
        GameSelect_ConstLayout.setVisibility(View.GONE);
        SettingMenu_layout.setVisibility(View.GONE);
        ScoresetMenu_Layout.setVisibility(View.GONE);
        BonusMenu_Layout.setVisibility(View.GONE);
        TeamBoard_ConstLayout.setVisibility(View.GONE);
        TeamShow_ConstLayout.setVisibility(View.GONE);
        long basetime = System.currentTimeMillis();
        long interval = basetime - pressedstamp;
        if (0 <= interval && finishstamp >= interval)
        {
            //앱 종료
            finish();
        }
        else
        {
            pressedstamp = basetime;
            Toast.makeText(getApplicationContext(), "한번 더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }

    //mySQLiteHelper 클래스 정의
    public class mySQLiteHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "TeamBoard.db";

        public mySQLiteHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //db 안에 테이블을 만드는 것
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE TeamTable (teamid INTEGER PRIMARY KEY, teamName TEXT, teamScore INTEGER);");
            db.execSQL("CREATE TABLE TeamMember (id INTEGER PRIMARY KEY AUTOINCREMENT, teamid INTEGER, teamMember TEXT, memCount INTEGER);");
            //팀 점수와 팀 소속 멤버를 별도의 테이블에 저장
        }
        //db 안에 테이블 제거하는 것
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS TeamTable");
            db.execSQL("DROP TABLE IF EXISTS TeamMember");
            onCreate(db);
        }
    }


}