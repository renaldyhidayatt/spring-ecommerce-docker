package com.sanedge.myecommerce.auth.user;

public enum ApplicationUserPermission {
    READ_SALES("read:sales"),
    WRITE_SALES("write:sales"),
    WRITE_REVIEWS("write:reviews"),
    WRITE_CATEGORIES("write:categories");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
