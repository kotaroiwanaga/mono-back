package jp.co.monocrea.enums;

/**
 * ソート順序を表す列挙型
 */
public enum OrderType {
    ASC("ASC"),
    DESC("DESC");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OrderType fromKey(String key) {
       return OrderType.valueOf(key.toUpperCase());
    }
}
