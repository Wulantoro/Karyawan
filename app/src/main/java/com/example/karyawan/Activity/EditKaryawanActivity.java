package com.example.karyawan.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RadioButton;
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
import com.example.karyawan.Adapter.SpinnerAdapter;
import com.example.karyawan.ImagePicker.RxImageConverter;
import com.example.karyawan.ImagePicker.RxImagePicker;
import com.example.karyawan.ImagePicker.Sources;
import com.example.karyawan.Model.Divisi;
import com.example.karyawan.Model.Karyawan;
import com.example.karyawan.R;
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
import static com.example.karyawan.Utils.GlobalVars.BASE_IP;
import static com.example.karyawan.Utils.GlobalVars.EXTERNAL_DIR_FILES;
import static com.example.karyawan.Utils.GlobalVars.IMAGES_DIR;

public class EditKaryawanActivity extends AppCompatActivity {

    private ImageView ivCamera;
    private ImageView ivGalery;
    private ImageView imgtgl;

    //open camera
    private RadioGroup converterRadioGroup;
    private Uri photoUri = null;
    private Uri finalPhotoUri = null;
    private File compressedImage = null;
    private File tempFile= null;
    private String photoExt = "";
    private String encodePhoto = "";
    private Bitmap theBitmap = null;

    private ImageView imgUser;
    private EditText etnama;
    private EditText etusername;
    private EditText etalamat, etpassword;
    private EditText  ettelp;
    private EditText ettgl;
    private RadioGroup rggender;
    private RadioButton rbgender;
    private Button btnsimpan;
    private Spinner spdivisi;
    SpinnerAdapter spinAdapter;

    private Gson gson;

    public SharedPreferences pref, prf;

    String id_krw;
    String id_krw2;
    private String tempId = "";
    private String namadiv = "";
    private String nmDiv;
    private static final int PICK_IMAGE_FILE = 1;

    private static final String TAG = EditKaryawanActivity.class.getName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_karyawan);

        gson = new Gson();
        imgUser = findViewById(R.id.imgUser);
        etnama = findViewById(R.id.etnama);
        etusername = findViewById(R.id.etusername);
        ettelp = findViewById(R.id.ettelp);
        etalamat = findViewById(R.id.etalamat);
        etpassword = findViewById(R.id.etpassword);
        rggender =  findViewById(R.id.rggender);
        ettgl = findViewById(R.id.ettgl);
        btnsimpan = findViewById(R.id.btnsimpan);
        ivCamera = findViewById(R.id.ivCamera);
        ivGalery = findViewById(R.id.ivGalery);
        spdivisi = findViewById(R.id.spdivisi);
        imgtgl = findViewById(R.id.imgtgl);

        pref = getSharedPreferences("Id_krw", MODE_PRIVATE);
        id_krw = pref.getString("id_krw", null);
        Log.e(TAG, "id karyawan = " + id_krw);


        converterRadioGroup = findViewById(R.id.radio_group);
        converterRadioGroup.check(R.id.radio_file);

        if (RxImagePicker.with(EditKaryawanActivity.this).getActiveSubscription() != null) {
            RxImagePicker.with(EditKaryawanActivity.this).getActiveSubscription().subscribe(this::onImagePicked);
        }

        id_krw2 = String.valueOf(System.currentTimeMillis());
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromSource(Sources.CAMERA);
            }
        });

        ivGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromSource(Sources.GALLERY);
            }
        });

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfil();
            }
        });

        //Function Tanggal
        imgtgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateSpinner();
            }
        });

        //get Divisi
        loadDiv();
