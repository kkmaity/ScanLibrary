package com.retrofitsample.restServices;

/**
 * Created by root on 14/12/16.
 */

public interface OnApiResponseListener {
    public <E> void onSuccess(E t);
    public <E> void onError(E t);
    public  void onError();
}
