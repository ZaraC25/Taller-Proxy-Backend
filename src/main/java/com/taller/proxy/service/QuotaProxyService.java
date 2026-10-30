package com.taller.proxy.service;

import com.taller.proxy.exception.QuotaExceededException;
import com.taller.proxy.model.GenerationRequest;
import com.taller.proxy.model.GenerationResponse;
import com.taller.proxy.model.UserAccount;
import org.springframework.stereotype.Service;

@Service("quotaProxyService")
public class QuotaProxyService implements AIGenerationService {
    private final AIGenerationService realService;
    private final UserAccountService accountService;

    public QuotaProxyService(MockAIGenerationService realService, UserAccountService accountService) {
        this.realService = realService;
        this.accountService = accountService;
    }

    @Override
    public GenerationResponse generate(GenerationRequest request) {
        UserAccount user = accountService.getUser();
        int estimatedTokens = request.getPrompt().length() / 4 + 10;

        if (user.getUsedTokensThisMonth() + estimatedTokens > user.getPlan().monthlyTokens) {
            throw new QuotaExceededException("Cuota mensual de tokens agotada.");
        }

        user.setUsedTokensThisMonth(user.getUsedTokensThisMonth() + estimatedTokens);
        user.getHistoryTokens().set(6, user.getUsedTokensThisMonth());
        return realService.generate(request);
    }
}