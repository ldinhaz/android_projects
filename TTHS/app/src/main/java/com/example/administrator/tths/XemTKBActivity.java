package com.example.administrator.tths;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.ConnectDB;

/**
 * Created by Administrator on 12/11/2017.
 */

public class XemTKBActivity extends AppCompatActivity {


    private GridView grvTKB;
    private ConnectDB con = null;
    private TextView tvTkbTitle;
    private TextView tvTenHsTkb;


    public static String mahs = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.thoi_khoa_bieu);


        connectComponent();
        initDataGridView();
        if(getClassIdByStudentId(mahs).equals("")){
            tvTenHsTkb.setTextColor(Color.RED);
            tvTenHsTkb.setText("Học sinh có mã '" + mahs + "' không tồn tại");
        }
        else{
            loadThoiKhoaBieu(getClassIdByStudentId(mahs));
            tvTkbTitle.setText(tvTkbTitle.getText() + " - LỚP: " + getClassIdByStudentId(mahs));
            tvTenHsTkb.setTextColor(Color.BLACK);
            tvTenHsTkb.setText(getStudentNameById(mahs));
        }

    }


    private void connectComponent(){
        con = MainActivity.getConnectDB();
        grvTKB = (GridView)findViewById(R.id.gvTKB);
        tvTkbTitle = (TextView)findViewById(R.id.tvTKB_title);
        tvTenHsTkb = (TextView)findViewById(R.id.tvTenHsTkb);
    }

    private void initDataGridView(){
        grvTKB.setAdapter(null);
    }




    private String getClassIdByStudentId(String s_id){
        String c_id = "";
        try{
            String sql = "SELECT malop FROM hoc_sinh WHERE mahs='" + s_id + "'";
            ResultSet rs = con.getDataFromDB(sql);
            while (rs.next()){
                c_id = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return c_id;
    }

    private String getStudentNameById(String s_id){
        String s_name = "";
        try{
            String sql = "SELECT hoten FROM hoc_sinh WHERE mahs='" + s_id + "'";
            ResultSet rs = con.getDataFromDB(sql);
            while (rs.next()){
                s_name = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        if(!s_name.equals("")){
            s_name = "Tên học sinh: " + s_name;
        }
        return s_name;
    }

    private void loadThoiKhoaBieu(String malop){

        String sql = null;

        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();

        try{

            int dates = 2;
            ResultSet rs = null;
            while(dates <= 7){
                sql = "SELECT thu, tiet, ten_mon, ma_lop FROM thoi_khoa_bieu tkb,mon_hoc mh WHERE tkb.ma_mon=mh.ma_mon AND tkb.thu='" + dates + "' AND ma_lop='" + malop + "'";
                rs = con.getDataFromDB(sql);
                Map<String, String> datac = new HashMap<String, String>();
                datac.put("a", "Thứ " + String.valueOf(dates));
                String[] t = {"b","c","d","e","f"};
                while(rs.next()){
                    datac.put(t[rs.getInt(2) - 1], "" + rs.getString(3));
                }

                data.add(datac);
                dates++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String[] from = {"a", "b", "c", "d", "e", "f"};
        int[] views = {R.id.txtThu, R.id.txtSapTiet1, R.id.txtSapTiet2, R.id.txtSapTiet3, R.id.txtSapTiet4, R.id.txtSapTiet5};

        SimpleAdapter x = new SimpleAdapter(this, data, R.layout.tkb_tag, from, views);

        grvTKB.setAdapter(x);

    }
}
