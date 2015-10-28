package serializetest;

import java.io.Serializable;

public class Person2 implements Serializable{
	private String name;
	private int age;

	public Person2(String name, int age){
		this.name = name;
		this.age = age;
	}

	public String getName(){return name;}
	public int getAge(){ return age;}


	private void writeObject(java.io.ObjectOutputStream stream)
	        throws java.io.IOException {
	    age = age >> 2;
	    stream.defaultWriteObject();
	}

    private void readObject(java.io.ObjectInputStream stream)
        throws java.io.IOException, ClassNotFoundException{

    	stream.defaultReadObject();
        age = age << 2;
    }
}
