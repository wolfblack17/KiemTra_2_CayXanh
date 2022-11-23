package com.example.cayxanh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TreeAdapter.CallBackItem {
    FloatingActionButton btnAdd;
    TreeAdapter adapter;
    FirebaseDatabase database;
    List<Tree> listTree;
    RecyclerView view;
    DatabaseReference reference;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("Danh Sách Cây Xanh");
        progressBar = findViewById(R.id.idPBLoading);
        listTree = new ArrayList<>();
        btnAdd =findViewById(R.id.floating_action_button);
        view = findViewById(R.id.rycListFood);
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("trees");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listTree.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()
                ) {
                    Tree tree = snapshot1.
                    getValue(Tree.class);
                    listTree.add(tree);
                }
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new TreeAdapter(listTree, MainActivity.this, getApplicationContext());
        view.setAdapter(adapter);



        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this,AddTreeActivity.class));
        });

    }



    @Override
    public void onClick(Tree tree) {
        Intent intent = new Intent(this,DetailTree.class);
        Bundle bundle =new Bundle();
        bundle.putSerializable("tree", tree);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClickDelte(Tree tree) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Bạn Có Muốn Xóa Cây Xanh Này Không")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        reference = FirebaseDatabase.getInstance().getReference();
                        Query query = reference.child("trees").orderByChild("idCay").equalTo(tree.getIdCay());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alert = builder.create();
        alert.show();
    }
}