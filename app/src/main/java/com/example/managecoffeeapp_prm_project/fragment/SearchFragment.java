package com.example.managecoffeeapp_prm_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.managecoffeeapp_prm_project.R;
import com.example.managecoffeeapp_prm_project.adapter.NguoiDungAdapter;
import com.example.managecoffeeapp_prm_project.adapter.SpinnerAdapterLoaiHang;
import com.example.managecoffeeapp_prm_project.adapter.ThucUongAdapter;
import com.example.managecoffeeapp_prm_project.dao.HangHoaDAO;
import com.example.managecoffeeapp_prm_project.dao.NguoiDungDAO;
import com.example.managecoffeeapp_prm_project.interfaces.ItemHangHoaOnClick;
import com.example.managecoffeeapp_prm_project.interfaces.ItemNguoiDungOnClick;
import com.example.managecoffeeapp_prm_project.model.HangHoa;
import com.example.managecoffeeapp_prm_project.model.NguoiDung;
import com.example.managecoffeeapp_prm_project.utils.MyToast;

public class SearchFragment extends Fragment {
    EditText edtSearch;
    Spinner spFill;
    RecyclerView listSearch;
    HangHoaDAO hangHoaDAO;
    NguoiDungDAO nguoiDungDAO;
    ImageView ivFilter;
    TextView tvNone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);

        hangHoaDAO = new HangHoaDAO(getContext());

        nguoiDungDAO = new NguoiDungDAO(getContext());

        loadSpinnerFillter();
        initLayouListSearch();


        // Spinner lọc loại hàng chọn Item
        spFill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listSearch.setVisibility(View.GONE); // Ẩn
                tvNone.setVisibility(View.VISIBLE); // Hiển thị
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Thực hiện tìm kiếm
                if (getTextSearch().isEmpty()) {
                    MyToast.error(getContext(), "Vui lòng nhập nội dung tìm kiếm");
                } else {
                    // Dữ liệu tìm kiếm
                    String dataSpinerFill = (String) spFill.getSelectedItem();
                    switch (dataSpinerFill) {
                        case "Chọn nội dung tìm kiếm....":
                            MyToast.error(getContext(), "Vui lòng chọn nội dung tìm kiếm");
                            listSearch.setVisibility(View.GONE);
                            tvNone.setVisibility(View.VISIBLE);
                            break;
                        case "Thức uống":
                            searchHangHoa();
                            break;
                        default:
                            searchNguoiDung();
                            break;
                    }
                }
            }
        });
        return view;
    }

    private void initView(View view) {
        edtSearch = view.findViewById(R.id.editSearch);
        spFill = view.findViewById(R.id.spFill);
        listSearch = view.findViewById(R.id.listSearch);
        ivFilter = view.findViewById(R.id.ivFilter);
        tvNone = view.findViewById(R.id.tvNone);
        tvNone.setVisibility(View.VISIBLE);
    }

    private void initLayouListSearch() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        listSearch.setLayoutManager(linearLayoutManager);
    }

    @NonNull
    private String getTextSearch() {
        return edtSearch.getText().toString().trim();
    }

    private void loadSpinnerFillter() {
        SpinnerAdapterLoaiHang spinnerAdapterLoaiHang = new SpinnerAdapterLoaiHang(getContext(), getListSpinerFillter());
        spFill.setAdapter(spinnerAdapterLoaiHang);
    }

    @NonNull
    private ArrayList<String> getListSpinerFillter() {
        // Danh sách danh mục tìm kiếm
        ArrayList<String> list = new ArrayList<>();
        list.add("Chọn nội dung tìm kiếm....");
        list.add("Thức uống");
        list.add("Nhân viên");

        return list;
    }

    private void searchNguoiDung() {
        // Lấy danh sách theo nội dùng tìm kiếm
        ArrayList<NguoiDung> listNguoiDung = new ArrayList<>();
        for (NguoiDung nguoiDung : nguoiDungDAO.getAllPositionNhanVien()) {
            if (String.valueOf(nguoiDung.getHoVaTen()).contains(getTextSearch())) {
                listNguoiDung.add(nguoiDung);
            }
        }

        // Load dữ liệu cho danh sách người dùng
        NguoiDungAdapter nguoiDungAdapter = new NguoiDungAdapter(listNguoiDung);
        listSearch.setAdapter(nguoiDungAdapter);
        /*
        * Kiểm tra số lượng danh sách người dùng
        * Nếu số lượng danh sách = 0: Hiện thị tvNone ẩn danh sách
        * Ngược lại số lượng danh sách > 0: Hiển thị danh sách, ẩn tvNone
        * */
        if (listNguoiDung.size() == 0) {
            listSearch.setVisibility(View.GONE);
            tvNone.setVisibility(View.VISIBLE);
        } else {
            listSearch.setVisibility(View.VISIBLE);
            tvNone.setVisibility(View.GONE);
        }
    }

    private void searchHangHoa() {
        // Lấy danh sách theo nội dùng tìm kiếm
        ArrayList<HangHoa> listHangHoa = new ArrayList<>();
        for (HangHoa hangHoa : hangHoaDAO.getAll()) {
            if (String.valueOf(hangHoa.getTenHangHoa()).contains(getTextSearch())) {
                listHangHoa.add(hangHoa);
            }
        }
        // Load danh sách thức uống
        ThucUongAdapter thucUongAdapter = new ThucUongAdapter(listHangHoa);
        /*
         * Kiểm tra số lượng danh sách hàng hoá
         * Nếu số lượng danh sách = 0: Hiện thị tvNone ẩn danh sách
         * Ngược lại số lượng danh sách > 0: Hiển thị danh sách, ẩn tvNone
         * */
        if (listHangHoa.size() == 0) {
            listSearch.setVisibility(View.GONE);
            tvNone.setVisibility(View.VISIBLE);
        } else {
            listSearch.setVisibility(View.VISIBLE);
            tvNone.setVisibility(View.GONE);
        }
        listSearch.setAdapter(thucUongAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reset form
        edtSearch.setText("");
        listSearch.setVisibility(View.GONE);
        tvNone.setVisibility(View.VISIBLE);
    }
}