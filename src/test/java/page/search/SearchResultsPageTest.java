package page.search;

import driver.App;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import page.MainPage;
import util.HanyuPinyinHelperUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class SearchResultsPageTest {

    private static SearchResultsPage searchResultsPage;
    @BeforeAll
    static void before(){
        App.getInstance().start();
        if (searchResultsPage == null){
            searchResultsPage = new MainPage().toSearchPage().searchDrugByBtn("甘定");
        }
    }

    //检查接个排序和精确度(保留两位小数)，低->高->低
    @Test
    void checkPriceSort() {
        //获得升序价格
        List<String> drugPricesASC = searchResultsPage.getDrugPrices();
        //获得降序价格
        List<String> drugPricesDES = searchResultsPage.getDrugPrices();
        //期望升序价格
        List<String> listASC = drugPricesASC.stream().sorted().collect(Collectors.toList());
        //期望降序价格
        List<String> listDES = drugPricesDES.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //计算保留小数位数
        drugPricesASC.forEach(p ->{
            System.out.println("price==========="+p);
            assertThat(p,containsString("."));
            String[] num = p.split("\\.");
            assertThat(num[1].length(),equalTo(2));
        });
        assertThat(listASC,equalTo(drugPricesASC)); //低->高
        assertThat(listDES,equalTo(drugPricesDES)); //高->低
    }

    @Test
    void checkTenantNumSort(){
        //获得降序报价数
        List<Integer> TenantNumDES = searchResultsPage.getTenantNum();
        //获得升序报价数
        List<Integer> TenantNumASC = searchResultsPage.getTenantNum();
        //期望降序报价数
        List<Integer> numDES = TenantNumDES.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //期望升序报价数
        List<Integer> numASC = TenantNumASC.stream().sorted().collect(Collectors.toList());
        assertThat(TenantNumASC,equalTo(numASC));
        assertThat(TenantNumDES,equalTo(numDES));
    }

    @Test
    void checkFilterSort(){
        List<String> drugNames = searchResultsPage.getDrugNames();
        List<String> brandList = new ArrayList<>();
        drugNames.forEach(name->{
            brandList.add(name.split(" ")[0]);
        });
        //按中文排序
        List<String> chineseSort = new HanyuPinyinHelperUtil().chineseSort(brandList);
        //实际排序
        List<String> brandSort = searchResultsPage.getFilterSort();
        searchResultsPage.scrollCancelFilter();
        assertThat(chineseSort,equalTo(brandSort));
    }

    @Test
    void checkFilter(){
        List<String> brandSort = searchResultsPage.getFilterSort().subList(0,2);
        List<String> drugNames = searchResultsPage.chooseFilter().getDrugNames();
        List<String> list =new ArrayList<>();
        drugNames.forEach(name->{
            list.add(name.split(" ")[0]);
        });
        brandSort.forEach(brand->{
            assertThat(list,hasItem(brand));
        });
    }
}