package com.taller.proxy.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class UserAccount {
    private Plan plan = Plan.FREE;
    private int requestsThisMinute = 0;
    private int usedTokensThisMonth = 0;
    private LocalDate lastResetDate = LocalDate.now();
    private List<Integer> historyTokens = new ArrayList<>();

    public UserAccount() {
        for(int i=0; i<7; i++) historyTokens.add(0);
    }

    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) { this.plan = plan; }
    public int getRequestsThisMinute() { return requestsThisMinute; }
    public void setRequestsThisMinute(int requestsThisMinute) { this.requestsThisMinute = requestsThisMinute; }
    public int getUsedTokensThisMonth() { return usedTokensThisMonth; }
    public void setUsedTokensThisMonth(int usedTokensThisMonth) { this.usedTokensThisMonth = usedTokensThisMonth; }
    public LocalDate getLastResetDate() { return lastResetDate; }
    public void setLastResetDate(LocalDate lastResetDate) { this.lastResetDate = lastResetDate; }
    public List<Integer> getHistoryTokens() { return historyTokens; }
}