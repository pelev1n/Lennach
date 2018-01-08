package com.caramelheaven.lennach.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergey F on 05.01.2018.
 */

public class File {

    @SerializedName("displayname")
    @Expose
    private String displayname;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("md5")
    @Expose
    private String md5;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nsfw")
    @Expose
    private Integer nsfw;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("tn_height")
    @Expose
    private Integer tnHeight;
    @SerializedName("tn_width")
    @Expose
    private Integer tnWidth;
    @SerializedName("type")
    @Expose
    private Long type;
    @SerializedName("width")
    @Expose
    private Integer width;

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNsfw() {
        return nsfw;
    }

    public void setNsfw(Integer nsfw) {
        this.nsfw = nsfw;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getTnHeight() {
        return tnHeight;
    }

    public void setTnHeight(Integer tnHeight) {
        this.tnHeight = tnHeight;
    }

    public Integer getTnWidth() {
        return tnWidth;
    }

    public void setTnWidth(Integer tnWidth) {
        this.tnWidth = tnWidth;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

}