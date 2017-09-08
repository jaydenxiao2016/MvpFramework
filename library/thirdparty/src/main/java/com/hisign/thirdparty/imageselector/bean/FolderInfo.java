package com.hisign.thirdparty.imageselector.bean;

import java.util.List;

/**
 * Folder bean
 * Created by Yancy on 2015/12/2.
 */
public class FolderInfo {

    public String name;
    public String path;
    public ImageInfo cover;
    public List<ImageInfo> mImageInfos;

    @Override
    public boolean equals(Object o) {
        try {
            FolderInfo other = (FolderInfo) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}