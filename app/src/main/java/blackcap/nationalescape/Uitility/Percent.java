package blackcap.nationalescape.Uitility;

/**
 * Created by 박효근 on 2018-08-21.
 */

public class Percent {
    public String Discount(String str_price1, String str_price2){
        int price1 = Integer.parseInt(str_price1);
        int price2 = Integer.parseInt(str_price2);

        int percent = 100 - (price2*100)/price1;
        return percent+"";
    }
}
