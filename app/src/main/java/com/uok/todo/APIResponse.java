package com.uok.todo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIResponse {

    @SerializedName("errorCode")
    private Integer errorCode;

    @SerializedName("error")
    private String error;

    @SerializedName("items")
    private List<Todo> items;

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getError() {
        return error;
    }

    public List<Todo> getItems() {
        return items;
    }
}
