package com.retrofitsample.recycler;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.example.florida.R;
import com.retrofitsample.R;
import com.retrofitsample.model.CustomObject;

import java.util.ArrayList;

public class ListAdapterGrupat extends RecyclerView.Adapter<ListAdapterGrupat.ViewHolder> {

    private ArrayList<CustomObject> arrayList;
    private Context context;
    private Class<?> cls;

    private int colorGreen, colorRed;
    private Drawable bgRed, bgGreen;

    public ListAdapterGrupat(Context context, ArrayList<CustomObject> arrayList, Class<?> cls) {
        this.arrayList = arrayList;
        this.context = context;
        this.cls = cls;

        colorGreen = context.getResources().getColor(R.color.green);
        colorRed = context.getResources().getColor(R.color.red);
        bgRed = context.getResources().getDrawable(R.drawable.label_new);
        bgGreen = context.getResources().getDrawable(R.drawable.label_old);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_grupat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CustomObject obj = arrayList.get(position);
     //   campul grup

        if (obj.getNetGrup().equals("0")) {
            holder.tvGrup.setVisibility(View.GONE);
            holder.tvGrup.setText(obj.getItem2());
        } else {
            holder.tvGrup.setVisibility(View.VISIBLE);
            holder.tvGrup.setText(obj.getItem2());

        }

     //   campul dreapta sus
        holder.item2.setText(obj.getItem2());
        //   campul stanga jos
        holder.item3.setText(obj.getItem3());
     //   campul stanga sus
        holder.item1.setText(obj.getItem1());
           //campul dreapta jos
        if (obj.getPrenume().equals("0.00")) {
            holder.item4.setText("Nu Raspunde");
            holder.item4.setTextColor(colorRed);
            holder.item4.setBackgroundDrawable(bgRed);
        } else {
            holder.item4.setText(obj.getNume() + " " + obj.getPrenume());
            holder.item4.setTextColor(colorGreen);
            holder.item4.setBackgroundDrawable(bgGreen);

           }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvGrup, item2, item3, item1, item4;

        ViewHolder(View v) {
            super(v);
            tvGrup = (TextView) v.findViewById(R.id.tvGrup);
            item2 = (TextView) v.findViewById(R.id.tvNumber);
            item3 = (TextView) v.findViewById(R.id.tvDate);
            item1 = (TextView) v.findViewById(R.id.tvName);
            item4 = (TextView) v.findViewById(R.id.tvState);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, cls);
                  //  intent.putExtra("number", arrayList.get(getAdapterPosition()).getNetAutonumber());
                    intent.putExtra("IdM", arrayList.get(getAdapterPosition()).getComandaid());
                    context.startActivity(intent);
                }
            });
        }
    }
}
