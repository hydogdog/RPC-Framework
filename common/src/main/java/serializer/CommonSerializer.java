package serializer;

import javax.sql.rowset.serial.SerialException;

/**
 * @author hydogdog
 */
public interface CommonSerializer {
    /**
     * json序列化标识
     */
    int JSON_SERIALIZER = 1;

    /**
     * 序列化
     * @param obj 要序列化的对象
     * @return 字节数组
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     * @param bytes 字节数组
     * @param clazz 要反序列化的类
     * @return 反序列化的对象
     */
    Object deserialize(byte[] bytes, Class<?> clazz) throws SerialException;

    /**
     * 获取序列化器的编号
     * @return 序列化器编号
     */
    int getCode();

    /**
     * 根据编号获取序列化器
     * @param code 序列化器编号
     * @return 序列化器
     */
    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            case 2:
                return new HessianSerializer();
            default:
                return null;
        }
    }
}
