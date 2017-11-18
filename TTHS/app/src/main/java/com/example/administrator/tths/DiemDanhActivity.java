package com.example.administrator.tths;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
 * Created by Administrator on 11/11/2017.
 */

public class DiemDanhActivity extends AppCompatActivity {

    private ConnectDB con = null;

    private GridView gv_dd;
    private Button btn_loc;
    private EditText txt_mahs;
    private TextView tv_dsdd;
    private CheckBox cbMonth;
    private Spinner sltMonth;
    private Spinner sltYear;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diem_danh);

        connectComponents();
        addItemToSpinner();
        setDataToGridView1(gv_dd);

        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = String.valueOf(txt_mahs.getText());
                if(cbMonth.isChecked()){
                    int m = Integer.parseInt(sltMonth.getSelectedItem().toString().split(" ")[1]);
                    int y = Integer.parseInt(sltYear.getSelectedItem().toString());
                    if(!c.equals("")){
                        setDataToGridView3(gv_dd, c, m, y);
                    }
                    else {
                        setDataToGridView4(gv_dd, m, y);
                    }
                }
                else{
                    if(!c.equals("")) {
                        setDataToGridView2(gv_dd, c);
                    }
                    else{
                        setDataToGridView1(gv_dd);
                    }
                }
            }
        });

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


    }

    private void setDataToGridView1(GridView gv){
        con = MainActivity.getConnectDB();
        String sql = "SELECT diem_danh.mahs, hoten, ngay, buoi, diem_danh.ghichu FROM hoc_sinh, diem_danh WHERE hoc_sinh.mahs=diem_danh.mahs";


        Date date = new Date();
        tv_dsdd.setText("DANH SÁCH ĐIỂM DANH THÁNG: " + (date.getMonth() + 1));
        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();
        try{
            ResultSet rs = con.getDataFromDB(sql);

            while(rs.next()){
                Map<String, String> dataC = new HashMap<String, String>();
                dataC.put("A", rs.getString(1) + " - " + rs.getString(2));
                dataC.put("B", "Ngày: " + rs.getDate(3) + "\tGiờ: " + rs.getTime(3));
                if(rs.getObject(4) != null){
                    dataC.put("C", "Buổi: " + rs.getString(4));
                }
                else dataC.put("C", "Buổi: ");
                if(rs.getObject(5) != null)
                    dataC.put("D", "Ghi chú: " + rs.getString(5));
                else dataC.put("D", "Ghi chú: ");
                if(rs.getDate(3).getMonth() == date.getMonth())
                    data.add(dataC);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String[] from = {"A", "B", "C", "D"};
        int[] views = {R.id.txtMaSo_Ten, R.id.txtNgayDiemDanh, R.id.txtBuoi, R.id.txtGhiChu};

        SimpleAdapter x = new SimpleAdapter(this, data, R.layout.diem_danh_tag, from, views);
        gv.setAdapter(x);
    }

    private void setDataToGridView2(GridView gv, String ma_hs){
        con = MainActivity.getConnectDB();
        String sql = "SELECT diem_danh.mahs, hoten, ngay, buoi, diem_danh.ghichu FROM hoc_sinh, diem_danh WHERE hoc_sinh.mahs=diem_danh.mahs AND diem_danh.mahs='" + ma_hs + "'";

        Date date = new Date();
        tv_dsdd.setText("DANH SÁCH ĐIỂM DANH THÁNG: " + (date.getMonth() + 1));
        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();
        try{
            ResultSet rs = con.getDataFromDB(sql);

            while(rs.next()){
                Map<String, String> dataC = new HashMap<String, String>();
                dataC.put("A", rs.getString(1) + " - " + rs.getString(2));
                dataC.put("B", "Ngày: " + rs.getDate(3) + "\tGiờ: " + rs.getTime(3));
                if(rs.getObject(4) != null){
                    dataC.put("C", "Buổi: " + rs.getString(4));
                }
                else dataC.put("C", "Buổi: ");
                if(rs.getObject(5) != null)
                    dataC.put("D", "Ghi chú: " + rs.getString(5));
                else dataC.put("D", "Ghi chú: ");
                if(rs.getDate(3).getMonth() == date.getMonth())
                    data.add(dataC);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String[] from = {"A", "B", "C", "D"};
        int[] views = {R.id.txtMaSo_Ten, R.id.txtNgayDiemDanh, R.id.txtBuoi, R.id.txtGhiChu};

        SimpleAdapter x = new SimpleAdapter(this, data, R.layout.diem_danh_tag, from, views);
        gv.setAdapter(x);
    }

    private void setDataToGridView3(GridView gv, String ma_hs, int m, int y){
        con = MainActivity.getConnectDB();
        String sql = "SELECT diem_danh.mahs, hoten, ngay, buoi, diem_danh.ghichu FROM hoc_sinh, diem_danh WHERE hoc_sinh.mahs=diem_danh.mahs AND diem_danh.mahs='" + ma_hs + "'";

        Date date = new Date();
        tv_dsdd.setText("DANH SÁCH ĐIỂM DANH THÁNG: " + m);
        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();
        try{
            ResultSet rs = con.getDataFromDB(sql);

            while(rs.next()){
                Map<String, String> dataC = new HashMap<String, String>();
                dataC.put("A", rs.getString(1) + " - " + rs.getString(2));
                dataC.put("B", "Ngày: " + rs.getDate(3) + "\tGiờ: " + rs.getTime(3));
                if(rs.getObject(4) != null){
                    dataC.put("C", "Buổi: " + rs.getString(4));
                }
                else dataC.put("C", "Buổi: ");
                if(rs.getObject(5) != null)
                    dataC.put("D", "Ghi chú: " + rs.getString(5));
                else dataC.put("D", "Ghi chú: ");
                if(rs.getDate(3).getMonth() == m - 1 && (rs.getDate(3).getYear() + 1900) == y)
                    data.add(dataC);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String[] from = {"A", "B", "C", "D"};
        int[] views = {R.id.txtMaSo_Ten, R.id.txtNgayDiemDanh, R.id.txtBuoi, R.id.txtGhiChu};

        SimpleAdapter x = new SimpleAdapter(this, data, R.layout.diem_danh_tag, from, views);
        gv.setAdapter(x);
    }

    private void setDataToGridView4(GridView gv, int m, int y){
        con = MainActivity.getConnectDB();
        String sql = "SELECT diem_danh.mahs, hoten, ngay, buoi, diem_danh.ghichu FROM hoc_sinh, diem_danh WHERE hoc_sinh.mahs=diem_danh.mahs";

        Date date = new Date();
        tv_dsdd.setText("DANH SÁCH ĐIỂM DANH THÁNG: " + m);
        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();
        try{
            ResultSet rs = con.getDataFromDB(sql);

            while(rs.next()){
                Map<String, String> dataC = new HashMap<String, String>();
                dataC.put("A", rs.getString(1) + " - " + rs.getString(2));
                dataC.put("B", "Ngày: " + rs.getDate(3) + "\tGiờ: " + rs.getTime(3));
                if(rs.getObject(4) != null){
                    dataC.put("C", "Buổi: " + rs.getString(4));
                }
                else dataC.put("C", "Buổi: ");
                if(rs.getObject(5) != null)
                    dataC.put("D", "Ghi chú: " + rs.getString(5));
                else dataC.put("D", "Ghi chú: ");
                if(rs.getDate(3).getMonth() == m - 1 && (rs.getDate(3).getYear() + 1900) == y)
                    data.add(dataC);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String[] from = {"A", "B", "C", "D"};
        int[] views = {R.id.txtMaSo_Ten, R.id.txtNgayDiemDanh, R.id.txtBuoi, R.id.txtGhiChu};

        SimpleAdapter x = new SimpleAdapter(this, data, R.layout.diem_danh_tag, from, views);
        gv.setAdapter(x);
    }



    private void connectComponents(){
        gv_dd = (GridView)findViewById(R.id.gvDiemDanh);
        btn_loc = (Button)findViewById(R.id.btnLocDD);
        txt_mahs = (EditText)findViewById(R.id.txtMaHS);
        tv_dsdd = (TextView)findViewById(R.id.tv_dsdd);
        cbMonth = (CheckBox)findViewById(R.id.cbMonth);
        sltMonth = (Spinner)findViewById(R.id.sltMonth);
        sltYear = (Spinner)findViewById(R.id.sltYear);
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
        ArrayAdapter a1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, y);
        ArrayAdapter a2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, m);

        sltYear.setAdapter(a1);
        sltMonth.setAdapter(a2);

    }
}
