package network;

import java.io.Serializable;

public class SocketData implements Serializable{
	private String name;
	private int age;

	public SocketData(String name, int age){
		this.name = name;
		this.age = age;
	}

	public String getName(){
		return name;
	}

	public int getAge(){
		return age;
	}

}
