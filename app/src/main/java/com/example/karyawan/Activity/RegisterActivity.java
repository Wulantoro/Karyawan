package com.example.karyawan.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.anggastudio.spinnerpickerdialog.SpinnerPickerDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.karyawan.ImagePicker.RxImageConverter;
import com.example.karyawan.ImagePicker.RxImagePicker;
import com.example.karyawan.ImagePicker.Sources;
import com.example.karyawan.R;
import com.example.karyawan.Model.Divisi;
import com.example.karyawan.Utils.GlobalVars;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import id.zelory.compressor.Compressor;
import io.reactivex.Observable;

import static com.example.karyawan.Utils.GlobalHelper.convertFileToContentUri;
import static com.example.karyawan.Utils.GlobalHelper.deleteRecursive;
import static com.example.karyawan.Utils.GlobalHelper.encodeFileBase64;
import static com.example.karyawan.Utils.GlobalHelper.getMimeTypeFromUri;
import static com.example.karyawan.Utils.GlobalHelper.getPath;
import static com.example.karyawan.Utils.GlobalVars.BASE_DIR;
import static com.example.karyawan.Utils.GlobalVars.EXTERNAL_DIR_FILES;
import static com.example.karyawan.Utils.GlobalVars.IMAGES_DIR;
import static java.lang.String.valueOf;

public class RegisterActivity extends AppCompatActivity {

    private ImageView imgtgl, imgUser;
    private TextView ettgl;
    private Spinner spdivisi;
    private Button btnsimpan;
    private EditText etnama, etusername, ettelp;
    private EditText etalamat, etpassword;

    private static final int PICK_IMAGE_FILE = 1;
    private ImageView imgAdd, ivCamera;
    private Uri photoUri;
    private RadioGroup converterRadioGroup;

    //Photo
    private String id_krw;
    private File tempFile= null;
    private File compressedImage = null;
    private Uri finalPhotoUri = null;
    private String photoExt = "";
    private String encodePhoto = "";
    private Bitmap theBitmap = null;

    private Gson gson;

