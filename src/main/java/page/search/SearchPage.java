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

    public SearchResultsPage searchDrugByPrompt(){
        parseSteps();
        return new SearchResultsPage();
    }

    public SearchResultsPage searchDrugStoreByPrompt(){
        parseSteps();
        return new SearchResultsPage();
    }

    public void scroll(){
        parseSteps();
    }
}
