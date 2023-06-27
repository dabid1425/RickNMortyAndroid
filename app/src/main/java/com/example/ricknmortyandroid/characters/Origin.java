package com.example.ricknmortyandroid.characters;

import android.os.Parcel;
import android.os.Parcelable;

public class Origin implements Parcelable {
    private String name;
    private String url;

    Origin(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    protected Origin(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Origin> CREATOR = new Creator<Origin>() {
        @Override
        public Origin createFromParcel(Parcel in) {
            return new Origin(in);
        }

        @Override
        public Origin[] newArray(int size) {
            return new Origin[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}