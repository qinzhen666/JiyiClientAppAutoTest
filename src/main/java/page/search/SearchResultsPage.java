package page.search;

import driver.App;
import page.BasePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResultsPage extends BasePage {

    public String getDrugName(){
        parseSteps();
        return getAttributeResult().get("drugName").toString();
    }

    public List<String> getDrugNames(){
        parseSteps();
        return (List<String>) getAttributeResult().get("findsTextList");
    }

    public List<String> getFilterSort(){
        parseSteps();
        return (List<String>) getAttributeResult().get("findsTextList");
    }

    public SearchResultsPage chooseFilter(){
        parseSteps();
        return this;
    }

    public SearchResultsPage scrollCancelFilter(){
        parseSteps();
        return this;
    }

    public String getDrugNamesSize(){
        parseSteps();
        return getAttributeResult().get("size").toString();
    }

    //点击价格排序按钮后，正则匹配出价格中的整数或小数，未处理保留小数位
    public List<String> getDrugPrices(){
        parseSteps();
        List<String> priceStringList =  (List<String>) getAttributeResult().get("findsTextList");
        System.out.println(priceStringList.toString());
        List<String> priceNumList = new ArrayList<>();
        priceStringList.forEach(price->{
            Pattern pattern = Pattern.compile("\\d+(?:\\.\\d+)?");
            Matcher matcher = pattern.matcher(price);
            while (matcher.find()){
                System.out.println("+++++++"+matcher.group());
                priceNumList.add(matcher.group());
            }
        });
        return priceNumList;
    }

    public List<Integer> getTenantNum(){
        parseSteps();
        List<String> tenantNum = (List<String>) getAttributeResult().get("findsTextList");
        List<Integer> tenantNumList = new ArrayList<>();
        tenantNum.forEach(s->{
            int num = Integer.parseInt(s.split("个")[0]);
            tenantNumList.add(num);
        });
        return tenantNumList;
    }

    public String getDrugStoreName(){
        parseSteps();
        return getAttributeResult().get("drugStoreName").toString();
    }

    public List<String> getDrugStoreNames(){
        parseSteps();
        return (List<String>) getAttributeResult().get("findsTextList");
    }

    public SearchPage backSearchPage(){
        parseSteps();
        return new SearchPage();
    }


}
