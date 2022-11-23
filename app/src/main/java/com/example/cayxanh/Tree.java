package com.example.cayxanh;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Tree implements Serializable {
    private String idCay;
    private String tenKhoaHocCay;
    private String tenThuongGoiCay;
    private String DacTinh;
    private  String MauLa;
    private String imgCay;

    public Tree() {
    }

    public Tree(String idCay, String tenKhoaHocCay, String tenThuongGoiCay, String dacTinh, String mauLa, String imgCay) {
        this.idCay = idCay;
        this.tenKhoaHocCay = tenKhoaHocCay;
        this.tenThuongGoiCay = tenThuongGoiCay;
        DacTinh = dacTinh;
        MauLa = mauLa;
        this.imgCay = imgCay;
    }

    public Tree(String tenKhoaHocCay, String tenThuongGoiCay, String dacTinh, String mauLa, String imgCay) {
        this.tenKhoaHocCay = tenKhoaHocCay;
        this.tenThuongGoiCay = tenThuongGoiCay;
        DacTinh = dacTinh;
        MauLa = mauLa;
        this.imgCay = imgCay;
    }

    public String getTenKhoaHocCay() {
        return tenKhoaHocCay;
    }

    public void setTenKhoaHocCay(String tenKhoaHocCay) {
        this.tenKhoaHocCay = tenKhoaHocCay;
    }

    public String getTenThuongGoiCay() {
        return tenThuongGoiCay;
    }

    public void setTenThuongGoiCay(String tenThuongGoiCay) {
        this.tenThuongGoiCay = tenThuongGoiCay;
    }

    public String getDacTinh() {
        return DacTinh;
    }

    public void setDacTinh(String dacTinh) {
        DacTinh = dacTinh;
    }

    public String getMauLa() {
        return MauLa;
    }

    public void setMauLa(String mauLa) {
        MauLa = mauLa;
    }

    public String getIdCay() {
        return idCay;
    }

    public void setIdCay(String idCay) {
        this.idCay = idCay;
    }

    public String getImgCay() {
        return imgCay;
    }

    public void setImgCay(String imgCay) {
        this.imgCay = imgCay;
    }

//    protected Tree(Parcel in) {
//        tenKhoaHocCay = in.readString();
//        tenThuongGoiCay = in.readString();
//        DacTinh = in.readString();
//        MauLa = in.readString();
//        imgCay = in.readString();
//        idCay = in.readString();
//    }

//    public static final Parcelable.Creator<Tree> CREATOR = new Parcelable.Creator<Tree>() {
//        @Override
//        public Tree createFromParcel(Parcel in) {
//            return new Tree(in);
//        }
//
//        @Override
//        public Tree[] newArray(int size) {
//            return new Tree[][size];
//        }
//    };

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//
//        parcel.writeString(tenKhoaHocCay);
//        parcel.writeString(tenThuongGoiCay);
//        parcel.writeString(DacTinh);
//        parcel.writeString(MauLa);
//        parcel.writeString(imgCay);
//        parcel.writeString(idCay);
//    }
//}
}
