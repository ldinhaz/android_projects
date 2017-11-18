package com.example.administrator.tths;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Administrator on 14/11/2017.
 */

public class ThongBaoFragment extends Fragment {


    private ConnectDB con = null;

    private ListView lvThongBao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.thong_bao, container, false);

        initComponents(view);

        loadThongBao();

        return view;
    }

    private void initComponents(View v){
        con = MainActivity.getConnectDB();
        lvThongBao = (ListView)v.findViewById(R.id.lvThongBao);
    }

    private void loadThongBao(){
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

        SimpleAdapter a = new SimpleAdapter(this.getActivity(), data, R.layout.tb_tag, from, views);
        lvThongBao.setAdapter(a);
    }
}
