package com.example.demo.exception;

public enum  CustomizeErrorCode implements ICustomizErrorCode {
    QUESTION_NOT_FOUND("数据不存在",2001),
    TAGRET_PARAM_NOT_FOUND("未选择内容回复",2002),
    USER_NOT_LOGIN("未登录，请先登录再评论",2003),
    SYSTEM_ERROR("服务器异常，请重试",2004),
    TYPE_PARAM_ERROR("类型错误，请重试",2005),
    COMMENT_NOT_FOUND("评论未找见",2006);
    private Integer code;
    private String message;


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(String message,Integer code){
        this.message = message;
        this.code = code;
    }
}
