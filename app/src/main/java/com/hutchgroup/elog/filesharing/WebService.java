package com.hutchgroup.elog.filesharing;


import android.content.Context;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class WebService {

    //Test Links
    public static  String GET_FILE_DETAIL ="http://209.97.200.208:3880/ELogService.svc/State/Get/";
    public static  String POST_FILE_DETAIL ="http://209.97.200.208:3880/ELogService.svc/State/Post";

    public String doGet(String request) {
        try {
            URL url = new URL(request);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-type", "application/json");


            if (conn.getResponseCode() == 200) {
                return getContent(conn.getInputStream());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        // LogFile.write("URL: " + url + "\n" + WebService.class.getName() + "::doGet Error:" + getContent(response.getEntity().getContent()) + ", ||" + status.toString(), LogFile.WEB_SERVICE, LogFile.ERROR_LOG);
        // System.out.println("ErrorLog: " + status.toString());
        return null;
    }

    public String doPost(String url, String data) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection conn= (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setDoOutput(true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
        outputStreamWriter.write(data);
        outputStreamWriter.flush();


        if (conn.getResponseCode()  == 200) {
            return getContent(conn.getInputStream());
        }
        // LogFile.write("URL: " + url + "\n" + WebService.class.getName() + "::doPost Error:" + getContent(response.getEntity().getContent()) + ", ||" + status.toString(), LogFile.WEB_SERVICE, LogFile.ERROR_LOG);
        // System.out.println("ErrorLog: " + status.toString());
        return null;
    }

    // Created By: Pallavi Wattamwar
    // Created Date: 14 april 2019
    // Purpose: get Remark info From Web
    public static boolean getFileFromWeb(Context context) {
        boolean status = true;
        WebService ws = new WebService();
        try {
            String result = ws
                    .doGet(GET_FILE_DETAIL
                            );
            if (result == null || result.isEmpty())
                return status;

            JSONArray obja = new JSONArray(result);
          ArrayList<FileBean> al = new ArrayList<>();
           /*
            for (int i = 0; i < obja.length(); i++) {
                JSONObject json = obja.getJSONObject(i);
                FileBean bean = new FileBean();
                bean.setFileExtension(json.getString("FileExtenstion"));
                bean.setFileContentLength(json.getInt("ContentLength"));
                bean.setFileName(json.getString("FileName"));
                bean.setPath(json.getString("Path"));
                bean.setFileType(json.getString("FileType"));
                al.add(bean);
            }*/


           // For Testing purpose
            FileBean bean1 = new FileBean();
            bean1.setPath("https://www.hutchsystems.com/docs/Hutch_AOBRD_Manual.pdf");
            bean1.setFileName("Hutch_AOBRD_Manual");
            bean1.setFileContentLength(22194557);
            bean1.setFileExtension(".pdf");
            bean1.setFileType("PDF");
            bean1.setId(102);
            al.add(bean1);

            FileBean bean4 = new FileBean();
            bean4.setPath("https://www.demonuts.com/Demonuts/smallvideo.mp4");
            bean4.setFileName("smallvideo");
            bean4.setFileContentLength(164826);
            bean4.setFileExtension(".mp4");
            bean4.setFileType("Video");
            bean4.setId(105);
            al.add(bean4);

            FileBean bean3 = new FileBean();
            bean3.setPath("https://pbs.twimg.com/profile_images/630285593268752384/iD1MkFQ0.png");
            bean3.setFileName("iD1MkFQ0");
            bean3.setFileContentLength(18136);
            bean3.setFileExtension(".png");
            bean3.setFileType("Image");
            bean3.setId(104);
            al.add(bean3);

            FileBean bean2 = new FileBean();
            bean2.setPath("https://www.hutchsystems.com/docs/Hutch_AOBRD_Flashcard.pdf");
            bean2.setFileName("Hutch_AOBRD_Flashcard");
            bean2.setFileContentLength(6561573);
            bean2.setFileExtension(".pdf");
            bean2.setFileType("PDF");
            bean2.setId(103);
            al.add(bean2);

            FileBean bean = new FileBean();
            bean.setPath("https://www.hutchsystems.com/docs/Hutch_ELD_Manual.pdf");
            bean.setFileName("Hutch_ELD_Manual");
            bean.setFileContentLength(21931132);
            bean.setFileExtension(".pdf");
            bean.setFileType("PDF");
            bean.setId(101);
            al.add(bean);


            FileBean bean6 = new FileBean();
            bean6.setPath("https://www.hutchsystems.com/docs/Hutch_Web_Portal_Manual.pdf");
            bean6.setFileName("Hutch_Web_Portal_Manual");
            bean6.setFileContentLength(35120782);
            bean6.setFileExtension(".pdf");
            bean6.setFileType("PDF");
            bean6.setId(106);
            al.add(bean6);

            FileBean bean7 = new FileBean();
            bean7.setPath("https://www.hutchsystems.com/docs/Hutch_ELD_Flashcard.pdf");
            bean7.setFileName("Hutch_ELD_Flashcard");
            bean7.setFileContentLength(8274854);
            bean7.setFileExtension(".pdf");
            bean7.setFileType("PDF");
            bean7.setId(107);
            al.add(bean7);

            FileBean bean8 = new FileBean();
            bean8.setPath("https://www.pexels.com/photo/85773/download/?search_query=&tracking_id=5kk6pdvayei");
            bean8.setFileName("bloom-close-up-colorful-85773");
            bean8.setFileContentLength(1323676 );
            bean8.setFileExtension(".jpeg");
            bean8.setFileType("Image");
            bean8.setId(108);
            al.add(bean8);


           /*



            /*
            FileBean bean = new FileBean();
            bean.setPath("https://www.hutchsystems.com/docs/Hutch_ELD_Manual.pdf");
            bean.setFileName("Hutch_ELD_Manual");
            bean.setFileContentLength(21931132);
            bean.setFileExtension(".pdf");
            bean.setFileType("PDF");
            bean.setId(101);
            al.add(bean);*/

           /*





            FileBean bean2 = new FileBean();
            bean2.setPath("https://www.hutchsystems.com/docs/Hutch_AOBRD_Flashcard.pdf");
            bean2.setFileName("Hutch_AOBRD_Flashcard");
            bean2.setFileContentLength(6561573);
            bean2.setFileExtension(".pdf");
            bean2.setFileType("PDF");
            bean2.setId(103);
            al.add(bean2);


            FileBean bean3 = new FileBean();
            bean3.setPath("https://pbs.twimg.com/profile_images/630285593268752384/iD1MkFQ0.png");
            bean3.setFileName("iD1MkFQ0");
            bean3.setFileContentLength(18136);
            bean3.setFileExtension(".png");
            bean3.setFileType("Image");
            bean3.setId(104);
            al.add(bean3);

            FileBean bean4 = new FileBean();
            bean4.setPath("https://www.demonuts.com/Demonuts/smallvideo.mp4");
            bean4.setFileName("smallvideo");
            bean4.setFileContentLength(164826);
            bean4.setFileExtension(".mp4");
            bean4.setFileType("Video");
            bean4.setId(105);
            al.add(bean4);*/



            if (al.size() > 0) {

                // Insert file in the database
                FileDB.Save(al,context);
            }


        } catch (Exception e) {
            status = false;

        }
        return status;
    }


    // Created By: Pallavi Wattamwar
    // Created Date: 04 July 2019
    // Purpose: POST Downloaded File
    public static boolean PostDownloadedfie(int id,Context context) {
        boolean status = true;
        WebService ws = new WebService();

        try {
            String data = FileDB.getDownloadedFile(id,context).toString();

            if (data.equals("[]")) {
                return status;
            }

            String result = ws.doPost(
                    POST_FILE_DETAIL,
                    data);
            if (result != null) {
                FileDB.updateDownloadedFile(context,id);
            }

        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }

        return status;

    }

        private String getContent(InputStream in) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            InputStream input = new BufferedInputStream(in);

            br = new BufferedReader(new InputStreamReader(input));
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            // LogFile.write(WebService.class.getName() + "::doPost Error:" + e.getMessage(), LogFile.WEB_SERVICE, LogFile.ERROR_LOG);
        }
        return null;

    }
}
