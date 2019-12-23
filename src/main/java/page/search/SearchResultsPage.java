package page.search;

import page.BasePage;

public class SearchResultsPage extends BasePage {

    public String getDrugName(){
        parseSteps();
        return getAttributeResult().get("drugName").toString();
    }

    public SearchPage backSearchPage(){
        parseSteps();
        return new SearchPage();
    }
}
