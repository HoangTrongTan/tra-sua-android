package com.example.trasua_android.Activitys;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trasua_android.Dao.dangnhapDao;
import com.example.trasua_android.Dao.imageloader;
import com.example.trasua_android.Databases.db_sqlite;
import com.example.trasua_android.R;
import com.example.trasua_android.TruyVan.thucthi;
import com.example.trasua_android.animation.anim;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;

public class home extends AppCompatActivity {
    private static int REQUEST_IMAGE_CAPTURE  = 1;
    private static int REQUEST_IMAGE_PICK = 2;
    TextInputEditText txt_taikhoan;
    TextInputEditText txt_pass;
    Button dangnhap,dangky;
    ImageButton chonanh;
    ImageButton chupanh;
    Uri photoUri;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Kích hoạt database
        db_sqlite.getInstance(this);

        dangnhap = findViewById(R.id.dangnhap);
        dangky = findViewById(R.id.dangky);

        txt_taikhoan = findViewById(R.id.txt_taikhoan);
        txt_pass = findViewById(R.id.txt_pass);

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = thucthi.check_login(txt_taikhoan.getText().toString(),txt_pass.getText().toString());
                if(cursor.getCount() > 0){
                    dangnhapDao.toast_success(home.this);
                }else{
                    dangnhapDao.toast_failed(home.this);
                }
            }
        });
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim.startAnimation(home.this,dangky);
                opendialog_NhapMaSinhVien(Gravity.CENTER);
            }
        });
    }

    public void  opendialog_NhapMaSinhVien(int Gravity){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dangky_form);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams atribuites = window.getAttributes();
        atribuites.gravity = Gravity;
        window.setAttributes(atribuites);

         chupanh = dialog.findViewById(R.id.chupanh);
         chonanh = dialog.findViewById(R.id.chonanh);
        TextInputLayout txt_layout_taikhoan = dialog.findViewById(R.id.txt_layout_taikhoan);
        TextInputEditText txt_taikhoan = dialog.findViewById(R.id.txt_taikhoan);

        TextInputLayout txt_layout_pass = dialog.findViewById(R.id.txt_layout_pass);
        TextInputEditText txt_pass = dialog.findViewById(R.id.txt_pass);

        Button ok = dialog.findViewById(R.id.ok);
        Button thoi = dialog.findViewById(R.id.thoi);

        chupanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(ACTION_IMAGE_CAPTURE);
                //yêu cầu quyền truy cập
                if(ActivityCompat.checkSelfPermission(home.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(home.this,new String[]{Manifest.permission.CAMERA},1);
                    return;
                }
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
        chonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonanh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
                    }
                });
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] anh = ConverttoArrayByte(chupanh);
                Toast.makeText(home.this, String.valueOf(anh), Toast.LENGTH_SHORT).show();

                try {
                    long check = thucthi.insert_Taikhoan(txt_taikhoan.getText().toString(),txt_pass.getText().toString(),anh);
                    if(check > 0){
                        Toast.makeText(home.this, "thêm thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(home.this, "lỗi", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Log.d("",e.toString());
                }
            }
        });


        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            chupanh.setImageBitmap(photo);
            Toast.makeText(this, String.valueOf(photo), Toast.LENGTH_SHORT).show();
        }

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            photoUri = imageloader.getImageUriFromGallery(this, data);

            imageloader.loadImage(this, photoUri, chonanh);
            Toast.makeText(this, String.valueOf(photoUri), Toast.LENGTH_SHORT).show();
        }
    }
    public byte[] ConverttoArrayByte(ImageButton img)
    {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}