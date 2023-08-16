package com.example.kampusku.DatabaseHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "kampus.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ADMIN = "admin";
    private static final String TABLE_STUDENTS = "mahasiswa";

    // Column names for admin
    private static final String COLUMN_NAMA_ADMIN = "nama";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // Column names for students
    private static final String COLUMN_NIM = "nim";
    private static final String COLUMN_NAMA = "nama";
    private static final String COLUMN_TGL_LAHIR = "tgl_lahir";
    private static final String COLUMN_JENIS_KELAMIN = "jk";
    private static final String COLUMN_ALAMAT = "alamat";

    // Create Table For Admin
    private static final String CREATE_TABLE_ADMIN = "CREATE TABLE " + TABLE_ADMIN +
            "(" +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_NAMA_ADMIN + " TEXT NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL" +
            ");";

    // Create Table For Students
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS +
            "(" +
                COLUMN_NIM + " TEXT PRIMARY KEY, " +
                COLUMN_NAMA + " TEXT NOT NULL, " +
                COLUMN_TGL_LAHIR + " DATE NOT NULL, " +
                COLUMN_JENIS_KELAMIN + " TEXT NOT NULL, " +
                COLUMN_ALAMAT + " TEXT NOT NULL" +
            ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ADMIN);
        sqLiteDatabase.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(sqLiteDatabase);
    }

    // Method for LOGIN
    public Boolean insertDataLogin(String nama,String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAMA, nama);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        long result = MyDB.insert(TABLE_ADMIN, null, cv);

        return result != -1;
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM admin WHERE email = ?", new String[] {email});

        return cursor.getCount() > 0;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM admin WHERE email = ? AND password = ?", new String[] {email, password});

        return cursor.getCount() > 0;
    }

    @SuppressLint("Range")
    public String getUserName(String email) {
        String userName = null;
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT nama FROM admin WHERE email = ?", new String[] {email});

        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndex("nama"));
        }

        cursor.close();
        return userName;
    }

    // Method For Master CRUD Student
    public void addStudent(String nim, String nama, String tgl_lahir, String jenis_kelamin, String alamat) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NIM, nim);
        cv.put(COLUMN_NAMA, nama);
        cv.put(COLUMN_TGL_LAHIR, tgl_lahir);
        cv.put(COLUMN_JENIS_KELAMIN, jenis_kelamin);
        cv.put(COLUMN_ALAMAT, alamat);
        long result = MyDB.insert(TABLE_STUDENTS, null, cv);

        if ( result == -1) {
            Toast.makeText(context, "Gagal Menambahkan Data Mahasiswa!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Berhasil Menambahkan Data Mahasiswa!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase MyDB = this.getReadableDatabase();

        Cursor cursor = null;
        if (MyDB != null) {
            cursor = MyDB.rawQuery(query, null);
        }

        return cursor;
    }

    public void updateStudent(String nim, String nama, String tgl_lahir, String jenis_kelamin, String alamat) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAMA, nama);
        cv.put(COLUMN_TGL_LAHIR, tgl_lahir);
        cv.put(COLUMN_JENIS_KELAMIN, jenis_kelamin);
        cv.put(COLUMN_ALAMAT, alamat);
        long result = MyDB.update(TABLE_STUDENTS, cv, "nim = ?", new String[] {nim});

        if ( result == -1) {
            Toast.makeText(context, "Gagal Mengubah Data Mahasiswa!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Berhasil Mengubah Data Mahasiswa!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteStudent(String nim) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        long result = MyDB.delete(TABLE_STUDENTS, "nim = ?", new String[] {nim});

        if ( result == -1) {
            Toast.makeText(context, "Gagal Menghapus Data Mahasiswa!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Berhasil Menghapus Data Mahasiswa!", Toast.LENGTH_SHORT).show();
        }
    }
}
