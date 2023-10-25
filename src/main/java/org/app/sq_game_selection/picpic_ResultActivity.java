package org.app.sq_game_selection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class picpic_ResultActivity extends AppCompatActivity {

    TextView ta,tb,tc,td;
    Integer Ta,Tb,Tc,Td;
    Button btn_33_backToMenu_ran;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picpic_activity_result);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();

        btn_33_backToMenu_ran = (Button) findViewById(R.id.btn_33_backToMenu_ran);

        Intent aaaintent = getIntent();
        String passname_team1 = "";
        String passname_team2 = "";
        String passname_team3 = "";
        String passname_team4 = "";
        passname_team1 = aaaintent.getStringExtra("team1name");
        passname_team2 = aaaintent.getStringExtra("team2name");
        passname_team3 = aaaintent.getStringExtra("team3name");
        passname_team4 = aaaintent.getStringExtra("team4name");

        Ta = intent.getIntExtra("a",0);
        Tb = intent.getIntExtra("b",0);
        Tc = intent.getIntExtra("c",0);
        Td = intent.getIntExtra("d",0);

        ta = (TextView) findViewById(R.id.textViewA);
        tb = (TextView) findViewById(R.id.textViewB);
        tc = (TextView) findViewById(R.id.textViewC);
        td = (TextView) findViewById(R.id.textViewD);

        ta.setText(passname_team1+"팀 : "+Ta);
        tb.setText(passname_team2+"팀 : "+Tb);
        tc.setText(passname_team3+"팀 : "+Tc);
        td.setText(passname_team4+"팀 : "+Td);

        btn_33_backToMenu_ran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomenu = new Intent(picpic_ResultActivity.this, sqgameselect.class);
                backtomenu.putExtra("teamscore_a", Ta);
                backtomenu.putExtra("teamscore_b", Tb);
                backtomenu.putExtra("teamscore_c", Tc);
                backtomenu.putExtra("teamscore_d", Td);
                //현재게임 스택에서 제외하기, 최적화 및 중복버그 방지
                backtomenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(backtomenu);
            }
        });

    }
}
