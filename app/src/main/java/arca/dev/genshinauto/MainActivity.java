package arca.dev.genshinauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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

        Boolean serviceStatus = pref.getBoolean("serviceStatus", false);
        Boolean pushStatus = pref.getBoolean("pushStatus", false);
        Switch serviceSw = findViewById(R.id.serviceSwitch);
        Switch pushSw = findViewById(R.id.pushSwitch);
        serviceSw.setChecked(serviceStatus);
        pushSw.setChecked(pushStatus);

        serviceSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    //알람매니저 실행
                    editor.putBoolean("serviceStatus", true);
                    editor.apply();
                } else {
                    //알람매니저 해제
                    editor.putBoolean("serviceStatus", false);
                    editor.apply();
                }
            }
        });


        pushSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    //푸시 켜기
                    editor.putBoolean("pushStatus", true);
                    editor.apply();
                } else {
                    //푸시 끄기
                    editor.putBoolean("pushStatus", false);
                    editor.apply();
                }
            }
        });
    }

    public void manual (View v) throws IOException {
        //TODO 등록된 정ㅇ보 있나 확인부터
        request();
    }

    /*public void hoyolabOpen (View v){

    }*/

    public void manualToken (View v){
        String formalUid = pref.getString("ltuid","");
        String formalToken = pref.getString("ltoken","");
        final EditText editUid = new EditText(this);
        editUid.setInputType(InputType.TYPE_CLASS_NUMBER);
        editUid.setText(formalUid);
        editUid.setHint("ltuid");
        final EditText editToken = new EditText(this);
        editToken.setInputType(InputType.TYPE_CLASS_TEXT);
        editToken.setText(formalToken);
        editToken.setHint("ltoken");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("UID 변경")
                .setMessage("변경할 UID를 입력해주세요.")
                .setView(editUid)
                .setPositiveButton("변경", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.putString("ltuid", editUid.getText().toString());
                        editor.apply();
                        dialogInterface.dismiss();
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                        builder2.setTitle("토큰 변경")
                                .setMessage("변경할 토큰을 입력해주세요.")
                                .setView(editToken)
                                .setPositiveButton("변경", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        editor.putString("ltoken", editToken.getText().toString());
                                        editor.apply();
                                        dialogInterface.dismiss();
                                    }
                                });
                        builder2.show();
                    }
                });
        builder.show();
    }

    public void request() throws IOException{
        String ltuid = pref.getString("ltuid", "");
        String ltoken = pref.getString("ltoken", "");

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url("https://hk4e-api-os.mihoyo.com/event/sol/sign?act_id=e202102251931481&lang=ko-kr")
                .addHeader("Cookie","ltuid=" + ltuid + ";ltoken=" + ltoken + ";")
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONObject json = new JSONObject(response.body().string());
                            Toast.makeText(MainActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e){
                            e.printStackTrace();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}