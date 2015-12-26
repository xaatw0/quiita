import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import org.junit.Assert;
import org.junit.Test;


public class LocalTimeTest {


	@Test
	public void now(){
		//現在時刻を取得。テストするとエラーが出るのでしない。
		LocalTime.now();
	}

	@Test
	public void ofGet(){

		// 11時23分
		LocalTime t1123 = LocalTime.of(11, 23);
		assertThat(t1123.getHour(), is(11));
		assertThat(t1123.getMinute(), is(23));

		// 11時23分45秒 と1ナノ秒
		// 1ナノ秒 = 0.000 000 001秒
		LocalTime t112345 = LocalTime.of(11, 23,45,1);
		assertThat(t112345.getHour(), is(11));
		assertThat(t112345.getMinute(), is(23));
		assertThat(t112345.getSecond(), is(45));
		assertThat(t112345.getNano(), is(1));
	}

	@Test
	public void of_境界線(){

		LocalTime.of(0, 0);
		LocalTime.of(23, 59);

		try {
			// 24時は設定できず、DateTimeExceptionが発生する
			LocalTime.of(24, 00);
			Assert.fail();
		}catch(DateTimeException ex){
		}
	}

	@Test
	public void staticFinal(){

		// 深夜の固定値
		LocalTime midnight = LocalTime.of(0, 0);
		assertThat(midnight, is(LocalTime.MIDNIGHT));

		// 正午の固定値
		LocalTime noon = LocalTime.of(12, 0);
		assertThat(noon, is(LocalTime.NOON));
	}

	@Test
	public void between(){

		LocalTime startTime = LocalTime.of(21, 30);
		LocalTime endTime = LocalTime.of(23, 00);

		// 分単位で差分を取得
		long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
		assertThat(minutes, is(90L));

		// 時間単位で差分を取得
		long hours = ChronoUnit.HOURS.between(startTime, endTime);
		assertThat(hours, is(1L));

		// 秒単位で差分を取得
		long seconds = ChronoUnit.SECONDS.between(startTime, endTime);
		assertThat(seconds , is((long) 60 * 60 + 30 * 60));
	}

	@Test
	public void between_overnight(){

		// 開始時間の方が後ろの時間の場合、マイナスになる
		LocalTime startTime = LocalTime.of(21, 30);
		LocalTime endTime = LocalTime.of(20, 00);

		long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
		assertThat(minutes, is(-90L));

		long hours = ChronoUnit.HOURS.between(startTime, endTime);
		assertThat(hours, is(-1L));
	}

	@Test
	public void between_深夜越えの調整(){

		// 22:00-6:00の夜間業務で、労働時間を取得する
		LocalTime startTime = LocalTime.of(22, 00);
		LocalTime endTime = LocalTime.of(6, 00);

		// -16時間になる
		long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
		assertThat(minutes, is((long) -16 * 60L ));

		// マイナスの場合、24時間を足すと丁度よくなる
		assertThat(24 * 60 + minutes, is(8 * 60L));
	}

	@Test
	public void parse(){

		assertThat(LocalTime.parse("12:34", DateTimeFormatter.ISO_TIME)
				, is(LocalTime.of(12,34)));

		assertThat(LocalTime.parse("12:34", DateTimeFormatter.ISO_LOCAL_TIME)
				, is(LocalTime.of(12,34)));

		assertThat(LocalTime.parse("02:34", DateTimeFormatter.ISO_LOCAL_TIME)
				, is(LocalTime.of(2,34)));

		//「2:34」はエラー(parse_exception参照)

		assertThat(LocalTime.parse("02:04", DateTimeFormatter.ISO_LOCAL_TIME)
				, is(LocalTime.of(2,4)));

		assertThat(LocalTime.parse("02:04:30", DateTimeFormatter.ISO_LOCAL_TIME)
				, is(LocalTime.of(2,4,30)));
	}

	/**
	 * 「2:34」はエラー
	 */
	@Test(expected = DateTimeParseException.class)
	public void parse_時間が一桁は無効(){
		LocalTime.parse("2:34", DateTimeFormatter.ISO_LOCAL_TIME);
	}

