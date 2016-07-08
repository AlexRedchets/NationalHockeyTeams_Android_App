package com.azvk.nationalhockeyteams.client;

import com.azvk.nationalhockeyteams.model.Player;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlayerClient {
    @GET("api/player/{team}")
    Call<List<Player>> player(
            @Path("team") String team
    );
}

