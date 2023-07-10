package serializer;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import enmu.SerializerCode;
import exception.SerialException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @ClassName HessianSerializer
 * @Description TODO
 * @Author hydogdog
 * @Date 2023/6/29 16:37
 */
@Slf4j
public class HessianSerializer implements CommonSerializer{
    @Override
    public byte[] serialize(Object obj) {
        Hessian2Output output = null;
        try {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            output = new Hessian2Output(byteArrayOutputStream);
            output.writeObject(obj);
            output.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new SerialException("序列化时有错误发生");
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        Hessian2Input hessian2Input = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            hessian2Input = new Hessian2Input(byteArrayInputStream);
            return hessian2Input.readObject();

        } catch (IOException e) {
            throw new SerialException("反序列化时有错误发生");
        } finally {
            try {
                hessian2Input.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public int getCode() {
        return SerializerCode.HESSIAN.getCode();
    }
}
