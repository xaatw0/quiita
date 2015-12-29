import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.Types;

import org.junit.Test;


public class SqlTypesTest {

	@Test
	public void Types(){
		assertThat(Types.ARRAY, is(2003));
		assertThat(Types.BIGINT, is(-5));
		assertThat(Types.BINARY, is(-2));
		assertThat(Types.BIT, is(-7));
		assertThat(Types.BLOB, is(2004));
		assertThat(Types.BOOLEAN, is(16));
		assertThat(Types.CHAR, is(1));
		assertThat(Types.CLOB, is(2005));
		assertThat(Types.DATALINK, is(70));
		assertThat(Types.DATE, is(91));
		assertThat(Types.DECIMAL, is(3));
		assertThat(Types.DISTINCT, is(2001));
		assertThat(Types.DOUBLE, is(8));
		assertThat(Types.FLOAT, is(6));
		assertThat(Types.INTEGER, is(4));
		assertThat(Types.JAVA_OBJECT, is(2000));
		assertThat(Types.LONGNVARCHAR, is(-16));
		assertThat(Types.LONGVARBINARY, is(-4));
		assertThat(Types.LONGVARCHAR, is(-1));
		assertThat(Types.NCHAR, is(-15));
		assertThat(Types.NCLOB, is(2011));
		assertThat(Types.NULL, is(0));
		assertThat(Types.NUMERIC, is(2));
		assertThat(Types.NVARCHAR, is(-9));
		assertThat(Types.OTHER, is(1111));
		assertThat(Types.REAL, is(7));
		assertThat(Types.REF, is(2006));
		assertThat(Types.ROWID, is(-8));
		assertThat(Types.SMALLINT, is(5));
		assertThat(Types.SQLXML, is(2009));
		assertThat(Types.STRUCT, is(2002));
		assertThat(Types.TIME, is(92));
		assertThat(Types.TIMESTAMP, is(93));
		assertThat(Types.TINYINT, is(-6));
		assertThat(Types.VARBINARY, is(-3));
		assertThat(Types.VARCHAR, is(12));
	}

}
