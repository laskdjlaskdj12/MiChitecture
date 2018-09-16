//package com.laskdjlaskdj12.Network.Download;
//
//import com.google.gson.*;
//import com.laskdjlaskdj12.Type.BlockCordinateStruct;
//import jdk.nashorn.internal.ir.Block;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Vector;
//
//import static java.sql.DriverManager.println;
//
//public class Download {
//
//    public Vector<BlockCordinateStruct> RequestBlockList(String requestBlockListPid) throws Exception{
//
//        try {
//            URL url = new URL("http://localhost:8080/download");
//            URLConnection conn = url.openConnection();
//
//            String Parameter = "BlockListPid=" + requestBlockListPid;
//
//            conn.setDoOutput(true);
//            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//            wr.write(Parameter);
//            wr.flush();
//
//            // 블록이 저장되면 인덱스를 불러오기
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            String BlockListJsonString = null;
//            while(in.ready()){
//                BlockListJsonString = in.readLine();
//            }
//
//            in.close();
//            wr.close();
//
//            Vector<BlockCordinateStruct> returnBlockCordinateStruct = new Vector<BlockCordinateStruct>();
//
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(BlockListJsonString);
//            JsonArray BlockListArray = element.getAsJsonArray();
//
//            for(JsonElement RecvBlockElement : BlockListArray){
//
//                BlockCordinateStruct BlockInfoVO = new BlockCordinateStruct();
//
//                BlockInfoVO.X = RecvBlockElement.getAsJsonObject().get("X").getAsDouble();
//                BlockInfoVO.Y = RecvBlockElement.getAsJsonObject().get("Y").getAsDouble();
//                BlockInfoVO.Z = RecvBlockElement.getAsJsonObject().get("Z").getAsDouble();
//                BlockInfoVO.MaterialName = RecvBlockElement.getAsJsonObject().get("MaterialName").toString();
//                returnBlockCordinateStruct.add(BlockInfoVO);
//            }
//
//            return returnBlockCordinateStruct;
//
//        }catch(IOException e){
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//
//            throw e;
//        }
//
//    }
//}
