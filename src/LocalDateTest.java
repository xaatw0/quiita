import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.WeekFields;

import org.junit.Ignore;
import org.junit.Test;

/**
 *日付だけの処理をするケースは結構ある。
 *Java8より前のCalendear,Dateは時間も含まれるので、できればJava8から導入されたLocalDateで実装したい。
 *そのため、LocalDateの基本的な使用方法を実践してみた。
 */
public class LocalDateTest {

	@Test @Ignore("nowは動的に変化するため、実施しない")
	public void now(){
		// 2015/07/27に実施
		LocalDate date = LocalDate.now();
		assertThat(date.getYear(), is(2015));
		assertThat(date.getMonth(), is(Month.JULY));
		assertThat(date.getDayOfMonth(), is(27));
	}


	@Test
	public void of1(){
		LocalDate date = LocalDate.of(2015, 10, 1);
		assertThat(date.getYear(), is(2015));
		assertThat(date.getMonth(), is(Month.OCTOBER));
		assertThat(date.getMonthValue(), is(10));
		assertThat(date.getDayOfMonth(), is(1));

		assertThat(date.getDayOfYear(), is(274));
		assertThat(date.getDayOfMonth(), is(1));
		//assertThat(date.getDayOfWeek(), is(4)); // この書き方ではテスト失敗(コンパイルは通る)
		assertThat(date.getDayOfWeek(), is(DayOfWeek.THURSDAY));
	}

	@Test
	public void of2(){
		LocalDate date = LocalDate.of(2015, Month.OCTOBER, 1);
		assertThat(date.getYear(), is(2015));
		assertThat(date.getMonth(), is(Month.OCTOBER));
		assertThat(date.getMonthValue(), is(10));
		assertThat(date.getDayOfMonth(), is(1));
	}

	@Test
	public void plus(){
		LocalDate date = LocalDate.of(2015, Month.OCTOBER, 1);
		assertThat(date.getYear(), is(2015));
		assertThat(date.getMonth(), is(Month.OCTOBER));
		assertThat(date.getDayOfMonth(), is(1));

		LocalDate date2 = date.plusDays(3);
		assertThat(date2.getYear(), is(2015));
		assertThat(date2.getMonth(), is(Month.OCTOBER));
		assertThat(date2.getDayOfMonth(), is(4));

		LocalDate date3 = date.plusMonths(3);
		assertThat(date3.getYear(), is(2016));
		assertThat(date3.getMonth(), is(Month.JANUARY));
		assertThat(date3.getDayOfMonth(), is(1));

		LocalDate date4 = date.plusYears(5);
		assertThat(date4.getYear(), is(2020));
		assertThat(date4.getMonth(), is(Month.OCTOBER));
		assertThat(date4.getDayOfMonth(), is(1));

		// 変化していないことを確認
		assertThat(date.getYear(), is(2015));
		assertThat(date.getMonth(), is(Month.OCTOBER));
		assertThat(date.getDayOfMonth(), is(1));
	}

	@Test
	public void minus(){
		LocalDate date = LocalDate.of(2015, Month.OCTOBER, 1);
		assertThat(date.getYear(), is(2015));
		assertThat(date.getMonth(), is(Month.OCTOBER));
		assertThat(date.getDayOfMonth(), is(1));

		LocalDate date2 = date.minusDays(3);
		assertThat(date2.getYear(), is(2015));
		assertThat(date2.getMonth(), is(Month.SEPTEMBER));
		assertThat(date2.getDayOfMonth(), is(28));

		LocalDate date3 = date.minusMonths(3);
		assertThat(date3.getYear(), is(2015));
		assertThat(date3.getMonth(), is(Month.JULY));
		assertThat(date3.getDayOfMonth(), is(1));

		LocalDate date4 = date.minusYears(5);
		assertThat(date4.getYear(), is(2010));
		assertThat(date4.getMonth(), is(Month.OCTOBER));
		assertThat(date4.getDayOfMonth(), is(1));

		// 変化していないことを確認
		assertThat(date.getYear(), is(2015));
		assertThat(date.getMonth(), is(Month.OCTOBER));
		assertThat(date.getDayOfMonth(), is(1));
	}

