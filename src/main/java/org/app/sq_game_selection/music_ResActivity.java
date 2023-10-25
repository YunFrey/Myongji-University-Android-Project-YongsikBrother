package org.app.sq_game_selection;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class music_ResActivity extends AppCompatActivity {
    TextView ta,tb,tc,td;
    Integer Ta,Tb,Tc,Td;
    Button btn_66_backToMenu_ran;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.music_activity_res);
        ActionBar actionBar = getSupportActionBar();

        btn_66_backToMenu_ran = (Button) findViewById(R.id.btn_66_backToMenu_ran);

        actionBar.hide();
        Intent intent = getIntent();

        Intent aaaintent=getIntent();
        String passname_team1="";
        String passname_team2="";
        String passname_team3="";
        String passname_team4="";
        passname_team1=aaaintent.getStringExtra("team1name");
        passname_team2=aaaintent.getStringExtra("team2name");
        passname_team3=aaaintent.getStringExtra("team3name");
        passname_team4=aaaintent.getStringExtra("team4name");

        Ta = intent.getIntExtra("a",0);
        Tb = intent.getIntExtra("b",0);
        Tc = intent.getIntExtra("c",0);
        Td = intent.getIntExtra("d",0);

        ta = (TextView) findViewById(R.id.textViewTA);
        tb = (TextView) findViewById(R.id.textViewTB);
        tc = (TextView) findViewById(R.id.textViewTC);
        td = (TextView) findViewById(R.id.textViewTD);

        ta.setText(passname_team1+"팀 : "+Ta);
        tb.setText(passname_team2+"팀 : "+Tb);
        tc.setText(passname_team3+"팀 : "+Tc);
        td.setText(passname_team4+"팀 : "+Td);

        btn_66_backToMenu_ran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomenu = new Intent(music_ResActivity.this, sqgameselect.class);
                backtomenu.putExtra("teamscore_a", Ta);
                backtomenu.putExtra("teamscore_b", Tb);
                backtomenu.putExtra("teamscore_c", Tc);
                backtomenu.putExtra("teamscore_d", Td);
                startActivity(backtomenu);
            }
        });



    }
}
