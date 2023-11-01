package com.example.managecoffeeapp_prm_project.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.io.InputStream;


import de.hdodenhof.circleimageview.CircleImageView;
import com.example.managecoffeeapp_prm_project.MainActivity;
import com.example.managecoffeeapp_prm_project.R;
import com.example.managecoffeeapp_prm_project.dao.NguoiDungDAO;
import com.example.managecoffeeapp_prm_project.model.NguoiDung;
import com.example.managecoffeeapp_prm_project.ui.ThietLapTaiKhoanActivity;
import com.example.managecoffeeapp_prm_project.utils.ImageToByte;
import com.example.managecoffeeapp_prm_project.utils.MyToast;

public class SettingFragment extends Fragment implements View.OnClickListener {

    public static final int PICK_IMAGE = 1;
    public static final String MA_NGUOIDUNG = "MA_NGUOIDUNG";

    TextView tvThietLapTaiKhoan, tvDoiMatKhuau, tvDangXuat, tvTenNguoiDung, tvChucVu, tvEmail;
    MainActivity mainActivity;
    NguoiDungDAO nguoiDungDAO;
    CircleImageView civHinhAnh;
    ImageView ivDoiHinhAnh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);

        mainActivity = ((MainActivity) getActivity());
        nguoiDungDAO = new NguoiDungDAO(getContext());

        fillActivity();

        tvThietLapTaiKhoan.setOnClickListener(this);
        tvDoiMatKhuau.setOnClickListener(this);
        tvDangXuat.setOnClickListener(this);
        ivDoiHinhAnh.setOnClickListener(this);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivDoiHinhAnh:
                requestPermissionPickImage();
                break;
            case R.id.tvThietLapTaiKhoan:
                openTLTKActivity();
                break;
            case R.id.tvDoiMatKhau:

                break;
            case R.id.tvDangXuat:

                break;
        }
    }

    private void initView(View view) {

        tvThietLapTaiKhoan = view.findViewById(R.id.tvThietLapTaiKhoan);
        tvDoiMatKhuau = view.findViewById(R.id.tvDoiMatKhau);
        tvDangXuat = view.findViewById(R.id.tvDangXuat);
        civHinhAnh = view.findViewById(R.id.civHinhAnh);
        tvTenNguoiDung = view.findViewById(R.id.tvTenNguoiDung);
        tvChucVu = view.findViewById(R.id.tvChucVu);
        tvEmail = view.findViewById(R.id.tvEmail);
        ivDoiHinhAnh = view.findViewById(R.id.ivDoiHinhAnh);
    }

    private void fillActivity() {
        NguoiDung nguoiDung = getNguoiDung();
        Bitmap bitmap = BitmapFactory.decodeByteArray(nguoiDung.getHinhAnh(),
                0,
                nguoiDung.getHinhAnh().length);
        // Gán dữ liệu cho View
        civHinhAnh.setImageBitmap(bitmap);
        tvTenNguoiDung.setText(nguoiDung.getHoVaTen());
        tvChucVu.setText(nguoiDung.getChucVu());
        tvEmail.setText(nguoiDung.getEmail());

    }

    private NguoiDung getNguoiDung() {
        // Lấy mã người dùng từ MainActivity theo fun(getKeyUser)
        String maNguoiDung = "admin";
        //Objects.requireNonNull(mainActivity).getKeyUser();
        return nguoiDungDAO.getByMaNguoiDung(maNguoiDung);
    }

    private void requestPermissionPickImage() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            // cấp quyền cho ứng dụng nếu chưa được cấp quyền
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
    }


    private void openTLTKActivity() {
        Intent intent = new Intent(getContext(), ThietLapTaiKhoanActivity.class);
        intent.putExtra(MA_NGUOIDUNG, getNguoiDung().getMaNguoiDung());
        startActivity(intent);
        ((Activity) requireContext()).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }


    private void updateAvatarUser(){
        NguoiDung nguoiDung = getNguoiDung();
        nguoiDung.setHinhAnh(ImageToByte.circleImageViewToByte(getContext(), civHinhAnh));

        if (nguoiDungDAO.updateNguoiDung(nguoiDung)) {
            MyToast.successful(getContext(), "Cập nhật ảnh đại diện thành công");
            fillActivity();
        } else {
            MyToast.error(getContext(), "Lỗi");
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                InputStream stream = requireContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                civHinhAnh.setImageBitmap(bitmap);
                updateAvatarUser();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fillActivity();
    }
}