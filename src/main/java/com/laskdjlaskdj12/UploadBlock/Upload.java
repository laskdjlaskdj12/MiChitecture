package com.laskdjlaskdj12.UploadBlock;

import com.google.gson.Gson;
import com.laskdjlaskdj12.ScanBlockStruct.ScanBlockStruct;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

public class Upload {

    public Upload() {
    }

    public boolean uploadBlockInfo(Vector<ScanBlockStruct> Block, String PlayerName) {

        if (Block == null) {
            System.out.println("BlockList 업로드가 실패했습니다.");
            return false;
        }


        JSONArray BlockDataList = new JSONArray();
        for (ScanBlockStruct ScanBlockIterator : Block) {

            BlockCordinateStruct info = new BlockCordinateStruct(ScanBlockIterator);

            Gson gson = new Gson();
            String Json = gson.toJson(info);
            BlockDataList.add(Json);
        }

        JSONObject SendData = new JSONObject();
        SendData.put("data", BlockDataList);
        SendData.put("userID", PlayerName);

        String JsonSendData = SendData.toJSONString();

        //블록을 전송
        try {

            URL url = new URL("http://localhost:8080/upload");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);

            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(JsonSendData.getBytes());
            os.flush();

            // 블록이 저장되면 인덱스를 불러오기
            String RecvBuffer;
            double RecvIndex = 0;
            InputStream input = conn.getInputStream();
            InputStreamReader Reader = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(Reader);

            while ((RecvBuffer = reader.readLine()) != null) {
                RecvIndex = Double.parseDouble(RecvBuffer);
            }

            System.out.println("저장 인덱스 : " + RecvBuffer);

            if (RecvIndex < 1) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
