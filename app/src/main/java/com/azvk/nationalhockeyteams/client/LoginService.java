package com.azvk.nationalhockeyteams.client;

import com.azvk.nationalhockeyteams.model.Team;
import com.azvk.nationalhockeyteams.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginService {
    @POST("api/login")
    Call<User> userLogin(@Body User user);

    @POST("api/registration")
    Call<User> userRegistration(@Body User user);

    @GET("api/team")
    Call<List<Team>> teamInfo();
}