package network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	public static void main(String args[]) throws UnknownHostException,
			IOException {

		String server = args[0];
		int port = Integer.parseInt(args[1]); // サーバー側のポート番号

		SocketData data = new SocketData(args[2], Integer.parseInt(args[3]));

		// サーバーに数値を送信
		try (Socket socket = new Socket(server, port);

				// サーバにデータ送信する設定
				OutputStream os = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
				ObjectOutputStream oos = new ObjectOutputStream(dos);

				// サーバからデータを受信する設定
				InputStream is = socket.getInputStream();
				DataInputStream dis = new DataInputStream(is);
				InputStreamReader reader = new InputStreamReader(dis);
				BufferedReader buffer = new BufferedReader(reader)) {

			// サーバにデータを送信する
			oos.writeObject(data);

			// サーバからデータを受信する
			String message = buffer.readLine();
			System.out.println(message);
		}
	}
}
