package com.laskdjlaskdj12.Network.Download;

import com.google.gson.Gson;
import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import com.laskdjlaskdj12.VO.BlockAreaVO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.net.URL;

public class Download implements Runnable{

    private final String PlayerUID;
    private final String DownloadCode;
    private final PlayerBlockStorageCache cache;

    public Download(String PlayerUID, String DownloadCode, PlayerBlockStorageCache cache){
        this.PlayerUID = PlayerUID;
        this.DownloadCode = DownloadCode;
        this.cache = cache;
    }

    @Override
    public void run() {

        try {
            URL url = new URL("http://localhost:8080/download");

            String responceDonwload = requestDownload(url, DownloadCode, PlayerUID);

            Gson gson = new Gson();

            BlockAreaVO blockAreaVO  = gson.fromJson(responceDonwload, BlockAreaVO.class);

            //만약 블록이 없다면 에러메세지를 리턴
            if(blockAreaVO == null){
                System.out.println("Convert From Donwload Block is NULL");
                return;
            }

            //받아온 블록을 저장 요청하기
            cache.saveBlockArea(PlayerUID, blockAreaVO);

        } catch (MalformedURLException | UnirestException e) {
            e.printStackTrace();
        }
    }

    private String requestDownload(URL url, String downloadCode, String playerUID) throws RuntimeException, UnirestException {

        HttpResponse<JsonNode> result = Unirest.post(url.toString())
                .field("BlockAreaID", downloadCode)
                .field("UserID", playerUID)
                .asJson();

        return result.getBody().toString();
    }


}