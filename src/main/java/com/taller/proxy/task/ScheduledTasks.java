package com.taller.proxy.task;

import com.taller.proxy.service.UserAccountService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class ScheduledTasks {
    private final UserAccountService accountService;

    public ScheduledTasks(UserAccountService accountService) {
        this.accountService = accountService;
    }

    @Scheduled(fixedRate = 60000)
    public void resetRateLimit() {
        accountService.getUser().setRequestsThisMinute(0);
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetMonthlyQuota() {
        accountService.getUser().setUsedTokensThisMonth(0);
        accountService.getUser().setLastResetDate(LocalDate.now());
    }
}