	@Test
	public void Period(){
		LocalDate start = LocalDate.of(2015, Month.OCTOBER, 1);
		LocalDate end = LocalDate.of(2016, Month.OCTOBER, 1);

		Period period = Period.between(start, end);
		assertThat(period.getYears(),is(1));
		assertThat(period.getMonths(),is(0));
		assertThat(period.getDays(),is(0));

		assertThat(period.toTotalMonths(),is(12L));

		period = Period.between(
				LocalDate.of(2011, Month.JANUARY, 1),
				LocalDate.of(2012, Month.FEBRUARY, 2));

		assertThat(period.getYears(),is(1));
		assertThat(period.getMonths(),is(1));
		assertThat(period.getDays(),is(1));

		// 開始日の方が未来日の場合、値はマイナスになる
		period = Period.between(
				LocalDate.of(2012, Month.FEBRUARY, 2),
				LocalDate.of(2011, Month.JANUARY, 1));

		assertThat(period.getYears(),is(-1));
		assertThat(period.getMonths(),is(-1));
		assertThat(period.getDays(),is(-1));

		assertThat(ChronoUnit.YEARS.between(start, end), is(1L));
		assertThat(ChronoUnit.MONTHS.between(start, end), is(12L));
		assertThat(ChronoUnit.DAYS.between(start, end), is(366L));
	}

	@Test
	public void parse(){
		LocalDate date = LocalDate.of(2015, 8, 18);
		assertThat(LocalDate.parse("2015-08-18"), is(date));
		assertThat(LocalDate.parse("2015-08-18", DateTimeFormatter.ISO_DATE), is(date));
		assertThat(LocalDate.parse("2015-08-18", DateTimeFormatter.ISO_LOCAL_DATE), is(date));

		assertThat(LocalDate.parse("20150818", DateTimeFormatter.BASIC_ISO_DATE), is(date));
	}

	@Test
	public void format(){

		// 2015年8月18日 230日目、34週の火曜日
		LocalDate date = LocalDate.of(2015, 8, 18);
		assertThat(date.format(DateTimeFormatter.ISO_DATE), is("2015-08-18"));
		assertThat(date.format(DateTimeFormatter.ISO_LOCAL_DATE), is("2015-08-18"));
		assertThat(date.format(DateTimeFormatter.BASIC_ISO_DATE), is("20150818"));
		assertThat(date.format(DateTimeFormatter.ISO_ORDINAL_DATE), is("2015-230"));
		assertThat(date.format(DateTimeFormatter.ISO_WEEK_DATE), is("2015-W34-2"));
	}

	@Test
	public void DateTimeFormatter(){

		// 出力する日付のフォーマットを変更する
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

		LocalDate date = LocalDate.of(2015, 8, 18);
		assertThat(date.format(formatter), is("2015/08/18"));

		TemporalAccessor parsed = formatter.parse("2016/12/07");
		assertThat(LocalDate.from(parsed), is(LocalDate.of(2016, 12, 7)));
		assertThat(parsed.query(LocalDate::from), is(LocalDate.of(2016, 12, 7)));

		assertThat(formatter.parse("2016/12/07", LocalDate::from), is(LocalDate.of(2016, 12, 7)));
	}

	@Test
	public void 週の初日(){

		LocalDate date = LocalDate.of(2015, 9, 15);

		LocalDate firstDayOfWeek = date.minusDays(date.getDayOfWeek().getValue());

		assertThat(firstDayOfWeek.getYear(), is(2015));
		assertThat(firstDayOfWeek.getMonthValue(), is(9));
		assertThat(firstDayOfWeek.getDayOfMonth(), is(13));
	}

	@Test
	public void 何週目か(){

		// ISO基準で何週目か
		WeekFields wf = WeekFields.ISO;
	    LocalDate ld = LocalDate.now()
	            .with(wf.weekBasedYear(), 2016)
	            .with(wf.weekOfWeekBasedYear(), 10) //第10週の
	            .with(wf.dayOfWeek(), DayOfWeek.WEDNESDAY.getValue()); //水曜日

	    assertThat(ld, is(LocalDate.of(2016,3,9)));

	    ld = LocalDate.now()
	            .with(wf.weekBasedYear(), 2016)
	            .with(wf.weekOfWeekBasedYear(), 37);
	    assertThat(ld, is(LocalDate.of(2016,9,14)));

	    ld = LocalDate.now()
	            .with(wf.weekBasedYear(), 2016)
	            .with(wf.weekOfWeekBasedYear(), 37)
        		.with(wf.dayOfWeek(), DayOfWeek.WEDNESDAY.getValue()); //水曜日
	    assertThat(ld, is(LocalDate.of(2016,9,14)));


	    // 2016年37週 月:12 火:13 水:14 木:15 金:16 土:17 日:18
	    LocalDate day = LocalDate.now()
	            .with(wf.weekBasedYear(), 2016)
	            .with(wf.weekOfWeekBasedYear(), 37);
	    assertThat(day.with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue()), is(LocalDate.of(2016,9,12)));
	    assertThat(day.with(wf.dayOfWeek(), DayOfWeek.SATURDAY.getValue()), is(LocalDate.of(2016,9,17)));
	    assertThat(day.with(wf.dayOfWeek(), DayOfWeek.SUNDAY.getValue()), is(LocalDate.of(2016,9,18)));

