package com.retrofitsample.recycler;

/**
 * Created by mircea on 29.04.2017.
 */


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.retrofitsample.FiltruActivity;
import com.retrofitsample.MainActivity;
import com.retrofitsample.R;
import com.retrofitsample.model.CustomObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class RatingGrupatActivity extends AppCompatActivity {

    private RecyclerView list;
    private ListAdapterGrupat adapter;
    private ArrayList<CustomObject> arrayList;
    private ProgressBar bar;
    private API service;
    String start;
    String end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        this.setTitle("Rating");
        bar = (ProgressBar) findViewById(R.id.progressBar2);
        bar.setVisibility(GONE);
        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String ora = sdf.format(new Date());
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user = mySharedPreferences.getString("edittext_preference", "");

        arrayList = new ArrayList<>();
        // aici trec ce activity vreau sa deschid la tap pe articol. Trebuie inlocuit ResultActivity cu numele dorit
        adapter = new ListAdapterGrupat(this, arrayList, MainActivity.class);
        list.setAdapter(adapter);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://floridaconstruct.eu/comenzi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(API.class);
       // bar.setVisibility(VISIBLE);
    }

    Callback<List<CustomObject>> listener = new Callback<List<CustomObject>>() {
        @Override
        public void onResponse(Call<List<CustomObject>> call, Response<List<CustomObject>> response) {
            bar.setVisibility(GONE);
            arrayList.clear();
            arrayList.addAll(response.body());
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<List<CustomObject>> call, Throwable t) {
            bar.setVisibility(GONE);
            Toast.makeText(RatingGrupatActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            Log.d("MyLogs", "" + t.getStackTrace());
            Log.d("MyLogs", "" + t.getLocalizedMessage());
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniuratinggrupat, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 3) {
                    String text = newText;
                    service.search(text).enqueue(listener);
                  return true;
                } else
                    return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addclient:
                this.setTitle(" Sugestii Clienti");
                Intent j = new Intent(getApplicationContext(), FiltruActivity.class);
                startActivityForResult(j, 2);

                return true;
            case R.id.filtru:
                Intent i = new Intent(getApplicationContext(), FiltruActivity.class);
                startActivityForResult(i, 1);
                 return true;
            case R.id.cauta:
              this.setTitle(" Observatii Clienti");
                Intent k = new Intent(getApplicationContext(), FiltruActivity.class);
                startActivityForResult(k, 3);
                return true;

//            case android.R.id.home:
//                this.finish();
//                overridePendingTransition(0, R.anim.right_out);
//                return true;

            default:
                return super.onOptionsItemSelected(item);


        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_CANCELED){
            // check if the request code is same as what is passed  here it is 2
            if(requestCode==1)
            {
                bar.setVisibility(VISIBLE);
                start=data.getStringExtra("start");
                end=data.getStringExtra("end");
                Toast.makeText(RatingGrupatActivity.this, "Evaluare in perioada: "+start+"--"+end, Toast.LENGTH_SHORT).show();;
                service.getRatingGrupat(start,end).enqueue(listener);
            }

            if(requestCode==2)
            {
                bar.setVisibility(VISIBLE);
                start=data.getStringExtra("start");
                end=data.getStringExtra("end");
                Toast.makeText(RatingGrupatActivity.this, "Sugestii clienti in perioada: "+start+"--"+end, Toast.LENGTH_SHORT).show();;
                service.getSugestii(start,end).enqueue(listener);

            }

            if(requestCode==3)
            {
                bar.setVisibility(VISIBLE);
                start=data.getStringExtra("start");
                end=data.getStringExtra("end");
                Toast.makeText(RatingGrupatActivity.this, "Observatii clienti in perioada: "+start+"--"+end, Toast.LENGTH_SHORT).show();;
                service.getObservatiiClienti(start,end).enqueue(listener);
            }

        }

    }
    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            java.lang.reflect.Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}