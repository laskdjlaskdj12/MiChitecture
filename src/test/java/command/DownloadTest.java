package command;

import com.laskdjlaskdj12.Network.Download.Download;
import com.laskdjlaskdj12.Player.PlayerBlockStorageCache;
import org.junit.Assert;
import org.junit.Test;

public class DownloadTest {

    @Test
    public void downloadTest(){

        String playerUID = "1234";
        String downloadCode = "1234-1234";
        PlayerBlockStorageCache cache = new PlayerBlockStorageCache();

        Download download = new Download("1234", downloadCode, cache);
        download.run();


        Assert.assertNotNull(cache.getSavedDownloadedBlock());
        Assert.assertEquals(cache.getSavedDownloadedBlock().size(),1);
    }
}
