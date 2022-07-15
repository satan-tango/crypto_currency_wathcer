package com.crypto.currency.cryptowatcher.service;

import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


@Service
public class CryptoCurrencyService {

    public String getCurrentCryptoPrice(String code) {
        String response = getDataFromApi(code);
        JSONParser parser = new JSONParser();
        String currentPrice = "";
        try {
            JSONArray rootJsonArray = (JSONArray) parser.parse(response);
            JSONObject rootJsonObject = (JSONObject) rootJsonArray.get(0);
            currentPrice = String.valueOf(rootJsonObject.get("price_usd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return currentPrice;
    }


    private String getDataFromApi(String code) {
        String response = "";
        final String APIUrl = "https://api.coinlore.net/api/ticker";
        try {
            URIBuilder uriBuilder = new URIBuilder(APIUrl);
            uriBuilder.addParameter("id", code);
            URL url = uriBuilder.build().toURL();

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder res = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                res.append(inputLine);
            }
            in.close();
            response = String.valueOf(res);
            return response;

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
