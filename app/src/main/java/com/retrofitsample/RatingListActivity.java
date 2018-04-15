package com.retrofitsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.retrofitsample.adapter.RatingListAdapter;
import com.retrofitsample.api.GetListAPI;
import com.retrofitsample.interfaces.ClickHandler;
import com.retrofitsample.model.ListApiResponse;
import com.retrofitsample.restServices.OnApiResponseListener;

import java.util.List;

/**
 * Created by User on 11-04-2018.
 */

public class RatingListActivity extends BaseActivity {
    LinearLayoutManager mLayoutManager;
    private RecyclerView mRecycler;
    List<ListApiResponse> mlist;
    private TextView txtNoItem;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratinglist);
        mRecycler = (RecyclerView)findViewById(R.id.mRecycler);
        txtNoItem = (TextView)findViewById(R.id.txtNoItem);
        mLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mLayoutManager);
        getList();
    }

    private void setAdapterRecyclerView(final List<ListApiResponse> mList) {
        RatingListAdapter RatingListAdapter = new RatingListAdapter(this, mList, new ClickHandler() {
            @Override
            public void listItemBtnClickListener(Object obj, int viewId) {
                int position= (int) obj;
                switch (viewId) {
                    case R.id.rLayout:
                        Intent i = new Intent(RatingListActivity.this,RatingDetailsActivity.class);
                        i.putExtra("idevaluare",mlist.get(position).getNetAutonumber());
                        startActivity(i);
                        break;
                }

            }
        });
        mRecycler.setAdapter(RatingListAdapter);

    }




    public void getList(){
        showLoading();
        new GetListAPI(new OnApiResponseListener() {
            @Override
            public <E> void onSuccess(E t) {
                hideLoading();
                mlist = (List<ListApiResponse>) t;
                if(mlist.size()>0)
                  setAdapterRecyclerView(mlist);
                else {
                    txtNoItem.setVisibility(View.VISIBLE);
                    mRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public <E> void onError(E t) {
               hideLoading();
                txtNoItem.setVisibility(View.VISIBLE);
                mRecycler.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
               hideLoading();
                txtNoItem.setVisibility(View.VISIBLE);
                mRecycler.setVisibility(View.GONE);
            }
        });
    }
}
