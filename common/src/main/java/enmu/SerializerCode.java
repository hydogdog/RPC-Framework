package enmu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hydogdog
 */

@AllArgsConstructor
@Getter
public enum SerializerCode {
    KRYO(0),
    JSON(1),
    HESSIAN(2),
    PROTOBUF(3);

    private final int code;
}