    private static String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        gson = new Gson();
        imgtgl = findViewById(R.id.imgtgl);
        ettgl = findViewById(R.id.ettgl);
        spdivisi = findViewById(R.id.spdivisi);
        imgAdd = findViewById(R.id.imgAdd);
        ivCamera = findViewById(R.id.ivCamera);
        imgUser = findViewById(R.id.imgUser);
        btnsimpan = findViewById(R.id.btnsimpan);
        etnama = findViewById(R.id.etnama);
        etusername = findViewById(R.id.etusername);
        ettelp = findViewById(R.id.ettelp);
        etalamat = findViewById(R.id.etalamat);
        etpassword = findViewById(R.id.etpassword);

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanProfil();
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromSource(Sources.CAMERA);
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });

        //open camera
        converterRadioGroup = findViewById(R.id.radio_group);
        converterRadioGroup.check(R.id.radio_file);
        if (RxImagePicker.with(RegisterActivity.this).getActiveSubscription() != null) {
            RxImagePicker.with(RegisterActivity.this).getActiveSubscription().subscribe(this::onImagePicked);
        }
        id_krw = valueOf(System.currentTimeMillis());

        //Function Tanggal
        imgtgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateSpinner();
            }
        });

        //get Divisi
        loadDiv();
    }

    private void pickImageFromSource(Sources source) {
        RxImagePicker.with(RegisterActivity.this).requestImage(source)
                .flatMap(uri -> {
                    switch (converterRadioGroup.getCheckedRadioButtonId()) {
                        case R.id.radio_file:
                            return RxImageConverter.uriToFile(RegisterActivity.this, uri, createTempFile());
                        case R.id.radio_bitmap:
                            return RxImageConverter.uriToBitmap(RegisterActivity.this, uri);
                        default:
                            return Observable.just(uri);
                    }
                })
                .subscribe(this::onImagePicked, throwable -> Toast.makeText(RegisterActivity.this, String.format("Error: %s", throwable), Toast.LENGTH_LONG).show());
    }

    private void onImagePicked(Object result) {
        if (result instanceof Bitmap) {

        }else {
            photoUri = Uri.parse(valueOf(result));
            tempFile = new File(valueOf(photoUri));
            compressedImage = compressFoto(RegisterActivity.this, tempFile);

            try {
                finalPhotoUri = convertFileToContentUri(RegisterActivity.this, compressedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        photoExt = "." + getMimeTypeFromUri(RegisterActivity.this, finalPhotoUri);
        encodePhoto = encodeFileBase64(getPath(RegisterActivity.this, finalPhotoUri));

        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }

                try {
                    theBitmap = Glide.
                            with(RegisterActivity.this).
                            asBitmap().
                            load(getPath(RegisterActivity.this, finalPhotoUri))
                            .apply(RequestOptions.circleCropTransform()).
                                    into(100, 100).get();

                    } catch (final ExecutionException e) {
                    Log.e("TAG","ExecutionException " + e.getMessage());
                } catch (final InterruptedException e) {
                    Log.e("TAG","InterruptedException " + e.getMessage());
                }
                return null;
            }

            @SuppressLint("WrongThread")
            @Override
            protected void onPostExecute(Void dummy) {
                if (null != theBitmap) {
                    // The full bitmap should be available here
                    //ivAvatar.setImageBitmap(theBitmap);

                    File mypath=new File(IMAGES_DIR,id_krw+".jpeg");

                    ContextWrapper cw = new ContextWrapper(RegisterActivity.this);
                    // path to /data/data/yourapp/app_data/imageDir
                    // Create imageDir
                    //File mypath=new File(fotoPath,userId+".jpeg");

                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(mypath);
                        // Use the compress method on the BitMap object to write image to the OutputStream
                        theBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    Glide.with(getApplicationContext())
                            .load(mypath)
                            .apply(RequestOptions.circleCropTransform())
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.user)
                            .into(imgUser);

                    Log.d("TAG", "Image loaded");
                };
            }
        }.execute();

        deleteRecursive(new File(valueOf(finalPhotoUri)));
        deleteRecursive(createTempFile());
        deleteRecursive(tempFile);

    }


    private File createTempFile() {
        return new File(BASE_DIR + EXTERNAL_DIR_FILES, id_krw + ".jpeg");
    }

    public static File compressFoto(Context context, File actualImage) {
        final String path = IMAGES_DIR;

        File compressedImage = new Compressor.Builder(context)
                .setMaxWidth(1280)
                .setMaxHeight(1024)
                .setQuality(85)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(path)
                .build()
                .compressToFile(actualImage);

        deleteRecursive(actualImage);

        return compressedImage;
    }

    private void openFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photoUri = data.getData();
            Glide.with(this)
                    .load(photoUri)
                    .skipMemoryCache(true)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgUser);
        }
    }



    private void loadDiv() {

        Log.e(TAG, "ip >> " + GlobalVars.BASE_IP + "divisi");

        AndroidNetworking.get(GlobalVars.BASE_IP + "divisi")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Divisi> result = new ArrayList<>();

                        if (result != null)
                            result.clear();

                        try {
                            Log.e(TAG, "divisi = " + response.toString(1));

                            String message = response.getString("message");

                            if (message.equals("Divisi were found")) {
                                String records = response.getString("data");

                                JSONArray dataArr = new JSONArray(records);

                                if (dataArr.length() > 0) {
                                    for (int i = 0; i < dataArr.length(); i++) {
                                        Divisi divisi = gson.fromJson(dataArr.getJSONObject(i).toString(), Divisi.class);
                                        result.add(divisi);
                                        Log.e(TAG, "dividsi >> " +divisi.getNmDivisi());
                                    }
                                    setDivisi(result);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONException "+e, Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "ANError "+ anError, Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private String div;
    private void setDivisi(final List<Divisi> divisiList) {
        ArrayAdapter<Divisi> voteTypeAdapter = new ArrayAdapter<Divisi>(getApplicationContext(), R.layout.divisi_spinner, divisiList) {

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position,convertView, parent);
                textView.setText(divisiList.get(position).getNmDivisi());

                return textView;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setText(divisiList.get(position).getNmDivisi());

                return textView;
            }
        };

        spdivisi.setAdapter(voteTypeAdapter);
        spdivisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                div = divisiList.get(i).getNmDivisi().toString();
                Log.e(TAG, "div = " + divisiList.get(i).getNmDivisi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void DateSpinner() {
        final SpinnerPickerDialog spinnerPickerDialog = new SpinnerPickerDialog();
        spinnerPickerDialog.setContext(this);
        spinnerPickerDialog.setAllColor(ContextCompat.getColor(this, R.color.greenMeetAp));
        spinnerPickerDialog.setmTextColor(Color.BLACK);
        spinnerPickerDialog.setArrowButton(true);
        spinnerPickerDialog.setOnDialogListener(new SpinnerPickerDialog.OnDialogListener() {
            @Override
            public void onSetDate(int month, int day, int year) {

                DateFormat simpleDate;
                Date date1;

                date1 = Calendar.getInstance().getTime();
                simpleDate = new SimpleDateFormat("ddMMyyyy");

                month = month + 1;
//                String date = day + "-" + month + "-" + year;
                String date = year + "-" + month + "-" + day;
                DateFormat dp_medium = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
                String dp_medium_uk_strg = dp_medium.format(Calendar.getInstance().getTime());
                ettgl.setText(date);
//                ettgl.setText((CharSequence) simpleDate);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onDismiss() {

            }
        });

        spinnerPickerDialog.show(this.getSupportFragmentManager(), "");
    }

    public void simpanProfil() {
        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray newArr = new JSONArray();
            jsonObject.put("nama_krw", etnama.getText().toString());
            jsonObject.put("username_krw", etusername.getText().toString());
            jsonObject.put("password", etpassword.getText().toString());
            jsonObject.put("divisi", spdivisi.getItemAtPosition(spdivisi.getSelectedItemPosition()).toString());
            jsonObject.put("telp_krw", ettelp.getText().toString());
            jsonObject.put("alamat_krw", etalamat.getText().toString());
            jsonObject.put("gender_krw", "LAKI - LAKI");
            jsonObject.put("tgllahir_krw", ettgl.getText().toString());
            jsonObject.put("image_name", id_krw+photoExt);
            jsonObject.put("image_file", encodePhoto);

            newArr.put(jsonObject);

            Log.e(TAG, "coba input = " + newArr.toString(1));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(GlobalVars.BASE_IP + "karyawan")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONExceptions" + e, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Gagal menambah data", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
