package com.retrofitsample.restServices;

import com.retrofitsample.model.GetUserMain;
import com.retrofitsample.model.Items;
import com.retrofitsample.model.ListApiResponse;
import com.retrofitsample.model.Order;
import com.retrofitsample.model.RatingDetails;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by tkyh on 9/22/2017.
 */

public interface MInterface {
    String BASE_URL ="http://floridaconstruct.eu/" ;

    @GET("http://floridaconstruct.eu/comenzi/test/comenzi.php")
    Call<ArrayList<Order>> getUsers();

    @GET("comenzi/test/testgrafic.php")
    Call<ArrayList<Order>> getComenziGrafic();

    @GET("comenzipeperioada.php")
    Call<ArrayList<Order>> getComenziGraficPerioada(@Query("start") String start,
                                                    @Query("end") String end);
    @GET("comenzi/test/produsemagazie.php")

    Call<ArrayList<Items>> getItems();

    @GET
     Call<GetUserMain> getUserData(@Url String url);
    @GET
    Call<ResponseBody> getRegister(@Url String url);


    @GET("comenzi/test/ratingnew.php")
    Call<ArrayList<ListApiResponse>> getRatingList();

    @GET
    Call<ArrayList<RatingDetails>> getRatingDetails(@Url String url);
}