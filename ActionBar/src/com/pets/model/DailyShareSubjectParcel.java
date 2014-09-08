package com.pets.model;

import com.pets.bean.DailyShareSubject;

import android.os.Parcel;
import android.os.Parcelable;

public class DailyShareSubjectParcel implements Parcelable {
	
	private static Parcelable.Creator<DailyShareSubject> create = new Parcelable.Creator<DailyShareSubject>() {

		@Override
		public DailyShareSubject createFromParcel(Parcel source) {
			source.readString();
			source.readString();
			return null;
		}

		@Override
		public DailyShareSubject[] newArray(int size) {
			return null;
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}

}
