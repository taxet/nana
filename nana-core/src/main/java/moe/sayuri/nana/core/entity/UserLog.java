package moe.sayuri.nana.core.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserLog {
    private String requestId;
    private String uri;
    private String params;
    private String method;
    private String ip;
    private String body;
    private String user;
    private Date startTime;
    private Date endTime;
    private String result;
    private String exception;
}
