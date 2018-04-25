package com.retrofitsample.api;


import com.retrofitsample.model.ListApiResponse;
import com.retrofitsample.model.RatingDetails;
import com.retrofitsample.restServices.OnApiResponseListener;
import com.retrofitsample.restServices.RestService;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 23/6/17.
 */

public class GetRatingListFilterAPI {
    private OnApiResponseListener listener;
    private HashMap<String, String> params;

    public GetRatingListFilterAPI(HashMap<String, String> params, OnApiResponseListener listener){
        this.listener = listener;
        this.params=params;
        doWebServiceCall();
    }
    public void doWebServiceCall(){
        Call<ArrayList<ListApiResponse>> list = RestService.getInstance().restInterface.getRatingFilteredList(params);
        list.enqueue(new Callback<ArrayList<ListApiResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ListApiResponse>> call, Response<ArrayList<ListApiResponse>> response) {
                if (response.body()!=null)
                    listener.onSuccess(response.body());
                else
                    listener.onError(response.errorBody());
            }
            @Override
            public void onFailure(Call<ArrayList<ListApiResponse>> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
