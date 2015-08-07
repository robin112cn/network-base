package com.whty.cms.base.ucenter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @ClassName: ResponseResult
 * @Description: 统一请求返回结果model
 * @author Qing.Luo
 * @date 2015年5月28日 上午10:07:59
 * @version
 *
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseResult<T> {

    private boolean success;

    private String message;

    private T data;

    /* 不提供直接设置errorCode的接口，只能通过setErrorInfo方法设置错误信息 */
    private String errorCode;

    private ResponseResult() {
    }

    public static <T> ResponseResult<T> newInstance() {
        return new ResponseResult<T>();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public T getTData(Class<T> t) {
    	String json = JacksonMapper.toJsonString(getData());
        return JacksonMapper.jsonToClass(json,t);
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
