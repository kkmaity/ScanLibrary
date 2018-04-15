package com.retrofitsample.api;


import com.retrofitsample.model.ListApiResponse;
import com.retrofitsample.restServices.OnApiResponseListener;
import com.retrofitsample.restServices.RestService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 23/6/17.
 */

public class GetListAPI {
    private OnApiResponseListener listener;

    public GetListAPI(OnApiResponseListener listener){
        this.listener = listener;
        doWebServiceCall();
    }
    public void doWebServiceCall(){
        Call<ArrayList<ListApiResponse>> list = RestService.getInstance().restInterface.getRatingList();
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
