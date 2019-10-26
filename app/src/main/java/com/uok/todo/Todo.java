package com.uok.todo;

import com.google.gson.annotations.SerializedName;

public class Todo {

    @SerializedName("id")
    private String id;

    @SerializedName("data")
    private Data data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

class Data {
    @SerializedName("status")
    private boolean status;

    @SerializedName("name")
    private String name;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}