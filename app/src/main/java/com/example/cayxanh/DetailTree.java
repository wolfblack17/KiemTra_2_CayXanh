package com.example.cayxanh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetailTree extends AppCompatActivity {
    TextView tenKhoaHoc, tenThuongGoi, dactinh, mauLa;
    ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_tree);
        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("Chi Tiết Cây Xanh");
        toolbar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Tree tree = (Tree) bundle.getSerializable("tree");
        Log.d("TAG", "onCreate: " + tree.getTenKhoaHocCay());
        tenKhoaHoc = findViewById(R.id.nameKhoaHoc);
        tenThuongGoi = findViewById(R.id.nameThuongGoi);
        dactinh = findViewById(R.id.DacTinh);
        mauLa = findViewById(R.id.MauLa);
        image = findViewById(R.id.imgAnh);

        tenKhoaHoc.setText("Tên Khoa Học :" + tree.getTenKhoaHocCay());
        tenThuongGoi.setText("Tên Thường Gọi: " + tree.getTenThuongGoiCay());
        dactinh.setText("Đặc Tính: " + tree.getDacTinh());
        mauLa.setText("Màu Lá: " + tree.getMauLa());
        getImage(tree.getIdCay(), getApplicationContext());


    }

    void getImage(String id, Context context) {
        StorageReference referenceDataImage = FirebaseStorage.getInstance().getReference().child("imageTrees");
        referenceDataImage.listAll().addOnSuccessListener(listResult -> {
            for (StorageReference files : listResult.getItems()
            ) {
                if (files.getName().equals(id)) {
                    files.getDownloadUrl().addOnSuccessListener(uri -> {
                        Picasso.with(context)
                                .load(uri)
                                .placeholder(R.mipmap.ic_launcher)
                                .into(image);
                        Log.d("TAG", "getImage: " + uri);
                    });
                }

            }
        }).addOnFailureListener(e -> {
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(DetailTree.this, MainActivity.class));
        return true;
    }

}
