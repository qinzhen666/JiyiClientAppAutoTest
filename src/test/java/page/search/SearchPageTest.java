package page.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import driver.App;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import page.MainPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SearchPageTest {

    private static SearchPage searchPage;
    @BeforeAll
    static void start(){
        App.getInstance().start();
        if(searchPage == null){
            searchPage = new MainPage().toSearchPage();
        }
    }

    //按钮品牌名搜索
    @ParameterizedTest
    @MethodSource("searchDrugData")
    void searchDrugByBtn(String drugName, String exceptResult){
        String getName = searchPage.searchDrugByBtn(drugName).getDrugName();
        new SearchResultsPage().backSearchPage();
        assertThat(getName,equalTo(exceptResult));
    }

    static Stream<Arguments> searchDrugData() throws IOException {
        Arguments arguments = null;
        List<Arguments> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Object[][] searchData = mapper.readValue(SearchPageTest.class.getResourceAsStream("/testcasedata/searchdata/searchdrugdata.yaml"),Object[][].class);
        for (Object[] objects : searchData) {
            String drugName = Arrays.asList(objects).get(0).toString();
            String exceptResult = Arrays.asList(objects).get(1).toString();
            arguments = arguments(drugName,exceptResult);
            list.add(arguments);
        }
        return Stream.of(list.get(0),list.get(1),list.get(2));
    }


    //关键字品牌名搜索
    @Test
    void searchDrugByKeyword(){
        String drugName = "甘";
        String firstKeyWord = searchPage.inputSearchKeyword(drugName).getFirstKeyWord();
        List<String> drugNames = searchPage.searchByClickKeyword().getDrugNames();
        assertThat(firstKeyWord,containsString(drugName));
        drugNames.forEach(name ->{
            System.out.println("断言");
            assertThat(name,containsString(firstKeyWord));
        });
    }

    @Test
    void scroll() {
        new MainPage().toSearchPage().scroll();
    }
}