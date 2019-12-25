package net.bytecoding.progressdialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyProgressDialog extends Dialog {

    public Activity mActivity;
    public Dialog myDialog;
    public ProgressBar myProgressBar;
    public TextView textView;
    public String message = "Loading...";
    LayoutInflater layoutInflater;
    public MyProgressDialog(Activity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.mActivity = activity;
        layoutInflater= LayoutInflater.from(mActivity);
//        Drawable draw=res.getDrawable(R.drawable.custom_progressbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_loading_dialog);
        myProgressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.title_myProgressDialog);
        textView.setText(message);

//        Drawable draw=mActivity.getResources().getDrawable(R.drawable.custom_progressbar);
// set the drawable as progress drawable
//        myProgressBar.setProgressDrawable(draw);
    }

    public void setMessage(String message) {
        this.message = message;
        textView.setText(message);
    }

}
