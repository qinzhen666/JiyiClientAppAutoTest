import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import driver.App;
import driver.GlobalConfig;
import org.junit.jupiter.api.Test;
import page.MainPage;

import java.io.IOException;

public class TestDemo {

    @Test
    void mainTest(){
        App.getInstance().start();
        new MainPage().toSearchPage();
    }


    @Test
    void test(){
        String path = "/driver/GlobalConfig.yaml";
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            System.out.println("path===" + path);
            GlobalConfig globalConfig = mapper.readValue(GlobalConfig.class.getResourceAsStream(path), GlobalConfig.class);
            System.out.println("globalConfig" + globalConfig);
            System.out.println(globalConfig.appiumConfig.wait);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
