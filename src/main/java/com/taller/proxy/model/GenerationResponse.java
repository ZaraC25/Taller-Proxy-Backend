package com.taller.proxy.model;

public class GenerationResponse {
    private String response;

    public GenerationResponse() {}
    public GenerationResponse(String response) { this.response = response; }
    
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
}