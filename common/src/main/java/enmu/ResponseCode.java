package enmu;

/**
 * @author hydogdog
 * 返回的枚举类型
 */
public enum ResponseCode {
    SUCCESS(200,"调用成功"),

    METHOD_NOT_FOUND(10100,"方法没有找到");


    /**
     * 返回的状态码
     */
    private Integer code;
    /**
     * 返回的信息
     */
    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
