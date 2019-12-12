package util;

import io.appium.java_client.MobileBy;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.openqa.selenium.By;
import page.BasePage;

@Data
@AllArgsConstructor
public class ScrollSelectUtil extends BasePage {

    private String scrollTo(String byType, String typeValue)
    {
        String uiautomatorStr = null;

        if (byType.equals("text"))
        {
            uiautomatorStr =
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + typeValue
                            + "\"))";
        }

        else if (byType.equals("id"))
        {
            uiautomatorStr =
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\""
                            + typeValue + "\"))";
        }

        else if (byType.equals("AccessibilityId"))
        {
            uiautomatorStr =
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().description(\""
                            + typeValue + "\"))";
        }
        return uiautomatorStr;
    }

    // className method
    private String scrollTo(String className, String classType, String type, int number)
    {
        String uiautomatorStr = null;

        // find element by classname && index
        if (classType.equals("classname") && type.equals("index"))
        {
            uiautomatorStr = "new UiScrollable(new UiSelector().scrollable(true).index(" + number
                    + ")).getChildByText(new UiSelector().className(\"" + className + "\")";
        }
        // find element by classname && instance
        else if (classType.equals("classname") && type.equals("instance"))
        {
            uiautomatorStr = "new UiScrollable(new UiSelector().scrollable(true).instance(" + number
                    + ")).getChildByText(new UiSelector().className(\"" + className + "\")";
        }
        return uiautomatorStr;
    }

    public void scrollSelect(String byType, String typeValue){
        By selectName = MobileBy.AndroidUIAutomator(scrollTo(typeValue,byType));
        find(selectName).click();
    }

    public void scrollSelect(String className, String classType, String type, int number){
        By selectName = MobileBy.AndroidUIAutomator(scrollTo(className, classType, type,number));
        find(selectName).click();
    }
}

