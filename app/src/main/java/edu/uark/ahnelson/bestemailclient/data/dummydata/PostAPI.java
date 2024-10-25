package edu.uark.ahnelson.bestemailclient.data.dummydata;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PostAPI {
    @GET("posts/")
    Call<List<Post>> loadPosts();
}
