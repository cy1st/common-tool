package com.cxy.tool;

/**
 * @author CXY
 * @className ResponseResult
 * @description 统一封装返回值
 * @date 2025/01/16 10:44
 */
public class ResponseResult<T> {

    private int code;

    private String message;

    private T data;

    public ResponseResult() {
    }

    public ResponseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult<T> success(T data, String message){
        return new ResponseResult<T>(Code.SUCCESS.getKey(),message,data);
    }

    public ResponseResult<T> success(T data){
        Code success = Code.SUCCESS;
        return new ResponseResult<T>(success.getKey(),success.getValue(),data);
    }

    public ResponseResult<T> fail(T data, String message){
        return new ResponseResult<T>(Code.FAIL.getKey(),message,data);
    }

    public ResponseResult<T> fail(T data){
        Code fail = Code.FAIL;
        return new ResponseResult<T>(fail.getKey(),fail.getValue(),data);
    }

    public ResponseResult<T> error(T data, String message){
        return new ResponseResult<T>(Code.SERVER_ERROR.getKey(),message,data);
    }

    public ResponseResult<T> error(T data){
        Code fail = Code.FAIL;
        return new ResponseResult<T>(fail.getKey(),fail.getValue(),data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * 状态码
     */
    public enum Code{

        SUCCESS(200,"成功"),

        SERVER_ERROR(500,"服务器异常"),

        FAIL(-1,"失败");

        private final int key;

        private final String value;

        Code(int key,String value){
            this.key = key;
            this.value = value;
        }

        public int getKey(){
            return this.key;
        }

        public String getValue(){
            return this.value;
        }

    }

}
