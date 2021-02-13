package com.example.folderaccess.model;

import java.util.ArrayList;
import java.util.HashMap;

public class FileListResponse {
    private String url;
    private ArrayList<FolderEntry> files;

    public FileListResponse(String url, ArrayList<FolderEntry> files) {
        this.url = url;
        this.files = files;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<FolderEntry> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<FolderEntry> files) {
        this.files = files;
    }

    public String getUrl() {
        return url;
    }
}
