package com.taller.proxy.service;

import com.taller.proxy.model.GenerationRequest;
import com.taller.proxy.model.GenerationResponse;

public interface AIGenerationService {
    GenerationResponse generate(GenerationRequest request);
}