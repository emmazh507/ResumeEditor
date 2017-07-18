package com.mjsearch.emma.resumeeditor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Education implements Parcelable {

    public String id;

    public String school;

    public String major;

    public Date startDate;

    public Date endDate;

    public List<String> courses;

    public Education() {
        //give an unique ID for each education
        id = UUID.randomUUID().toString();
    }

    protected Education(Parcel in) {
        this.id=in.readString();
        this.school=in.readString();
        this.major=in.readString();
        this.startDate=new Date(in.readLong()); //do a test of readString()
        this.endDate=new Date(in.readLong());
        this.courses=in.createStringArrayList();
    }

    //tranfer class Education content to parcel
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(id);
        out.writeString(school);
        out.writeString(major);
        out.writeLong(startDate.getTime());
        out.writeLong(endDate.getTime());
        out.writeStringList(courses);
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    public int describeContents() {
        return 0;
    }
}
