package net.silica.inforView;

import android.os.AsyncTask;

import com.google.gson.Gson;

import net.silica.homeView.HttpHander;
import net.silica.model.Book;
import net.silica.model.SubcribeBook;
import net.silica.model.Customer;
import net.silica.model.SubcribeBook;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadSubcribeAsyncTask extends AsyncTask<String, String, ArrayList<SubcribeBook>> {
    ArrayList<SubcribeBook> arrayList = new ArrayList<SubcribeBook>();
    SubcribeBook subcribeBook;
    HttpHander httpHander = new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_SUCRIBE_BOOK = "SubcribeBook";

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
    protected ArrayList<SubcribeBook> doInBackground(String... args) {
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
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_SUCRIBE_BOOK);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String json = jsonArray.get(i).toString();
                    subcribeBook = gson.fromJson(json, SubcribeBook.class);
                    Book book=gson.fromJson(json,Book.class);
                    Customer customer=gson.fromJson(json,Customer.class);
                    subcribeBook.setBook(book);
                    subcribeBook.setCustomer(customer);
//                    // adding HashList to ArrayList
                    arrayList.add(subcribeBook);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


}
