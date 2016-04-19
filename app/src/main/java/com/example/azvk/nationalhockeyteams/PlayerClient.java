package com.example.azvk.nationalhockeyteams;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlayerClient {
    @GET("{api}/{player}")
    Call<List<Player>> players(
            @Path("api") String api,
            @Path("player") String player
    );

}

