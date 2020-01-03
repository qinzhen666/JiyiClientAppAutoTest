package page.search;
import page.BasePage;

import java.util.HashMap;

public class SearchPage extends BasePage {

    public SearchResultsPage searchDrugByBtn(String searchInfo){
        HashMap<String,String> map = new HashMap<>();
        map.put("sendText",searchInfo);
        setSendParam(map);
        parseSteps();
        return new SearchResultsPage();
    }

    public SearchPage inputSearchKeyword(String searchInfo){
        HashMap<String,String> map = new HashMap<>();
        map.put("sendText",searchInfo);
        setSendParam(map);
        parseSteps();
        return this;
    }

    public SearchResultsPage searchByClickKeyword(){
        parseSteps();
        return new SearchResultsPage();
    }

    public SearchResultsPage searchDrugStoreByInput(String searchInfo){
        HashMap<String,String> map = new HashMap<>();
        map.put("sendText",searchInfo);
        setSendParam(map);
        parseSteps();
        return new SearchResultsPage();
    }

    public String getFirstKeyWord(){
        parseSteps();
        return getAttributeResult().get("keyword").toString();
    }

    public SearchResultsPage searchByHistory(){
        parseSteps();
        return new SearchResultsPage();
    }

    public SearchResultsPage searchByHot(){
        parseSteps();
        return new SearchResultsPage();
    }

    public String getFirstHot(){
        parseSteps();
        return getAttributeResult().get("hotSearch").toString();
    }

    public SearchPage deleteHistorySearch(){
        parseSteps();
        return this;
    }

    public SearchPage deleteHistoryCancel(){
        parseSteps();
        return this;
    }

    public Integer getHistorySearchSize(){
        parseSteps();
        return (Integer) getAttributeResult().get("size");
    }


    public void scroll(){
        parseSteps();
    }
}
