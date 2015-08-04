package streamtest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class OutputStreamMethod {

	Map<String, String> map = new HashMap<>();

	public String put(String key, String value ){
		return map.put(key, value);
	}

	public void write(OutputStream outputtStream) throws IOException{

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputtStream));

		String[] groups =
				map.values().stream()
				.distinct()
				.sorted()
				.toArray(count->new String[count]);


		for (int i = 0; i < groups.length; i++){

			final String group = groups[i];

			String[] members =
					map.keySet().stream()
					.filter(p-> group.equals(map.get(p)))  // 同じグループのみでフィルタリングしてる
					.sorted()
					.toArray(count -> new String[count]);

			writer.write(group  + ":" + String.join(",", members));

			// 最終グループでないときは、改行を入れる
			if (i != groups.length -1){
				writer.newLine();
			}
		}

		writer.flush();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		File file = new File("src/streamtest/OutputStreamMethodTest.txt");
		if (file.exists()){
			System.err.println("ファイルがあります:" + file.getAbsolutePath());
			System.exit(-1);
		}

		OutputStreamMethod target = new OutputStreamMethod();
		target.put("北海道", "北海道");
		target.put("青森県", "東北");
		target.put("岩手県", "東北");
		target.put("秋田県", "東北");
		target.put("茨城県", "関東");
		target.put("栃木県", "関東");
		target.put("群馬県", "関東");

		target.write(new FileOutputStream(file));
	}
}
