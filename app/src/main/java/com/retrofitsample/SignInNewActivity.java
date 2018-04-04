package com.retrofitsample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.retrofitsample.model.GetUserMain;
import com.retrofitsample.model.UserData;
import com.retrofitsample.restServices.RestService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kamal on 03/07/2018.
 */

public class SignInNewActivity extends BaseActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 111;
    private String vtext;
    private String vlocatie;
    private String key;
    private FrameLayout googleLoginFrame;
    private TextView btn_sign_in;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_new_activity);

        googleLoginFrame=(FrameLayout)findViewById(R.id.googleLoginFrame);
        googleLoginFrame.setOnClickListener(this);
        final SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        vtext = mySharedPreferences.getString("edittext_preference", "");
        vlocatie = mySharedPreferences.getString("idlocatie", "");
        key= mySharedPreferences.getString("token", "");
        initGoogleSignIn();

    }


    private void getUserDetails(final String email, final String profFoto, final String name, final String id) {
        String url="comenzi/test/drepturiuseri.php?mail="+email;
        Call call= RestService.getInstance().restInterface.getUserData(url);
       call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                GetUserMain main= (GetUserMain) response.body();
                if (main!=null){
                    if ( main.getEarthquakes()!=null) {
                        List<UserData> data = main.getEarthquakes();
                        if (data != null && data.size() > 0) {
                            preference.setEmail(data.get(0).getMail());
                            updateList(data.get(0).getIdEmployee(),data.get(0).getMail());
                            startActivity(new Intent(SignInNewActivity.this,MainActivity.class));
                            finish();
                        }
                    }else {
                        doRegistration(email,profFoto,name,id);
                    }
                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });





    }

    private void doRegistration(final String email, String profFoto, String name, final String id) {

        String url = "http://www.floridaconstruct.eu/comenzi/test/registercutoken.php?Token=" + key + "&idmember=" + id + "&poza=" + profFoto + "&mail=" + email;
        Call call= RestService.getInstance().restInterface.getRegister(url);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                response.body();

               preference.setEmail(email);
                updateList(id,email);
                String res=response.body().toString();
                startActivity(new Intent(SignInNewActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call call, Throwable t) {


            }
        });


    }

    private void initGoogleSignIn() {
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.googleLoginFrame:
                signIn();
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                String id= account.getId();
                String fullName= account.getDisplayName();
                Uri imgUrl = account.getPhotoUrl();
                String email=account.getEmail();
                //commonAPI.showToastMsg("Thanks..."+fullName);
                if (imgUrl!=null&&id!=null&&fullName!=null)
                    getUserDetails(email,imgUrl.toString(),fullName,id);


            } else {
                showToastMsg("Something went wrong...");
                // Google Sign In failed, update UI appropriately
            }
        }
    }

   /* private void doSocialLogin(String id, String fullName, String imgUrl, String email) {
        getUserDetails(email);
        //showToastMsg(email);
    }*/

    private void showToastMsg(String s) {
        Toast.makeText(SignInNewActivity.this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void updateList(String id,String email) {
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mySharedPreferences.edit().putString("edittext_preference", id.toString()).commit();
        mySharedPreferences.edit().putString("idmember",id.toString()).commit();
      //  mySharedPreferences.edit().putString("montator",montator.toString()).commit();
       // mySharedPreferences.edit().putString("idlocatie",loc.toString()).commit();
        mySharedPreferences.edit().putString("mail",email.toString()).commit();
        //Toast.makeText(SignInActivity.this, "montator " +montator , Toast.LENGTH_SHORT).show();
       // Toast.makeText(SignInActivity.this, "idlocatie " +loc , Toast.LENGTH_SHORT).show();
    };
}
