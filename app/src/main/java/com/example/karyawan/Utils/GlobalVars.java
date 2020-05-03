package com.example.karyawan.Utils;

import android.os.Environment;

import net.rehacktive.waspdb.WaspDb;
import net.rehacktive.waspdb.WaspFactory;
import net.rehacktive.waspdb.WaspHash;

import java.io.File;

public class GlobalVars {
//    public static final String BASE_IP = "http://10.0.2.2/perusahaan/index.php/";
    public static final String BASE_IP = "http://192.168.43.249/perusahaan/index.php/";
    public static final File BASE_DIR = Environment.getExternalStorageDirectory();
    public static final String EXTERNAL_DIR_FILES = "/perusahaan";
    public static final String PICTURES_DIR_FILES = "/Pictures";
    public static final String IMAGES_DIR = BASE_DIR + EXTERNAL_DIR_FILES + "/images/";

    public static final String DB_MASTER = "DB_MEETAP_APPS";
    public static final String DB_PASS = "1234567890";

//    public static String getDatabasePath(){
//        File file = new File(Environment.getExternalStorageDirectory() + EXTERNAL_DIR_FILES +"/db/" + Encryps.encrypt("POST"));
//        if(!file.exists())file.mkdirs();
//        return Environment.getExternalStorageDirectory() + EXTERNAL_DIR_FILES +"/db/" + Encryps.encrypt("POST");
//    }
//
//    public static String getDatabasePass(){
//        return Encryps.encrypt(DB_PASS);
//    }
//    public static WaspHash getTableHash(String tableName){
//        WaspDb db = WaspFactory.openOrCreateDatabase(GlobalVars.getDatabasePath(),
//                GlobalVars.DB_MASTER,
//                GlobalVars.getDatabasePass());
//        WaspHash returnedTable = db.openOrCreateHash(tableName);
//        return returnedTable;
//    }
}
