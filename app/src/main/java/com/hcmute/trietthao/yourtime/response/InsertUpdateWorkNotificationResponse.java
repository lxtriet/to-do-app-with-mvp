package com.hcmute.trietthao.yourtime.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lttha on 11/11/2017.
 */

public class InsertUpdateWorkNotificationResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public InsertUpdateWorkNotificationResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public InsertUpdateWorkNotificationResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
