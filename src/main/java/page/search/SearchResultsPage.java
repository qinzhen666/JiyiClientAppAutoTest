package page.search;

import page.BasePage;

import java.util.List;

public class SearchResultsPage extends BasePage {

    public String getDrugName(){
        parseSteps();
        return getAttributeResult().get("drugName").toString();
    }

    public List<String> getDrugNames(){
        parseSteps();
        return (List<String>) getAttributeResult().get("findsTextList");
    }

    public SearchPage backSearchPage(){
        parseSteps();
        return new SearchPage();
    }
}
