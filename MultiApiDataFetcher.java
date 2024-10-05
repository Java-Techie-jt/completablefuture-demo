package com.javatechie.async;

import java.util.concurrent.CompletableFuture;

public class MultiApiDataFetcher {

    public CompletableFuture<String> fetchWeatherData() {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay(2000); // Simulate network delay
            return "Weather: Sunny, 25°C";
        });
    }

    public CompletableFuture<String> fetchNewsHeadlines() {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay(3000); // Simulate network delay
            return "News: Java 23 Released!";
        });
    }

    public CompletableFuture<String> fetchStockPrices() {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay(1500); // Simulate network delay
            return "Stocks: AAPL - $150, GOOGL - $2800";
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

        MultiApiDataFetcher fetcher = new MultiApiDataFetcher();

        //combine multiple independent future (more than 2) -> allOf(n task)
        //-> weatherDetailsAPI
        CompletableFuture<String> weatherFuture = fetcher.fetchWeatherData();
        //-> news apis
        CompletableFuture<String> newsFuture = fetcher.fetchNewsHeadlines();
        //-> stockPrice apis
        CompletableFuture<String> stockPriceFuture = fetcher.fetchStockPrices();

        //wait for all future to complete
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(weatherFuture, newsFuture, stockPriceFuture);

        //process results after all future are completed
        allFutures.thenRun(() -> {
            String weather = weatherFuture.join();
            String news = newsFuture.join();
            String stock = stockPriceFuture.join();
            System.out.println("Aggregated Data : ");
            System.out.println(weather);
            System.out.println(news);
            System.out.println(stock);
        }).join();


    }
}
