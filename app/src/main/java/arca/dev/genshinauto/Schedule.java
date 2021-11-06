package arca.dev.genshinauto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Schedule extends BroadcastReceiver {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DEV", "onReceive: received");
        pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = pref.edit();

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
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONObject json = new JSONObject(response.body().string());
                            Toast.makeText(context, json.getString("message"), Toast.LENGTH_SHORT).show();
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
