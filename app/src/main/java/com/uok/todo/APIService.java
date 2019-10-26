package com.uok.todo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @GET("todo/{user}")
    Call<APIResponse> getAllItems(@Path("user") String user);

    @POST("todo/{user}/{id}")
    Call<APIResponse> updateItem(@Path("user") String user, @Path("id") String id, @Body Data data);

    @POST("todo/{user}")
    Call<APIResponse> addItem(@Path("user") String user, @Body Data data);

}
