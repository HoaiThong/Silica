package net.silica.login;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import net.silica.model.ApiManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHandler extends AsyncTask<String, Void, Integer> {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    String url = "http://192.168.0.110/KindleServer/view/signin.php";
    OkHttpClient client = new OkHttpClient();
    Activity mActivity;
    JSONObject obj;
    String TAG_SUCCESS = "success";
    String TAG_MESSAGE = "message";
    String result = "{\"success\":-1,\"message\":\"Null\"}";
    private static int i = 0;
    private String message = "";
    private String msg="";

    public OkHttpHandler(Activity mActivity) {
        super.onPreExecute();
        this.mActivity = mActivity;
        this.url=new ApiManager().API_URL_SIGNIN;
//        myProgressDialog = new MyProgressDialog(mActivity);
//        myProgressDialog.setMessage(message);
//        myProgressDialog.setCancelable(false);
//        myProgressDialog.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        String data = params[0];
//        int bool = -1;
//        while (i <= 1) {
//            bool = executePost(url, data);
//            i++;
//            if (bool) return bool;
//        }
        return executePost(url, data);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Toast.makeText(mActivity,msg, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onPostExecute(Boolean aBoolean) {
//        super.onPostExecute(aBoolean);
////        myProgressDialog.dismiss();
//    }

    public int executePost(String url, String data) {
        Log.d("url :", url);
        String credential = Credentials.basic("silicaandroid", "ZnSr3t9Ub8J0XV9v");
//        System.out.println(credential);
        RequestBody body = RequestBody.create(data,JSON);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("request", data)
                .build();

        client=new OkHttpClient();
//        client = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
        i++;
//        credential = "Basic a2luZGxldm46Wm53I1NyM3Q5VWI4SjBYVjl2QA==";
        Request request = new Request.Builder()
                .header("Authorization", credential)
                .url(url)
                .post(body).build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            result = response.body().string();
            obj = new JSONObject(result);
            int s = obj.getInt(TAG_SUCCESS);
            msg=obj.getString(TAG_MESSAGE);
            return s;

        } catch (IOException e) {
            Log.d("ioe :", e.toString());
            return -1;
        } catch (JSONException e) {
            Log.d("joe :", e.toString());
//                return excutePost(url, data);
            return -1;
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
