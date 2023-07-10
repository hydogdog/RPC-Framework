package serializer;

import javax.sql.rowset.serial.SerialException;

/**
 * @ClassName ProtoBufSerializer
 * @Description TODO
 * @Author hydogdog
 * @Date 2023/6/29 16:38
 */
public class ProtoBufSerializer implements CommonSerializer{
    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) throws SerialException {
        return null;
    }

    @Override
    public int getCode() {
        return 0;
    }
}
