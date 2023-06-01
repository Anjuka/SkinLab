package com.fyp.vishmi.skinlab.ui.shelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fyp.vishmi.skinlab.ui.diary.DiaryActivity;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.adapater.ShelfAdapter;
import com.fyp.vishmi.skinlab.model.ShelfData;
import com.fyp.vishmi.skinlab.model.ShelfItemList;
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

import java.util.ArrayList;
import java.util.Map;

public class ShelfActivity extends AppCompatActivity {

    private ConstraintLayout cl_shelf;
    private ConstraintLayout cl_diary;
    private ConstraintLayout cl_consult;
    private ConstraintLayout cl_home;
    private ConstraintLayout cl_scan;
    private TextView tv_user_name;
    private TextView tv_count;
    private Button btn_add_gods;
    private RecyclerView rv_shelf;


    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ShelfItemList shelfItemList;

    String userID = "";
    private GridLayoutManager layoutManager;

    private ArrayList<ShelfItemList> shelfItemListArrayList = new ArrayList<>();
    private ArrayList<Map<String, String>> serviceRecordsLists = new ArrayList<>();

    private ShelfAdapter shelfAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf);

        cl_shelf = findViewById(R.id.cl_shelf);
        cl_scan = findViewById(R.id.cl_scan);
        cl_diary = findViewById(R.id.cl_diary);
        cl_consult = findViewById(R.id.cl_consult);
        cl_home = findViewById(R.id.cl_home);
        tv_user_name = findViewById(R.id.tv_user_name);
        btn_add_gods = findViewById(R.id.btn_add_gods);
        tv_count = findViewById(R.id.tv_count);
        rv_shelf = findViewById(R.id.rv_shelf);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        layoutManager = new GridLayoutManager(this, 1);
        rv_shelf.setHasFixedSize(true);
        rv_shelf.setLayoutManager(layoutManager);

        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                tv_user_name.setText("Hello " + documentSnapshot.getString("fName") + "!");
            }
        });

        DocumentReference documentReferenceShelf = firebaseFirestore.collection("shelfProducts").document(userID);
        documentReferenceShelf.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                serviceRecordsLists = (ArrayList<Map<String, String>>) (documentSnapshot.get("shelfItemListArrayList"));
                if(serviceRecordsLists != null) {
                    Log.d("TAG", "onEvent: " + shelfItemListArrayList);
                    for (int x = 0; x < serviceRecordsLists.size(); x++) {
                        String name = serviceRecordsLists.get(x).get("fName");
                        String brand = serviceRecordsLists.get(x).get("brand");
                        String manifac_date = serviceRecordsLists.get(x).get("manifac_date");
                        String ex_date = serviceRecordsLists.get(x).get("ex_date");
                        String open_date = serviceRecordsLists.get(x).get("open_date");
                        String note = serviceRecordsLists.get(x).get("note");
                        String rating = String.valueOf(serviceRecordsLists.get(x).get("rating"));
                        String img_url = serviceRecordsLists.get(x).get("img_url");

                        shelfItemList = new ShelfItemList(name, brand, manifac_date, ex_date, open_date, note, Integer.parseInt(rating), img_url);
                        shelfItemListArrayList.add(shelfItemList);
                    }
                }

                Log.d("TAG", "onEvent: shelfItemListArrayList " + shelfItemListArrayList);
                tv_count.setText(shelfItemListArrayList.size() + " Products");

                shelfAdapter = new ShelfAdapter(ShelfActivity.this, shelfItemListArrayList, new ShelfAdapter.itemClickListner() {
                    @Override
                    public void onItemClick(int postion) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(ShelfActivity.this);

                        builder.setTitle("Delete...");
                        builder.setMessage("Are you sure, you need delete this item?");

                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                dialog.dismiss();

                                serviceRecordsLists = (ArrayList<Map<String, String>>) (documentSnapshot.get("shelfItemListArrayList"));
                                shelfItemListArrayList.remove(postion);

                                ShelfData shelfData = new ShelfData(firebaseAuth.getUid(), shelfItemListArrayList);

                                DocumentReference documentReference = firebaseFirestore.collection("shelfProducts").document(firebaseAuth.getCurrentUser().getUid());
                                documentReference.set(shelfData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                       shelfAdapter.notifyDataSetChanged();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Log.d("TAG", "onFailure: "+e.getMessage());
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
                });
                rv_shelf.setAdapter(shelfAdapter);
            }
        });


        btn_add_gods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddNew = new Intent(getApplicationContext(), AddProductToShelfActivity.class);
                intentAddNew.putExtra("list", shelfItemListArrayList);
                startActivity(intentAddNew);
                overridePendingTransition(0,0);
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

        cl_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDiary = new Intent(getApplicationContext(), DiaryActivity.class);
                startActivity(intentDiary);
                overridePendingTransition(0, 0);
                finish();

            }
        });
    }
}