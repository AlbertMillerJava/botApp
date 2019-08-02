package com.millerBot.models;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.HashMap;
import java.util.Map;

public class Account {

    String apiKey;

    public Account(String apiKey) {
        this.apiKey = apiKey;
    }


    public String getBalances(){
        String json = "";
        String apikey = "xxx";
        String apisecret = "xxx";
        String nonce = String.valueOf(System.currentTimeMillis());
        String uri = "https://bittrex.com/api/v1.1/account/getbalances?apikey=" + apikey + "&nonce=" + nonce;
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secret = new SecretKeySpec(apisecret.getBytes(), "HmacSHA512");

            mac.init(secret);

            byte[] digest = mac.doFinal(uri.getBytes());
            String sign = new String(digest);
            sign = org.apache.commons.codec.binary.Hex.encodeHexString(digest);
            HttpURLConnection con = (HttpURLConnection) new URL(uri).openConnection();
            con.setRequestProperty("apisign", sign);
            con.setRequestMethod("GET");
            con.connect();

            con.getInputStream();
            InputStream inputStream = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            json = bufferedReader.readLine();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException y) {
            y.printStackTrace();
        }

        return json;
    }

    public Map <String,Double> getAvailable(){
        Map<String,Double> availableMap = new HashMap<>();
        String json = getBalances();
        String[] temp = json.split("Currency\":");
        for (int i = 1 ; i< temp.length; i++){
            String [] prepareData = temp[i].split(",");

            String currency = prepareData[0];

            String [] prepareData3 = temp[i].split("\"Available\":");
            String[] prepareData4 = prepareData3[1].split(",");
            double currecnyAvailable = Double.parseDouble(prepareData4[0]);
            availableMap.put(currency,currecnyAvailable);

        }
        return availableMap;

    }
    public double getAvilableCurrency(){


        double quantity = getAvailable().get("LTC");
        return quantity;

    }

    public double getRate(){
        double rate;
        String json = "";
        try {
            String currency = "LTC";
            currency.replaceAll("\"","");
            URL url = new URL("https://api.bittrex.com/api/v1.1/public/getticker?market=BTC-"+currency);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            json = bufferedReader.readLine();
            System.out.println(json);

        }catch (IOException e ){
            e.printStackTrace();
           try{
               Thread.sleep(5000);
           }catch (InterruptedException x){
               x.printStackTrace();
           }

        }
            String[] prepare = json.split("Ask\":");

            String [] prepare2 = prepare[1].split(",");
            rate =Double.parseDouble(prepare2[0]);
            return rate;
    }
}
