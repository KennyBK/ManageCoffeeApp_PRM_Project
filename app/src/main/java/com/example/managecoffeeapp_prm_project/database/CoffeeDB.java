package com.example.managecoffeeapp_prm_project.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.managecoffeeapp_prm_project.R;
import com.example.managecoffeeapp_prm_project.utils.ImageToByte;

public class CoffeeDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "Coffee";
    public static final int DB_VERSION = 1;
    public Context context;

    public static final String TABLE_NGUOIDUNG = "CREATE TABLE NGUOIDUNG(" +
            "maNguoiDung TEXT PRIMARY KEY," +
            "hoVaTen TEXT NOT NULL," +
            "hinhAnh BLOB," +
            "ngaySinh DATE NOT NULL," +
            "email TEXT NOT NULL," +
            "chucVu TEXT NOT NULL," +
            "gioiTinh TEXT NOT NULL," +
            "matKhau TEXT NOT NULL)";

    public static final String TABLE_LOAIHANG = "CREATE TABLE LOAIHANG(" +
            "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
            "hinhAnh BLOB," +
            "tenLoai TEXT NOT NULL)";

    public static final String TABLE_HANGHOA = "CREATE TABLE HANGHOA(" +
            "maHangHoa INTEGER PRIMARY KEY AUTOINCREMENT," +
            "hinhAnh BLOB," +
            "tenHangHoa TEXT NOT NULL," +
            "giaTien INTEGER NOT NULL, " +
            "maLoai INTEGER REFERENCES LOAIHANG(maLoai)," +
            "trangThai INTEGER NOT NULL)";

    public CoffeeDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // TẠO TABLE NGƯỜI DUNG
        sqLiteDatabase.execSQL(TABLE_NGUOIDUNG);
        // TẠO TABLE LOẠI HÀNG
        sqLiteDatabase.execSQL(TABLE_LOAIHANG);
        // TẠO TABLE HÀNG HÓA
        sqLiteDatabase.execSQL(TABLE_HANGHOA);


        String insertNguoiDung = "INSERT INTO NGUOIDUNG(maNguoiDung, hoVaTen, hinhAnh, ngaySinh, email, chucVu, gioiTinh, matKhau) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(insertNguoiDung, new Object[]{"admin", "ADMIN", ImageToByte.drawableToByte(context, R.drawable.avata_2), "2003-01-01", "admin@gmail.com", "Admin", "Nam", 1212});
        sqLiteDatabase.execSQL(insertNguoiDung, new Object[]{"nhanvien", "Nguyễn Văn Duẩn", ImageToByte.drawableToByte(context, R.drawable.images_2), "2003-01-01", "duannv@gmail.com", "NhanVien", "Nam", 12345});
        sqLiteDatabase.execSQL(insertNguoiDung, new Object[]{"ND2", "Nguyễn Tiến Đạt", ImageToByte.drawableToByte(context, R.drawable.avatar_3), "2003-01-01", "datnt@gmail.com", "NhanVien", "Nam", 1234});
        sqLiteDatabase.execSQL(insertNguoiDung, new Object[]{"ND3", "Nguyễn Văn Dũng", ImageToByte.drawableToByte(context, R.drawable.pngtree_user_avatar_boy_png_image_4693645), "2003-01-01", "dungnv@gmail.com", "NhanVien", "Nam", 123321});

        String insertLoaiHang = "INSERT INTO LOAIHANG(hinhAnh, tenLoai) VALUES(?, ?)";
        sqLiteDatabase.execSQL(insertLoaiHang, new Object[]{ImageToByte.drawableToByte(context, R.drawable.sample_data_loai_hang_caphe), "Cà phê"});
        sqLiteDatabase.execSQL(insertLoaiHang, new Object[]{ImageToByte.drawableToByte(context, R.drawable.sample_data_loai_hang_nuocep), "Nước ép"});
        sqLiteDatabase.execSQL(insertLoaiHang, new Object[]{ImageToByte.drawableToByte(context, R.drawable.sample_data_loai_hang_soda), "Soda"});
        sqLiteDatabase.execSQL(insertLoaiHang, new Object[]{ImageToByte.drawableToByte(context, R.drawable.sample_data_loai_hang_trasua), "Trà sữa"});

        String insertHangHoa = "INSERT INTO HANGHOA(tenHangHoa, hinhAnh, giaTien, maLoai, trangThai) VALUES(?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Cà phê máy", ImageToByte.drawableToByte(context, R.drawable.sample_data_hanghoa_cfmay), 15000, 1, 1});
        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Cà phê phin", ImageToByte.drawableToByte(context, R.drawable.sample_data_hanghoa_cfphin), 12000, 1, 1});
        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Cà phê sài gòn", ImageToByte.drawableToByte(context, R.drawable.sample_data_hanghoa_cfsaigon), 20000, 1, 1});
        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Cà phê bọt biển", ImageToByte.drawableToByte(context, R.drawable.sample_data_hanghoa_cfbotbien), 25000, 1, 0});

        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Nước ép cam", ImageToByte.drawableToByte(context, R.drawable.sample_data_hanghoa_epcam), 27000, 2, 1});
        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Nước ép dứa", ImageToByte.drawableToByte(context, R.drawable.sample_data_hanghoa_epdua), 25000, 2, 0});
        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Nước ép ổi", ImageToByte.drawableToByte(context, R.drawable.sample_data_hanghoa_epoi), 23000, 2, 0});
        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Chanh đá", ImageToByte.drawableToByte(context, R.drawable.sample_data_hanghoa_chanhda), 20000, 2, 1});

        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Soda bạc hà", ImageToByte.drawableToByte(context, R.drawable.sample_data_loai_hang_soda_bacha), 33000, 3, 1});
        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Soda việt quất", ImageToByte.drawableToByte(context, R.drawable.sample_data_loai_hang_soda_vietquat), 35000, 3, 0});
        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Soda trái cây", ImageToByte.drawableToByte(context, R.drawable.sample_data_loai_hang_soda_traicay), 35000, 3, 1});

        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Trà sữa khoai môn", ImageToByte.drawableToByte(context, R.drawable.sample_data_hanghoa_trasuamon), 23000, 4, 1});
        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Trà sữa thái xanh", ImageToByte.drawableToByte(context, R.drawable.sample_data_hanghoa_trasuathaixanh), 24000, 4, 1});
        sqLiteDatabase.execSQL(insertHangHoa, new Object[]{"Trà sữa truyền thống", ImageToByte.drawableToByte(context, R.drawable.sample_data_hanghoa_trasuatruyenthong), 25000, 4, 1});

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LOAIHANG");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS HANGHOA");

            onCreate(sqLiteDatabase);
        }

    }
}
