package com.retrofitsample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.retrofitsample.R;
import com.retrofitsample.interfaces.ClickHandler;
import com.retrofitsample.model.ListApiResponse;

import java.util.ArrayList;
import java.util.List;


public class RatingListAdapter extends RecyclerView.Adapter<RatingListAdapter.MyViewHolder> {

    private Context mContext;
    private ClickHandler _interface;
    private List<ListApiResponse> dataList=new ArrayList<>();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtClient;
        public TextView txtdatemontaj;
        public TextView txtLocatie;
        public RatingBar rbRate;
        public RelativeLayout rLayout;

        public MyViewHolder(View view) {
            super(view);
            txtClient=(TextView)view.findViewById(R.id.txtClient);
            txtdatemontaj=(TextView)view.findViewById(R.id.txtdatemontaj);
            txtLocatie=(TextView)view.findViewById(R.id.txtLocatie);
            rbRate=(RatingBar)view.findViewById(R.id.rbRate);
            rLayout=(RelativeLayout)view.findViewById(R.id.rLayout);
        }
    }


    public RatingListAdapter(Context mContext, List<ListApiResponse> projectListingData, ClickHandler clickHandler) {
        this.mContext=mContext;
        this.dataList=projectListingData;
        this._interface = clickHandler;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_raiting_listing, parent, false);




        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Float rate = Float.parseFloat(dataList.get(position).getRate());
        holder.rbRate.setRating(rate);
        holder.txtClient.setText(dataList.get(position).getClient());
        holder.txtLocatie.setText(dataList.get(position).getLocatie());
        holder.txtdatemontaj.setText(dataList.get(position).getDatamontaj());

        holder.rLayout.setTag(position);
        holder.rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object objects = v.getTag();
                _interface.listItemBtnClickListener(objects,v.getId());
            }
        });


    }





    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
