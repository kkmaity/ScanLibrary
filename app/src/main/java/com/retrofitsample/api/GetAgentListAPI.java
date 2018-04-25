package com.retrofitsample.api;


import com.retrofitsample.model.Agent;
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

public class GetAgentListAPI {
    private OnApiResponseListener listener;

    public GetAgentListAPI(OnApiResponseListener listener){
        this.listener = listener;
        doWebServiceCall();
    }
    public void doWebServiceCall(){
        Call<ArrayList<Agent>> list = RestService.getInstance().restInterface.getAgentList();
        list.enqueue(new Callback<ArrayList<Agent>>() {
            @Override
            public void onResponse(Call<ArrayList<Agent>> call, Response<ArrayList<Agent>> response) {
                if (response.body()!=null)
                    listener.onSuccess(response.body());
                else
                    listener.onError(response.errorBody());
            }
            @Override
            public void onFailure(Call<ArrayList<Agent>> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
