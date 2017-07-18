package com.mjsearch.emma.resumeeditor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;


public class Experience implements Parcelable {
	public String id;
	public String company;
	public Date startDate;
	public Date endDate;
	public String content;

	public Experience() {
		this.id = UUID.randomUUID().toString();
	}

	protected Experience(Parcel in) {
		this.id = in.readString();
		this.company = in.readString();
		this.startDate = new Date(in.readLong());
		this.endDate = new Date(in.readLong());
		this.content = in.readString();
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(company);
		out.writeLong(startDate.getTime());
		out.writeLong(endDate.getTime());
		out.writeString(content);
	} 

	public static final Creator<Experience> CREATOR = new Creator<Experience>() {
		public Experience createFromParcel(Parcel in) {
			return new Experience(in);
		}

		@Override
		public Experience[] newArray(int size) {
			return new Experience[size];
		}		
	};

	public int describeContents() {
		return 0;
	}


}


