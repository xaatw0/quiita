import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;


public class sqlDateとutilDateとLocalDate {

	public LocalDate convertToLocalDate(java.sql.Date sqlDate){
		 return sqlDate.toLocalDate();
	}

	@Test
	public void SqlDate2LocalDate(){

		java.sql.Date sqlDate = new java.sql.Date(0);
		LocalDate localDate = LocalDate.of(1970, 1, 1);

		assertThat(convertToLocalDate(sqlDate), is(localDate));
	}

	public java.util.Date convertToUtilDate(java.sql.Date sqlDate){
		return sqlDate;
	}

	@Test
	public void SqlDate2UtilDate(){

		java.sql.Date sqlDate = new java.sql.Date(0);
		java.util.Date utilDate = new java.util.Date(0);

		assertThat(convertToUtilDate(sqlDate) , is(utilDate));
	}

	public LocalDate convertToLocalDate(java.util.Date utilDate){
		return utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	@Test
	public void UtilDate2LocalDate(){

		java.util.Date utilDate = new java.util.Date(0);
		LocalDate localDate = LocalDate.of(1970, 1, 1);

		assertThat(convertToLocalDate(utilDate), is(localDate));
	}

	public java.sql.Date convertToSqlDate(java.util.Date utilDate){
		return new java.sql.Date(utilDate.getTime());
	}

	@Test
	public void UtilDate2SqlDate(){

		java.util.Date utilDate = new java.util.Date(0);
		java.sql.Date sqlDate = new java.sql.Date(0);

		assertThat(convertToSqlDate(utilDate) , is(sqlDate));
	}

	public java.util.Date convertToUtilDate(LocalDate localDate){
		 return java.util.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@Test
	public void LocalDate2UtilDate(){

		ZonedDateTime offsetDateTime = ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 0, 0, 0), ZoneId.systemDefault());
		LocalDate localDate = offsetDateTime.toLocalDate();

		// new java.util.Date(0).toString() -> <Thu Jan 01 09:00:00 JST 1970>
		// UTCで0時のため、日本では9時。-9時間の時差補正をしている。
		java.util.Date utilDate = new java.util.Date(-9 * 60 * 60 * 1000);

		assertThat(convertToUtilDate(localDate), is(utilDate));
	}

	public java.sql.Date convertToSqlDate(LocalDate localDate){
		return java.sql.Date.valueOf(localDate);
	}

	@Test
	public void LocalDate2SqlDate(){

		LocalDate localDate = LocalDate.of(1970, 1, 1);
		java.sql.Date sqlDate = new java.sql.Date(-9 * 60 * 60 * 1000);

		assertThat(convertToSqlDate(localDate), is(sqlDate));
	}

	public ZonedDateTime convertToZonedDateTime(java.util.Date utilDate){
		return utilDate.toInstant().atZone(ZoneId.systemDefault());
	}

	@Test
	public void UtilDate2ZonedDateTime(){

		java.util.Date utilDate = new java.util.Date(0);
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 9, 0, 0), ZoneId.systemDefault());

		assertThat(convertToZonedDateTime(utilDate), is(zonedDateTime));
	}

	public ZonedDateTime convertToZonedDateTime(java.sql.Date sqlDate){
		return sqlDate.toLocalDate().atStartOfDay(ZoneId.systemDefault());
	}

	@Test
	public void SqlDate2ZonedDateTime(){

		java.sql.Date sqlDate = new java.sql.Date(0);
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 0, 0, 0), ZoneId.systemDefault());

		assertThat(convertToZonedDateTime(sqlDate), is(zonedDateTime));
	}

	public ZonedDateTime convertToZonedDateTime(LocalDate localDate){
		return localDate.atStartOfDay(ZoneId.systemDefault());
	}

	@Test
	public void LocalDate2ZonedDateTime(){

		LocalDate localDate = LocalDate.of(1970, 1, 1);
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 0, 0, 0), ZoneId.systemDefault());

		assertThat(convertToZonedDateTime(localDate), is(zonedDateTime));
	}

	public java.util.Date convertToUtilDate(ZonedDateTime zonedDateTime){
		return java.util.Date.from(zonedDateTime.toInstant());
	}

	@Test
	public void ZonedDateTime2UtilDate(){

		java.util.Date utilDate = new java.util.Date(0);
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 9, 0, 0), ZoneId.systemDefault());

		assertThat(convertToUtilDate(zonedDateTime), is(utilDate));
	}

	public java.sql.Date convertToSqlDate(ZonedDateTime zonedDateTime){
		return java.sql.Date.valueOf(zonedDateTime.toLocalDate());
	}

	@Test
	public void ZonedDateTime2SqlDate(){

		java.sql.Date sqlDate = new java.sql.Date(-9 * 60 * 60 * 1000);
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 0, 0, 0), ZoneId.systemDefault());

		assertThat(convertToSqlDate(zonedDateTime), is(sqlDate));
	}

	public LocalDate convertToLocalDate(ZonedDateTime zonedDateTime){
		return zonedDateTime.toLocalDate();
	}

	@Test
	public void ZonedDateTime2LocalDate(){

		LocalDate localDate = LocalDate.of(1970, 1, 1);
		ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 0, 0, 0), ZoneId.systemDefault());

		assertThat(convertToLocalDate(zonedDateTime), is(localDate));
	}
}
