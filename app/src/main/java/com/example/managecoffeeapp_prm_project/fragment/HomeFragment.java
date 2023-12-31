package com.example.managecoffeeapp_prm_project.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import com.example.managecoffeeapp_prm_project.MainActivity;
import com.example.managecoffeeapp_prm_project.R;
import com.example.managecoffeeapp_prm_project.adapter.ThucUongHomeFragmentAdapter;
import com.example.managecoffeeapp_prm_project.dao.HangHoaDAO;
import com.example.managecoffeeapp_prm_project.dao.NguoiDungDAO;
import com.example.managecoffeeapp_prm_project.model.HangHoa;
import com.example.managecoffeeapp_prm_project.model.NguoiDung;
import com.example.managecoffeeapp_prm_project.ui.LoaiThucUongActivity;
import com.example.managecoffeeapp_prm_project.ui.NhanVienActivity;
import com.example.managecoffeeapp_prm_project.ui.ThucUongActivity;
import com.example.managecoffeeapp_prm_project.utils.MyToast;


public class HomeFragment extends Fragment implements View.OnClickListener {
    TextView tvHi;
    CircleImageView civHinhAnh;
    ViewPager2 vpSlideImage;
    CardView cvLoai, cvThucUong, cvNhanVien;
    MainActivity mainActivity;
    NguoiDungDAO nguoiDungDAO;
    HangHoaDAO hangHoaDAO;
    RecyclerView recyclerViewThucUong;
    Handler handler;
    Runnable runnable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initOnClickCard();

        mainActivity = ((MainActivity) getActivity());
        nguoiDungDAO = new NguoiDungDAO(getContext());
        hangHoaDAO = new HangHoaDAO(getContext());

        welcomeUser();
        loadListThucUong();

        return view;
    }



    private void initView(View view) {
        vpSlideImage = view.findViewById(R.id.vpSlideImage);

        cvLoai = view.findViewById(R.id.cardLoaiThucUong);
        cvThucUong = view.findViewById(R.id.cardThucUong);
        cvNhanVien = view.findViewById(R.id.cardNhanVien);

        tvHi = view.findViewById(R.id.tvHi);
        civHinhAnh = view.findViewById(R.id.hinhAnh);
        recyclerViewThucUong = view.findViewById(R.id.recyclerViewThucUong);
    }

    private void initOnClickCard() {

        cvLoai.setOnClickListener(this);
        cvThucUong.setOnClickListener(this);
        cvNhanVien.setOnClickListener(this);

    }

    private void loadListThucUong() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewThucUong.setLayoutManager(linearLayoutManager);


        ArrayList<HangHoa> listHangHoa = hangHoaDAO.getAll();
        ThucUongHomeFragmentAdapter adapter = new ThucUongHomeFragmentAdapter(listHangHoa);
        recyclerViewThucUong.setAdapter(adapter);
    }



    @SuppressLint("SetTextI18n")
    private void welcomeUser() {
        NguoiDung nguoiDung = getNguoiDung();
        Bitmap bitmap = BitmapFactory.decodeByteArray(nguoiDung.getHinhAnh(),
                0,
                nguoiDung.getHinhAnh().length);

        // Gán dữ liệu cho view
        tvHi.setText("Hello, " + nguoiDung.getHoVaTen());
        civHinhAnh.setImageBitmap(bitmap);
    }

    private NguoiDung getNguoiDung() {
        // Lấy mã người dùng từ MainActivity thông qua hàm getKeyUser
        String maNguoiDung = Objects.requireNonNull(mainActivity).getKeyUser();
        // Lây đối tượng người dùng theo mã
        return nguoiDungDAO.getByMaNguoiDung(maNguoiDung);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.cardLoaiThucUong:
                // Mở màng hình quản lý loại hàng
                startActivity(new Intent(getContext(), LoaiThucUongActivity.class));
                (requireActivity()).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
                break;
            case R.id.cardThucUong:
                // Mở màng hình quản lý thức uống
                startActivity(new Intent(getContext(), ThucUongActivity.class));
                (requireActivity()).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
                break;
            case R.id.cardNhanVien:
                if (getNguoiDung().isAdmin()) {
                    // Người dùng có chức vụ ="Admin" -> Mở màng hình quản lý nhân viên
                    startActivity(new Intent(getContext(), NhanVienActivity.class));
                    (requireActivity()).overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
                } else {
                    // Người dung có chức vụ = "NhanVien"
                    MyToast.error(getContext(), "Chức năng dành cho Admin");
                }
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        welcomeUser();
        loadListThucUong();

    }
}