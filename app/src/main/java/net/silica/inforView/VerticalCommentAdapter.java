package net.silica.inforView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import net.silica.R;
import net.silica.model.Comment;

import java.util.List;

public class VerticalCommentAdapter extends BaseAdapter {

    private List<Comment> listData;
    private LayoutInflater layoutInflater;
    private Context mActivity;
    private ViewHolder holder;
    private int rowIndex = 0;
    private ItemClickListener itemClickListener;
    RequestOptions requestOptions;

    public VerticalCommentAdapter(Context aContext, List<Comment> listData) {
        this.mActivity = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(mActivity);
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher_round);
        requestOptions.error(R.drawable.ic_cart);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_comment, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Comment c = listData.get(position);
        String nameGoogle = c.getCustomer().getNameGoogle();
        String nameFacebook = c.getCustomer().getNameFaceBook();
        if (nameGoogle.equals("")) {
            if (!nameFacebook.equals("")) holder.tv_customer_display.setText(nameFacebook);
        } else holder.tv_customer_display.setText(nameGoogle);

        holder.tv_comment.setText(c.getComment());
        Glide.with(mActivity)
                .setDefaultRequestOptions(requestOptions)
                .load(c.getCustomer().getIconUrlCustomer())
                .into(holder.icon);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    static class ViewHolder {
        private TextView tv_customer_display;
        private TextView tv_comment;
        private ImageView icon;

        public ViewHolder(View view) {
            tv_customer_display = view.findViewById(R.id.tv_name_customer_display);
            tv_comment = view.findViewById(R.id.tv_comment_display);
            icon = view.findViewById(R.id.img_icon_logo);
        }

    }


}

