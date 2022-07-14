package arca.dev.genshinauto;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

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

import static android.content.Context.NOTIFICATION_SERVICE;
import static arca.dev.genshinauto.MainActivity.NOTI_CHANNEL;

public class Schedule extends BroadcastReceiver {
    SharedPreferences pref;
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);

        int hour = pref.getInt("hour", 0);
        int min = pref.getInt("min", 0);

        String ltuid = pref.getString("ltuid", "");
        String ltoken = pref.getString("ltoken", "");

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url(context.getString(R.string.hoyolab_api_url))
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
                            Log.d("dev", "run: " + response.body().toString());
                            JSONObject json = new JSONObject(response.body().string());
                            String msg = json.getString("message");
                            Boolean pushStatus = pref.getBoolean("pushStatus", true);
                            if (pushStatus) {
                                if (msg.equals("OK")){
                                    msg = context.getString(R.string.checkin_complete);
                                }
                                pushSender(msg);
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        MainActivity.createAlarm(context.getApplicationContext(), hour, min);
    }

    public void pushSender(String msg){
        Log.d("DEV", "pushSender: start");
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), NOTI_CHANNEL);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground))
                .setTicker(msg)
                .setWhen(System.currentTimeMillis())
                .setNumber(1)
                .setContentTitle(context.getString(R.string.app_name))
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
}
