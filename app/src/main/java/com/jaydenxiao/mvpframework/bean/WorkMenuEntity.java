package com.jaydenxiao.mvpframework.bean;

/**
 * 类名：WorkMenuEntity.java
 * 描述：
 * 作者：xsf
 * 创建时间：2016/12/23
 * 最后修改时间：2016/12/23
 */

public class WorkMenuEntity {
    private String title;
    private Class<?> cls;
    private int icon;
    private int notReadCount;

    public WorkMenuEntity(String title, int icon, int notReadCount, Class<?> cls) {
        this.title = title;
        this.icon = icon;
        this.notReadCount = notReadCount;
        this.cls=cls;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public int getNotReadCount() {
        return notReadCount;
    }

    public void setNotReadCount(int notReadCount) {
        this.notReadCount = notReadCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
