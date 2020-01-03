package net.silica.readView.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import net.bytecoding.epublibrary.core.TableOfContent;
import net.silica.R;

import java.util.List;

/**
 *
 */
public class ChapterAdapter extends BaseAdapter {

    private List<TableOfContent.Chapter> listData;
    private LayoutInflater layoutInflater;
    private Activity mActivity;
    private ViewHolder holder;
    private int rowIndex = 0;
    private ItemClickListener itemClickListener;

    public ChapterAdapter(Activity mActivity, List<TableOfContent.Chapter> listData) {
        this.mActivity = mActivity;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(mActivity);
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

    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_chapter, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowIndex = position;
                notifyDataSetChanged();
                itemClickListener.onClick(v, position, false);
            }
        });
        TableOfContent.Chapter c = listData.get(position);
        String title = c.getTitle();

        holder.tv_title_chapter.setText(title);
        if (rowIndex == position)
            holder.tv_title_chapter.setTextColor(mActivity.getResources().getColor(R.color.black));
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private class ViewHolder {
        private TextView tv_title_chapter;

        public ViewHolder(View view) {
            tv_title_chapter = view.findViewById(R.id.tv_title);
        }

    }


}

