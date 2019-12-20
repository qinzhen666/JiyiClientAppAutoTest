package page;

import page.home.HomePage;
import page.search.SearchPage;

public class MainPage extends BasePage {

    public SearchPage toSearchPage(){
        parseSteps();
        return new SearchPage();
    }

    public HomePage toHomePage(){
        parseSteps();
        return new HomePage();
    }
}
