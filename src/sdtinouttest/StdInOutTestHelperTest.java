package sdtinouttest;


import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

public class StdInOutTestHelperTest {

	@Test
	public void 一行() throws Exception {

		StdInOutTestHelper helper = new StdInOutTestHelper();
		helper.setInput(new String[] { "a" });
		一行.main(null);

		String[] expected = new String[] { "A" };
		assertArrayEquals(expected, helper.getOutput());
	}

	@Test
	public void 五行() throws Exception {

		StdInOutTestHelper helper = new StdInOutTestHelper();
		helper.setInput(new String[] { "a", "bb", "ccc", "dddd", "eeeee" });
		五行.main(null);

		String[] expected = new String[] { "A", "BB", "CCC", "DDDD", "EEEEE" };
		assertArrayEquals(expected, helper.getOutput());
	}

	@Test
	public void C011_1() throws Exception {
		String[] inputData = { "w", "3", "abc def", "def", "abc de", };
		String[] outputData = { "5" };

		StdInOutTestHelper helper = new StdInOutTestHelper();
		helper.setInput(inputData);
		C011.main(null);
		assertArrayEquals(outputData, helper.getOutput());
	}

	@Test
	public void C011_2() throws Exception {
		String[] inputData = {"c","3","abc def","def","abc de" };
		String[] outputData = { "19",};

		StdInOutTestHelper helper = new StdInOutTestHelper();
		helper.setInput(inputData);
		C011.main(null);
		assertArrayEquals(outputData, helper.getOutput());
	}

	@Test
	public void C011_3() throws Exception {
		String[] inputData = {"wc","3","abc def","def","abc de",};
		String[] outputData = {"5","19", };

		StdInOutTestHelper helper = new StdInOutTestHelper();
		helper.setInput(inputData);
		C011.main(null);
		assertArrayEquals(outputData, helper.getOutput());
	}

	@Test
	public void C011_4() throws Exception {
		String[] inputData = {"wc","4","abc def","def","","abc de", };
		String[] outputData = {"5","20"};

		StdInOutTestHelper helper = new StdInOutTestHelper();
		helper.setInput(inputData);
		C011.main(null);
		assertArrayEquals(outputData, helper.getOutput());
	}

	@Test
	public void C011_5() throws Exception {
		String[] inputData = {"wc","0"};
		String[] outputData = {"0","0"};

		StdInOutTestHelper helper = new StdInOutTestHelper();
		helper.setInput(inputData);
		C011.main(null);
		assertArrayEquals(outputData, helper.getOutput());
	}

	@Test
	public void C011_6() throws Exception {
		String[] inputData = {"wc","1",""};
		String[] outputData = {"0","1"};

		StdInOutTestHelper helper = new StdInOutTestHelper();
		helper.setInput(inputData);
		C011.main(null);
		assertArrayEquals(outputData, helper.getOutput());
	}

	@Test
	public void C011_7() throws Exception {
		String[] inputData = {"wc","4","abc def","def"," ","abc de", };
		String[] outputData = {"5","21"};

		StdInOutTestHelper helper = new StdInOutTestHelper();
		helper.setInput(inputData);
		C011.main(null);
		assertArrayEquals(outputData, helper.getOutput());
	}

	@Test
	public void C011_8() throws Exception {
		String[] inputData = {"wc","4","abc def","def"," a","abc de", };
		String[] outputData = {"6","22"};

		StdInOutTestHelper helper = new StdInOutTestHelper();
		helper.setInput(inputData);
		C011.main(null);
		assertArrayEquals(outputData, helper.getOutput());
	}

	@Test
	public void C011_9() throws Exception {
		String[] inputData = {"wc","4","abc  def","def"," a","abc de", };
		String[] outputData = {"6","23"};

		StdInOutTestHelper helper = new StdInOutTestHelper();
		helper.setInput(inputData);
		C011.main(null);
		assertArrayEquals(outputData, helper.getOutput());
	}

	public static class 一行 {

		public static void main(String[] arg) {
			Scanner scanner = new Scanner(System.in);
			String str = scanner.next();
			System.out.println(str.toUpperCase());
		}
	}

	public static class 五行 {

		public static void main(String[] arg) {
			Scanner scanner = new Scanner(System.in);
			System.out.println(scanner.next().toUpperCase());
			System.out.println(scanner.next().toUpperCase());
			System.out.println(scanner.next().toUpperCase());
			System.out.println(scanner.next().toUpperCase());
			System.out.println(scanner.next().toUpperCase());
		}
	}

	public static class C011 {
		public static void main(String[] args) throws Exception {
			Scanner scanner = new Scanner(System.in);

			String option = scanner.next();
			int N = scanner.nextInt();
			scanner.nextLine();

			int wordCount = 0;
			int charaCount = 0;

			for (int i = 0; i < N ; i++) {

				String line = scanner.nextLine();

				charaCount += line.length() + 1;

				line = line.trim();
				if(line.length() == 0){
					continue;
				}

				String[] words = line.split(" +");
				wordCount += words.length;
			}

			if (option.contains("w")) {
				System.out.println(wordCount);
			}
			if (option.contains("c")) {
				System.out.println(charaCount);
			}

			scanner.close();
		}
	}
}
