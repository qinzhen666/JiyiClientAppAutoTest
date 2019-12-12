package page.search;

import driver.App;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import page.MainPage;

import static org.junit.jupiter.api.Assertions.*;

class SearchPageTest {

    @BeforeAll
    static void start(){
        App.getInstance().start();
    }
    @Test
    void scroll() {
        new MainPage().toSearchPage().scroll();
    }
}