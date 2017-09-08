package com.hisign.thirdparty.imageselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.hisign.thirdparty.R;
import com.hisign.thirdparty.imageselector.adapter.FolderAdapter;
import com.hisign.thirdparty.imageselector.adapter.ImageAdapter;
import com.hisign.thirdparty.imageselector.bean.FolderInfo;
import com.hisign.thirdparty.imageselector.bean.ImageInfo;
import com.hisign.thirdparty.imageselector.utils.FileUtils;
import com.hisign.thirdparty.imageselector.utils.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * ImageSelectorFragment
 * Created by Yancy on 2015/12/2.
 */
public class ImageSelectorFragment extends Fragment {

    private static final int LOADER_ALL = 0;
    private static final int LOADER_CATEGORY = 1;
    private static final int REQUEST_CAMERA = 100;

    private ArrayList<String> resultList;
    private List<FolderInfo> mFolderInfoList = new ArrayList<>();

    private List<ImageInfo> mImageInfoList = new ArrayList<>();

    private Callback callback;

    private ImageAdapter imageAdapter;
    private FolderAdapter folderAdapter;

    private ListPopupWindow folderPopupWindow;

    private TextView category_button;
    private View popupAnchorView;
    private RecyclerView grid_image;

    private boolean hasFolderGened = false;

    private File tempFile;

    private Context context;

    private ImageConfig imageConfig;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("The Activity must implement ImageSelectorFragment.Callback interface...");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.imageselector_main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();

        category_button = (TextView) view.findViewById(R.id.category_button);
        grid_image = (RecyclerView) view.findViewById(R.id.grid_image);
        
        popupAnchorView = view.findViewById(R.id.footer_layout);

        init();
    }

    private void init() {
        imageConfig = ImageSelector.getImageConfig();

        folderAdapter = new FolderAdapter(context, imageConfig);

        imageAdapter = new ImageAdapter(context, mImageInfoList, imageConfig);

        grid_image.setAdapter(imageAdapter);

        resultList = imageConfig.getPathList();

        category_button.setText(R.string.all_folder);
        category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (folderPopupWindow == null) {
                    createPopupFolderList(ScreenUtils.getScreenWidth(getContext()), ScreenUtils.getScreenHeight(getContext()));
                }

                if (folderPopupWindow.isShowing()) {
                    folderPopupWindow.dismiss();
                } else {
                    folderPopupWindow.show();
                    int index = folderAdapter.getSelectIndex();
                    index = index == 0 ? index : index - 1;
                    folderPopupWindow.getListView().setSelection(index);
                }
            }
        });
    
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        grid_image.setLayoutManager(gridLayoutManager);
        imageAdapter = new ImageAdapter(getContext(), mImageInfoList,imageConfig);
        imageAdapter.setOnCallBack(new ImageAdapter.OnCallBack() {
            @Override
            public void OnClickCamera(List<String> selectPhotoList) {
                resultList.clear();
                resultList.addAll(selectPhotoList);
                showCameraAction();
            }
        
            @Override
            public void OnClickPhoto(List<String> selectPhotoList) {
                if (resultList != null) {
                    resultList.clear();
                    resultList.addAll(selectPhotoList);
    
                    if (!imageConfig.isMultiSelect() && resultList.size() > 0) {
                        if (callback != null) {
                            callback.onSingleImageSelected(selectPhotoList.get(0));
                        }
                    } else {
                        if (callback != null) {
                            callback.onImageSelected(resultList);
                        }
                    }
                }
            }
        });
        imageAdapter.setSelectPhoto(resultList);
        grid_image.setAdapter(imageAdapter);

    }

    /**
     * 创建弹出的ListView
     */
    private void createPopupFolderList(int width, int height) {
        folderPopupWindow = new ListPopupWindow(getActivity());
        folderPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        folderPopupWindow.setAdapter(folderAdapter);
        folderPopupWindow.setContentWidth(width);
        folderPopupWindow.setWidth(width);
        folderPopupWindow.setHeight(height * 5 / 8);
        folderPopupWindow.setAnchorView(popupAnchorView);
        folderPopupWindow.setModal(true);
        folderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                folderAdapter.setSelectIndex(i);

                final int index = i;
                final AdapterView v = adapterView;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        folderPopupWindow.dismiss();

                        if (index == 0) {
                            getActivity().getSupportLoaderManager().restartLoader(LOADER_ALL, null, mLoaderCallback);
                            category_button.setText(R.string.all_folder);
                        } else {
                            FolderInfo folderInfo = (FolderInfo) v.getAdapter().getItem(index);
                            if (null != folderInfo) {
                                mImageInfoList.clear();
                                mImageInfoList.addAll(folderInfo.mImageInfos);
                                imageAdapter.notifyDataSetChanged();
                                category_button.setText(folderInfo.name);
                            }
                        }
                        // 滑动到最初始位置
                        grid_image.smoothScrollToPosition(0);
                    }
                }, 100);

            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (folderPopupWindow != null) {
            if (folderPopupWindow.isShowing()) {
                folderPopupWindow.dismiss();
            }
        }
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 选择相机
     */
    private void showCameraAction() {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            tempFile = FileUtils.createTmpFile(getActivity(), imageConfig.getFilePath());
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            Toast.makeText(context, R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (tempFile != null) {
                    if (callback != null) {
                        callback.onCameraShot(tempFile);
                    }
                }
            } else {
                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.SIZE};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == LOADER_ALL) {
                CursorLoader cursorLoader =
                        new CursorLoader(getActivity(),
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                                null, null, IMAGE_PROJECTION[2] + " DESC");
                return cursorLoader;
            } else if (id == LOADER_CATEGORY) {
                CursorLoader cursorLoader = new CursorLoader(getActivity(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[0] + " like '%" + args.getString("path") + "%'", null, IMAGE_PROJECTION[2] + " DESC");
                return cursorLoader;
            }

            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                int count = data.getCount();
                if (count > 0) {
                    List<ImageInfo> tempImageInfoList = new ArrayList<>();
                    data.moveToFirst();
                    do {
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        int size = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]));
                        boolean showFlag = size > 1024 * 5;                           //是否大于5K
                        if (showFlag) {
                            ImageInfo imageInfo = new ImageInfo(path, name, dateTime);
                            tempImageInfoList.add(imageInfo);
                            if (!hasFolderGened) {
                                File imageFile = new File(path);
                                File folderFile = imageFile.getParentFile();
                                FolderInfo folderInfo = new FolderInfo();
                                folderInfo.name = folderFile.getName();
                                folderInfo.path = folderFile.getAbsolutePath();
                                folderInfo.cover = imageInfo;
                                if (!mFolderInfoList.contains(folderInfo)) {
                                    List<ImageInfo> imageInfoList = new ArrayList<>();
                                    imageInfoList.add(imageInfo);
                                    folderInfo.mImageInfos = imageInfoList;
                                    mFolderInfoList.add(folderInfo);
                                } else {
                                    FolderInfo f = mFolderInfoList.get(mFolderInfoList.indexOf(folderInfo));
                                    f.mImageInfos.add(imageInfo);
                                }
                            }
                        }

                    } while (data.moveToNext());

                    mImageInfoList.clear();
                    mImageInfoList.addAll(tempImageInfoList);
                    imageAdapter.notifyDataSetChanged();

                    folderAdapter.setData(mFolderInfoList);

                    hasFolderGened = true;

                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    public interface Callback {
        void onSingleImageSelected(String path);

        void onImageSelected(List<String> photoList);

        void onCameraShot(File imageFile);
    }

}