package arca.dev.genshinauto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

public class BootReceiver extends BroadcastReceiver {
    public static AlarmManager alarmManager = null;
    public static PendingIntent sender = null;
    SharedPreferences pref;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            pref = context.getSharedPreferences("pref", MODE_PRIVATE);
            Boolean serviceStatus = pref.getBoolean("serviceStatus", false);
            int hour = pref.getInt("hour", 0);
            int min = pref.getInt("min", 0);
            if (serviceStatus){
                alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Intent intent_a = new Intent(context, Schedule.class);
                sender = PendingIntent.getBroadcast(context, 12321, intent_a, 0);

                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("PRC"));
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, min);
                calendar.set(Calendar.SECOND, 10);
                if(Build.VERSION.SDK_INT >= 23){
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY, sender);
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY, sender);
                }
                Log.d("DEV", "BootReceiver: success");
            }
        }
    }

}
