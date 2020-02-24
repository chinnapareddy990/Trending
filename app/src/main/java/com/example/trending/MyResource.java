package com.example.trending;

class MyResource {

    String technology,filepath,videoid;

    public MyResource() {
    }

    public MyResource(String technology, String filepath, String videoid) {
        this.technology = technology;
        this.filepath = filepath;
        this.videoid = videoid;
    }

    public String getTechnology() {
        return technology;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getVideoid() {
        return videoid;
    }
}
