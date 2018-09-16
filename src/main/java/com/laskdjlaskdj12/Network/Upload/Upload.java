package com.laskdjlaskdj12.Network.Upload;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Upload implements Runnable {

    private String blockAreaJson;
    private String playerUID;

    private Player player;

    public Upload(String blockAreaJson, Player player) {
        this.blockAreaJson = blockAreaJson;
        this.playerUID = player.getUniqueId().toString();
    }

    public Upload(String blockAreaJson, String playerUID){
        this.blockAreaJson = blockAreaJson;
        this.playerUID = playerUID;
    }

    @Override
    public void run() {

        //블록을 전송
        try {
            URL url = new URL("http://localhost:8080/upload");

            String responseUpload = requestUpload(url, blockAreaJson, playerUID);

            System.out.print(responseUpload);

        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private String requestUpload(URL url, String blockAreaJson, String playerID) throws IOException {
        String uploadStatus;

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(String.valueOf(url));

            List<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("data", blockAreaJson));
            param.add(new BasicNameValuePair("userID", playerID));

            httpPost.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse response = client.execute(httpPost);

            if(response.getStatusLine().getStatusCode() != 200){
                HashMap resultMap = new HashMap<>();
                resultMap.put("ErrorCode", response.getStatusLine().getStatusCode());

                Gson gson = new Gson();
                uploadStatus = gson.toJson(resultMap);
            }else{

                uploadStatus = EntityUtils.toString(response.getEntity());
            }
        }

        return uploadStatus;
    }
}
