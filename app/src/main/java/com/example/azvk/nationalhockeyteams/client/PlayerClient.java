package com.example.azvk.nationalhockeyteams.client;

import com.example.azvk.nationalhockeyteams.model.Player;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlayerClient {
    @GET("api/player")
    Call<List<Player>> player();

}

