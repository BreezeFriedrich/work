package com.ys.intelligentlock.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ys.intelligentlock.JsonData.GatewayIpData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/19.
 */
public class DataBaseUtil {

    public Context context;
    public MySQLiteOpenHelper helper;
    public final String DatabaseName="GatewayIpData";

    public DataBaseUtil(Context context){
        this.context=context;
        helper=new MySQLiteOpenHelper(context,DatabaseName,null,1);
    }

    public class MySQLiteOpenHelper extends SQLiteOpenHelper {
        public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql="create table if not exists gatewayIpData(id integer primary key,phoneNumber varchar(50),gatewayCode varchar(50),gatewayIp varchar(50))";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public void putGatewayIp(String phoneNumber,String gatewayCode,String gatewayIp){
        SQLiteDatabase database=helper.getWritableDatabase();
        String sql="insert into gatewayIpData (phoneNumber,gatewayCode,gatewayIp) values (?,?,?)";
        database.execSQL(sql,new String[]{phoneNumber,gatewayCode,gatewayIp});
        database.close();
    }

    public String getGatewayIp(String phoneNumber,String gatewayCode){
        SQLiteDatabase database=helper.getReadableDatabase();
        String sql="select gatewayIp from gatewayIpData where phoneNumber=? and gatewayCode=?";
        Cursor cursor=database.rawQuery(sql,new String[]{phoneNumber,gatewayCode});
        String gatewayIp=null;
        if(cursor.moveToFirst()){
            gatewayIp=cursor.getString(cursor.getColumnIndex("gatewayIp"));
        }
        cursor.close();
        database.close();
        return gatewayIp;
    }

    public List<GatewayIpData.GatewayIpInfo> getAllGatewayIp(String phoneNumber){
        SQLiteDatabase database=helper.getReadableDatabase();
        String sql="select * from gatewayIpData where phoneNumber=?";
        Cursor cursor=database.rawQuery(sql,new String[]{phoneNumber});
        List<Map<String,String>> list=new ArrayList<>();
        List<GatewayIpData.GatewayIpInfo> gatewayIpList=new ArrayList<>();
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String gatewayCode=cursor.getString(cursor.getColumnIndex("gatewayCode"));
                String gatewayIp=cursor.getString(cursor.getColumnIndex("gatewayIp"));
                Map<String,String> map=new HashMap<>();
                map.put("gatewayCode",gatewayCode);
                map.put("gatewayIp",gatewayIp);
                list.add(map);

                GatewayIpData.GatewayIpInfo gatewayIpInfo=new GatewayIpData().new GatewayIpInfo();
                gatewayIpInfo.gatewayCode=gatewayCode;
                gatewayIpInfo.gatewayIp=gatewayIp;
                gatewayIpList.add(gatewayIpInfo);
                cursor.moveToNext();
            }
        }
        cursor.close();
        database.close();
        return gatewayIpList;
    }

    public void putAllGatewayIp(String phoneNumber,List<GatewayIpData.GatewayIpInfo> gatewayIpList){
        SQLiteDatabase database=helper.getWritableDatabase();
        String sql="insert into gatewayIpData (phoneNumber,gatewayCode,gatewayIp) values (?,?,?)";
        database.execSQL(sql);
        for(GatewayIpData.GatewayIpInfo gatewayIpInfo:gatewayIpList){
            putGatewayIp(phoneNumber,gatewayIpInfo.gatewayCode,gatewayIpInfo.gatewayIp);
        }
        database.close();
    }

    public void deleteAllGatewayIp(){
        SQLiteDatabase database=helper.getWritableDatabase();
        String sql="delete from gatewayIpData";
        database.execSQL(sql);
        database.close();
    }
}
