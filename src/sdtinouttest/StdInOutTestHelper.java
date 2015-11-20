package sdtinouttest;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

/**
 * 標準入力を外部から設定し、標準出力を取得するクラス
 */
public class StdInOutTestHelper {

	/**
	 * 改行コード
	 */
	private final String SEP = System.lineSeparator();

	/**
	 * 入力ストリーム
	 */
	private PipedOutputStream pw;

	/**
	 * 出力結果
	 */
	private StringBuilder result = new  StringBuilder();

	/**
	 * コンストラクタ
	 * @throws IOException
	 */
	public StdInOutTestHelper() throws IOException{

		// 標準入力をストリームにする。
		pw  = new PipedOutputStream();
		System.setIn(new PipedInputStream(pw));


		OutputStream out = new OutputStream(){

			@Override
			public void write(int b) throws IOException {
				// 標準出力の値をresultに追加する
				result.append((char)b);
			}
		};

		// 標準出力を設定する
		System.setOut(new PrintStream(out));
	}

	/**
	 * 標準入力から入力したい値を設定する
	 * @param input 入力値
	 * @throws IOException
	 */
	public void setInput(String[] input) throws IOException{
		for(String data: input){
			pw.write((data + SEP).getBytes());
		}
	}


	/**
	 * 標準出力を取得する
	 * @return
	 */
	public String[] getOutput(){
		return result.toString().split(SEP);
	}

	/**
	 * 標準入力・標準出力の設定を元に戻す
	 */
	public void close(){
		System.setIn(null);
		System.setOut(null);
	}
}
