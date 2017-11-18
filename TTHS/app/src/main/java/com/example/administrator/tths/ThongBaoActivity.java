package com.example.administrator.tths;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.ConnectDB;

/**
 * Created by Administrator on 13/11/2017.
 */

public class ThongBaoActivity extends AppCompatActivity {

    private ConnectDB con = null;

    private ListView lvThongBao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thong_bao);

        connectComponents();

        setDataToListView();
    }


    private void setDataToListView(){



        String sql = "SELECT * FROM thong_bao";

        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();

        try{
            ResultSet rs = con.getDataFromDB(sql);

            while (rs.next()){
                Map<String, String> datac = new HashMap<String,String>();
                datac.put("title", rs.getString(2));
                datac.put("dpost", "Ngày đăng: " + rs.getDate(4));

                data.add(datac);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        String[] from = {"title", "dpost"};
        int[] views = {R.id.txtTenThongBao, R.id.txtNgayDangTB};

        SimpleAdapter sa = new SimpleAdapter(this, data, R.layout.tb_tag, from, views);

        lvThongBao.setAdapter(sa);

    }



    private void connectComponents(){
        con = MainActivity.getConnectDB();
        lvThongBao = (ListView)findViewById(R.id.lvThongBao);
    }
}
