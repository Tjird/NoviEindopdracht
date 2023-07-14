package nl.minfin.eindopdracht.objects.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    BACKOFFICE(Roles.BACKOFFICE),
    MECHANIC(Roles.MECHANIC),
    CASHIER(Roles.CASHIER),
    ADMIN(Roles.ADMIN);

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
