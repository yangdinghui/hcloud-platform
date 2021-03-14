package hcloud.platform;

import hcloud.platform.modules.lock.service.RedissonLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class HcloudPlatformApplicationTests {

    @Autowired
    private RedissonLock lockService;

    @Test
    public void contextLoads() {
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(()->{
                lockService.lock(finalI +"", 1);
            }).start();
        }

    }
    @Test
    public void formatter(){
        String formatStr = String.format("(metaData.%s.metadataValue%s:%s)", "name", true ? "" : ".keyword", "tom");
        System.out.println(formatStr);
    }

    @Test
    public void date(){
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR,24);
        System.out.println(now.after(calendar.getTime()));
    }
}
