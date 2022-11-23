package com.example.cayxanh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ViewHodelTree> {
    List<Tree> list;
    private CallBackItem callBackItem;
    Context context;
    interface CallBackItem {
        void onClick(Tree tree);
        void onClickDelte(Tree tree);
    }


    public TreeAdapter(List<Tree> list, CallBackItem callBackItem,Context context)
    {
        this.list = list;
        this.context = context;
        this.callBackItem = callBackItem;
    }
    @NonNull
    @Override
    public ViewHodelTree onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHodelTree(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tree, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodelTree holder, int position) {
        Tree tree = list.get(position);
        if (tree == null){
           return;
        } else {
            holder.initData(tree, context);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHodelTree extends RecyclerView.ViewHolder{
        private ImageView image;
        ImageButton btnDelete;
        private TextView tvTenKHoaHoc,tvTenThuongDung;
        public ViewHodelTree(@NonNull View itemView) {
            super(itemView);
            tvTenKHoaHoc = itemView.findViewById(R.id.tvItemTree);
            tvTenThuongDung = itemView.findViewById(R.id.tvItemTree1);
            image = itemView.findViewById(R.id.Itemimg);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
        void initData(Tree tree, Context context){
            tvTenKHoaHoc.setText("Tên Khoa Học: "+tree.getTenKhoaHocCay());
            tvTenThuongDung.setText("Tên Thường Gọi: "+tree.getTenThuongGoiCay());
            getImage(tree.getIdCay(), context);
            itemView.setOnClickListener(v -> {
                callBackItem.onClick(tree);
            });

            btnDelete.setOnClickListener(v -> {
                callBackItem.onClickDelte(tree);
            });
        }
        void getImage(String id, Context context){
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
                            Log.d("TAG", "getImage: "+uri);
                        });
                    }

                }
            }).addOnFailureListener(e -> {
            });
        }

    }

}
