package com.hisign.thirdparty.imageselector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisign.thirdparty.R;
import com.hisign.thirdparty.imageselector.ImageConfig;
import com.hisign.thirdparty.imageselector.bean.FolderInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 文件夹适配器
 * Created by Yancy on 2015/12/2.
 */
public class FolderAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<FolderInfo> mFolderInfoList = new ArrayList<>();
    private final static String TAG = "FolderAdapter";

    private int lastSelected = 0;

    private ImageConfig imageConfig;

    public FolderAdapter(Context context, ImageConfig imageConfig) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.imageConfig = imageConfig;
    }

    public void setData(List<FolderInfo> folderInfos) {
        if (folderInfos != null && folderInfos.size() > 0) {
            mFolderInfoList.addAll(folderInfos);
        } else {
            mFolderInfoList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFolderInfoList.size() + 1;
    }

    @Override
    public FolderInfo getItem(int position) {
        if (position == 0)
            return null;
        return mFolderInfoList.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.imageselector_item_folder, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (holder != null) {
            if (position == 0) {
                holder.folder_name_text.setText(R.string.all_folder);
                holder.image_num_text.setText("" + getTotalImageSize() + (context.getResources().getText(R.string.sheet)));

                if (mFolderInfoList.size() > 0) {
                    FolderInfo folderInfo = mFolderInfoList.get(0);

                    imageConfig.getImageLoader().displayImage(context, folderInfo.cover.path, holder.folder_image);
                    
                }
            } else {

                FolderInfo folderInfo = getItem(position);
                holder.folder_name_text.setText(folderInfo.name);
                holder.image_num_text.setText("" + folderInfo.mImageInfos.size() + (context.getResources().getText(R.string.sheet)));

                imageConfig.getImageLoader().displayImage(context, folderInfo.cover.path, holder.folder_image);
            }

            if (lastSelected == position) {
                holder.indicator.setVisibility(View.VISIBLE);
            } else {
                holder.indicator.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }

    class ViewHolder {

        ImageView folder_image;
        TextView folder_name_text;
        TextView image_num_text;
        ImageView indicator;

        ViewHolder(View itemView) {
            folder_image = (ImageView) itemView.findViewById(R.id.folder_image);
            folder_name_text = (TextView) itemView.findViewById(R.id.folder_name_text);
            image_num_text = (TextView) itemView.findViewById(R.id.image_num_text);
            indicator = (ImageView) itemView.findViewById(R.id.indicator);
            itemView.setTag(this);
        }

    }

    public int getSelectIndex() {
        return lastSelected;
    }


    private int getTotalImageSize() {
        int result = 0;
        if (mFolderInfoList != null && mFolderInfoList.size() > 0) {
            for (FolderInfo folderInfo : mFolderInfoList) {
                result += folderInfo.mImageInfos.size();
            }
        }
        return result;
    }


    public void setSelectIndex(int position) {
        if (lastSelected == position)
            return;
        lastSelected = position;
        notifyDataSetChanged();
    }

}