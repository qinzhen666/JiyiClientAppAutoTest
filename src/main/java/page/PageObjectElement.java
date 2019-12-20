package page;

import driver.App;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageObjectElement {

    public List<HashMap<String, String>> getElement() {
        return element;
    }

    public void setElement(List<HashMap<String, String>> element) {
        this.element = element;
    }

    public List<HashMap<String,String>> element = new ArrayList<>();

    /**
     * 获取指定系统和版本的定位符
     * @return
     */
    public By getLocator(){
        String os = App.getInstance().getPlatform().toLowerCase();
        String version = App.getInstance().getVersion();
        System.out.println("当前执行的系统是" + os + "  当前执行的版本是" + version);
        for (HashMap<String, String> map : element) {
            if (map.get("os").equals(os) && map.get("version").equals(version)){
                if (map.get("id") != null){
                    if (os.equals("ios")){
                        return MobileBy.AccessibilityId(map.get("id"));
                    }else {
                        return By.id(map.get("id"));
                    }
                }else if (map.get("text") != null){
                    return By.xpath("//*[@text='" +map.get("text") + "']");
                }else if (map.get("xpath") != null){
                    return By.xpath(map.get("xpath"));
                }else if (map.get("aid") != null){
                    return MobileBy.AccessibilityId(map.get("aid"));
                }else if (map.get("iospredicate") != null){
                    return MobileBy.iOSNsPredicateString(map.get("iospredicate"));
                }
            }
        }return null;
    }
}
