package com.fyp.vishmi.skinlab.ui.diary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.adapater.DiaryAdapter;
import com.fyp.vishmi.skinlab.helper.RealPathUtil;
import com.fyp.vishmi.skinlab.model.DiaryData;
import com.fyp.vishmi.skinlab.model.DiaryDataList;
import com.fyp.vishmi.skinlab.ui.scan.CameraActivity;
import com.fyp.vishmi.skinlab.ui.consult.ConsultDocListActivity;
import com.fyp.vishmi.skinlab.ui.dashboard.DashboardActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DiaryActivity extends AppCompatActivity implements DiaryAdapter.CbClickListner {

    private static final int REQUEST_CODE = 1;

    private Button btn_add_img;
    private Button btn_compare;
    private TextView tv_user_name;
    private RecyclerView rv_diary;
    private ConstraintLayout cl_shelf;
    private ConstraintLayout cl_diary;
    private ConstraintLayout cl_consult;
    private ConstraintLayout cl_home;
    private ConstraintLayout cl_scan;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private ArrayList<DiaryDataList> diaryDataLists = new ArrayList<>();
    private ArrayList<DiaryData> diaryData = new ArrayList<>();
    private ArrayList<Integer> selected_position = new ArrayList<>();

    DiaryAdapter diaryAdapter;
    String Date = "";
    String Time = "";

    String userID = "";
    private GridLayoutManager layoutManager;

    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        btn_add_img = findViewById(R.id.btn_add_img);
        rv_diary = findViewById(R.id.rv_diary);
        btn_compare = findViewById(R.id.btn_compare);
        cl_shelf = findViewById(R.id.cl_shelf);
        cl_scan = findViewById(R.id.cl_scan);
        cl_diary = findViewById(R.id.cl_diary);
        cl_consult = findViewById(R.id.cl_consult);
        cl_home = findViewById(R.id.cl_home);
        tv_user_name = findViewById(R.id.tv_user_name);

        layoutManager = new GridLayoutManager(this, 1);
        rv_diary.setHasFixedSize(true);
        rv_diary.setLayoutManager(layoutManager);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.
                PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest
                    .permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                tv_user_name.setText("Hello " + documentSnapshot.getString("fName") + "!");
            }
        });

        progressDialog.setMessage("Loading");
        progressDialog.show();

        firebaseFirestore.collection("diaryImageUploads").whereEqualTo("id", userID).
                get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DiaryData> diary = queryDocumentSnapshots.toObjects(DiaryData.class);
                        diaryData.addAll(diary);
                        diaryDataLists.clear();

                        if (diaryData.size() !=0) {

                            diaryDataLists = diaryData.get(0).getDiaryDataLists();
                            Log.d("TAG", "onSuccess: " + diary);

                            diaryAdapter = new DiaryAdapter(DiaryActivity.this, diaryDataLists, new DiaryAdapter.itemClickListner() {
                                @Override
                                public void onItemClick(int postion) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DiaryActivity.this);

                                    builder.setTitle("Delete...");
                                    builder.setMessage("Are you sure, you need delete this item?");

                                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do nothing but close the dialog
                                            dialog.dismiss();

                                            diaryDataLists.remove(postion);

                                            DiaryData diaryData = new DiaryData(diaryDataLists, userID);

                                            DocumentReference documentReference = firebaseFirestore.collection("diaryImageUploads").document(firebaseAuth.getCurrentUser().getUid());
                                            documentReference.set(diaryData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                    diaryAdapter.notifyDataSetChanged();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    Log.d("TAG", "onFailure: " + e.getMessage());
                                                }
                                            });

                                        }
                                    });

                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            // Do nothing
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            }, DiaryActivity.this::onCBClick);
                            rv_diary.setAdapter(diaryAdapter);
                        }
                        progressDialog.cancel();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("TAG", "onFailure: " + e);
                progressDialog.cancel();
            }
        });


        btn_add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open gallery
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
            }
        });

        btn_compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selected_position.size() < 2){
                    Toast.makeText(DiaryActivity.this, "Need 2 images to compare", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), DiaryCompareActivity.class);
                    intent.putExtra("first", selected_position.get(0));
                    intent.putExtra("second", selected_position.get(1));
                    startActivity(intent);
                    finish();
                }

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

        cl_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intentDiary = new Intent(getApplicationContext(), Diary)
            }
        });

        cl_consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMyDoc = new Intent(getApplicationContext(), ConsultDocListActivity.class);
                startActivity(intentMyDoc);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                ExifInterface exif = new ExifInterface(RealPathUtil.getRealPath(DiaryActivity.this, imageUri));

                String exFileName = exif.getAttribute(ExifInterface.TAG_DATETIME);
                if(exFileName != null) {
                    String[] splited = exFileName.split("\\s+");
                     Date = splited[0];
                     Time = splited[1];
                }
                else {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                    String strDate = sdf.format(c.getTime());
                    exFileName = strDate;

                    String[] splited = strDate.split("\\s+");
                    Date = splited[0];
                    Time = splited[1];
                    Log.d("TAG", "onActivityResult: ");
                }
                Log.d("TAG", "onActivityResult: " + exFileName);
                Log.d("TAG", "onActivityResult: " );

                progressDialog.setMessage("Uploading...");
                progressDialog.show();

                StorageReference image = storageReference.child("diaryUploads/"+ exFileName);
                image.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                DiaryDataList diaryDataList = new DiaryDataList(uri.toString(), Date, Time);
                                diaryDataLists.add(diaryDataList);
                                DiaryData diaryData = new DiaryData(diaryDataLists, userID);

                                DocumentReference documentReference = firebaseFirestore.collection("diaryImageUploads").document(firebaseAuth.getCurrentUser().getUid());
                                documentReference.set(diaryData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Intent intent = new Intent(getApplicationContext(), DiaryActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Log.d("TAG", "onFailure: "+e.getMessage());
                                    }
                                });

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
                        Log.d("TAG", "onFailure: " + e.getMessage());
                        progressDialog.cancel();
                    }
                });



            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(DiaryActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(DiaryActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCBClick(int cb_postion, boolean isCheck) {

        Log.d("TAG", "onCBClick: " + cb_postion + " " + isCheck);

        if (isCheck == false){
            selected_position.remove(cb_postion);
        }
        else {

            if (selected_position.size() == 2){
                Toast.makeText(DiaryActivity.this, "Maximum compare images already selected...", Toast.LENGTH_SHORT).show();
            }
            else {
                selected_position.add(cb_postion);
            }
        }

        Log.d("TAG", "onCBClick: " + selected_position);
    }
}