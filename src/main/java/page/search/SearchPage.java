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
        return new SearchPage();
    }

    public SearchResultsPage searchByClickKeyword(){
        parseSteps();
        return new SearchResultsPage();
    }

    public SearchResultsPage searchDrugStoreByKeyWord(){
        parseSteps();
        return new SearchResultsPage();
    }

    public String getFirstKeyWord(){
        parseSteps();
        return getAttributeResult().get("keyword").toString();
    }

    public void scroll(){
        parseSteps();
    }
}
