package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface CountryService {

    @GET("/country")
    Call<List<Country>> get4RandomCountries();
}
