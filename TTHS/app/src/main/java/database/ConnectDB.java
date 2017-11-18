package database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by Administrator on 10/11/2017.
 */

public class ConnectDB {

    private String hostName;// = "192.168.0.100";
    private String className = "net.sourceforge.jtds.jdbc.Driver";
    private String dbName;// = "Diem_Danh_HS";
    private String user;// = "leo";
    private String password;// = "123123";

    public ConnectDB(String a, String b, String c, String d){
        this.hostName = a;
        this.dbName = b;
        this.user = c;
        this.password = d;
    }


    @SuppressLint("NewApi")
    public Connection createConnection(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection con = null;
        String connectionString = null;

        try {
            Class.forName(className);
            connectionString = "jdbc:jtds:sqlserver://" + hostName + ";"
                    + "databaseName=" + dbName + ";user=" + user + ";password="
                    + password + ";";
            con = DriverManager.getConnection(connectionString);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }


    public ResultSet getDataFromDB(String sql){
        Connection con = createConnection();
        Statement stm = null;
        ResultSet rs = null;

        try{
            stm = con.createStatement();
            rs = stm.executeQuery(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
}
