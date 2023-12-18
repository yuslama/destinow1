package com.example.destinow1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.destinow1.komentar.AdapterKomentar;
import com.example.destinow1.komentar.Datakomentar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    TextView detailjudul, detaildeskripsi, detaillokasi;
    ImageView detailImage;
    String key = "";
    String imageUrl = "";
    String imageURL;
    Uri uri;

    //komentar
    EditText edt_komentar;
    Button btn_komentar;
    ImageView add_image_komentar;
    RecyclerView recyclerView;
    List<Datakomentar> dataList1;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    AdapterKomentar adapter1;
    //coba
    List<DataClass> dataList;
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailImage = findViewById(R.id.gambarItemDetail);
        detailjudul = findViewById(R.id.judulItemdetil);
        detaildeskripsi = findViewById(R.id.deskripsiItemdetil);
        detaillokasi = findViewById(R.id.lokasiItemdetil);

        edt_komentar = findViewById(R.id.edtuploadisiKomentar);
        btn_komentar = findViewById(R.id.btnKomentar);
        add_image_komentar = findViewById(R.id.addImage_komentar);
        recyclerView = findViewById(R.id.recyclerViewKomentar);




        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailjudul.setText(bundle.getString("Judul"));
            detaildeskripsi.setText(bundle.getString("Deskripsi"));
            detaillokasi.setText(bundle.getString("Lokasi"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Gambar");
            Glide.with(this).load(bundle.getString("Gambar")).into(detailImage);
        }

        // komentar
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            add_image_komentar.setImageURI(uri);
                        } else {
                            Toast.makeText(DetailActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        add_image_komentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        //
        String namawaisata = detailjudul.getText().toString();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(DetailActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DetailActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
        dataList1 = new ArrayList<>();
        adapter1 = new AdapterKomentar(DetailActivity.this, dataList1);
        recyclerView.setAdapter(adapter1);
        databaseReference = FirebaseDatabase.getInstance().getReference("Data komentar2").child(namawaisata);
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList1.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    Datakomentar dataClass = itemSnapshot.getValue(Datakomentar.class);
                    dataClass.setKeykomentar(itemSnapshot.getKey());
                    dataList1.add(dataClass);
                }
                adapter1.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });


        btn_komentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                komentarToas();
            }
        });
    }
    public void saveData(){
        if(uri == null){
            uploadData(null);


        }
        else {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Data komentar2")
                    .child(uri.getLastPathSegment());
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageURL = urlImage.toString();
                    uploadData(uri);
                    dialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                }
            });
        }
    }

    public void uploadData(Uri uri){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = currentFirebaseUser.getEmail();

        String namawisata = detailjudul.getText().toString();
        String komentar = edt_komentar.getText().toString();
        String nama = email;
        Datakomentar datakomentar = new Datakomentar(nama, komentar, imageURL);

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Data komentar2").child(namawisata).push()
                .setValue(datakomentar).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(DetailActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            edt_komentar.setText("");

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DetailActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void komentarToas(){
        String isikomentar = edt_komentar.getText().toString();

        if (isikomentar.isEmpty()){
            Toast.makeText(this, "Masukan Komentar", Toast.LENGTH_SHORT).show();
        }
        else {
            saveData();
        }
    }


}