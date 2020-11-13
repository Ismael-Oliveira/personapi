package roca.made.personapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum PhoneType {

    HOME("home"),
    MOBILE("mobile"),
    COMMERCIAL("commercial");

    private final String description;

    PhoneType(String description) {
        this.description = description;
    }

}
