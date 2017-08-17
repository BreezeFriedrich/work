package com.yishu.domain;

public class FileUploadResult<T> {
    private boolean isSuccessful;
    private T info;//T类型应当为UploadFailInfo或者UploadSuccessInfo.

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}