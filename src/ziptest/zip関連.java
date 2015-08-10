package ziptest;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.Test;


public class zip関連 {

    /**
     * zip内のファイルを読み込んで、一つのファイルに連結する。
     */
    public void 連結_zip読み込み(InputStream zipFile, OutputStream output ) throws IOException{

        try(ZipInputStream zipIn = new ZipInputStream(zipFile,Charset.forName("SJIS"))
        	;BufferedOutputStream out = new BufferedOutputStream(output))
        {
    		byte[] buffer = new byte[1024];

    		ZipEntry entry = null;
    		while(null != (entry = zipIn.getNextEntry())){

	    			int size = 0;
	    			while(0 < (size = zipIn.read(buffer))){
	    				out.write(buffer, 0, size);
	    			}
    			zipIn.closeEntry();
    		}
        }
    }

    /**
	 * zip関連.zipには3つのファイルが入っている。エンコードはShiftJIS<br/>
	 * 1.txt:あいうえお<br/>
	 * 2.txt:かきくけこ(改行)<br/>
	 * 3.txt:さしすせそ<br/>
	 * 幸いzip内のファイルが名前の順番どおり呼ばれるのでちゃんと動くが、基本的には動作が保障されてないと思う。
     */
    @Test
    public void 連結_zip読み込みTest() throws IOException{

    	ByteArrayOutputStream output = new ByteArrayOutputStream();
    	連結_zip読み込み(getClass().getResourceAsStream("連結_zip読み込みTest.zip"), output);
    	assertThat( new String(output.toByteArray(),"SJIS"), is("あいうえおかきくけこ\r\nさしすせそ"));
    }


    /**
     * zip内の日々の合計が「果物名:数」が入っている。果物ごとの3日分の合計を算出する。
     */
	public Map<String, Integer> zip読み込みして処理(InputStream inputStream) throws IOException{

		Map<String, Integer> result = new HashMap<>();

		try (ZipInputStream zipIn = new ZipInputStream(inputStream,Charset.forName("SJIS"))) {

			// zip内のファイルがなくなるまで読み続ける
			while (null != zipIn.getNextEntry()) {

				String line;
				BufferedReader reader = new BufferedReader(new InputStreamReader(zipIn, "Shift_JIS"));
				while(null != (line = reader.readLine())){

					String[] data = line.split(":");

					String fruit = data[0];
					int value = Integer.parseInt(data[1]);

					// すでに果物があれば加える。なければ、そのまま代入する。
					result.computeIfPresent(fruit, (k,v) -> v + value);
					result.putIfAbsent(fruit, value);
				}
				zipIn.closeEntry();
			}
		}

		return result;
	}

    /**
	 * zip関連.zipには3つのファイルが入っている。エンコードはShiftJIS<br/>
	 * 1.txt:りんご:3 みかん:1 バナナ:4<br/>
	 * 2.txt:りんご:4 みかん:2<br/>
	 * 3.txt:りんご:5 みかん:3 バナナ:1<br/>
	 * 「りんご:3」で一行。
     */
    @Test
    public void zip読み込みして処理Test() throws IOException{

    	Map<String, Integer> target =
    			zip読み込みして処理(getClass().getResourceAsStream("zip読み込みして処理Test.zip"));

    	// 果物は3種類
    	assertThat(target.size(), is(3));

    	assertThat(target.get("りんご"), is(12));
    	assertThat(target.get("みかん"), is(6));
    	assertThat(target.get("バナナ"), is(5));
    }

    /**
     * 指定されたInputStreamをzipファイルに買い込む。ファイル名は「順番.txt」にする。
     */
    public void zip書き込み(OutputStream out, InputStream... input) throws IOException{

		try(ZipOutputStream zos = new ZipOutputStream(out)){

			int count = 0;

			for (InputStream stream: input){
				ZipEntry entry = new ZipEntry((count ++) + ".txt" );
				zos.putNextEntry(entry);

				try (InputStream is = new BufferedInputStream(stream)) {
					byte[] buf = new byte[1024];
					for (int len = 0; 0 < (len = is.read(buf))  ;) {
						zos.write(buf, 0, len);
					}
				}
			}
		}
    }

    /**
     * zipを書き込んで、zipを展開して内容を確認する。<br/>
     * ByteArrayOuputStreamで実施しているため、close処理が適当、、、
     */
    @Test
    public void zip書き込みTest() throws IOException{

    	String[] data = {"あいうえお","かきくけこ","さしすせそ"};
    	ByteArrayInputStream[] input = new  ByteArrayInputStream[data.length];

    	for (int i = 0; i < data.length; i++){
    		input[i] =  new ByteArrayInputStream(data[i].getBytes());
    	}

    	// Zipに書き込む(メモリー上に書き込む)
    	ByteArrayOutputStream output = new ByteArrayOutputStream();
    	zip書き込み(output, input);

    	// 以下、zipを展開して、中身を確認する
    	try(ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(output.toByteArray())))
	    {
			byte[] buffer = new byte[1024];

			for (int i = 0; i < data.length ; i++){
				zipIn.getNextEntry();

    			int size = 0;
    			while(0 < (size = zipIn.read(buffer))){

    				String zipString = new String(Arrays.copyOf(buffer, size));
    				assertThat(zipString, is(data[i]));
    			}
				zipIn.closeEntry();
			}
	    }
    }
}
