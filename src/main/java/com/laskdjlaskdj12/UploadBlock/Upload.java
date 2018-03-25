package com.laskdjlaskdj12.UploadBlock;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
        Gson gson = new Gson();
        for (ScanBlockStruct ScanBlockIterator : Block) {

            BlockCordinateStruct info = new BlockCordinateStruct(ScanBlockIterator);

            String Json = gson.toJson(info);
            BlockDataList.add(Json);
        }

        String JsonSendData = gson.toJson(BlockDataList);

        //블록을 전송
        try {

            URL url = new URL("http://localhost:8080/upload");
            URLConnection conn = url.openConnection();

            String Parameter = "userID=" + PlayerName + "&data=" + JsonSendData;

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(Parameter);
            wr.flush();

            // 블록이 저장되면 인덱스를 불러오기
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String RecvBuffer;
            double ReturnIndex = 0;
            while ((RecvBuffer = in.readLine()) != null) {
                    ReturnIndex = Double.parseDouble(RecvBuffer);
            }
            in.close();
            wr.close();

            System.out.println("저장 인덱스 : " + RecvBuffer);

            if (ReturnIndex < 1) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
