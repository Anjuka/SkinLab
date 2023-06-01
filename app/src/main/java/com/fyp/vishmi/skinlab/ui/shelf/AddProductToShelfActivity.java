package com.fyp.vishmi.skinlab.ui.shelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.helper.RequestUserPermission;
import com.fyp.vishmi.skinlab.model.ShelfData;
import com.fyp.vishmi.skinlab.model.ShelfItemList;
import com.fyp.vishmi.skinlab.ui.scan.CameraActivity;
import com.fyp.vishmi.skinlab.ui.dashboard.DashboardActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddProductToShelfActivity extends AppCompatActivity {

    public static String TAG = "AddProductToShelfActivity";

    private EditText et_full_name;
    private EditText et_mani_date;
    private EditText et_ex_date;
    private EditText et_open_date;
    private EditText et_c_number;
    private EditText et_brand;
    private EditText et_rating;
    private Button btn_add;
    private ImageView iv_back;
    private ImageView iv_camera;
    private CircleImageView iv_camera_main;
    private ConstraintLayout cl_home;
    private ConstraintLayout cl_scan;

    private String full_name;
    private String manifac_date;
    private String brand;
    private String ex_date;
    private String open_date;
    private int rating;
    private String note;
    private String img_url = "";

    int type =0;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;

    private ArrayList<ShelfItemList> shelfItemListArrayList = new ArrayList<>();

    final Calendar myCalendar= Calendar.getInstance();

    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;

    private Camera camera;
    private int cameraId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_to_shelf);

        et_full_name = findViewById(R.id.et_full_name);
        cl_scan = findViewById(R.id.cl_scan);
        et_mani_date = findViewById(R.id.et_mani_date);
        iv_camera_main = findViewById(R.id.iv_camera_main);
        iv_camera = findViewById(R.id.iv_camera);
        et_ex_date = findViewById(R.id.et_ex_date);
        et_open_date = findViewById(R.id.et_open_date);
        et_c_number = findViewById(R.id.et_c_number);
        et_brand = findViewById(R.id.et_brand);
        btn_add = findViewById(R.id.btn_add);
        et_rating = findViewById(R.id.et_rating);
        iv_back = findViewById(R.id.iv_back);
        cl_home = findViewById(R.id.cl_home);

        iv_camera.setVisibility(View.VISIBLE);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        RequestUserPermission requestUserPermission = new RequestUserPermission(this);
        requestUserPermission.verifyStoragePermissions();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.
                PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest
                    .permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        // do we have a camera?
        if (!getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
                    .show();
        }


        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                String myFormat="MM/dd/yy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);

                if (type == 0){
                    et_mani_date.setText(dateFormat.format(myCalendar.getTime()));
                }

                if (type == 1){
                    et_ex_date.setText(dateFormat.format(myCalendar.getTime()));
                }

                if (type == 3) {
                    et_open_date.setText(dateFormat.format(myCalendar.getTime()));
                }
            }
        };

        et_mani_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 0;
                new DatePickerDialog(AddProductToShelfActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        et_ex_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                new DatePickerDialog(AddProductToShelfActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        et_open_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 3;
                new DatePickerDialog(AddProductToShelfActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        iv_camera_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file = new File(storageDir + File.separator + "CAPTURE.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getApplicationContext(),
                        "com.fyp.vishmi.skinlab.fileprovider", // Over here
                        file));
//                Log.d("printXX",file.toString());
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, "file:/" +file);
//                Intent intentCrop = new Intent(getContext(), ImageCropActivity.class);
//                startActivity(intentCrop);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        shelfItemListArrayList = getIntent().getParcelableArrayListExtra("list");
        Log.d("TAG", "onCreate: "+ shelfItemListArrayList);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                full_name = et_full_name.getText().toString().trim();
                manifac_date = et_mani_date.getText().toString().trim();
                brand = et_brand.getText().toString().trim();
                ex_date = et_ex_date.getText().toString().trim();
                open_date = et_open_date.getText().toString().trim();
                note = et_c_number.getText().toString().trim();
                if (!et_rating.getText().toString().equals("")) {
                    rating = Integer.parseInt(et_rating.getText().toString().trim());
                }
                else {
                    rating = 0;
                }
                if (rating > 5){
                    rating = 5;
                }

                if (full_name.isEmpty() || manifac_date.isEmpty() || brand.isEmpty()  || ex_date.isEmpty() || open_date.isEmpty()){
                    Toast.makeText(AddProductToShelfActivity.this, "Required fields are empty", Toast.LENGTH_SHORT).show();
                }
                else {

                    ShelfItemList shelfItemList = new ShelfItemList(
                            full_name,
                            brand,
                            manifac_date,
                            ex_date,
                            open_date,
                            note,
                            rating,
                            img_url);

                    shelfItemListArrayList.add(shelfItemList);

                    ShelfData shelfData = new ShelfData(firebaseAuth.getUid(), shelfItemListArrayList);

                    DocumentReference documentReference = firebaseFirestore.collection("shelfProducts").document(firebaseAuth.getCurrentUser().getUid());
                    documentReference.set(shelfData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Intent intent = new Intent(getApplicationContext(),  ShelfActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.d("TAG", "onFailure: "+e.getMessage());
                        }
                    });
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),  ShelfActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intentHome);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        cl_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public static String renameFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "JPEG_" + timeStamp + "_";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String decodedImgString;
        Uri uri;

        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.i(TAG, String.valueOf(getIntent().getData()));

            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File file = new File(storageDir + File.separator + "CAPTURE.jpg");

            String fileName;

            File dir = new File(storageDir + File.separator + "ZharkPictures");

            try {
                if (dir.exists()) {
                    System.out.println("Directory Exists");

                } else {
                    dir.mkdir();
                    System.out.println("Directory Created");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            File newExFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String exFileName = AddProductToShelfActivity.renameFile();
            String destinationPath = newExFile.getAbsolutePath() + File.separator + exFileName + ".jpg";

            Log.i(TAG, String.valueOf(destinationPath));

            File destination = new File(destinationPath);

            try {
                FileUtils.copyFile(file, destination);

            } catch (IOException e) {
                e.printStackTrace();
            }
            uri = Uri.fromFile(destination);

            StorageReference image = storageReference.child("images/"+ exFileName);
            image.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            img_url = uri.toString();

                            Glide.with(getApplicationContext())
                                    .load(uri)
                                    .into(iv_camera_main);

                            iv_camera.setVisibility(View.INVISIBLE);
                            progressDialog.cancel();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.cancel();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: " + e.getMessage());
                }
            });

        }
    }
}