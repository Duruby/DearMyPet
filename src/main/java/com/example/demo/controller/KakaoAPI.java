package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class KakaoAPI {

    public String getAccessToken(String code) {
    	System.out.println("code:"+code);
        String accessToken = "";
        String refreshToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
        	URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=4c8dd27831f9ef2d08913b5d30ed2d00");//javascript키
            sb.append("&redirect_url=http://localhost:8090/login");
            sb.append("&code=" + code);
            
            bw.write(sb.toString());
			bw.flush();
			
			int responseCode = conn.getResponseCode();
			System.out.println("response code = " + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line = "";
			String result = "";
			while((line = br.readLine())!=null) {
				result += line;
			}
			System.out.println("response body="+result);
			
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			
			accessToken = element.getAsJsonObject().get("access_token").getAsString();
			refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();
			
			br.close();
			bw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
    }

    public HashMap<String, Object> getUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqUrl = "https://kapi.kakao.com/v2/user/me";
        try {
        	URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode =" + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line = "";
			String result = "";
			
			while((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body ="+result);
			
			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);
			
			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
			System.out.println(kakaoAccount);
			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			System.out.println(nickname);
			String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
			System.out.println(email);
			
			
			userInfo.put("nickname", nickname);
			userInfo.put("email", email);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfo;
    }

    public void kakaoLogout(String accessToken) {
        String reqURL = "http://kapi.kakao.com/v1/user/logout";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void unlink(String access_Token) { 
    	String reqURL = "https://kapi.kakao.com/v1/user/unlink";
    	 try {
    	 URL url = new URL(reqURL); 
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
    	conn.setRequestMethod("POST");
    	 conn.setRequestProperty("Authorization", "Bearer " + access_Token); 

    	int responseCode = conn.getResponseCode(); 
    	System.out.println("responseCode : " + responseCode);

    	 BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); 

    	String result = ""; 
    	String line = ""; 

    	while ((line = br.readLine()) != null) { 
    	result += line;
    	 } 
    	System.out.println(result);
    	 } catch (IOException e) {
    	 e.printStackTrace();
    	 } 
    	}

}