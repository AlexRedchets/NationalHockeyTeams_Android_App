package com.azvk.nationalhockeyteams.client;

import com.azvk.nationalhockeyteams.model.Player;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PlayerSearchClient {

    @POST("api/player/search")
    Call<Player> searchPlayer(
            @Body Player player
    );
}
