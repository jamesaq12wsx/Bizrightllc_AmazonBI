package com.analyze.model;

/**
 * External api request status
 */
public enum ApiRequestStatus {

    CREATE("create"),
    PROCESSING("processing"),
    FINISHED("finish"),
    ERROR("error");

    private String value;

    ApiRequestStatus(String status) {
        this.value = status;
    }

    public String getValue() {
        return value;
    }

    public static void main(String[] args) {
        System.out.println(ApiRequestStatus.CREATE.getValue());
    }
}
