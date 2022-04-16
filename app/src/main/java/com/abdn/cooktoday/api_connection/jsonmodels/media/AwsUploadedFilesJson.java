package com.abdn.cooktoday.api_connection.jsonmodels.media;

import java.util.List;

public class AwsUploadedFilesJson {
    private List<String> files;

    public AwsUploadedFilesJson(List<String> files) {
        this.files = files;
    }

    public List<String> getFiles() {
        return files;
    }
}
