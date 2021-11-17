package cn.linshenkx.halosyncserver.httpclient;

import lombok.Data;

@Data
public class HaloErrorResult {
    private Integer status;
    private String message;
    private String devMessage;
    private String data;
}
