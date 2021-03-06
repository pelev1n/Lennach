package com.caramelheaven.lennach.models.model.common;

import android.os.Parcel;
import android.os.Parcelable;

public class DataSet implements Parcelable {
    //image
    private String displayNameImage;
    private String nameImage;
    private Integer sizeImage;
    private String thumbnail;
    private Integer widthImage;
    private Integer heightImage;

    @Override
    public String toString() {
        return "DataSet{" +
                "displayNameImage='" + displayNameImage + '\'' +
                ", nameImage='" + nameImage + '\'' +
                ", sizeImage=" + sizeImage +
                ", thumbnail='" + thumbnail + '\'' +
                ", widthImage=" + widthImage +
                ", heightImage=" + heightImage +
                '}';
    }

    public String getDisplayNameImage() {
        return displayNameImage;
    }

    public void setDisplayNameImage(String displayNameImage) {
        this.displayNameImage = displayNameImage;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public Integer getSizeImage() {
        return sizeImage;
    }

    public void setSizeImage(Integer sizeImage) {
        this.sizeImage = sizeImage;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getWidthImage() {
        return widthImage;
    }

    public void setWidthImage(Integer widthImage) {
        this.widthImage = widthImage;
    }

    public Integer getHeightImage() {
        return heightImage;
    }

    public void setHeightImage(Integer heightImage) {
        this.heightImage = heightImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayNameImage);
        dest.writeString(this.nameImage);
        dest.writeValue(this.sizeImage);
        dest.writeString(this.thumbnail);
        dest.writeValue(this.widthImage);
        dest.writeValue(this.heightImage);
    }

    public DataSet() {
    }

    protected DataSet(Parcel in) {
        this.displayNameImage = in.readString();
        this.nameImage = in.readString();
        this.sizeImage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.thumbnail = in.readString();
        this.widthImage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.heightImage = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<DataSet> CREATOR = new Parcelable.Creator<DataSet>() {
        @Override
        public DataSet createFromParcel(Parcel source) {
            return new DataSet(source);
        }

        @Override
        public DataSet[] newArray(int size) {
            return new DataSet[size];
        }
    };
}
