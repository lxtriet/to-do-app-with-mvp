package com.hcmute.trietthao.yourtime.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hcmute.trietthao.yourtime.model.modelOffline.NhomCVMO;
import com.hcmute.trietthao.yourtime.service.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DBNhomCVSQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "yt-10-11.sqlite";  // tên database
    private static final String DB_PATH_SUFFIX = "/databases/";
    static Context context;
    private static Service mService;
    public  interface WorkgroupListener{
        void getListWorkGroup(List<NhomCVMO> nhomCVMOs);
    }
    private WorkgroupListener workgroupListener;
    ArrayList<NhomCVMO> nhomCVMOs = new ArrayList<>();

    // Định nghĩa database
    public DBNhomCVSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    // Truy vấn trả về list category
    public ArrayList<NhomCVMO> getListWorkGroup(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<NhomCVMO> list=new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM NhomCV", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            NhomCVMO ncv = new NhomCVMO();
            ncv.setIdNhom(cursor.getInt(0));
            ncv.setTenNhom(cursor.getString(1));

            cursor.moveToNext();
            list.add(ncv);
        }
        cursor.close();
        db.close();
        return list;
    }

// Lấy cơ sở dữ liệu từ Asset
    public void CopyDataBaseFromAsset() throws IOException {
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        String outFileName = getDatabasePath();

        File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    // Lấy đường dẫn database
    private static String getDatabasePath() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }
    // Mở database
    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = context.getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
