package com.retrofitsample;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.retrofitsample.recycler.RatingGrupatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
public class ImgToPDFConverterActivity extends AppCompatActivity implements Imageutils.ImageAttachmentListener {
    Imageutils imageutils;
    private Bitmap bitmap;
    private String file_name;
    private Button takeImageBtn;
    private Button convertBtn;
    private Button editformBtn;
    private Button ratingBtn;
    private ImageView imageView;
    private RecyclerView listPDF;
    private String path;
    private Image image;
    private Uri imgURI;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_to_pdf);
        imageutils =new Imageutils(this);
        takeImageBtn=(Button)findViewById(R.id.takeImageBtn);
        convertBtn=(Button)findViewById(R.id.convertBtn);
        editformBtn=(Button)findViewById(R.id.editForm);
        ratingBtn=(Button)findViewById(R.id.ratingForm);
        imageView=(ImageView)findViewById(R.id.imageView);
        listPDF=(RecyclerView)findViewById(R.id.listPDF);
        takeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageutils.imagepicker(1);
            }
        });
        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap!=null)
                new CreatingPdf().execute();
            }
        });
        editformBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetaliiSolicitare.class);
                startActivity(intent);
            }
        });
        ratingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RatingGrupatActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        this.bitmap=file;
        this.file_name=filename;
        imageView.setImageBitmap(file);
        String path =  Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imageutils.createImage(file,filename,path,false);
       imgURI=uri;
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
                    Toast.makeText(ImgToPDFConverterActivity.this, "Error on creating application folder", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }

            path = path + "Kamal_990" + ".pdf";
            Document document = new Document(PageSize.A4, 38, 38, 50, 38);
            Rectangle documentRect = document.getPageSize();
            try {
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
                document.open();
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),imgURI);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 70, stream);
                    image = Image.getInstance(getRealPathFromUri(ImgToPDFConverterActivity.this,imgURI));
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

            document.close();
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
        target.setDataAndType(Uri.fromFile(file), ImgToPDFConverterActivity.this.getString(R.string.pdf_type));
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, ImgToPDFConverterActivity.this.getString(R.string.open_file));
        try {
            ImgToPDFConverterActivity.this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ImgToPDFConverterActivity.this, ImgToPDFConverterActivity.this.getString(R.string.toast_no_pdf_app), Toast.LENGTH_LONG).show();
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
