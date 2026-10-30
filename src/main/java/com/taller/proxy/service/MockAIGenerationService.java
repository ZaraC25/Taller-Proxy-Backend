package com.taller.proxy.service;

import com.taller.proxy.model.GenerationRequest;
import com.taller.proxy.model.GenerationResponse;
import org.springframework.stereotype.Service;

@Service
public class MockAIGenerationService implements AIGenerationService {
    @Override
    public GenerationResponse generate(GenerationRequest request) {
        try { Thread.sleep(1200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        String[] respuestas = {
            "La inteligencia artificial es fascinante.",
            "Aquí tienes una idea: usa el patrón Proxy.",
            "El clima de hoy es soleado y agradable.",
            "Para resolver eso, divide y vencerás.",
            "El texto generado es totalmente aleatorio."
        };
        int randomIndex = (int) (Math.random() * respuestas.length);
        String text = respuestas[randomIndex] + " [Procesado prompt: " + request.getPrompt() + "]";
        return new GenerationResponse(text);
    }
}