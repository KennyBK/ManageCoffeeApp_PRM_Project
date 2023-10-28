package com.example.managecoffeeapp_prm_project.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.managecoffeeapp_prm_project.fragment.SettingFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import com.example.managecoffeeapp_prm_project.R;
import com.example.managecoffeeapp_prm_project.dao.NguoiDungDAO;
import com.example.managecoffeeapp_prm_project.model.NguoiDung;
import com.example.managecoffeeapp_prm_project.utils.MyToast;
import com.example.managecoffeeapp_prm_project.utils.XDate;

public class ThietLapTaiKhoanActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack, ivEditTen, ivEditNgaySinh, ivEditEmail, ivEditGioiTinh, ivEditChucVu;
    CircleImageView civHinhAnh;
    TextView tvMaNguoiDung, tvTenNguoiDung, tvNgaySinh, tvGioiTinh, tvEmail, tvChucVu;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_lap_tai_khoan);
        initView();
        nguoiDungDAO = new NguoiDungDAO(this);
        getInfoNguoiDung();
        initOnclickIv();
    }


    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        civHinhAnh = findViewById(R.id.civHinhAnh);
        tvMaNguoiDung = findViewById(R.id.tvMaNguoiDung);
        tvTenNguoiDung = findViewById(R.id.tvTenNguoiDung);
        tvNgaySinh = findViewById(R.id.tvNgaySinh);
        tvGioiTinh = findViewById(R.id.tvGioiTinh);
        tvEmail = findViewById(R.id.tvEmail);
        tvChucVu = findViewById(R.id.tvChucVu);
        ivEditTen = findViewById(R.id.ivEditHoVaTen);
        ivEditNgaySinh = findViewById(R.id.ivEditNgaySinh);
        ivEditEmail = findViewById(R.id.ivEditEmail);
        ivEditGioiTinh = findViewById(R.id.ivEditGioiTinh);
        ivEditChucVu = findViewById(R.id.ivEditChucVu);
    }

    private void initOnclickIv() {
        ivBack.setOnClickListener(this);
        ivEditTen.setOnClickListener(this);
        ivEditGioiTinh.setOnClickListener(this);
        ivEditNgaySinh.setOnClickListener(this);
        ivEditEmail.setOnClickListener(this);
        ivEditChucVu.setOnClickListener(this);
    }

    private NguoiDung getObjectNguoiDung() {
        Intent intent = getIntent();
        String maNguoiDung = intent.getStringExtra(SettingFragment.MA_NGUOIDUNG);
        return nguoiDungDAO.getByMaNguoiDung(maNguoiDung);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivEditHoVaTen:

                break;
            case R.id.ivEditNgaySinh:

                break;
            case R.id.ivEditGioiTinh:

                break;
            case R.id.ivEditEmail:

                break;
            case R.id.ivEditChucVu:

                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void getInfoNguoiDung() {
        NguoiDung nguoiDung = getObjectNguoiDung();
        Bitmap bitmap = BitmapFactory.decodeByteArray(nguoiDung.getHinhAnh(), 0, nguoiDung.getHinhAnh().length);
        civHinhAnh.setImageBitmap(bitmap);
        tvMaNguoiDung.setText(nguoiDung.getMaNguoiDung());
        tvTenNguoiDung.setText(nguoiDung.getHoVaTen());
        tvNgaySinh.setText(XDate.toStringDate(nguoiDung.getNgaySinh()));
        if (nguoiDung.getGioiTinh().equals(NguoiDung.GENDER_FEMALE)) {
            tvGioiTinh.setText("Nữ");
        } else {
            tvGioiTinh.setText("Nam");
        }
        tvEmail.setText(nguoiDung.getEmail());
        tvChucVu.setText(nguoiDung.getChucVu());
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }
}