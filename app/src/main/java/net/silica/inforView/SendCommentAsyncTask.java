package net.silica.inforView;

import android.os.AsyncTask;
import android.util.Log;

import net.silica.model.ApiManager;
import net.silica.model.Comment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SendCommentAsyncTask extends AsyncTask<String, String, String> {
    HttpHander httpHander = new HttpHander();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ERROR = "error";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_COMMENT = "comment";
    private String API_URL_SEND_COMMENT = "";

    ArrayList<Comment> arrayList;

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
    protected String doInBackground(String... args) {
        arrayList = new ArrayList<>();
        API_URL_SEND_COMMENT = new ApiManager().API_URL_SEND_COMMENT;
        String jsonComment = args[0];
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("comment", jsonComment));
        JSONObject jsonObject = httpHander.makeHttpRequest(API_URL_SEND_COMMENT, "POST", params);
        try {
            String success = jsonObject.getString(TAG_SUCCESS);
            String message = jsonObject.getString(TAG_MESSAGE);
            if (success.equals(TAG_SUCCESS)) {
                return TAG_SUCCESS;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return TAG_ERROR;
    }


}
