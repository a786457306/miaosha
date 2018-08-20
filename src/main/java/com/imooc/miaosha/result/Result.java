package com.imooc.miaosha.result;


/**
 * Result
 *
 * @author Daydreamer
 * @date 2018/8/17 10:12
 */
public class Result<T> {
    private int code;
    private String message;
    private T data;

    /**
     * 成功时调用
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 失败时调用
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm){
        return new Result<T>(cm);
    }

    //并不希望用户通过构造函数声称对象，所以用private，让用户调用封装好的方法
    private Result(T data){
        this.code = 0;
        this.message = "success";
        this.data = data;
    }

    private Result(CodeMsg cm){
        if (cm == null){
            return;
        }
        this.code = cm.getCode();
        this.message = cm.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
