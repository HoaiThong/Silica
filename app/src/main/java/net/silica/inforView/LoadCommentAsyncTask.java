package net.silica.inforView;

import android.os.AsyncTask;

import com.google.gson.Gson;

import net.silica.homeView.HttpHander;
import net.silica.model.Book;
import net.silica.model.Comment;
import net.silica.model.Customer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadCommentAsyncTask extends AsyncTask<String, String, ArrayList<Comment>> {
    ArrayList<Comment> arrayList = new ArrayList<Comment>();
    Comment comment;
    HttpHander httpHander = new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_COMMENT = "comment";

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
    protected ArrayList<Comment> doInBackground(String... args) {
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
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_COMMENT);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String json = jsonArray.get(i).toString();
                    comment = gson.fromJson(json, Comment.class);
                    Book book=gson.fromJson(json,Book.class);
                    Customer customer=gson.fromJson(json,Customer.class);
                    comment.setBook(book);
                    comment.setCustomer(customer);
//                    // adding HashList to ArrayList
                    arrayList.add(comment);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


}
