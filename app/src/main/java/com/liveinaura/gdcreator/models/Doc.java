package com.liveinaura.gdcreator.models;

public class Doc {
    private String name;
    private String path;
    private long date;

    public Doc(String name, String path, long date) {
        this.name = name;
        this.path = path;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
