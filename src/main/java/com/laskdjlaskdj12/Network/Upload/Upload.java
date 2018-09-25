package com.laskdjlaskdj12.Network.Upload;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.URL;

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

            if(responseUpload.isEmpty()){
                System.out.println("업로드에 실패했습니다.");
                System.out.println("에러메세지 : " + responseUpload);
                return;
            }

            System.out.println("업로드에 성공했습니다.");
            System.out.print("업로드 ID : " + responseUpload);

        } catch (IOException | NullPointerException | UnirestException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private String requestUpload(URL url, String blockAreaJson, String playerID) throws UnirestException {
        HttpResponse<String> responce = Unirest.post(url.toString())
                .field("userID", playerID)
                .field("data", blockAreaJson)
                .asString();

        return responce.getBody();
    }
}
