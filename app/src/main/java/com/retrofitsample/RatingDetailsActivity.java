package com.retrofitsample;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.retrofitsample.api.GetRatingDetailsAPI;
import com.retrofitsample.model.RatingDetails;
import com.retrofitsample.restServices.OnApiResponseListener;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;


public class RatingDetailsActivity extends BaseActivity {

    private Button cameraButton;
    private static final int REQUEST_CODE = 99;
    private String path;
    private Image image;
    private Uri imgURI;
    List<RatingDetails> mlist;
    public TextView txtClient;
    public TextView txtdatemontaj,sugestii,datasunat,numemontat,concluziimontator;
    public TextView txtLocatie,raspuns1,raspuns2,raspuns3,raspuns4,raspuns5,rezultat,observatii,numeseller,numemasurat;
    public RatingBar rbRate;
    private String[] permission = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA};
    private static final int CAMERA_PERMISSION=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_details);
        cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_CAMERA));
        txtClient=(TextView)findViewById(R.id.txtClient);
        txtdatemontaj=(TextView)findViewById(R.id.txtdatemontaj);
        txtLocatie=(TextView)findViewById(R.id.txtLocatie);
        raspuns1=(TextView)findViewById(R.id.raspuns1);
        raspuns2=(TextView)findViewById(R.id.raspuns2);
        raspuns3=(TextView)findViewById(R.id.raspuns3);
        raspuns4=(TextView)findViewById(R.id.raspuns4);
        raspuns5=(TextView)findViewById(R.id.raspuns5);
        sugestii=(TextView)findViewById(R.id.sugestii);
        observatii=(TextView)findViewById(R.id.observatii);
        datasunat=(TextView)findViewById(R.id.datasunat);
        numeseller=(TextView)findViewById(R.id.numeseller);
        numemasurat=(TextView)findViewById(R.id.numemasurat);
        numemontat=(TextView)findViewById(R.id.numemontat);
        rezultat=(TextView)findViewById(R.id.rezultat);
        concluziimontator=(TextView)findViewById(R.id.concluziimontator);
        rbRate=(RatingBar)findViewById(R.id.rbRate);

        Intent i = getIntent();
        if(i!=null){
            String NetAutonumber = i.getStringExtra("idevaluare");
            getList(NetAutonumber);
        }

    }

    public void getList(String NetAutonumber){
        showLoading();
        String url = "comenzi/test/evaluare.php?idevaluare="+NetAutonumber;
        new GetRatingDetailsAPI(url,new OnApiResponseListener() {
            @Override
            public <E> void onSuccess(E t) {
                hideLoading();
                mlist = (List<RatingDetails>) t;
                if(mlist.size()>0) {
                    rbRate.setRating(Float.parseFloat(mlist.get(0).getRate()));
                    txtClient.setText(mlist.get(0).getClient());
                    txtdatemontaj.setText(mlist.get(0).getDatamontaj());
                    txtLocatie.setText(mlist.get(0).getLocatie());
                    raspuns1.setText(mlist.get(0).getRaspuns1());
                    raspuns2.setText(mlist.get(0).getRaspuns2());
                    raspuns3.setText(mlist.get(0).getRaspuns3());
                    raspuns4.setText(mlist.get(0).getRaspuns4());
                    raspuns5.setText(mlist.get(0).getRaspuns5());
                    sugestii.setText(mlist.get(0).getSugestii());
                    observatii.setText(mlist.get(0).getObservatii());
                    datasunat.setText(mlist.get(0).getDatasunat());
                    numeseller.setText(mlist.get(0).getNumeseller());
                    numemasurat.setText(mlist.get(0).getNumemasurat());
                    numemontat.setText(mlist.get(0).getNumemontat());
                    rezultat.setText(mlist.get(0).getRezultat());
                    concluziimontator.setText(mlist.get(0).getConcluziimontator());
                }

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



    protected void startScan(int preference) {
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private class ScanButtonClickListener implements View.OnClickListener {

        private int preference;

        public ScanButtonClickListener(int preference) {
            this.preference = preference;
        }

        public ScanButtonClickListener() {
        }

        @Override
        public void onClick(View v) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                boolean hasPermission = (ContextCompat.checkSelfPermission(RatingDetailsActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermission) {
                    getPermission();
                }else{
                    startScan(preference);
                }
            }else{
                startScan(preference);
            }


        }
    }


    private void getPermission(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            boolean hasPermission = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                ActivityCompat.requestPermissions(this,permission, CAMERA_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this,"Please consider granting it this permission",Toast.LENGTH_LONG);
                    getPermission();
                }
            }
        }

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgURI=uri;
                new CreatingPdf().execute();
               // getContentResolver().delete(uri, null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class CreatingPdf extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/pdfFile";

            File folder = new File(path);
            if (!folder.exists()) {
                boolean success = folder.mkdir();
                if (!success) {
                    Toast.makeText(RatingDetailsActivity.this, "Error on creating application folder", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }

            path = path + System.currentTimeMillis() + ".pdf";
            Document document = new Document(PageSize.A4, 38, 38, 50, 38);
            Rectangle documentRect = document.getPageSize();
            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
                document.open();
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),imgURI);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 70, stream);
                image = Image.getInstance(getRealPathFromUri(RatingDetailsActivity.this,imgURI));
                if (bmp.getWidth() > documentRect.getWidth()
                        || bmp.getHeight() > documentRect.getHeight()) {
                    image.scaleAbsolute(documentRect.getWidth(), documentRect.getHeight());
                } else {
                    image.scaleAbsolute(bmp.getWidth(), bmp.getHeight());
                }
                image.setAbsolutePosition(
                        (documentRect.getWidth() - image.getScaledWidth()) / 2,
                        (documentRect.getHeight() - image.getScaledHeight()) / 2);
                image.setBorder(Image.BOX);
                image.setBorderWidth(15);
                document.add(image);
                document.newPage();
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            openFile(path);
        }
    }
    public void openFile(String name) {
        File file = new File(name);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), this.getString(R.string.pdf_type));
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, this.getString(R.string.open_file));
        try {
            this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, this.getString(R.string.toast_no_pdf_app), Toast.LENGTH_LONG).show();
        }
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
