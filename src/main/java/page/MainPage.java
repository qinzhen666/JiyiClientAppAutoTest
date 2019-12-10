package page;

import page.search.SearchPage;

public class MainPage extends BasePage {

    public SearchPage toSearchPage(){
        parseSteps();
        return new SearchPage();
    }
}
