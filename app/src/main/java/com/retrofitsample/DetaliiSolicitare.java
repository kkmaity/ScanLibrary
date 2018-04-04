package com.retrofitsample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.retrofitsample.model.Items;
import com.retrofitsample.restServices.MInterface;


public class DetaliiSolicitare extends Activity {


    EditText inputCant;
    EditText inputData;
    EditText inputDataLivrat;
    EditText inputNote;
    EditText txtinputCategorie;
    EditText inputAlocat;
    EditText solicitantHidden;
    EditText idAlocatHidden;
    EditText idSolicitare;
    Button button;
    Button salveazamodificari;
    String produs;
    String idclient;
    String cant;
    String datasolicitat;
    String datalivrat;
    String note;
    String categorie;
    String idalocat;
    String agent;
    String iduser;
    String idprodus;
    String idsolicitant;
    String mesaj;

    final Context context = this;
    String adresaurl;
    public ArrayList<Items> orderList=new ArrayList<>();
    ArrayList<String>  mStringList= new ArrayList<String>();
    ArrayList<String>  mIDList= new ArrayList<String>();
    private AutoCompleteTextView actv;
    private ProgressDialog pr;
    private Map<String,String> productID;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaliisolicitare);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        iduser = mySharedPreferences.getString("edittext_preference", "");
        idSolicitare = (EditText) findViewById(R.id.idClient);
        inputCant = (EditText) findViewById(R.id.cant);
        inputData = (EditText) findViewById(R.id.data);
        inputDataLivrat = (EditText) findViewById(R.id.datalivrat);
        inputNote = (EditText) findViewById(R.id.inputExplicatii);
        solicitantHidden = (EditText) findViewById(R.id.idComandaHidden);
        txtinputCategorie = (EditText) findViewById(R.id.idsolicitant);
        inputAlocat = (EditText) findViewById(R.id.inputAlocat);
        idAlocatHidden = (EditText) findViewById(R.id.idAlocatHidden);
        button = (Button) findViewById(R.id.Map);
        salveazamodificari = (Button) findViewById(R.id.salveazamodificari);
        button.setVisibility(View.INVISIBLE);
        final Button rezolvat = (Button) findViewById(R.id.rezolvat);
        pr=new ProgressDialog(this);
        pr.setMessage("Loading...");
        Intent j = getIntent();
        final String id = j.getStringExtra("IdM");
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        getSortedList();
        preiadate("");

        txtinputCategorie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //     Intent i = new Intent(getApplicationContext(), ListaAgenti.class);
                //    i.putExtra("cod", "3");
                //    startActivityForResult(i, 3);

            }

        });
        inputAlocat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //      Intent i = new Intent(getApplicationContext(), ListaAgenti.class);
                //      i.putExtra("cod", "4");

                //      startActivityForResult(i, 4);

            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        salveazamodificari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        rezolvat.setOnClickListener(new View.OnClickListener() {
            //When you click the button, Alert dialog will be showed
            @Override
            public void onClick(View v) {


            }

        });
    }

    public void preiadate(final String adresaurl) {

        //   new HttpHandler() {
        //      @Override
        //       public HttpUriRequest getHttpRequestMethod() {
        //     }

        //       @Override
        //        public ArrayList<String> onResponse(String result) {

        //	mylist = new ArrayList<HashMap<String, String>>();

        //           try {
        //               JSONObject json = new JSONObject(result);
        //               JSONArray earthquakes = json.getJSONArray("earthquakes");

        //              for (int i = 0; i < earthquakes.length(); i++) {
        //                  JSONObject e = earthquakes.getJSONObject(i);
        //                  idclient = e.getString(("idsolicitare"));
        //                   idsolicitant = e.getString(("solicitant"));
        //                  idalocat = e.getString(("alocat"));
        //                  produs = e.getString(("produs"));
        //                  cant = e.getString(("cant"));
        //                  datasolicitat = e.getString(("data"));
        //                 datalivrat = e.getString(("datalivrat"));
        //                 note = e.getString(("note"));
        //                 agent = e.getString(("NumeAlocat"));
        //                  categorie = e.getString(("agent"));
        //                 idprodus = e.getString(("idprodus"));

        //             }

        //        } catch (JSONException e) {
        //            e.printStackTrace();
        //         }
        //         updateList();
        //          return null;
        //      }
        //   }.execute();
    }


    private void updateList() {

        actv.setText(produs);
        idSolicitare.setText(idclient);
        inputData.setText(datasolicitat);
        inputCant.setText(cant);
        inputDataLivrat.setText(datalivrat);
        inputNote.setText(note);
        txtinputCategorie.setText(categorie);
        // idCategorieHidden.setText(idcategorie);
        solicitantHidden.setText(idsolicitant);
        inputAlocat.setText(agent);
        idAlocatHidden.setText(idalocat);


    }


    public void preiadaterezolva(final String adresaurl) {

    }


    private void updateListRezolva() {
        Toast.makeText(DetaliiSolicitare.this, mesaj, Toast.LENGTH_SHORT).show();
    }


    public void preiadatealoca(final String adresaurl) {

    }


    private void updateListAloca() {

    }


    private void getSortedList() {
        pr.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.floridaconstruct.eu/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MInterface restInt=retrofit.create(MInterface.class);
        restInt.getItems().enqueue(new retrofit2.Callback<ArrayList<Items>>() {


            @Override
            public void onResponse(retrofit2.Call<ArrayList<Items>> call, retrofit2.Response<ArrayList<Items>> response) {
                pr.dismiss();
                if(response.isSuccessful()) {
                    orderList.clear();
                    mStringList.clear();
                    mIDList.clear();
                    orderList = response.body();
                    productID=new HashMap<>();
                    for (int i=0;i<orderList.size();i++){
                        if (orderList.get(i).getItem4()!=null) {
                            mStringList.add(orderList.get(i).getItem4());
                            mIDList.add(orderList.get(i).getItem3());
                            productID.put(orderList.get(i).getItem3(),orderList.get(i).getItem4());
                        }
                    }
                    getAutoComplete();


                }else {
                    int statusCode  = response.code();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<Items>> call, Throwable t) {
                pr.dismiss();
                Toast.makeText(DetaliiSolicitare.this,"No Internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAutoComplete() {
        Object[] mStringArray = mStringList.toArray();
        final String [] stockArr= mStringList.toArray(new String[mStringList.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.dropdown, stockArr);

        System.out.println(stockArr.toString());
        //Getting the instance of AutoCompleteTextView
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String selectedItem = actv.getText().toString();

                Object ff = getKeyFromValue(productID, selectedItem);
                String key= (String) ff;
                idprodus = key;
                Toast.makeText(DetaliiSolicitare.this,key,Toast.LENGTH_LONG).show();
            }
        });
    }
    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    private void chekMail(){
        // HERE YOU HAVE TO CReATE THE FUNCTION TO CHEK IF MAIL EXIST IN DATABASE

    }
}




