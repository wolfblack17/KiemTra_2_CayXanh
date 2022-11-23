package com.example.cayxanh;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddTreeActivity extends AppCompatActivity {
    Button btnAddTree, btnCan;
    ImageView imgTree;
    private Uri avatar;
    TextInputLayout edtName, edtTreeName, edtMauLa, edtDacTinh;
    private static final int PICL_IMAGES_CODE = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_tree);
        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("Thêm Cây Xanh");
        toolbar.setDisplayHomeAsUpEnabled(true);
        btnAddTree = findViewById(R.id.btnCreatTree);
        btnCan = findViewById(R.id.btnCancel);
        imgTree = findViewById(R.id.imgAdd);
        edtName = findViewById(R.id.edtTenKhoaHoc);
        edtTreeName = findViewById(R.id.edtTenThuongGoi);
        edtDacTinh = findViewById(R.id.edtDacTinh);
        edtMauLa = findViewById(R.id.edtMauLa);

        btnAddTree.setOnClickListener(v -> {

            if (checkValidate()) {
                Tree tree = new Tree(edtName.getEditText().getText().toString(),
                        edtTreeName.getEditText().getText().toString(),
                        edtDacTinh.getEditText().getText().toString(),
                        edtMauLa.getEditText().getText().toString(),
                        null);
                CreateTree(tree);
            }
        });
        imgTree.setOnClickListener(v -> {
            addImage();
        });
        btnCan.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

    }

    private void CreatImage(String id) {
        if (avatar != null) {
            StorageReference reference = FirebaseStorage.getInstance().getReference("imageTrees/" + id);
            reference.putFile(avatar).addOnSuccessListener(taskSnapshot -> {
                Toast.makeText(getApplicationContext(), "Thêm cảnh cây thành công", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(command -> {
                Toast.makeText(getApplicationContext(), "Thêm ảnh cây thất bại", Toast.LENGTH_SHORT).show();
            });
        }

    }


    private void CreateTree(Tree tree) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("trees");
        String key = reference.push().getKey();
        Tree tree1 = new Tree(key,
                tree.getTenKhoaHocCay(),
                tree.getTenThuongGoiCay(),
                tree.getDacTinh(),
                tree.getMauLa(),
                null);
        CreatImage(key);
        reference.child(key).setValue(tree1)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity( new Intent(this ,MainActivity.class));
                        Toast.makeText(this, "Tạo cây thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Tạo cây thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    Boolean checkValidate() {
        if (edtName.getEditText().getText().toString().isEmpty()) {
            edtName.setError("Bạn chưa nhập tên");
            edtName.requestFocus();
            return false;
        }
        if (edtTreeName.getEditText().getText().toString().isEmpty()) {
            edtTreeName.setError("Bạn chưa nhập tên");
            edtTreeName.requestFocus();
            return false;
        }
        if (edtDacTinh.getEditText().getText().toString().isEmpty()) {
            edtDacTinh.setError("Bạn chưa nhập tên");
            edtDacTinh.requestFocus();
            return false;
        }
        if (edtMauLa.getEditText().getText().toString().isEmpty()) {
            edtMauLa.setError("Bạn chưa nhập tên");
            edtMauLa.requestFocus();
            return false;
        }

        return true;
    }

    private void addImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICL_IMAGES_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICL_IMAGES_CODE) {
                avatar = data.getData();
                imgTree.setImageURI(avatar);
                Log.d("TAG", "onActivityResult: " + data.getData());

            }
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        startActivity( new Intent(AddTreeActivity.this,MainActivity.class));
        return true;
    }
}
