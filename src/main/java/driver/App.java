package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class App {
    public static AppiumDriver driver;
    private static GlobalConfig globalConfig = new GlobalConfig().loadConfig("/driver/GlobalConfig.yaml");
    private static App app;
    public static App getInstance(){
        if (app == null){
            app = new App();
        }
        return app;
    }

    public void start(){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
//        globalConfig.loadConfig("/driver/GlobalConfig.yaml");
        globalConfig.appiumConfig.capabilities.entrySet().forEach(entry -> {
            desiredCapabilities.setCapability(entry.getKey(),entry.getValue());
        });
        try {
            if (globalConfig.appiumConfig.capabilities.get("platformName").toString().toLowerCase().equals("android")){
                driver = new AndroidDriver(new URL(globalConfig.appiumConfig.url),desiredCapabilities);
            }else if (globalConfig.appiumConfig.capabilities.get("platformName").toString().toLowerCase().equals("ios")){
                driver = new IOSDriver(new URL(globalConfig.appiumConfig.url),desiredCapabilities);
            }
            driver.manage().timeouts().implicitlyWait(globalConfig.appiumConfig.wait, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //判断首页是否进入
        new WebDriverWait(driver,15).until(x->{
           String xml = driver.getPageSource();
           Boolean checkMainPage = xml.contains("com.medical.fast:id/txt_title") || xml.contains("saoyisao");
            System.out.println("主页元素查找结果："+checkMainPage);
            return checkMainPage;
        });
    }

    public String getPlatform(){
        String platformName = driver.getCapabilities().getCapability("platformName").toString().toLowerCase();
        System.out.println("platformName:" +platformName);
        return platformName.toLowerCase();
    }

    public String getVersion(){
        return globalConfig.appiumConfig.version;
    }

}
