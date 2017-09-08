package com.hisign.thirdparty.imageselector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.hisign.thirdparty.R;
import com.hisign.thirdparty.imageselector.ImageConfig;
import com.hisign.thirdparty.imageselector.bean.ImageInfo;
import com.hisign.thirdparty.imageselector.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表中图片的适配器
 * Created by Yancy on 2016/1/27.
 */
public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ImageInfo> imageInfoList;                      // 本地照片数据
    private List<String> selectPhoto = new ArrayList<>();                   // 选择的图片数据
    private OnCallBack onCallBack;
    private final static String TAG = "ImageAdapter";
    
    private ImageConfig imageConfig;

    private final static int HEAD = 0;    // 开启相机时需要显示的布局
    private final static int ITEM = 1;    // 照片布局

    public ImageAdapter(Context mContext, List<ImageInfo> imageInfoList, ImageConfig imageConfig) {
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.imageInfoList = imageInfoList;
        this.imageConfig = imageConfig;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD) {
            return new HeadHolder(mLayoutInflater.inflate(R.layout.imageselector_item_camera, parent, false));
        }
        return new ViewHolder(mLayoutInflater.inflate(R.layout.imageselector_item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // 设置 每个imageView 的大小
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = ScreenUtils.getScreenWidth(mContext) / 3;
        params.width = ScreenUtils.getScreenWidth(mContext) / 3;
        holder.itemView.setLayoutParams(params);

        if (getItemViewType(position) == HEAD) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageConfig.getMaxSize() <= selectPhoto.size()) {        // 当选择图片达到上限时， 禁止继续添加
                        return;
                    }
                    onCallBack.OnClickCamera(selectPhoto);
                }
            });
            return;
        }

        final ImageInfo imageInfo;
        if (imageConfig.isShowCamera()) {
            imageInfo = imageInfoList.get(position - 1);
        } else {
            imageInfo = imageInfoList.get(position);
        }
        final ViewHolder viewHolder = (ViewHolder) holder;
        imageConfig.getImageLoader().displayImage(mContext, imageInfo.path, viewHolder.ivPhotoImage);


        if (selectPhoto.contains(imageInfo.path)) {
            viewHolder.chkPhotoSelector.setChecked(true);
            viewHolder.chkPhotoSelector.setButtonDrawable(R.mipmap.imageselector_select_checked);
            viewHolder.vPhotoMask.setVisibility(View.VISIBLE);
        } else {
            viewHolder.chkPhotoSelector.setChecked(false);
            viewHolder.chkPhotoSelector.setButtonDrawable(R.mipmap.imageselector_select_uncheck);
            viewHolder.vPhotoMask.setVisibility(View.GONE);
        }

        if (!imageConfig.isMultiSelect()) {
            viewHolder.chkPhotoSelector.setVisibility(View.GONE);
            viewHolder.vPhotoMask.setVisibility(View.GONE);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageConfig.isMultiSelect()) {
                    selectPhoto.clear();
                    selectPhoto.add(imageInfo.path);
                    onCallBack.OnClickPhoto(selectPhoto);
                    return;
                }

                if (selectPhoto.contains(imageInfo.path)) {
                    selectPhoto.remove(imageInfo.path);
                    viewHolder.chkPhotoSelector.setChecked(false);
                    viewHolder.chkPhotoSelector.setButtonDrawable(R.mipmap.imageselector_select_uncheck);
                    viewHolder.vPhotoMask.setVisibility(View.GONE);
                } else {
                    if (imageConfig.getMaxSize() <= selectPhoto.size()) {        // 当选择图片达到上限时， 禁止继续添加
                        return;
                    }
                    selectPhoto.add(imageInfo.path);
                    viewHolder.chkPhotoSelector.setChecked(true);
                    viewHolder.chkPhotoSelector.setButtonDrawable(R.mipmap.imageselector_select_checked);
                    viewHolder.vPhotoMask.setVisibility(View.VISIBLE);
                }
                onCallBack.OnClickPhoto(selectPhoto);
            }
        });


    }


    /**
     * 照片的 Holder
     */
    private class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhotoImage;
        private View vPhotoMask;
        private CheckBox chkPhotoSelector;

        private ViewHolder(View itemView) {
            super(itemView);
            ivPhotoImage = (ImageView) itemView.findViewById(R.id.photo_image);
            vPhotoMask = itemView.findViewById(R.id.photo_mask);
            chkPhotoSelector = (CheckBox) itemView.findViewById(R.id.photo_check);
        }
    }

    /**
     * 相机按钮的 Holder
     */
    private class HeadHolder extends RecyclerView.ViewHolder {
        private HeadHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (imageConfig.isShowCamera() && position == 0) {
            return HEAD;
        }
        return ITEM;
    }

    @Override
    public int getItemCount() {
        if (imageConfig.isShowCamera())
            return imageInfoList.size() + 1;
        else
            return imageInfoList.size();
    }

    public interface OnCallBack {
        void OnClickPhoto(List<String> selectPhoto);

        void OnClickCamera(List<String> selectPhoto);
    }

    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    /**
     * 传入已选的图片
     *
     * @param selectPhoto 已选的图片路径
     */
    public void setSelectPhoto(List<String> selectPhoto) {
        this.selectPhoto.addAll(selectPhoto);
    }
}
