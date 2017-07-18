package com.mjsearch.emma.resumeeditor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;


public class Project implements Parcelable {

	public String id;
	public String name;
	public String content;

	public Project() {
		this.id = UUID.randomUUID().toString();
	}

	protected Project(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.content = in.readString();
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(name);
		out.writeString(content);
	}

	public static final Creator<Project> CREATOR = new Creator<Project>() {
		public Project createFromParcel(Parcel in) {
			return new Project(in);
		}

		public Project[] newArray(int size) {
			return new Project[size];
		}
	};

	public int describeContents() {
		return 0;
	}

}
