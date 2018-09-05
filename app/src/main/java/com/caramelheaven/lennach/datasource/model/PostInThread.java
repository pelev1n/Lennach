package com.caramelheaven.lennach.datasource.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostInThread {

    @SerializedName("Error")
    @Expose
    private Object error;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Num")
    @Expose
    private Integer num;

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
