import driver.App;
import org.junit.jupiter.api.Test;
import page.MainPage;

public class TestDemo {

    @Test
    void mainTest(){
        App.getInstance().start();
        new MainPage().toSearchPage();
    }
}
