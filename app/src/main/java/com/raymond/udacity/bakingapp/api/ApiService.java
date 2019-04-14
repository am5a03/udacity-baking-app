package com.raymond.udacity.bakingapp.api;

import com.raymond.udacity.bakingapp.models.api.ApiRecipe;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Single<List<ApiRecipe>> getReceipes();
}
