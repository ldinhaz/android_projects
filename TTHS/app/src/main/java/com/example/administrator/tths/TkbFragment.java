package com.example.administrator.tths;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by Administrator on 14/11/2017.
 */

public class TkbFragment extends Fragment {


    private EditText edtId;
    private Button btnResult;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.schedule_first, container, false);
        context = view.getContext();
        initComponents(view);

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XemTKBActivity x = new XemTKBActivity();
                x.mahs = String.valueOf(edtId.getText());
                Intent intent = new Intent(context, x.getClass());
                startActivity(intent);
            }
        });
        return view;
    }


    private void initComponents(View view){
        edtId = (EditText)view.findViewById(R.id.edt_id_schedule);
        btnResult = (Button)view.findViewById(R.id.btn_res_schedule);
    }


}
