package com.taller.proxy.model;

public enum Plan {
    FREE(10, 50000, "FREE"),
    PRO(60, 500000, "PRO"),
    ENTERPRISE(9999, 9999999, "ENTERPRISE");

    public final int requestsPerMinute;
    public final int monthlyTokens;
    public final String displayName;

    Plan(int requestsPerMinute, int monthlyTokens, String displayName) {
        this.requestsPerMinute = requestsPerMinute;
        this.monthlyTokens = monthlyTokens;
        this.displayName = displayName;
    }
}