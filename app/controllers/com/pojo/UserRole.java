package controllers.com.pojo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {

    CUSTOMER("customer"),
    SELLER("seller");
    private final String value;
    private final static Map<String, UserRole> CONSTANTS = new HashMap<String, UserRole>();

    static {
        for (UserRole c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private UserRole(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static UserRole fromValue(String value) {
        UserRole constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
