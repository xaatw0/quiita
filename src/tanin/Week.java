package tanin;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

import org.junit.Test;

public class Week {
    public static void main(String[] args) {
        int nenn = 2015;
        int syuu = 2;
        int niti = 5;
        System.out.println("検索条件は" + nenn + "年の" + syuu + "週目の" + niti
                + "日目です。");
        // Calendarを生成
        Calendar cal = Calendar.getInstance();
        cal.set(nenn, 0, 1);// Month値は0から始まる(1月は0になる)。これは１月１日
        for (int i = 0; i < 400; i++) {
            System.out.println("================");
            System.out.println("検索" + i + "回目");
            System.out.println("年: " + cal.get(Calendar.YEAR));
            System.out.println("月: " + cal.get(Calendar.MONTH)
                    + " ※月は、0～11の数値で表されるのでご注意ください。(1月→0 ～ 12月→11)");
            System.out.println("日: " + cal.get(Calendar.DATE));
            System.out.println("今年の: " + cal.get(Calendar.WEEK_OF_YEAR) + "週目");
            System.out.println("今週の: " + cal.get(Calendar.DAY_OF_WEEK) + "日目");

            if (cal.get(Calendar.WEEK_OF_YEAR) == syuu) {
                if (cal.get(Calendar.DAY_OF_WEEK) == niti) {
                    System.out.println("================");
                    System.out.println("検索した日時は:" + cal.getTime());
                    break;
                }
            }
            cal.add(Calendar.DATE, 1);//最初を検証してからaddしないと1/1を検証できない
        }

    }


    @Test
    public void Week(){
    	// 元旦を取得する
    	LocalDate date = LocalDate.of(2015,1,1);
    	
    	// 元旦の週の日曜日を取得する
    	LocalDate firstDayOfWeek = date.minusDays(date.getDayOfWeek().getValue());
    	
    	// 1週間足して、4日足す
    	LocalDate result = firstDayOfWeek.plusWeeks(2 - 1).plusDays(5 - 1);

    	assertThat(result.getYear(), is(2015));
    	assertThat(result.getMonth(), is(Month.JANUARY));
    	assertThat(result.getDayOfMonth(), is(8));
    }
}
