package arca.dev.genshinauto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView)findViewById(R.id.webView);

        initWebView();
    }

    public void initWebView(){
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(webView, true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("https://webstatic-sea.mihoyo.com/ys/event/signin-sea/index.html?act_id=e202102251931481&lang=ko-kr");

    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    public void autoToken (View v){
        try {
            String cookieRaw = CookieManager.getInstance().getCookie("https://webstatic-sea.mihoyo.com/ys/event/signin-sea/index.html?act_id=e202102251931481&lang=ko-kr") + ";";

            String ltoken_target = "ltoken=";
            String ltuid_target = "ltuid=";
            int ltoken_t_num = cookieRaw.indexOf(ltoken_target);
            int ltuid_t_num = cookieRaw.indexOf(ltuid_target);
            String ltoken = cookieRaw.substring(ltoken_t_num+7,(cookieRaw.substring(ltoken_t_num).indexOf(";")+ltoken_t_num));
            String ltuid = cookieRaw.substring(ltuid_t_num+6,(cookieRaw.substring(ltuid_t_num).indexOf(";")+ltuid_t_num));

            Log.d("dev", "autoToken: " + ltoken);
            Log.d("dev", "autoToken: " + ltuid);

            pref = getSharedPreferences("pref", MODE_PRIVATE);
            editor = pref.edit();

            editor.putBoolean("firstRun", false);
            editor.putString("ltoken", ltoken);
            editor.putString("ltuid", ltuid);
            editor.apply();

            Toast.makeText(this, "토큰이 정상적으로 등록되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        } catch (StringIndexOutOfBoundsException e){
            Toast.makeText(this, "유효한 토큰이 발견되지 않았습니다.", Toast.LENGTH_SHORT).show();
        }

    }

}