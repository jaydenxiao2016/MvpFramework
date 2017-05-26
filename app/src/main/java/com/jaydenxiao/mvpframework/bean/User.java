package com.jaydenxiao.mvpframework.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * des:用户
 * Created by xsf
 * on 2016.09.15:11
 */

public class User implements Parcelable {
    private int user_id;
    private String pwd;
    private String icon;//头像
    private String SubCompanyId;
    private String SubCompanyName;
    private String SubCompanyUid;
    private String companyId;
    private String companyName;
    private String companyUid;
    private String departId;
    private String departName;
    private String departUid;
    private String userUid;
    private String userId;
    private String userPwd;
    private String userName;
    private boolean rememberPwd;

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSubCompanyId() {
        return SubCompanyId;
    }

    public void setSubCompanyId(String subCompanyId) {
        SubCompanyId = subCompanyId;
    }

    public String getSubCompanyName() {
        return SubCompanyName;
    }

    public void setSubCompanyName(String subCompanyName) {
        SubCompanyName = subCompanyName;
    }

    public String getSubCompanyUid() {
        return SubCompanyUid;
    }

    public void setSubCompanyUid(String subCompanyUid) {
        SubCompanyUid = subCompanyUid;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyUid() {
        return companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartUid() {
        return departUid;
    }

    public void setDepartUid(String departUid) {
        this.departUid = departUid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User() {
    }

    public boolean isRememberPwd() {
        return rememberPwd;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public void setRememberPwd(boolean rememberPwd) {
        this.rememberPwd = rememberPwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", pwd='" + pwd + '\'' +
                ", icon='" + icon + '\'' +
                ", SubCompanyId='" + SubCompanyId + '\'' +
                ", SubCompanyName='" + SubCompanyName + '\'' +
                ", SubCompanyUid='" + SubCompanyUid + '\'' +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyUid='" + companyUid + '\'' +
                ", departId='" + departId + '\'' +
                ", departName='" + departName + '\'' +
                ", departUid='" + departUid + '\'' +
                ", userUid='" + userUid + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.user_id);
        dest.writeString(this.pwd);
        dest.writeString(this.icon);
        dest.writeString(this.SubCompanyId);
        dest.writeString(this.SubCompanyName);
        dest.writeString(this.SubCompanyUid);
        dest.writeString(this.companyId);
        dest.writeString(this.companyName);
        dest.writeString(this.companyUid);
        dest.writeString(this.departId);
        dest.writeString(this.departName);
        dest.writeString(this.departUid);
        dest.writeString(this.userUid);
        dest.writeString(this.userId);
        dest.writeString(this.userPwd);
        dest.writeString(this.userName);
        dest.writeByte(this.rememberPwd ? (byte) 1 : (byte) 0);
    }

    protected User(Parcel in) {
        this.user_id = in.readInt();
        this.pwd = in.readString();
        this.icon = in.readString();
        this.SubCompanyId = in.readString();
        this.SubCompanyName = in.readString();
        this.SubCompanyUid = in.readString();
        this.companyId = in.readString();
        this.companyName = in.readString();
        this.companyUid = in.readString();
        this.departId = in.readString();
        this.departName = in.readString();
        this.departUid = in.readString();
        this.userUid = in.readString();
        this.userId = in.readString();
        this.userPwd = in.readString();
        this.userName = in.readString();
        this.rememberPwd = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
