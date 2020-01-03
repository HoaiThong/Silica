package net.silica.homeView;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import net.silica.model.Book;
import net.silica.model.Content;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadDataAsyncTask extends AsyncTask<String, String, ArrayList<Book>> {
    ArrayList<Book> arrayList = new ArrayList<Book>();
    Book book;
    HttpHander httpHander = new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_BOOK = "book";

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * getting All mangas from url
     *
     * @return
     */
    protected ArrayList<Book> doInBackground(String... args) {
        Gson gson = new Gson();
        String startAt = args[0];
        String API_URL = args[1];
//        Log.e("start At", startAt);
//        Log.e("API", API_URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("startAt", startAt));
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            String success = jsonObject.getString(TAG_SUCCESS);
            String message = jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)) {
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_BOOK);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String json = jsonArray.get(i).toString();
                    book = gson.fromJson(json, Book.class);
//                    // adding HashList to ArrayList
                    arrayList.add(book);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


}