//        getDivisi();

        loadProfil();


    }

    private void pickImageFromSource(Sources source) {

        RxImagePicker.with(EditKaryawanActivity.this).requestImage(source)
                .flatMap(uri -> {
                    switch (converterRadioGroup.getCheckedRadioButtonId()) {
                        case R.id.radio_file:
                            return RxImageConverter.uriToFile(EditKaryawanActivity.this, uri, createTempFile());
                        case R.id.radio_bitmap:
                            return RxImageConverter.uriToBitmap(EditKaryawanActivity.this, uri);
                        default:
                            return Observable.just(uri);
                    }
                })
                .subscribe(this::onImagePicked, throwable -> Toast.makeText(EditKaryawanActivity.this, String.format("Error: %s", throwable), Toast.LENGTH_LONG).show());
    }

    private void onImagePicked(Object result) {
        if (result instanceof Bitmap) {
            //ivImage.setImageBitmap((Bitmap) result);
        }else{
            photoUri = Uri.parse(String.valueOf(result));

            tempFile = new File(String.valueOf(photoUri));

            compressedImage = compressFoto(EditKaryawanActivity.this, tempFile);


            try {
                finalPhotoUri = convertFileToContentUri(EditKaryawanActivity.this, compressedImage);

            } catch (Exception e) {
                e.printStackTrace();
            }

            photoExt = "."+getMimeTypeFromUri(EditKaryawanActivity.this, finalPhotoUri);
            encodePhoto = encodeFileBase64(getPath(EditKaryawanActivity.this, finalPhotoUri));

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }

                    try {
                        theBitmap = Glide.
                                with(EditKaryawanActivity.this).
                                asBitmap().
                                load(getPath(EditKaryawanActivity.this, finalPhotoUri)).
                                into(100,100).
                                get();
                    } catch (final ExecutionException e) {
                        Log.e("TAG","ExecutionException " + e.getMessage());
                    } catch (final InterruptedException e) {
                        Log.e("TAG","InterruptedException " + e.getMessage());
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(Void dummy) {
                    if (null != theBitmap) {
                        // The full bitmap should be available here
                        //ivAvatar.setImageBitmap(theBitmap);

                        File mypath=new File(IMAGES_DIR,id_krw2+".jpeg");

                        ContextWrapper cw = new ContextWrapper(EditKaryawanActivity.this);
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
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .placeholder(R.drawable.user)
                                .into(imgUser);

                        Log.d("TAG", "Image loaded");
                    };
                }
            }.execute();

            deleteRecursive(new File(String.valueOf(finalPhotoUri)));
            deleteRecursive(createTempFile());
            deleteRecursive(tempFile);

        }
    }

    private File createTempFile() {
        return new File(BASE_DIR + EXTERNAL_DIR_FILES, id_krw2 + ".jpeg");
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


    private void getDivisi(){

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle(("Silahkan tunggu.."));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        JSONObject jsonObject = new JSONObject();
        AndroidNetworking.get(BASE_IP+"divisi")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            String message = response.getString("message");
                            String records = response.getString("records");

                            if (message.equals("success")){
                                JSONArray arr = new JSONArray(records);

                                List<Divisi> list = new ArrayList<>();

                                Divisi divisi = new Divisi();
                                divisi.setIdDivisi("0");
                                divisi.setNmDivisi("Divisi");
                                list.add(divisi);

                                for (int i=0;i<arr.length();i++){
                                    Divisi resp = gson.fromJson(arr.getJSONObject(i).toString(), Divisi.class);
                                    list.add(resp);
                                }

                                spinAdapter = new SpinnerAdapter(EditKaryawanActivity.this, R.layout.divisi_spinner, list);
                                spdivisi.setAdapter(spinAdapter);

                                if (namadiv!=null && !divisi.equals("")){
                                    for (int i=0;i<list.size();i++){
                                        if (list.get(i).getNmDivisi().equals(namadiv))
                                            spdivisi.setSelection(i);
                                    }
                                }

                                spdivisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        //Toast.makeText(getApplicationContext(), list.get(i).getCategory() + " " + list.get(i).getId(), Toast.LENGTH_SHORT).show();
                                        nmDiv = list.get(i).getNmDivisi();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
                            }

                            progress.dismiss();
                        } catch (JSONException e) {
                            progress.dismiss();
                            Toast.makeText(getApplicationContext(),String.valueOf(e), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(),String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadDiv() {

        Log.e(TAG, "ip >> " + BASE_IP + "divisi");

        AndroidNetworking.get(BASE_IP + "divisi")
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

    private void loadProfil() {
        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id_krw", id_krw);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.get(BASE_IP + "karyawan?id_krw=" + id_krw)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Log.e(TAG, "response = " + response.toString(1));

                            String message = response.getString("message");

                            if (message.equals("Karyawan Ditemukan")) {
                                String records = response.getString("data");
                                JSONObject jsonObject = new JSONObject(records);
                                etnama.setText(jsonObject.getString("nama_krw"));
                                etusername.setText(jsonObject.getString("username_krw"));
//                                jsonObject.put("divisi", spdivisi.getItemAtPosition(spdivisi.getSelectedItemPosition()).toString());
//                                rbgender.setText(jsonObject.getString("gender_krw"));
                                ettgl.setText(jsonObject.getString("tgllahir_krw"));
                                ettelp.setText(jsonObject.getString("telp_krw"));
                                etalamat.setText(jsonObject.getString("alamat_krw"));
                                etpassword.setText(jsonObject.getString("password"));

                                Glide.with(getApplicationContext())
                                        .load(jsonObject.getString("image_file"))
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .centerCrop()
                                        .dontAnimate()
                                        .into(imgUser);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void updateProfil() {

        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray newArr = new JSONArray();
            jsonObject.put("id_krw", id_krw);
            jsonObject.put("nama_krw", etnama.getText().toString());
            jsonObject.put("username_krw", etusername.getText().toString());
            jsonObject.put("password", etpassword.getText().toString());
            jsonObject.put("divisi", spdivisi.getItemAtPosition(spdivisi.getSelectedItemPosition()).toString());
            jsonObject.put("telp_krw", ettelp.getText().toString());
            jsonObject.put("alamat_krw", etalamat.getText().toString());
            jsonObject.put("gender_krw", rbgender.getText().toString());
            jsonObject.put("tgllahir_krw", ettgl.getText().toString());
            jsonObject.put("image_name", id_krw2+photoExt);
            jsonObject.put("image_file", encodePhoto);

            newArr.put(jsonObject);

            Log.e(TAG, "coba input = " + newArr.toString(1));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.put(BASE_IP + "karyawan?id_krw")
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(EditKaryawanActivity.this, message, Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), ProfilActivity.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONExceptions" + e, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Gagal mengubah data", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), anError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }
}
