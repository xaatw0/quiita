import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;

/**
 *複数のディレクトリを監視する。対象は新規ファイルのみ。
 *参考)
 *ディレクトリの変更監視
 *https://docs.oracle.com/cd/E26537_01/tutorial/essential/io/notification.html
 *
 *WatchService で再帰的にディレクトリ監視で難あり
 *http://etc9.hatenablog.com/entry/20120519/1337449611
 */
public class DirectoriesWatcher implements Runnable {

	private String[] dirs;

	public DirectoriesWatcher(String... dirs) {
		this.dirs = dirs;
	}

	@Override
	public void run() {

		Map<WatchKey, Path> watchKeys = new HashMap<>();
		WatchService watcher = null;

		try{
			watcher = FileSystems.getDefault().newWatchService();

			// 監視するディレクトリを登録する。
			// 後でWatchKeyからディレクトリを参照できるようにMapに保存する
			for (String dirPath: dirs){
				Path path = Paths.get(dirPath);
				WatchKey key = path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
				watchKeys.put(key, path);
			}
		} catch(IOException ex){
			ex.printStackTrace();
			return;
		}

		for(;;) {

			//監視キーの送信を待機
		    WatchKey key;
		    try {
		    	key = watcher.take();
		    } catch (InterruptedException x) {
		    	continue;
		    }

			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();

				// この監視キーが登録されるイベントはENTRY_CREATEイベントだけですが、
		        // イベントが消失したり破棄されたりした場合は、OVERFLOWイベントが
		        // 発生することがあります。
				if (kind == StandardWatchEventKinds.OVERFLOW) {
				    continue;
				}

			    //ファイル名はイベントのコンテキストです。
				WatchEvent<Path> ev = (WatchEvent<Path>)event;
				Path filename = ev.context();

	            Path child = watchKeys.get(key).resolve(filename);

	            boolean isPlainText = false;

	            try {
					isPlainText = Files.probeContentType(child).equals("text/plain");
				} catch (IOException e) {
					e.printStackTrace();
				}

	            System.out.printf("New file '%s' is a %splain text file.%n", filename, isPlainText ? "" : "not ");

			}

			// 監視を再開する
			key.reset();
		}

	}

    public static void main(String[] args) throws InterruptedException {

		DirectoriesWatcher watcher = new DirectoriesWatcher("C:\\tmp\\1", "C:\\tmp\\2");
		Thread thread = new Thread(watcher);
		thread.start();

		System.out.println("監視を開始しました");
	}
}


