package com.retrofitsample.api;


import com.retrofitsample.model.ListApiResponse;
import com.retrofitsample.model.RatingDetails;
import com.retrofitsample.restServices.OnApiResponseListener;
import com.retrofitsample.restServices.RestService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 23/6/17.
 */

public class GetRatingDetailsAPI {
    private OnApiResponseListener listener;
    private String params;

    public GetRatingDetailsAPI(String params,OnApiResponseListener listener){
        this.listener = listener;
        this.params=params;
        doWebServiceCall();
    }
    public void doWebServiceCall(){
        Call<ArrayList<RatingDetails>> list = RestService.getInstance().restInterface.getRatingDetails(params);
        list.enqueue(new Callback<ArrayList<RatingDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<RatingDetails>> call, Response<ArrayList<RatingDetails>> response) {
                if (response.body()!=null)
                    listener.onSuccess(response.body());
                else
                    listener.onError(response.errorBody());
            }
            @Override
            public void onFailure(Call<ArrayList<RatingDetails>> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
