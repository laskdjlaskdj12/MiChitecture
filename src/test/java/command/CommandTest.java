package command;

import com.google.gson.Gson;
import com.laskdjlaskdj12.Network.Upload.Upload;
import com.laskdjlaskdj12.VO.BlockInfoVO;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandTest {

    @Test
    public void uploadToServerTest(){
        String url = "http://localhost:8080/upload";

        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("data", "NoneData"));
        param.add(new BasicNameValuePair("userID", "NoneData"));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(param));
            CloseableHttpResponse response = client.execute(httpPost);

            String ResponceBody = EntityUtils.toString(response.getEntity());

            System.out.println(ResponceBody);

            Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void uploadBlockAddressTest(){
        List<BlockInfoVO> list = new ArrayList<>();

        //예시값들을 넣음
        for(int i = 0; i < 50; i++){
            BlockInfoVO block = new BlockInfoVO();
            block.setX((double)i);
            block.setY((double)i);
            block.setZ((double)i);
            block.setMaterialID(i);

            list.add(block);
        }

        //json으로 변환함
        Gson gson = new Gson();
        String blockData = gson.toJson(list);
        Assert.assertFalse(blockData == null);

        //플레이어 만듬
        String userID = "Test12345";

        Upload uploader = new Upload(blockData, userID);

        uploader.run();
    }
}
