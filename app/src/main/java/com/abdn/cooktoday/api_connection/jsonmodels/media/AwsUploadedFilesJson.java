package com.abdn.cooktoday.api_connection.jsonmodels.media;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AwsUploadedFilesJson {
    private List<String> files;

    public AwsUploadedFilesJson(List<String> files) {
        this.files = files;
    }

    public AwsUploadedFilesJson(JSONArray filesJson) {
        this.files = new ArrayList<>();
        for (int i = 0; i < filesJson.length(); i++) {
            try {
                this.files.add(filesJson.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getFiles() {
        return files;
    }
}
