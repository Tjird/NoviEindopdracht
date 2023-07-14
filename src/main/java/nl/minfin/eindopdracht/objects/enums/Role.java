package nl.minfin.eindopdracht.objects.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN(Roles.ADMIN),
    BACKOFFICE(Roles.BACKOFFICE),
    CASHIER(Roles.CASHIER),
    MECHANIC(Roles.MECHANIC);

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public @Override String getAuthority() {
        return this.authority;
    }

}
