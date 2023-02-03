package jp.ac.jec.cm0129.android112quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

public class QuizActivity extends AppCompatActivity {

    public  static final int CODE_QUIZ = 456;
    private int nowIndex = 0;
    private int correctNo = 0;
    private int highScore = 0;

    private String[] questions = {
            "ケータイアプリケーション科は創立10年になる",
            "今年は戌年だ",
            "就活は就職活動の略だ",
            "ケータイアプリケーション科はJavaの授業をしている",
            "授業は120分授業だ"
    };
    private boolean[] ans = {
            false,
            false,
            true,
            true,
            false
    };

    private TextView txtQuestion;
    private TextView qIndex;

    private boolean isTrue = false;
    private int percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtQuestion = findViewById(R.id.question);
        txtQuestion.setText(questions[nowIndex]);
        qIndex = findViewById(R.id.qIndex);
        qIndex.setText("第"+(nowIndex+1)+"問");

        SharedPreferences sp = getSharedPreferences("android112Quiz",MODE_PRIVATE);
        String s = sp.getString("Score","");
        if(s.equals("")){

        } else {
            highScore = Integer.parseInt(s);
        }


        ImageButton btnYes = findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isTrue = true;
                checkQuestion();
            }
        });

        ImageButton btnNo = findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isTrue = false;
                checkQuestion();
            }
        });
    }
    public void checkQuestion() {

        if(ans[nowIndex] == isTrue){
            correctNo++;

        } else {

        }
        float arrayLength = new Float(questions.length);
        double res = (correctNo / arrayLength) * 100.0;
        percentage = (int) Math.round(res);
        System.out.println("percentage----->"+percentage);

        System.out.println("CorrectNo = "+correctNo+"/"+questions.length);
        nowIndex++;



        if(questions.length <= nowIndex){
            showToast();

            Intent intent = getIntent();
            intent.putExtra("correctNumFromQuizz",String.valueOf(correctNo));
            setResult(RESULT_OK,intent);

            if (highScore < correctNo){
                highScore = correctNo;
            }
            SharedPreferences sp = getSharedPreferences("android112Quiz",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Score",String.valueOf(highScore));
            editor.apply();

            finish();
            //closeApp();
            return;
        }

        qIndex.setText("第"+(nowIndex+1)+"問");
        txtQuestion.setText(questions[nowIndex]);

    }
    public void showToast(){
        //Toast.makeText(QuizActivity.this,str,Toast.LENGTH_SHORT).show();
        FancyToast.makeText(this,"あなたは　"+correctNo+" 問正解しました。　また、正解率は　"+percentage+"　％です。", FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
    }
    public void closeApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAndRemoveTask(); //closes the application
                } else {
                    finishAffinity();
                    System.exit(0); //use these two lines of code are for older versions of android
                }
            }
        }, 5000); // 5 seconds
    }

}