	    // 2016年1週 月:4 火:5 水:6 木:7 金:8 土:9 日:10
	    day = LocalDate.now()
	            .with(wf.weekBasedYear(), 2016)
	            .with(wf.weekOfWeekBasedYear(), 1);
	    assertThat(day.with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue()), is(LocalDate.of(2016,1,4)));
	    assertThat(day.with(wf.dayOfWeek(), DayOfWeek.SATURDAY.getValue()), is(LocalDate.of(2016,1,9)));
	    assertThat(day.with(wf.dayOfWeek(), DayOfWeek.SUNDAY.getValue()), is(LocalDate.of(2016,1,10)));

	    /** 0週目、実行時エラー[1-53,54]のみ
	    day = LocalDate.now()
	            .with(wf.weekBasedYear(), 2016)
	            .with(wf.weekOfWeekBasedYear(), 0);**/

	    // 2015年53週 月:28 火:29 水:30 木:31 金:1 土:2 日:3
	    day = LocalDate.now()
	            .with(wf.weekBasedYear(), 2015)
	            .with(wf.weekOfWeekBasedYear(), 53);
	    assertThat(day.with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue()), is(LocalDate.of(2015,12,28)));
	    assertThat(day.with(wf.dayOfWeek(), DayOfWeek.SATURDAY.getValue()), is(LocalDate.of(2016,1,2)));
	    assertThat(day.with(wf.dayOfWeek(), DayOfWeek.SUNDAY.getValue()), is(LocalDate.of(2016,1,3)));

	    // 日曜日を含む週が1週目で何週目か
	    wf = WeekFields.SUNDAY_START;
	    day = LocalDate.now()
	            .with(wf.weekBasedYear(), 2016)
	            .with(wf.weekOfWeekBasedYear(), 1);
	    assertThat(day.with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue()), is(LocalDate.of(2015,12,27)));
	    assertThat(day.with(wf.dayOfWeek(), DayOfWeek.SATURDAY.getValue()), is(LocalDate.of(2016,1,1)));
	    assertThat(day.with(wf.dayOfWeek(), DayOfWeek.SUNDAY.getValue()), is(LocalDate.of(2016,1,2)));
	}

	@Test
	public void testfor(){
		int count = 0;
		LocalDate from = LocalDate.of(2014, 1, 1);
		LocalDate to = LocalDate.of(2015, 1, 1);
		for (LocalDate index = from; index.isBefore(to); index = index.plusMonths(1)){
			count ++;
		}

		assertThat(count, is(12));
	}

	@Test
	public void toString_(){
		assertThat(LocalDate.of(2016, 11, 11).toString(), is("2016-11-11"));
		assertThat(LocalDate.of(2016,  1, 11).toString(), is("2016-01-11"));
		assertThat(LocalDate.of(2016, 11,  1).toString(), is("2016-11-01"));
		assertThat(LocalDate.of(2016,  1,  1).toString(), is("2016-01-01"));
		assertThat(LocalDate.of(  16,  1,  1).toString(), is("0016-01-01"));
	}

	@Test
	public void minusMonth(){
		assertThat(LocalDate.of(2017, 2, 28).minusMonths(1), is(LocalDate.of(2017, 1, 28)));
		assertThat(LocalDate.of(2017, 3, 29).minusMonths(1), is(LocalDate.of(2017, 2, 28)));
		assertThat(LocalDate.of(2017, 3, 30).minusMonths(1), is(LocalDate.of(2017, 2, 28)));
	}
}
