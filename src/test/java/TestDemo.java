import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import driver.App;
import driver.GlobalConfig;
import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import page.MainPage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

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

    @Test
    void testIos() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","ios");
        capabilities.setCapability("deviceName","iPhone (2)");
        capabilities.setCapability("platformVersion","12.0");
        capabilities.setCapability("udid","auto");
        capabilities.setCapability("xcodeOrgId","7X8J5HLRH9");
        capabilities.setCapability("bundleId","com.testerallen.apple-samplecode.UICatalog");
        capabilities.setCapability("xcodeSigningId","iPhone Developer");
        capabilities.setCapability("usePrebuiltWDA","true");
        capabilities.setCapability("automationName","XCUITest");
        IOSDriver iosDriver = new IOSDriver(new URL("http://127.0.0.1:5723/wd/hub"),capabilities);
        iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        System.out.println(iosDriver.getPageSource());
//        iosDriver.findElementByAccessibilityId("Buttons").click();
//        iosDriver.findElementByIosNsPredicate("value=='Buttons'").click();
//        iosDriver.findElementByIosNsPredicate("label=='Buttons'").click();
//        iosDriver.findElementByIosNsPredicate("name=='Buttons'").click();
//        iosDriver.findElementByXPath("(//*[@type='XCUIElementTypeStaticText'])[7]" ).click();
        iosDriver.findElementByIosNsPredicate("type=='XCUIElementTypeStaticText' AND name=='Buttons'").click();
    }
}
