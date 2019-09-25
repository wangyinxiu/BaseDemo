package com.xiu.data.bean.response;

public class Token {

    private String token;

    private String userId;

    private String timeout;

    private String cycle;
    /**
     * 控车指令时间
     */
    private String instructTime;

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getTimeout() {
        return timeout;
    }

    public String getCycle() {
        return cycle;
    }

    public String getInstructTime() {
        return instructTime;
    }
}
