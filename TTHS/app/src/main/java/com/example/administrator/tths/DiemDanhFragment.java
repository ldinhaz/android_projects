package com.example.administrator.tths;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.ConnectDB;

/**
 * Created by Administrator on 14/11/2017.
 */

public class DiemDanhFragment extends Fragment {


    private ConnectDB con = null;

    private GridView gv_dd;
    private Button btn_loc;
    private EditText txt_mahs;
    private TextView tv_dsdd;
    private CheckBox cbMonth;
    private Spinner sltMonth;
    private Spinner sltYear;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.diem_danh, container, false);

        connectComponents(view);
        addItemToSpinner();
        setDataToGridView(gv_dd, "", 0, 0, 0);

        /* Bắt sự kiện cho nút Lọc */
        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Khi nhấn vào nút Lọc */
                // Lấy giá trị trong ô text mã học sinh
                String c = String.valueOf(txt_mahs.getText());
                // Kiểm tra xem có chọn vào check box tháng, năm không
                if(cbMonth.isChecked()){
                    // Lấy giá trị tại dropdow tháng
                    int m = Integer.parseInt(sltMonth.getSelectedItem().toString().split(" ")[1]);
                    // Lấy giá trị tại dropdow năm
                    int y = Integer.parseInt(sltYear.getSelectedItem().toString());
                    // Kiểm tra mã học sinh có rỗng hay không
                    if(!c.equals("")){
                        // Không rỗng thì gọi hàm theo type 3, lấy dữ liệu theo mã học sinh, tháng và năm
                        setDataToGridView(gv_dd, c, m, y, 3);
                    }
                    else {
                        // Rỗng thì gọi theo type 2, chỉ lấy dữ liệu theo tháng và năm
                        setDataToGridView(gv_dd,"", m, y, 2);
                    }
                }
                else{
                    // Không chọn vào check box tháng, năm
                    if(!c.equals("")) {
                        // Mã học sinh không rỗng thì gọi hàm theo type 1, lấy dữ liệu theo mà học sinh
                        setDataToGridView(gv_dd, c, 0, 0, 1);
                    }
                    else{
                        // Ngược lại, lấy tất cả dữ liệu theo tháng hiện tại
                        setDataToGridView(gv_dd, "", 0, 0, 0);
                    }
                }
            }
        });

        /* Bắt sự kiện cho check box tháng, năm */
        cbMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbMonth.isChecked()){
                    sltMonth.setVisibility(View.VISIBLE);
                    sltYear.setVisibility(View.VISIBLE);
                }else{
                    sltMonth.setVisibility(View.INVISIBLE);
                    sltYear.setVisibility(View.INVISIBLE);
                }
            }
        });

        return view;
    }


    private void setDataToGridView(GridView gridView, String a, int b, int c, int type){
        /*
        - Hàm này sẽ lấy dữ liệu từ database và gán vào cho GridView được truyền vào
        - Việc gán giá trị vào GridView sẽ phụ thuộc vào giá trị type
        - Giá trị type bao gồm các trường hợp sau:
            + Khi type = 0: Việc gán giá trị cho GridView không có điều kiện (tức lấy tất cả dữ liệu từ DB)
            + Khi type = 1: ----------------------------- phụ thuộc vào biến a (xem phần chi tiết biến)
            + Khi type = 2: ----------------------------- phụ thuộc vào biến b, c (----------------------)
            + Khi type = 3: ----------------------------- phụ thuộc vào cả a, b, c (----------------------)
        - Chi tiết biến a, b:
            + Biến a sẽ giũ giá trị của mã học sinh
            + Biến b sẽ giữ giá trị của tháng cần lấy dữ liệu
            + Biến c sẽ giữ giá trị của năm cần lấy dữ liệu
            + Biến b và c là liên quan nhau
         */

        /*--- Chi tiết code ---*/

        // Khởi tạo giá trị biến con với giá trị được tạo từ hàm getConnectDB() trong class MainActivity
        // Biến con dùng để truy cập các hàm trong class ConnectDB
        con = MainActivity.getConnectDB(); // Chi tiết hàm getConnectDB() xem ở class MainActivity

        // Khởi tạo giá trị chuỗi truy vấn  SQL, dùng để lấy dữ liệu từ DB
        String sql = null;

        // Khởi tạo biến lấy ngày hiện tại, dùng để lấy dữ liệu theo tháng, năm hiện tại
        Date date = new Date(); // Biến date sẽ lưu giữ thông tin ngày, tháng, năm hiện tại

        // Khởi tạo List để lưu các giá trị trả về từ CSDL để gán vào GridView
        // Đối tượng của List là tham số Map<K,V> với K là String, V là String
        List<Map<String, String>> data = null;

        // Khởi tạo biến ResultSet lưu dữ liệu lấy từ CSDL
        ResultSet rs = null;


        /* Xây dựng chuỗi truy vấn SQL */
        // Tạo giá trị chuỗi truy vấn
        sql = "SELECT diem_danh.mahs, hoten, ngay, buoi, diem_danh.ghichu " +
                "FROM hoc_sinh, diem_danh " +
                "WHERE hoc_sinh.mahs=diem_danh.mahs";
        // Tính toán các trường hợp type để xây dựng chuỗi truy vấn SQL
        switch(type){
            case 0:
                // Lấy tất cả dữ liệu tháng hiện tại
                sql += " AND MONTH(ngay)='" + (date.getMonth() + 1) + "'";
                break;
            case 1:
                // Lấy tất cả dữ liệu tháng hiện tại theo mã học sinh a
                sql += " AND MONTH(ngay)='" + (date.getMonth() + 1) + "' AND diem_danh.mahs='" + a + "'";
                break;
            case 2:
                // Lấy tất cả dữ liệu theo tháng b, năm c
                sql += " AND MONTH(ngay)='" + b + "' AND YEAR(ngay)='" + c + "'";
                break;
            case 3:
                // Lấy tất cả dữ liệu theo mã học sinh a, tháng b, năm c
                sql += " AND diem_danh.mahs='" + a + "' AND MONTH(ngay)='" + b + "' AND YEAR(ngay)='" + c + "'";
                break;
        }
        /* Kết thúc xây dựng chuỗi truy vấn */

        // Khởi tạo List lưu dữ liệu
        data = new ArrayList<Map<String, String>>();

        /* Gán dữ liệu vào List data */
        try{
            // Lấy dữ liệu từ CSDL theo câu truy vấn sql và lưu vào ResultSet
            rs = con.getDataFromDB(sql);

            // Thực hiện gán dữ liệu vào list
            while(rs.next()){
                // Tạo một đối tượng lưu dữ liệu 1 dòng trong CSDL
                Map<String, String> rows = new HashMap<String, String>();
                rows.put("A", rs.getString(1) + " - " + rs.getString(2));
                rows.put("B", "Ngày: " + rs.getDate(3) + "\tGiờ: " + rs.getTime(3));
                if(rs.getObject(4) != null){
                    rows.put("C", "Buổi: " + rs.getString(4));
                }else{
                    rows.put("C", "Buổi: ");
                }

                if(rs.getObject(5) != null){
                    rows.put("D", "Ghi chú: " + rs.getString(5));
                }else{
                    rows.put("D", "Ghi chú: ");
                }
                data.add(rows);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        /* Kết thúc gán dữ liệu vào List data */

        /* Gán dữ liệu vào GridView */
        // Tạo mảng lưu giá trị các khóa
        String[] keys = {"A", "B", "C", "D"};

        // Tạo mảng lưu các định dạng style cho các khóa
        int[] views = {R.id.txtMaSo_Ten, R.id.txtNgayDiemDanh, R.id.txtBuoi, R.id.txtGhiChu};

        // Tạo một simple adapter
        SimpleAdapter sa = new SimpleAdapter(getActivity(), data, R.layout.diem_danh_tag, keys, views);

        // Gán adapter cho GridView
        gridView.setAdapter(sa);
        /* Kết thúc gán dữ liệu GridView */

        /*--- Kết thúc code ---*/

    }
    private void connectComponents(View v){
        gv_dd = (GridView)v.findViewById(R.id.gvDiemDanh);
        btn_loc = (Button)v.findViewById(R.id.btnLocDD);
        txt_mahs = (EditText)v.findViewById(R.id.txtMaHS);
        tv_dsdd = (TextView)v.findViewById(R.id.tv_dsdd);
        cbMonth = (CheckBox)v.findViewById(R.id.cbMonth);
        sltMonth = (Spinner)v.findViewById(R.id.sltMonth);
        sltYear = (Spinner)v.findViewById(R.id.sltYear);
    }

    private void addItemToSpinner(){
        Date date = new Date();
        int cy = date.getYear() + 1900;
        int i;
        String[] m = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        String[] y = new String[10];
        for(i = 0; i < m.length; i++){
            m[i] = "Tháng " + m[i];
        }
        for(i = cy; i >= cy - 9; i--){
            y[cy - i] = String.valueOf(i);
        }
        ArrayAdapter a1 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, y);
        ArrayAdapter a2 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, m);

        sltYear.setAdapter(a1);
        sltMonth.setAdapter(a2);

    }
}
