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
import android.widget.EditText;
import android.widget.Toast;

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

    public void manual (View v) throws IOException {
        //TODO 등록된 정ㅇ보 있나 확인부터
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
                String finalResponse = response.body().string();
                Log.d("dev", "manual: " + finalResponse);
            }
        });


        //String act_id = RetrofitService.act_id;
        //String lang = RetrofitService.lang;
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        HashMap<String, Object> input = new HashMap<>();
        //input.put("act_id", act_id);
        //input.put("lang", lang);
        input.put("ltuid", ltuid);
        input.put("ltoken", ltoken);
        retrofitService.postData(input).enqueue(new Callback<Data>(){
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response){
                if (response.isSuccessful()){
                    Data body = response.body();
                    if (body!=null){
                        Log.d("getMessage", body.getMessage());
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t){

            }
        });*/
    }

    public void hoyolabOpen (View v){

    }

    public void manualToken (View v){

    }
}