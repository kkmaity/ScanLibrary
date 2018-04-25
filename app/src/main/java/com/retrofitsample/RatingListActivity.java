package com.retrofitsample;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.retrofitsample.adapter.RatingListAdapter;
import com.retrofitsample.api.GetAgentListAPI;
import com.retrofitsample.api.GetListAPI;
import com.retrofitsample.api.GetRatingListFilterAPI;
import com.retrofitsample.interfaces.ClickHandler;
import com.retrofitsample.model.Agent;
import com.retrofitsample.model.ListApiResponse;
import com.retrofitsample.restServices.OnApiResponseListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 11-04-2018.
 */

public class RatingListActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    LinearLayoutManager mLayoutManager;
    private RecyclerView mRecycler;
    List<ListApiResponse> mlist;
    private TextView txtNoItem;
    public  int year,month,day;
    private int mYear, mMonth, mDay;
    private int nr;
    private static final int DATE_DIALOG_ID = 0;
    private Spinner sp_employee_1,sp_employee_2,sp_employee_3;
    private TextView txtEnd,txtStart,tv_reset,tv_filter;
    private String idseller,idmasurat,idmontat;
    DatePickerDialog datePickerDialog;

    List<Agent> mListAgent =  new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratinglist);
        mRecycler = (RecyclerView)findViewById(R.id.mRecycler);
        txtNoItem = (TextView)findViewById(R.id.txtNoItem);
        tv_filter = (TextView)findViewById(R.id.tv_filter);
        tv_reset = (TextView)findViewById(R.id.tv_reset);
        mLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mLayoutManager);

        getList();

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, this,  mYear, mMonth, mDay);

        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAgentList();
            }
        });

        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 getList();
            }
        });
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


    public void getFilteredList(){
        showLoading();
        new GetRatingListFilterAPI(getParams(),new OnApiResponseListener() {
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

    private HashMap<String,String> getParams() {
        HashMap<String,String> map = new HashMap<>();
        map.put("start",txtStart.getText().toString());
        map.put("end",txtEnd.getText().toString());
        map.put("idseller",idseller);
        map.put("idmasurat",idmasurat);
        map.put("idmontat",idmontat);
        return map;
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

    public void getAgentList(){
        showLoading();
        new GetAgentListAPI(new OnApiResponseListener() {
            @Override
            public <E> void onSuccess(E t) {
                hideLoading();
                mListAgent = (List<Agent>) t;
                if(mListAgent.size()>0)
                    showDialog();

            }

            @Override
            public <E> void onError(E t) {
                hideLoading();
            }

            @Override
            public void onError() {
                hideLoading();
            }
        });
    }

    private void showDialog(){
        final Dialog dialog=new Dialog(this,android.R.style.Theme_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_filter);
        txtEnd = (TextView)dialog.findViewById(R.id.tv_enddate);
        txtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nr=1;
                datePickerDialog.show();
            }
        });
        txtStart = (TextView)dialog.findViewById(R.id.tv_startdate);
        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nr=0;
                datePickerDialog.show();
            }
        });
        sp_employee_1 = (Spinner)dialog.findViewById(R.id.sp_employee_1);
        sp_employee_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idseller=mListAgent.get(i).getItem1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_employee_2 = (Spinner)dialog.findViewById(R.id.sp_employee_2);
        sp_employee_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idmasurat=mListAgent.get(i).getItem1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_employee_3 = (Spinner)dialog.findViewById(R.id.sp_employee_3);
        sp_employee_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idmontat=mListAgent.get(i).getItem1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btSubmit = (Button)dialog.findViewById(R.id.btSubmit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFilteredList();
                dialog.dismiss();
            }
        });
        ArrayAdapter<Agent> empAdapter_1 = new ArrayAdapter<Agent>(this, android.R.layout.simple_spinner_item, mListAgent);
        empAdapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_employee_1.setAdapter(empAdapter_1);

        ArrayAdapter<Agent> empAdapter_2 = new ArrayAdapter<Agent>(this, android.R.layout.simple_spinner_item, mListAgent);
        empAdapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_employee_2.setAdapter(empAdapter_2);

        ArrayAdapter<Agent> empAdapter_3 = new ArrayAdapter<Agent>(this, android.R.layout.simple_spinner_item, mListAgent);
        empAdapter_3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_employee_3.setAdapter(empAdapter_3);
        dialog.show();
    }






    @Override
    public void onDateSet(DatePicker view, int yearSelected, int monthOfYear, int dayOfMonth) {
        year = yearSelected;
        month = monthOfYear + 1;
        day = dayOfMonth;
        if(nr==1){txtEnd.setText(year + "-" + month + "-" + day);}
        else{txtStart.setText(year + "-" + month + "-" + day);}
    }
}
