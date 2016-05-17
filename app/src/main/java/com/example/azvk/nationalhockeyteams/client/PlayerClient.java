package com.example.azvk.nationalhockeyteams.client;

import com.example.azvk.nationalhockeyteams.model.Player;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlayerClient {
    @GET("api/player/{team}")
    Call<List<Player>> player(
            @Path("team") String team
    );

}

