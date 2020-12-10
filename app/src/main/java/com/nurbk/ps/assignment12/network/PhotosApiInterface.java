package com.nurbk.ps.assignment12.network;

import com.nurbk.ps.assignment12.model.Photo;
import com.nurbk.ps.assignment12.model.Root;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotosApiInterface {


    @GET("rest/?api_key=c978438c8cc7ec853341dde4df7db9fb&format=json&nojsoncallback=1")
    Call<Root> getMethodData(@Query("method") String method,
                             @Query("user_id") String userId,
                             @Query("text") String text);

}
