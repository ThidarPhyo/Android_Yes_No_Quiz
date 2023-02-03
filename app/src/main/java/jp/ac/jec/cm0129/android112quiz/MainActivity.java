package jp.ac.jec.cm0129.android112quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String name = "名無し";
    private String correctNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton btnUser = findViewById(R.id.btnUserSetting);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                intent.putExtra("userName",name);
                startActivityForResult(intent,SettingActivity.CODE_SETTING);
            }
        });

        ImageButton btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,QuizActivity.class);

                startActivityForResult(intent,QuizActivity.CODE_QUIZ);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("resultCode= "+resultCode);
        if(requestCode == SettingActivity.CODE_SETTING){
            if(resultCode == RESULT_OK){
                name = data.getStringExtra("userName");
                TextView txtWel = findViewById(R.id.txtWelcome);
                txtWel.setText(" ようこそ 「"+name+"」さん!");

                return;
            }
        } else if (requestCode == QuizActivity.CODE_QUIZ){
            if(resultCode == RESULT_OK){
                //System.out.println("resultCode= "+resultCode);

                TextView score = findViewById(R.id.hiScore);
                TextView highScore = findViewById(R.id.highScore);
                correctNum = data.getStringExtra("correctNumFromQuizz");
                //System.out.println("message= "+correctNum);
                SharedPreferences sp = getSharedPreferences("android112Quiz",MODE_PRIVATE);
                String s = sp.getString("Score","");

                highScore.setText("HighScore : "+s);
                score.setText("現在のハイスコア： "+correctNum+"問："+name+"さん");
                return;
            }
        }
    }
}