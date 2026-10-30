package com.taller.proxy.service;

import com.taller.proxy.exception.RateLimitExceededException;
import com.taller.proxy.model.GenerationRequest;
import com.taller.proxy.model.GenerationResponse;
import com.taller.proxy.model.UserAccount;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class RateLimitProxyService implements AIGenerationService {
    private final AIGenerationService nextService;
    private final UserAccountService accountService;

    public RateLimitProxyService(@Qualifier("quotaProxyService") AIGenerationService nextService, UserAccountService accountService) {
        this.nextService = nextService;
        this.accountService = accountService;
    }

    @Override
    public GenerationResponse generate(GenerationRequest request) {
        UserAccount user = accountService.getUser();
        if (user.getRequestsThisMinute() >= user.getPlan().requestsPerMinute) {
            throw new RateLimitExceededException("Límite de peticiones por minuto excedido.");
        }
        user.setRequestsThisMinute(user.getRequestsThisMinute() + 1);
        return nextService.generate(request);
    }
}