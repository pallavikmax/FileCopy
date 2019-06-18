package com.hutchgroup.elog.filesharing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FileDB {


    public static boolean Save(String fileExtension, String fileType, String fileName, String path,
                               int fileContentLength, int id, Context context) {
        FileBean bean = new FileBean();
        bean.setFileExtension(fileExtension);
        bean.setFileType(fileType);
        bean.setFileName(fileName);
        bean.setPath(path);
        bean.setFileContentLength(fileContentLength);

        return Save(bean, context);
    }

    // Created By: Deepak Sharma
    // Created Date: 21 Nov 2016 4:31 PM
    // get current system date time and format it using User TimeZoneId
    public static String getCurrentDateTime() {
        Date d = new Date();
        String datetime = "";
        try {
            SimpleDateFormat sdfLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            datetime = sdfLocal.format(d);

           /* if (datetime.length() > 19) {
                 datetime = correctDate(datetime);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datetime;
    }

    // Created By: Pallavi Wattamwar
    // Created Date: 11 June 2019
    // purpose : save database
    private static boolean Save(FileBean bean, Context context) {
        boolean status = true;
        FileDBhelper helper = null;
        SQLiteDatabase database = null;
        try {
            helper = new FileDBhelper(context);
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("FileType", bean.getFileType());
            values.put("FileName", bean.getFileName());
            values.put("FileExtension", bean.getFileExtension());
            values.put("ContentLength", bean.getFileContentLength());
            values.put("CreatedDate", getCurrentDateTime());
            values.put("FilePath", bean.getPath());
            values.put("SyncFg", 0);
            values.put("DownloadFg",0);
            database.insert(FileDBhelper.TABLE_FILE_DETAIL,
                    "DocumentId", values);

        } catch (Exception e) {
            status = false;
            e.printStackTrace();

        } finally {
            database.close();
            helper.close();
        }
        return status;

    }

    // Created By: Pallavi Wattamwar
    // Created Date: 11 June 2019
    // purpose : get file from server
    public static ArrayList<FileBean> getFileFromServer(Context context) {
        ArrayList<FileBean> fileList = new ArrayList<>();
        FileDBhelper helper = null;
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            helper = new FileDBhelper(context);
            database = helper.getReadableDatabase();
            cursor = database.rawQuery("select * from " +
                    FileDBhelper.TABLE_FILE_DETAIL + " where Syncfg=0 and DownloadFg=0 ", null);
            while (cursor.moveToNext()) {
                FileBean bean = new FileBean();
                bean.setId(cursor.getInt(cursor.getColumnIndex("DocumentId")));
                bean.setFileType(cursor.getString(cursor.getColumnIndex("FileType")));
                bean.setFileName(cursor.getString(cursor.getColumnIndex("FileName")));
                bean.setPath(cursor.getString(cursor.getColumnIndex("FilePath")));
                bean.setFileContentLength(cursor.getInt(cursor.getColumnIndex("ContentLength")));
                bean.setFileExtension(cursor.getString(cursor.getColumnIndex("FileExtension")));


                fileList.add(bean);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    // Created By: Pallavi Wattamwar
    // Created Date: 11 June 2019
    // purpose : get downloaded files from server
    public static ArrayList<FileBean> getDownloadedFiles(Context context) {
        ArrayList<FileBean> fileList = new ArrayList<>();
        FileDBhelper helper = null;
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            helper = new FileDBhelper(context);
            database = helper.getReadableDatabase();
            cursor = database.rawQuery("select * from " +
                    FileDBhelper.TABLE_FILE_DETAIL + " where DownloadFg=1  order by CreatedDate desc ", null);
            while (cursor.moveToNext()) {
                FileBean bean = new FileBean();
                bean.setId(cursor.getInt(cursor.getColumnIndex("DocumentId")));
                bean.setFileType(cursor.getString(cursor.getColumnIndex("FileType")));
                bean.setFileName(cursor.getString(cursor.getColumnIndex("FileName")));
                bean.setPath(cursor.getString(cursor.getColumnIndex("FilePath")));
                bean.setFileContentLength(cursor.getInt(cursor.getColumnIndex("ContentLength")));
                bean.setFileExtension(cursor.getString(cursor.getColumnIndex("FileExtension")));

                fileList.add(bean);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileList;
    }

    // Created By: Pallavi Wattamwar
    // Created Date: 3 June 2019
    // Purpose: Save File Detail
    public static void Save(ArrayList<FileBean> list, Context context) {
        FileDBhelper helper = null;
        SQLiteDatabase database = null;

        int tripInspectionId = 0;
        try {
            helper = new FileDBhelper(context);
            database = helper.getWritableDatabase();
            for (FileBean bean : list) {
                int id = 0;
                ContentValues values = new ContentValues();
                Cursor cursor = database.rawQuery("select DocumentId from " + FileDBhelper.TABLE_FILE_DETAIL + " where DocumentId=?"

                        ,  new String[]{bean.getId() + ""});
                if (cursor.moveToNext()) {
                    id = cursor.getInt(0);
                }
                values.put("DocumentId", bean.getId());
                values.put("FileType", bean.getFileType());
                values.put("FileName", bean.getFileName());
                values.put("FileExtension", bean.getFileExtension());
                values.put("ContentLength", bean.getFileContentLength());
                values.put("CreatedDate", getCurrentDateTime());
                values.put("FilePath", bean.getPath());
                values.put("SyncFg", 0);

                if (id == 0) {
                    values.put("DownloadFg",0);
                    database.insertOrThrow(FileDBhelper.TABLE_FILE_DETAIL,
                            "DocumentId", values);


                } /*else {

                    database.update(FileDBhelper.TABLE_FILE_DETAIL, values,
                            " DocumentId= ?", new String[]{bean.getId()
                                    + ""});
                }*/
            }

            Log.e(FileDB.class.getName(), "Saved " + tripInspectionId);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                database.close();
                helper.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static JSONArray getDownloadedFile(int documentId, Context context) {

        FileDBhelper helper = null;
        SQLiteDatabase database = null;
        Cursor cursor = null;
        JSONArray array = new JSONArray();
        int id = 0;
        try {
            helper = new FileDBhelper(context);
            database = helper.getReadableDatabase();

            cursor = database.rawQuery("select DocumentId from " + FileDBhelper.TABLE_FILE_DETAIL +
                            " where DocumentId=?"
                    , new String[]{documentId + ""});
            while (cursor.moveToNext()) {
                JSONObject obj = new JSONObject();

                obj.put("InspectionId", cursor.getInt(0));
                obj.put("Downloadfg", true);

                array.put(obj);

            }
        } catch (Exception exe) {
            exe.printStackTrace();

        } finally {
            try {
                cursor.close();
                database.close();
                helper.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return array;
    }


    // Created By: Pallavi Wattamwar
    // Created Date: 5 June 2019
    // Purpose: update syncfg
    public static void updateSyncFgDownloadedFile(Context context, int id) {
        FileDBhelper helper = null;
        SQLiteDatabase database = null;
        JSONArray array = new JSONArray();
        try {
            helper = new FileDBhelper(context);
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("SyncFg", 1);
            database.update(FileDBhelper.TABLE_FILE_DETAIL, values,
                    " DocumentId=?", new String[]{id + ""});

        } catch (Exception exe) {

            exe.printStackTrace();
        } finally {
            try {
                database.close();
                helper.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Created By: Pallavi Wattamwar
    // Created Date: 5 June 2019
    // Purpose: update downloaded file
    public static void updateDownloadedFile(Context context, int id) {
        FileDBhelper helper = null;
        SQLiteDatabase database = null;
        JSONArray array = new JSONArray();
        try {
            helper = new FileDBhelper(context);
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Downloadfg", 1);
            values.put("SyncFg", 1);
            database.update(FileDBhelper.TABLE_FILE_DETAIL, values,
                    " DocumentId=?", new String[]{id + ""});

        } catch (Exception exe) {

            exe.printStackTrace();
        } finally {
            try {
                database.close();
                helper.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
