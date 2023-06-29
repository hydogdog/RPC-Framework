package exception;

/**
 * @ClassName RpcException
 * @Description TODO
 * @Author hydogdog
 * @Date 2023/6/25 17:38
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error) {
        super(error.getMessage());
    }


}
