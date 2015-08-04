package streamtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


/**
ファイルを読み込むメソッドの引数はInputStreamが良い、と思う。
ファイルを読み込むメソッドを作成しようとする。引数として考えるのが、
・ファイル名のString型
・ファイルのFile型
が考えられるが、InputStreamが良いのではないか、と結論付けた。
理由としては「テストしやすい」からである。
以下、テスト方法や実際の実装方法をまとめた。

ファイル出力時はどうかな、と実施してみた。
OutputStreamを引数にとるのがよいかも。
 */
public class InputStreamMethod {

	/**
	 * 一行で「グループ名:メンバー1,メンバー2,メンバー3」という形式のファイルを読み込む。<br/>
	 * メンバーをキーにして、どのグループに所属しているか確認できるMapを返す。
	 * @param inputStream 読み込みファイル
	 * @return メンバーをキーにして、どのグループに所属しているか確認できるMap
	 */
	public Map<String, String> read(InputStream inputStream){
		try(BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, Charset.forName("UTF-8")))){

			Map<String, String> result = new HashMap<>();

			String line;
			while((line = reader.readLine()) != null){

				// グループ名
				String group = line.substring(0, line.indexOf(":"));

				// メンバー名
				for (String member: line.substring(group.length() + 1).split(",")){
					result.put(member, group);
				}
			}

			return result;

		}catch (IOException | IllegalArgumentException | StringIndexOutOfBoundsException e) {
			// 複数例外catchを使用。初めて使うw

			System.err.println("グループの設定ファイルがおかしい");
			return null;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		File file = new File("src/streamtest/InputStreamMethodTest.txt");
		if (! file.exists()){
			System.err.println("ファイルがありません:" + file.getAbsolutePath());
			System.exit(-1);
		}

		InputStreamMethod target = new InputStreamMethod();

		Map<String, String> map = target.read(new FileInputStream(file));
		map.keySet().stream()
		.map(key -> key +":" + map.get(key))
		.forEach(System.out::println);
	}

}
