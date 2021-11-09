package arca.dev.genshinauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    public static final String NOTI_CHANNEL = "12321";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static AlarmManager alarmManager = null;
    public static PendingIntent sender = null;
    String updateUrl = "https://raw.githubusercontent.com/dev-by-david/GenshinAuto/main/app/version.txt";
    //final NotificationManager notificationManager;
    //final Notification.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();

        checkUpdate();

        boolean checkFirstRun = pref.getBoolean("firstRun", true);

        if (checkFirstRun) {
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
        Boolean pushStatus = pref.getBoolean("pushStatus", true);
        Switch serviceSw = findViewById(R.id.serviceSwitch);
        Switch pushSw = findViewById(R.id.pushSwitch);
        serviceSw.setChecked(serviceStatus);
        pushSw.setChecked(pushStatus);

        serviceSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //알람매니저 실행
                    alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getApplicationContext(), Schedule.class);
                    sender = PendingIntent.getBroadcast(getApplicationContext(), 12321, intent, 0);


                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("PRC")); //Asia/Seoul
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, 0); //1
                    calendar.set(Calendar.MINUTE, 0); //10 BootReceiver에도 똑같이
                    calendar.set(Calendar.SECOND, 30);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, sender);
                    Log.d("DEV", "onCheckedChanged: success");

                    editor.putBoolean("serviceStatus", true);
                    editor.apply();
                } else {
                    //알람매니저 해제
                    if (sender!=null){
                        alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getApplicationContext(), Schedule.class);
                        sender = PendingIntent.getBroadcast(getApplicationContext(),12321, intent, 0);
                        alarmManager.cancel(sender);
                        sender.cancel();
                        alarmManager = null;
                        sender = null;
                        Log.d("DEV", "onCheckedChanged: canceled" );
                    }
                    editor.putBoolean("serviceStatus", false);
                    editor.apply();
                }
            }
        });


        pushSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
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

    public void manual(View v) throws IOException {
        //TODO 등록된 정ㅇ보 있나 확인부터
        request();
        //Intent intent = new Intent(getApplicationContext(), Schedule.class);
        //startActivity(intent);
    }

    /*public void hoyolabOpen (View v){

    }*/

    public void manualToken(View v) {
        String formalUid = pref.getString("ltuid", "");
        String formalToken = pref.getString("ltoken", "");
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

    public void request() throws IOException {
        String ltuid = pref.getString("ltuid", "");
        String ltoken = pref.getString("ltoken", "");

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url("https://hk4e-api-os.mihoyo.com/event/sol/sign?act_id=e202102251931481&lang=ko-kr")
                .addHeader("Cookie", "ltuid=" + ltuid + ";ltoken=" + ltoken + ";")
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
                        try {
                            JSONObject json = new JSONObject(response.body().string());
                            String msg = json.getString("message");
                            Boolean pushStatus = pref.getBoolean("pushStatus", true);
                            if (pushStatus){
                                if (msg.equals("OK")){
                                    msg = "출석체크 완료!";
                                    //Toast.makeText(MainActivity.this, "출석체크 완료!", Toast.LENGTH_SHORT).show();
                                }
                                pushSender(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void pushSender(String msg){
        Log.d("DEV", "pushSender: start");
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTI_CHANNEL);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground))
                .setTicker(msg)
                .setWhen(System.currentTimeMillis())
                .setNumber(1)
                .setContentTitle("원신 자동출첵")
                .setContentText(msg)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            builder.setSmallIcon(R.drawable.ic_launcher_background);
            CharSequence channelName = "GENSHIN NOTIFICATION CHANNEL";
            String description = "OREO Compat";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(NOTI_CHANNEL, channelName, importance);
            channel.setDescription(description);

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        } else builder.setSmallIcon(R.mipmap.ic_launcher);
        assert notificationManager != null;
        notificationManager.notify(12321, builder.build());
    }

    void checkUpdate(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuffer stringBuffer = new StringBuffer();
                try {
                    URL url = new URL(updateUrl);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    if (conn!=null){
                        conn.setConnectTimeout(5000);
                        conn.setUseCaches(false);
                        if (conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                            while (true){
                                String line = bufferedReader.readLine();
                                if (line == null) break;
                                stringBuffer.append(line);
                            }
                            bufferedReader.close();
                        }
                        conn.disconnect();
                    }
                    String currentVersion = getString(R.string.version);
                    if (!currentVersion.equals(stringBuffer.toString())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("업데이트 알림")
                                .setMessage("신규 버전이 있습니다. 지금 업데이트 하시겠습니까?")
                                .setCancelable(true)
                                .setPositiveButton("지금 업데이트", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/dev-by-david/GenshinAuto/releases"));
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        builder.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}