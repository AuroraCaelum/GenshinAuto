package arca.dev.genshinauto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();

        boolean checkFirstRun = pref.getBoolean("firstRun", true);

        if (checkFirstRun){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("토큰 등록")
                    .setMessage("호요랩 로그인을 통해 자동 등록 하시거나, 직접 토큰을 입력해주세요.")
                    .setCancelable(false)
                    .setPositiveButton("수동 등록", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //TODO 등록 웹뷰 띄우기
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("자동 등록", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                            startActivity(intent);
                        }
                    });
            builder.show();
        } else {
            Log.d("firstRun", "N");
        }
    }

    public void manual (View v){

    }

    public void hoyolabOpen (View v){

    }

    public void manualToken (View v){

    }
}