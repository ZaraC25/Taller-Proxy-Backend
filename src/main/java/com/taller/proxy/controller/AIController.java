package com.taller.proxy.controller;

import com.taller.proxy.model.GenerationRequest;
import com.taller.proxy.model.GenerationResponse;
import com.taller.proxy.model.UserAccount;
import com.taller.proxy.model.Plan;
import com.taller.proxy.service.AIGenerationService;
import com.taller.proxy.service.UserAccountService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AIController {

    private final AIGenerationService aiService;
    private final UserAccountService accountService;

    public AIController(AIGenerationService aiService, UserAccountService accountService) {
        this.aiService = aiService;
        this.accountService = accountService;
    }

    @PostMapping("/ai/generate")
    public ResponseEntity<?> generate(@RequestBody GenerationRequest request) {
        try {
            GenerationResponse response = aiService.generate(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals("RateLimitExceededException")) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Retry-After", "10");
                return ResponseEntity.status(429).headers(headers).body(Map.of("error", e.getMessage()));
            }
            throw e;
        }
    }

    @GetMapping("/quota/status")
    public ResponseEntity<?> getStatus() {
        UserAccount user = accountService.getUser();
        return ResponseEntity.ok(Map.of(
            "plan", user.getPlan().displayName,
            "usedTokens", user.getUsedTokensThisMonth(),
            "remainingTokens", user.getPlan().monthlyTokens - user.getUsedTokensThisMonth(),
            "requestsThisMinute", user.getRequestsThisMinute(),
            "maxRequestsPerMinute", user.getPlan().requestsPerMinute,
            "resetDate", user.getLastResetDate().plusMonths(1).toString()
        ));
    }

    @GetMapping("/quota/history")
    public ResponseEntity<?> getHistory() {
        return ResponseEntity.ok(accountService.getUser().getHistoryTokens());
    }

    @PostMapping("/quota/upgrade")
    public ResponseEntity<?> upgrade() {
        UserAccount user = accountService.getUser();
        if (user.getPlan() == Plan.FREE) {
            user.setPlan(Plan.PRO);
            return ResponseEntity.ok(Map.of("message", "Upgrade a PRO exitoso"));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "No se puede actualizar"));
    }
}