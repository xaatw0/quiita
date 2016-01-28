import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class MultiThreadInsert implements Callable<Integer> {

	private final static String POSTGRESQL_DRIVER = "org.postgresql.Driver";

    private int start;
    private int insertSize;
    private int commitInterval;

    public MultiThreadInsert(int start, int insertSize, int commitInterval) {
        this.start = start;
        this.insertSize = insertSize;
        this.commitInterval = commitInterval;
    }

    @Override
    public Integer call() throws Exception {

        // データベースの指定
        String server   = "localhost";   // PostgreSQL サーバ ( IP または ホスト名 )
        String dbname   = "parkingdb";         // データベース名
        String url = "jdbc:postgresql://" + server + "/" + dbname;
        String user     = "postgres";         //データベース作成ユーザ名
        String password = "ps2227";     //データベース作成ユーザパスワード

        final String sqlStr = "INSERT INTO DEST(COLUMN1) VALUES(?)";

        Class.forName (POSTGRESQL_DRIVER);
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement sql = connection.prepareStatement(sqlStr);) {
            connection.setAutoCommit(false);

            int j = 0;
            final int n = start + insertSize;
            for (int i = start; i < n; i++) {
                sql.setString(1, String.format("%016d", i));
                sql.executeUpdate();

                if (j == commitInterval) {
                    connection.commit();
                    j = 0;
                }
                j++;
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        long s = System.currentTimeMillis();

        final int INSERT_SIZE = 100_000;
        final int THREAD_SIZE = 4;
        final int INSERT_PER_THREAD_SIZE = INSERT_SIZE / THREAD_SIZE;
        final int COMMIT_INTERVAL = 1000;

        List<Future<Integer>> results = new ArrayList<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_SIZE);
        for (int i=0; i<THREAD_SIZE; i++) {
            int startNum = i * INSERT_PER_THREAD_SIZE + 1;
            results.add(threadPool.submit(
               new MultiThreadInsert(startNum, INSERT_PER_THREAD_SIZE, COMMIT_INTERVAL)));
        }
        for (Future<Integer> result : results) {
            try {
                result.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();

        long e = System.currentTimeMillis();
        System.out.println(e - s);
    }
}
