package com.uguroztunc.cs310news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsRepository {

    public void getAllCategories(ExecutorService srv, Handler uiHandler)
    {
        srv.execute(() -> {
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getallnewscategories");
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }

                JSONObject answer = new JSONObject(buffer.toString());
                JSONArray items = new JSONArray(answer.getJSONArray("items").toString());

                // List of strings which will store category names with id order
                List<String> data = new ArrayList<>();

                for (int i = 0; i < items.length(); i++)
                {
                    for (int j = 0; j < items.length(); j++)
                    {
                        JSONObject current = items.getJSONObject(j);
                        if ( current.getInt("id") == i+1 )
                        {
                            data.add(current.getString("name"));
                        }
                    }
                }
                
                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }


    public void getNewsByCatId(ExecutorService srv, Handler uiHandler, int catId)
    {
        srv.execute(() -> {
            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + String.valueOf(catId));
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }


                JSONObject answer = new JSONObject(buffer.toString());

                JSONArray items = new JSONArray(answer.getJSONArray("items").toString());

                List<NewsItem> data = new ArrayList<>();

                for (int i = 0; i < items.length(); i++)
                {
                    JSONObject current = items.getJSONObject(i);

                    NewsItem temp = new NewsItem(current.getInt("id"), current.getString("title"), current.getString("text"), current.getString("date"), current.getString("image"), current.getString("categoryName"));

                    data.add(temp);
                }


                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void getNewsById(ExecutorService srv, Handler uiHandler, int newsId)
    {
        srv.execute(() -> {
            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + String.valueOf(newsId));
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }


                JSONObject answer = new JSONObject(buffer.toString());

                JSONArray items = new JSONArray(answer.getJSONArray("items").toString());

                List<NewsItem> data = new ArrayList<>();


                for (int i = 0; i < items.length(); i++)
                {
                    JSONObject current = items.getJSONObject(i);

                    NewsItem temp = new NewsItem(current.getInt("id"), current.getString("title"), current.getString("text"), current.getString("date"), current.getString("image"), current.getString("categoryName"));

                    data.add(temp);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void downloadImage(ExecutorService srv, Handler uiHandler,String path){
        srv.execute(()->{
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                Bitmap bitmap =  BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void getCommentById(ExecutorService srv, Handler uiHandler, int newsId)
    {
        srv.execute(() -> {
            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + String.valueOf(newsId));
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }

                JSONObject answer = new JSONObject(buffer.toString());
                JSONArray items = new JSONArray(answer.getJSONArray("items").toString());

                List<CommentItem> data = new ArrayList<>();

                for (int i = 0; i < items.length(); i++)
                {
                    JSONObject current = items.getJSONObject(i);

                    CommentItem temp = new CommentItem(current.getInt("id"), current.getInt("news_id"), current.getString("name"), current.getString("text"));

                    data.add(temp);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void postComment(ExecutorService srv, Handler uiHandler, int newsId, String name, String comment)
    {
        srv.execute(()->{

            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/savecomment");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");


                JSONObject outputData  = new JSONObject();

                outputData.put("name",name);
                outputData.put("text",comment);
                outputData.put("news_id",String.valueOf(newsId));


                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());


                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line ="";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject retVal = new JSONObject(buffer.toString());

                conn.disconnect();

                int retValInt = retVal.getInt("serviceMessageCode");

                Message msg = new Message();
                msg.obj = retValInt;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });

    }



}
