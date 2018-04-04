package com.retrofitsample;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.READ_PHONE_STATE;

public class RegisteredEmailAccounts extends Activity {


		public static final int RequestPermissionCode = 1;
		ListView listView ;
		Button button;
		ArrayList<String> SampleArrayList ;
		ArrayAdapter<String> arrayAdapter;
		Pattern pattern;
		Account[] account ;
		String[] StringArray;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_conturi_mail);

            EnableRuntimePermission();

            listView = (ListView) findViewById(R.id.listview1);
            button = (Button) findViewById(R.id.button);
            SampleArrayList = new ArrayList<String>();
            pattern = Patterns.EMAIL_ADDRESS;
            String email = null;

            Pattern gmailPattern = Patterns.EMAIL_ADDRESS;
            Account[] accounts = AccountManager.get(this).getAccounts();
            for (Account account : accounts) {
                if (gmailPattern.matcher(account.name).matches()) {
                    email = account.name;
                }
            }
            Toast.makeText(this, "Email Address: " + email, Toast.LENGTH_LONG).show();

        }

		public void EnableRuntimePermission() {

			ActivityCompat.requestPermissions(RegisteredEmailAccounts.this, new String[]
					{
							GET_ACCOUNTS,
							READ_PHONE_STATE
					}, RequestPermissionCode);

		}

		@Override
		public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
			switch (requestCode) {

				case RequestPermissionCode:

					if (grantResults.length > 0) {

						boolean GetAccountPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
						boolean ReadPhoneStatePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

						if (GetAccountPermission && ReadPhoneStatePermission) {

							Toast.makeText(RegisteredEmailAccounts.this, "Permission Granted", Toast.LENGTH_LONG).show();
						}
						else {
							Toast.makeText(RegisteredEmailAccounts.this,"Permission Denied",Toast.LENGTH_LONG).show();

						}
					}

					break;
			}
		}

	}
