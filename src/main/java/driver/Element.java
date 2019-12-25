package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Element {

    /*private By android;
    private By ios;*/
    private static int i = 1;
    private AppiumDriver driver = App.getInstance().driver;

    /*public Element(By android,By ios) {
        this.android = android;
        this.ios = ios;
    }*/


   /* public WebElement find(){
        WebElement element = null;
        if (App.getInstance().getPlatform().equals("android")){
            element = getWebElement(android);
        }else if (App.getInstance().getPlatform().equals("ios")){
            element = getWebElement(ios);
        }
        return element;
    }*/


    public WebElement find(By by) {
        try {
            System.out.println(by);
            return driver.findElement(by);
        }catch (Exception e){
            if (i > 3){  //设置最多递归3次
                i = 1;
                return driver.findElement(by);
            }
            System.out.println("进入弹框处理第" + i+ "次");
            handleAlertByPageSource();
            i++;
            return find(by); //最后调用自身完成递归，防止多弹框同时出现造成定位失败
        }
    }

    /*public List<WebElement> finds(){
        List<WebElement> elements = new ArrayList<>();
        if (App.getInstance().getPlatform().equals("android")){
            elements = getWebElements(android);
        }else if (App.getInstance().getPlatform().equals("ios")){
            elements = getWebElements(ios);
        }
        return elements;
    }*/

    public List<WebElement> findElements(By by) {
        try {
            System.out.println("findElements的by是："+by);
            return driver.findElements(by);
        }catch (Exception e){
            if (i > 3){
                i = 1;
                return driver.findElements(by);
            }
            System.out.println("进入弹框处理第" + i+ "次");
            handleAlertByPageSource();
            i++;
            return findElements(by);
        }
    }


    //很多弹框的话，最好的是直接定位到到底哪个弹框在界面上，元素的判断使用xpath
    private void handleAlertByPageSource() {
        //可以得到一个文本字符串，也可以理解为当前页面的xml
        String pageSource = driver.getPageSource();

        //android标记黑名单
        String adBox = "com.xueqiu.android:id/ib_close";  //广告
        String updateBox = "com.xueqiu.android:id/image_cancel"; //升级弹框
        String gesturePromptBox = "com.xueqiu.android:id/snb_tip_text"; //新功能提示
        String evaluateBox = "com.xueqiu.android:id/md_buttonDefaultNegative"; //评价
        //将标记和定位符存入mapAndroid
        HashMap<String,By> mapAndroid = new HashMap<>();
        mapAndroid.put(adBox,By.id("ib_close"));
        mapAndroid.put(gesturePromptBox,By.id("snb_tip_text"));
        mapAndroid.put(evaluateBox,By.id("md_buttonDefaultNegative"));
        mapAndroid.put(updateBox,By.id("image_cancel"));

        //ios标记黑名单
        String adBoxIos = "com.xueqiu.android:id/ib_close";  //广告
        String updateBoxIos = "com.xueqiu.android:id/image_cancel"; //升级弹框
        String gesturePromptBoxIos = "com.xueqiu.android:id/snb_tip_text";
        String evaluateBoxIos = "com.xueqiu.android:id/md_buttonDefaultNegative";
        //将标记和定位符存入mapIOS
        HashMap<String,By> mapIOS = new HashMap<>();
        mapIOS.put(adBox,By.id("ib_close"));
        mapIOS.put(gesturePromptBox,By.id("snb_tip_text"));
        mapIOS.put(evaluateBox,By.id("md_buttonDefaultNegative"));
        mapIOS.put(updateBox,By.id("image_cancel"));

        //临时修改隐式等待时间，防止查找黑名单中元素过久
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        if (App.getInstance().getPlatform().equals("android")){
            mapAndroid.entrySet().forEach(entry -> {
                if (pageSource.contains(entry.getKey())){
                    if (entry.getKey().equals("com.xueqiu.android:id/snb_tip_text")){ //新功能提示弹框需要点击其他地方
                        System.out.println("gesturePromptBox found");
                        Dimension size = App.getInstance().driver.manage().window().getSize();
                        new TouchAction<>(driver).tap(PointOption.point(size.width/2,size.height/2)).perform();
                    }else {
                        driver.findElement(entry.getValue()).click();
                    }
                }
            });
        }else if (App.getInstance().getPlatform().equals("ios")){
            mapIOS.entrySet().forEach(entry -> {
                if (pageSource.contains(entry.getKey())){
                    if (entry.getKey().equals("com.xueqiu.android:id/snb_tip_text")){ //新功能提示弹框需要点击其他地方
                        System.out.println("gesturePromptBox found");
                        Dimension size = App.getInstance().driver.manage().window().getSize();
                        new TouchAction<>(driver).tap(PointOption.point(size.width/2,size.height/2)).perform();
                    }else {
                        driver.findElement(entry.getValue()).click();
                    }
                }
            });
        }
        //判断完成后将隐式等待时间恢复
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
    }
}
