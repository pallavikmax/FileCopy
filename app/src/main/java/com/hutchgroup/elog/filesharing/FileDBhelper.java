package com.hutchgroup.elog.filesharing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public  class FileDBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FileDatabase.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_FILE_DETAIL = "FileDetail";

    private static final String TABLE_CREATE_FILE_DETAIL = "create table "
            + TABLE_FILE_DETAIL
            + "(DocumentId INTEGER , FileType INTEGER, FileName text, FileExtension text,FilePath text,ContentLength INTEGER,CreatedDate text,DownloadFg INTEGER, SyncFg INTEGER)";

    public FileDBhelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_FILE_DETAIL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
