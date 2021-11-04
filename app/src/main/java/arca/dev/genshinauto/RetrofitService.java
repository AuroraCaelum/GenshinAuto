package arca.dev.genshinauto;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {
    String url = "https://hk4e-api-os.mihoyo.com/event/sol/sign?act_id=e202102251931481&lang=ko-kr/";
    //String url = "https://hk4e-api-os.mihoyo.com/event/sol/";
    //String act_id = "e202102251931481";
    //String lang = "ko-kr";

    @FormUrlEncoded
    @POST("/posts")
    Call<Data> postData(@FieldMap HashMap<String, Object> param);
}
