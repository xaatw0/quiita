package network;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class SocketThreadedServerTest {

	@Test
	public void Result() throws IOException{

		// サーバが受信(クライアントが送信)するデータを作成する
		InputStream input;

	    try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    		ObjectOutputStream oos = new ObjectOutputStream(baos)){

	    	SocketData data = new SocketData("name", 5);
	    	oos.writeObject(data);
	    	input = new ByteArrayInputStream(baos.toByteArray());
	    }

	    // サーバが送信(クライアントが受信)するデータ
	    ByteArrayOutputStream output = new ByteArrayOutputStream();

	    // サーバの受信と発信を実施する
	    SocketThreadedServer.Result target = new SocketThreadedServer.Result(input, output);
	    target.run();

	    // サーバが送信(クライアントが受信)した内容を確認する
	    try(BufferedReader buffer = new BufferedReader(
				new InputStreamReader(
						new DataInputStream(
								new ByteArrayInputStream(output.toByteArray()))))){
	    	assertThat(buffer.readLine(), is("Success: name(5)"));
	    }
	}

}
