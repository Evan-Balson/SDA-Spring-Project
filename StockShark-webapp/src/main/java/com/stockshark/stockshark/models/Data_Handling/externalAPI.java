package com.stockshark.stockshark.models.Data_Handling;


import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public class externalAPI {
    private static final String API_KEY = "JGCLFOUSE8PP5S1P"; // Replace with your actual API key
    private static final HttpClient client = HttpClient.newHttpClient();

    public JSONObject getStockData(String symbol) throws IOException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date()); // Format today's date
        return getStockDataByDateRange(symbol, today, today);
    }

    public JSONObject getStockDataByDateRange(String symbol, String startDate, String endDate) throws IOException, InterruptedException {

        // Construct the URL based on the Alpha Vantage parameter details
        String url = "https://www.alphavantage.co/query?" +
                "function=TIME_SERIES_MONTHLY_ADJUSTED&symbol=" + symbol +
                "&outputsize=compact&datatype=json&apikey=" + API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());


        JSONObject jsonResponse = new JSONObject(response.body());



        return jsonResponse;
    }
}

