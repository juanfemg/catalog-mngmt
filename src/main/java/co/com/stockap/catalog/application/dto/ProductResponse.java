package co.com.stockap.catalog.application.dto;

public record ProductResponse(String name, String category, double price, int quantity, String status) { }
