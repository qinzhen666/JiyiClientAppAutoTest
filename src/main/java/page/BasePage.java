package page;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import driver.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import util.ScrollSelectUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BasePage {

//    private PageObjectModel model = new PageObjectModel();
    private HashMap<String,String> sendParam = new HashMap<>();  //send传递
    private HashMap<String,Object> attributeResult = new HashMap<>(); //attribute传递

    public HashMap<String, Object> getAttributeResult() {
        return attributeResult;
    }

    public HashMap<String, String> getSendParam() {
        return sendParam;
    }

    public void setSendParam(HashMap<String, String> sendParam) {
        this.sendParam = sendParam;
    }

    public WebElement find(By by){
        WebElement element = new Element().find(by);
        return element;
    }

    public List<WebElement> finds(By by){
        List<WebElement> elements = new Element().finds(by);
        return elements;
    }

    //解析步骤
    /**
     * yaml默认以和page中相同method名和相同包结构位置放置
     */
    public void parseSteps(){
        String method = Thread.currentThread().getStackTrace()[2].getMethodName();
        System.out.println("当前执行的方法步骤是："+ method);
        parseSteps(method);
    }

    private void parseSteps(String method){
        String path = "/"+this.getClass().getCanonicalName().replace(".","/") + ".yaml";
        System.out.println("执行的method是："+ method+"  方法配置文件路径：" +path);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            PageObjectModel model = mapper.readValue(this.getClass().getResourceAsStream(path), PageObjectModel.class);
            parseStepsFromYaml(model.methods.get(method),model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 需要自定义yaml配置中的方法名和文件放置位置的
     * @param method PO中的方法名
     * @param path yaml文件的路径
     */
    public void parseSteps(String method, String path){
        System.out.println("执行的method是："+ method+"  方法配置文件路径：" +path);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            PageObjectModel model = mapper.readValue(this.getClass().getResourceAsStream(path), PageObjectModel.class);
            parseStepsFromYaml(model.methods.get(method),model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private PageObjectModel loadModel() {
        String path = "/" + this.getClass().getCanonicalName().replace(".", "/") + ".yaml";
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            PageObjectModel model = mapper.readValue(this.getClass().getResourceAsStream(path), PageObjectModel.class);
            return model;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从yaml中获取对应系统(android,ios)和版本(version)的定位符和行为流
     * @param steps 方法步骤
     */
    private void parseStepsFromYaml(PageObjectMethod steps,PageObjectModel model){
        //获取指定系统和版本的定位符
        steps.getSteps().forEach(step -> {
            WebElement webElement = null;
            List<WebElement> elementList = null;
            if (step.get("element") != null){
                System.out.println("开始查找element");
                System.out.println(step.get("element"));
                System.out.println("==="+model.elements.get("edit_search_out"));
//                webElement = find(model.elements.get(step.get("element")).getLocator());
                if (step.get("finds") == null){
                    webElement = find(model.elements.get(step.get("element")).getLocator());
                    //获取行为流
                    if (step.get("send") != null){
                        String send = step.get("send").replace("$sendText", sendParam.get("sendText"));
                        assert webElement != null;
                        webElement.sendKeys(send);
                    }else if (step.get("get") != null){
                        assert webElement != null;
                        String attribute = webElement.getAttribute(step.get("get"));
                        attributeResult.put(step.get("dump"),attribute);
                    }else if (step.get("scrollSelect") != null){
                        String[] split = step.get("scrollSelect").split("$");
                        String byType = split[0];
                        String typeValue = split[1];
                        new ScrollSelectUtil().scrollSelect(byType,typeValue);
                    }
                    else {
                        assert webElement != null;
                        webElement.click();
                    }
                }else if (step.get("finds").equals("text")){
                    elementList = finds(model.elements.get("element").getLocator());
                    List<String> eleText = new ArrayList<>();
                    elementList.forEach(ele->{
                        eleText.add(ele.getText());
                    });
                    attributeResult.put("findsTextList",eleText);
                }else if (step.get("finds").equals("size")){
                    elementList = finds(model.elements.get("element").getLocator());
                    int size = elementList.size();
                    attributeResult.put("size",size);
                }
            }

        });
    }
}
