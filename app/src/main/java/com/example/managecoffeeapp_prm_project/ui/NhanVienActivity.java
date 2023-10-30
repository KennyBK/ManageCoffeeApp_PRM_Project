package com.example.managecoffeeapp_prm_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managecoffeeapp_prm_project.R;
import com.example.managecoffeeapp_prm_project.adapter.NguoiDungAdapter;
import com.example.managecoffeeapp_prm_project.dao.NguoiDungDAO;
import com.example.managecoffeeapp_prm_project.interfaces.ItemNguoiDungOnClick;
import com.example.managecoffeeapp_prm_project.model.NguoiDung;

public class NhanVienActivity extends AppCompatActivity {
    public static final String MA_NGUOI_DUNG = "maNguoiDung";
    Toolbar toolBar;
    RecyclerView recyclerViewNhanVien;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        initToolBar();
        initView();
        nguoiDungDAO = new NguoiDungDAO(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loadData();
    }

    private void initView() {
        recyclerViewNhanVien = findViewById(R.id.recyclerViewNhanVien);
    }

    private void initToolBar() {
        toolBar = findViewById(R.id.toolbarNhanVien);
        setSupportActionBar(toolBar);
    }

    private void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NhanVienActivity.this, RecyclerView.VERTICAL, false);
        recyclerViewNhanVien.setLayoutManager(linearLayoutManager);
        NguoiDungAdapter nguoiDungAdapter = new NguoiDungAdapter(nguoiDungDAO.getAllPositionNhanVien(), new ItemNguoiDungOnClick() {
            @Override
            public void itemOclick(View view, NguoiDung nguoiDung) {
                PopupMenu popup = new PopupMenu(NhanVienActivity.this, view);
                popup.getMenuInflater()
                        .inflate(R.menu.menu_more_nhan_vien, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_chitet:
                                openChiTietNhanVienActivity(nguoiDung);
                                break;
                        }
                        return true;
                    }
                });
            }
        });
        recyclerViewNhanVien.setAdapter(nguoiDungAdapter);
    }

    private void openChiTietNhanVienActivity(NguoiDung nguoiDung) {
        Intent intent = new Intent(NhanVienActivity.this, ChiTietNhanVienActivity.class);
        intent.putExtra(MA_NGUOI_DUNG, nguoiDung.getMaNguoiDung());
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            startActivity(new Intent(NhanVienActivity.this, ThemNhanVienActivity.class));
            overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
