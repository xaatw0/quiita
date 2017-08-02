package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

public class AsynchronousSocket {

	public static final int PORT = 12345;
	public static final int SIZE = 20;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		Consumer<Throwable> error = (t) -> t.printStackTrace();

		server(error);
		client(error);

		while (true) {
			Thread.sleep(1);
		}
	}

	public static void server(Consumer<Throwable> error) throws IOException {

		AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
		server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		server.bind(new InetSocketAddress("localhost", PORT));

		server.accept(null,
				new CompletionHandler<AsynchronousSocketChannel, Void>() {
					@Override
					public void completed(AsynchronousSocketChannel socket,Void attachment) {

						System.out.println("SERVER: Accept");
						ByteBuffer buffer = ByteBuffer.allocate(SIZE);
						socket.read(buffer, buffer,
								new CompletionHandler<Integer, ByteBuffer>() {
									@Override
									public void completed(Integer result, ByteBuffer attachment) {
										System.out.println("SERVER: Read " + result);
										if (result < 0) {
											error.accept(new IOException("Closed"));
											try {
												socket.close();
											} catch (IOException e) {
											}
											return;
										}

										if (buffer.hasRemaining()) {
											socket.read(attachment, attachment, this);
											return;
										}

										buffer.flip();

										socket.write(
												buffer,
												buffer,
												new CompletionHandler<Integer, ByteBuffer>() {
													@Override
													public void completed(Integer result, ByteBuffer attachment) {
														System.out.println("SERVER: Write "+ result);
														if (result == 0) {
															error.accept(new IOException("Write Error"));
															return;
														}

														if (buffer.hasRemaining()) {
															socket.write(buffer, buffer, this);
															return;
														}

														System.out.println("SERVER: Done "+ Arrays.toString(buffer.array()));

														try {
															socket.close();
														} catch (IOException e) {
														}
													}

													@Override
													public void failed(Throwable exc, ByteBuffer attachment) {
														error.accept(exc);
													}
												});
									}

									@Override
									public void failed(Throwable exc, ByteBuffer attachment) {
										error.accept(exc);
									}
								});
					}

					@Override
					public void failed(Throwable exc, Void attachment) {
						error.accept(exc);
					}
				});
	}

	public static void client(Consumer<Throwable> error) throws IOException {
		AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
		client.connect(new InetSocketAddress("localhost", PORT), null,
				new CompletionHandler<Void, Void>() {
					@Override
					public void completed(Void result, Void attachment) {
						System.out.println("CLIENT: Connected");
						ByteBuffer wBuf = ByteBuffer.allocate(SIZE);
						Random rand = new Random();
						while (wBuf.hasRemaining()) {
							wBuf.put((byte) (rand.nextInt() % 128));
						}
						wBuf.flip();

						client.write(wBuf, wBuf,
								new CompletionHandler<Integer, ByteBuffer>() {
									@Override
									public void completed(Integer result,ByteBuffer attachment) {
										System.out.println("CLIENT: write "+ result);
										if (result == 0) {
											error.accept(new IOException("Write Error"));
											return;
										}

										if (wBuf.hasRemaining()) {
											client.write(wBuf, wBuf, this);
											return;
										}

										ByteBuffer rBuf = ByteBuffer.allocate(SIZE);
										client.read(rBuf, rBuf,
												new CompletionHandler<Integer, ByteBuffer>() {
													@Override
													public void completed(Integer result,ByteBuffer attachment) {
														System.out.println("CLIENT: read "+ result);
														if (result < 0) {
															error.accept(new IOException("Closed"));
															try {
																client.close();
															} catch (IOException e) {
															}
															return;
														}

														if (rBuf.hasRemaining()) {
															client.read(rBuf,rBuf, this);
															return;
														}

														System.out.println("CLIENT: done "
																+ Arrays.toString(rBuf.array()));
														try {
															client.close();
														} catch (IOException e) {
														}
													}

													@Override
													public void failed(Throwable exc, ByteBuffer attachment) {
														error.accept(exc);
													}
												});
									}

									@Override
									public void failed(Throwable exc,ByteBuffer attachment) {
										error.accept(exc);
									}
								});
					}

					@Override
					public void failed(Throwable exc, Void attachment) {
						error.accept(exc);
					}
				});
	}
}
