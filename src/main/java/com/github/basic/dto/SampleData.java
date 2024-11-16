package com.github.basic.dto;

public class SampleData {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SampleData(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
