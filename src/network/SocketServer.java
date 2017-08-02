package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	public static void main(String args[]) throws IOException, InterruptedException {
		int port = Integer.parseInt(args[0]); // サーバ側の待受ポート番号

		try (ServerSocket server = new ServerSocket(port)) {

			for(;;){

				// クライアントからの通信開始要求が来るまで待機
				Socket socket = server.accept();

				// 以下、クライアントからの要求発生後

				try (// クライアントからのデータ受信する設定
					InputStream inputStream = socket.getInputStream();
					DataInputStream inputData = new DataInputStream(inputStream);
					ObjectInputStream input = new ObjectInputStream(inputData);

					// クライアントへのデータ送信を実施する設定
					OutputStream outputStream = socket.getOutputStream();
					DataOutputStream outputData = new DataOutputStream(outputStream)){



						SocketData receivedData = (SocketData) input.readObject();

						// 同時実行で並列にできるように待機時間を設ける
						Thread.sleep(receivedData.getAge() * 100);

						// クライアントに返信するデータを作成する
						outputData.writeBytes("Success: " + receivedData.getName() + "(" + receivedData.getAge() + ")");

				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