	@Test(expected = DateTimeParseException.class)
	public void parse_24時は無効(){
		LocalTime.parse("24:00", DateTimeFormatter.ISO_LOCAL_TIME);
	}
	@Test
	public void format(){
		assertThat(LocalTime.of(2,4).format(DateTimeFormatter.ISO_TIME), is("02:04:00"));
		assertThat(LocalTime.of(12,34).format(DateTimeFormatter.ISO_TIME), is("12:34:00"));
		assertThat(LocalTime.of(2,4,1).format(DateTimeFormatter.ISO_TIME), is("02:04:01"));

		assertThat(LocalTime.of(12,34).format(DateTimeFormatter.ISO_LOCAL_TIME), is("12:34:00"));

	}

	@Test
	public void isBeforeAfter(){
		LocalTime time1 = LocalTime.of(1,0);
		LocalTime time2 = LocalTime.of(2,0);

		assertThat(time1.isBefore(time2), is(true));
		assertThat(time2.isBefore(time1), is(false));

		assertThat(time1.isAfter(time2), is(false));
		assertThat(time2.isAfter(time1), is(true));

		// 同じ時刻は全てfalse
		LocalTime time3 = LocalTime.of(1,0);
		assertThat(time1.isBefore(time3), is(false));
		assertThat(time3.isBefore(time1), is(false));
		assertThat(time1.isAfter(time3), is(false));
		assertThat(time3.isAfter(time1), is(false));
	}

	@Test
	public void plusMinus(){

		assertThat(LocalTime.NOON.plusHours(1), is(LocalTime.of(13, 0)));
		assertThat(LocalTime.NOON.plusMinutes(1), is(LocalTime.of(12, 1)));
		assertThat(LocalTime.NOON.plusSeconds(1), is(LocalTime.of(12, 0, 1)));
		assertThat(LocalTime.NOON.plusNanos(1), is(LocalTime.of(12, 0, 0, 1)));

		assertThat(LocalTime.NOON.minusHours(1), is(LocalTime.of(11, 0)));
		assertThat(LocalTime.NOON.minusMinutes(1), is(LocalTime.of(11, 59)));
		assertThat(LocalTime.NOON.minusSeconds(1), is(LocalTime.of(11, 59, 59)));
		assertThat(LocalTime.NOON.minusNanos(1), is(LocalTime.of(11, 59, 59, 999999999)));
	}

	@Test
	public void toSecondNanoOfDay(){
		assertThat(LocalTime.of(1, 1, 1).toSecondOfDay(), is(60*60 + 60 + 1));
		assertThat(LocalTime.of(0, 0, 1, 1).toNanoOfDay(), is(1000000001L));
	}

	@Test
	public void with(){
		LocalTime target = LocalTime.of(11, 23,45,1);
		assertThat(target.withHour(0), is(LocalTime.of(0, 23,45,1)));
		assertThat(target.withMinute(0), is(LocalTime.of(11, 0,45,1)));
		assertThat(target.withSecond(0), is(LocalTime.of(11, 23,0,1)));
		assertThat(target.withNano(0), is(LocalTime.of(11, 23,45)));
	}

	@Test
	public void 丸め込み(){
		LocalTime result = LocalTime.of(11, 30);

		assertThat(truncate(true,  LocalTime.of(11, 25,  1), 15), is(result));
		assertThat(truncate(true,  LocalTime.of(11, 30, 59), 15), is(result));

		assertThat(truncate(false, LocalTime.of(11, 31,  1), 15), is(result));
		assertThat(truncate(false, LocalTime.of(11, 44, 59), 15), is(result));

		assertThat(truncate(true,  LocalTime.of(11, 13), 15), is(not(result)));
		assertThat(truncate(true,  LocalTime.of(11, 13), 30), is(result));

		assertThat(truncate(false,  LocalTime.of(11, 46), 15), is(not(result)));
		assertThat(truncate(false, LocalTime.of(11, 46), 30), is(result));
	}

	/**
	 * LocalTimeを指定の分で丸め込む
	 * @param isStart true:後ろに丸め込む false:前に丸め込む
	 * @param time 丸め込む時間
	 * @param minutes 丸め込む分数
	 * @return 丸め込まれた時間
	 */
	private LocalTime truncate(boolean isStart, LocalTime time, int minutes){

		LocalTime truncated = time.truncatedTo(ChronoUnit.MINUTES);
		int value = truncated.getMinute() % minutes;

		return value == 0 ?
				truncated:
				truncated.plusMinutes(isStart ? minutes - value : - value);
	}
}
