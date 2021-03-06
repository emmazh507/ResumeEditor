package com.mjsearch.emma.resumeeditor.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class BasicInfo implements Parcelable {

    public String name;
    public String email;
    public Uri imageUri;

    public BasicInfo() {

    }

    protected BasicInfo(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.imageUri = in.readParcelable(Uri.class.getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeParcelable(imageUri, flags);
    }

    public static final Creator<BasicInfo> CREATOR = new Creator<BasicInfo>() {
        @Override
        public BasicInfo createFromParcel(Parcel in) {return new BasicInfo(in);}

        @Override
        public BasicInfo[] newArray(int size) {return new BasicInfo[size];}
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
