package net.silica.inforView;

import android.os.AsyncTask;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RatingBookAsyncTask extends AsyncTask<String, String, Void> {
    HttpHander httpHander=new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * getting All mangas from url
     * @return
     */
    protected Void doInBackground(String... args) {
        String idCustomer=args[0];
        String idBook=args[1];
        String star=args[2];
        String fcmtoken=args[3];
        String API_URL=args[4];
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("idCustomer", idCustomer));
        params.add(new BasicNameValuePair("idBook", idBook));
        params.add(new BasicNameValuePair("ratingStar", star));
        params.add(new BasicNameValuePair("fcmtoken", fcmtoken));
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL, "POST", params);
        try {
            String success=jsonObject.getString(TAG_SUCCESS);
            String message=jsonObject.getString(TAG_MESSAGE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
