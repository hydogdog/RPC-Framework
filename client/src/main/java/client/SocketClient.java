package client;

import dto.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient implements RpcClient{
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private String host;

    private int port;


    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try (Socket socket = new Socket("127.0.0.1", 9999)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用时有错误发生：", e);
            logger.error(e.getMessage());
            return null;
        }

    }


}
