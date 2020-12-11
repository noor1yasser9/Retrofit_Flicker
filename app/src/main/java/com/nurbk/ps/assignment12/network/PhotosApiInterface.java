package com.nurbk.ps.assignment12.network;

import com.nurbk.ps.assignment12.model.Photo;
import com.nurbk.ps.assignment12.model.Root;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotosApiInterface {


//    https://www.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=&format=rest

    @GET("rest/?api_key=5aa34d49b5889b2ad7b98ffd0fe03f4a&format=json&nojsoncallback=1")
    Call<Root> getMethodData(@Query("method") String method,
                             @Query("user_id") String userId,
                             @Query("text") String text);

}
