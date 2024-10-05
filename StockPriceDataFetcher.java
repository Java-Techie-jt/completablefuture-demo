package com.javatechie.async;

import java.util.concurrent.CompletableFuture;

public class StockPriceDataFetcher {


    public CompletableFuture<Double> fetchStockPriceFromApi1(String symbol) {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate a network delay
            simulateDelay(2000); // Simulate a delay of 2 seconds
            return 150.0; // Simulated stock price from API 1
        });
    }

    public CompletableFuture<Double> fetchStockPriceFromApi2(String symbol) {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate a network delay
            simulateDelay(1000); // Simulate a delay of 3 seconds
            return 155.0; // Simulated stock price from API 2
        });
    }

    private void simulateDelay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {

        StockPriceDataFetcher fetcher = new StockPriceDataFetcher();

        String stockSymbol = "APPL"; //apple inc

        //fetch stock price from both the API
        CompletableFuture<Double> api1Results = fetcher.fetchStockPriceFromApi1(stockSymbol);
        CompletableFuture<Double> api2Results = fetcher.fetchStockPriceFromApi2(stockSymbol);

        //Use anyOf to wait any of the future to complete
        CompletableFuture<Object> anyOfResults = CompletableFuture.anyOf(api1Results, api2Results);

        //process the result
        anyOfResults.thenAccept(price -> {
            System.out.println("Received stock price : $" + price);
        }).join();


    }
}
