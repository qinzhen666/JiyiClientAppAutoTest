package page.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import driver.App;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import page.MainPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

//import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SearchPageTest {

    private static SearchPage searchPage;
    private static SearchResultsPage searchResultsPage;
    @BeforeAll
    static void start(){
        App.getInstance().start();
        if(searchPage == null){
            searchPage = new MainPage().toSearchPage();
        }
        if (searchResultsPage == null){
            searchResultsPage = new SearchResultsPage();
        }
    }

    //按钮品牌名搜索(药品存在)
    @ParameterizedTest
    @MethodSource("searchDrugData")
    void searchDrugByBtn(String drugName, String exceptResult){
        String getName = searchPage.searchDrugByBtn(drugName).getDrugName();
        searchResultsPage.backSearchPage();
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


    //关键字品牌名搜索(药品存在)
    @Test
    void searchDrugByKeyword(){
        String drugName = "甘";
        String firstKeyWord = searchPage.inputSearchKeyword(drugName).getFirstKeyWord();
        List<String> drugNames = searchPage.searchByClickKeyword().getDrugNames();
        searchResultsPage.backSearchPage();
        assertThat(firstKeyWord,containsString(drugName));
        drugNames.forEach(name ->{
            System.out.println("断言");
            assertThat(name,containsString(firstKeyWord));
        });
    }

    //搜索存在的药店,首位匹配
    @ParameterizedTest
    @CsvSource({
            "陕西新领地,陕西新领地思派大药房有限公司",
            "云南蒂梯匹,云南蒂梯匹药业有限公司",
    })
    void searchDrugStoreByInput(String storeName, String exceptResult){
        String drugStoreName = searchPage.searchDrugStoreByInput(storeName).getDrugStoreName();
        searchResultsPage.backSearchPage();
        assertThat(drugStoreName,equalTo(exceptResult));
    }

    //搜索存在的药店,模糊匹配
    @ParameterizedTest
    @CsvSource({
            "新领地",
            "蒂梯匹",
    })
    void searchDrugStoreByInputKeyword(String storeNameKW){
        List<String> drugStoreNames = searchPage.searchDrugStoreByInput(storeNameKW).getDrugNames();
        searchResultsPage.backSearchPage();
        drugStoreNames.forEach(name ->{
            assertThat(name,containsString(storeNameKW));
        });
    }

    //搜索不存在的药品
    @Test
    void searchNoExistDrug(){
        String noDrugInfo = "抱歉，未找到商品哦~";
        int size = searchPage.searchDrugByBtn("NoExistDrug").getDrugNames().size();
        boolean checkExist = new SearchResultsPage().checkExist(noDrugInfo);
        searchResultsPage.backSearchPage();
        assertThat(size, greaterThanOrEqualTo(1));
        assertThat(checkExist,equalTo(true));
    }

    //搜索不存在的药店
    @Test
    void searchNoExistDrugStore(){
        String noDrugStoreInfo = "抱歉，未找到商家哦~";
        int size = searchPage.searchDrugStoreByInput("NoExistDrugStore").getDrugStoreNames().size();
        boolean checkExist = new SearchResultsPage().checkExist(noDrugStoreInfo);
        searchResultsPage.backSearchPage();
        assertThat(size, greaterThanOrEqualTo(1));
        assertThat(checkExist,equalTo(true));
    }

    //通过历史搜索药品
    @ParameterizedTest
    @MethodSource("searchDrugData")
    void searchDrugByHistory(String searchDrugName,String expectResult){
        String drugName = searchPage.searchDrugByBtn(searchDrugName).backSearchPage().searchByHistory().getDrugName();
        searchResultsPage.backSearchPage();
        assertThat(drugName,equalTo(expectResult));
    }

    //通过历史搜索药店
    @ParameterizedTest
    @CsvSource({
            "新领地",
            "蒂梯匹",
    })
    void searchDrugStoreByHistory(String storeNameKW){
        List<String> drugStoreNames = searchPage.searchDrugStoreByInput(storeNameKW).backSearchPage().searchByHistory().getDrugStoreNames();
        searchResultsPage.backSearchPage();
        drugStoreNames.forEach(name ->{
            assertThat(name,containsString(storeNameKW));
        });
    }

    //通过热门搜索商品
    @Test
    void searchByHot(){
        String firstHot = searchPage.getFirstHot();
        List<String> drugNames = searchPage.searchByHot().getDrugNames();
        searchResultsPage.backSearchPage();
        drugNames.forEach(name->{
            assertThat(name,containsString(firstHot));
        });
    }

    //删除历史记录
    @Test
    void deleteHistory(){
        Integer beforeDeleteSize = searchPage.searchDrugByBtn("历史搜索").backSearchPage().getHistorySearchSize();
        boolean afterDeleteSize = searchPage.deleteHistorySearch().checkExist("历史搜索");
        assertThat(beforeDeleteSize,greaterThanOrEqualTo(1));
        assertThat(afterDeleteSize,equalTo(false));
    }

    //删除历史记录取消
    @Test
    void deleteHistoryCancel(){
        Integer beforeDeleteSize = searchPage.searchDrugByBtn("历史搜索").backSearchPage().getHistorySearchSize();
        Integer afterDeleteSize = searchPage.deleteHistoryCancel().getHistorySearchSize();
        assertThat(beforeDeleteSize,greaterThanOrEqualTo(1));
        assertThat(afterDeleteSize,equalTo(beforeDeleteSize));
    }

    @Test
    void scroll() {
        searchPage.scroll();
    }
}