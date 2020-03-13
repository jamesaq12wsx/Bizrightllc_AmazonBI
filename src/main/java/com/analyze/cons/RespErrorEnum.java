package com.analyze.cons;

/**
 * @ClassName RespErrorEnum
 * @Description 返回错误码
 */
public enum RespErrorEnum {

    //新增的statusCode
    BAD_REQ_DATA("402", "Bad Request Data"),
    FORBIDDEN("403", "Business Forbbiden"),
    NOT_FOUND_DATA("404", "Not Found Data"),

    PARAM_INVALID("50000", "参数无效"),
    SERVICE_NO_RESPOND("50001","服务器无返回数据"),
    SERVICE_DATA_EXPC("50002","服务器数据异常"),
    FILE_NOT_EXIT("50003","文件不存在"),
    GENERATE_TASK_ERROR("50004","任务生成失败"),
    UPDATE_TASK_ERROR("50005","任务更新失败"),


    ;


    private String subStatusCode;
    private String subStatusMsg;

    RespErrorEnum(String subStatusCode, String subStatusMsg) {
        this.subStatusCode = subStatusCode;
        this.subStatusMsg = subStatusMsg;
    }

    public String getSubStatusMsg(){
        return this.subStatusMsg;
    }
    public String getSubStatusCode(){
        return this.subStatusCode;
    }


    public static RespErrorEnum getErrorEnum(String subStatusCode){
        for(RespErrorEnum errorEnum : RespErrorEnum.values()){
            if(errorEnum.getSubStatusCode().equalsIgnoreCase(subStatusCode)){
                return errorEnum;
            }
        }
        return RespErrorEnum.PARAM_INVALID;
    }
}
