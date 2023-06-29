package enmu;

/**
 * @author hydogdog
 */

public enum PackageType {
    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;

    PackageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
