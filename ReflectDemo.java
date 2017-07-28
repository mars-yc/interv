package com.master.interv.core.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

//http://www.cnblogs.com/lzq198754/p/5780331.html
public class ReflectDemo {

	public static void main(String[] args) {
		try {
			// usage 1: create instance of class
			Class<?> clas = Class.forName("com.master.interv.core.reflect.SampleClass");
			Object obj = clas.newInstance();
			System.out.println("The object is: " + obj);
			
			// usage 2: get the class name and package for a given object
			System.out.println("The class name is: " + obj.getClass().getName());
			
			// usage 3: get the super class of a given object
			System.out.println("The super class is: " + obj.getClass().getSuperclass());
			
			// usage 4: get the interfaces implemented
			Class<?> interfaces[] = clas.getInterfaces();
			System.out.print("Interfaces implemented: ");
			for(int i=0; i< interfaces.length; i++) {
				System.out.print(interfaces[i] + " ");
			}
			System.out.println();
			
			// usage 5: get all the constructors of a class
			Constructor<?>[] cons = clas.getConstructors();
			for(int i=0; i<cons.length; i++) {
				System.out.print("cons[" + (i+1) + "]: (");
				Class<?>[] parameterTypes = cons[i].getParameterTypes();
				for(int j=0; j<parameterTypes.length; j++) {
					if(j == parameterTypes.length -1)
						System.out.print(parameterTypes[j].getName());
					else
						System.out.print(parameterTypes[j].getName() + ", ");
				}
				System.out.print(")");
			}
			System.out.println();
			
			// usage 6: instantiate a class by specified constructor
			for(int i=0; i<cons.length; i++) {
				Class<?>[] parameterTypes = cons[i].getParameterTypes();
				if(parameterTypes.length == 1 && parameterTypes[0] == java.lang.String.class) {
					Object instance = cons[i].newInstance("Lily");
					System.out.println(instance);
				}
			}
			
			// usage 7: get all the fields including public/private/protected
			Field[] fields1 = clas.getDeclaredFields();
			for(int i=0; i<fields1.length; i++) {
				int modifier = fields1[i].getModifiers();
				String priv = Modifier.toString(modifier); // privilege
				Class<?> type = fields1[i].getType();
				System.out.println(priv + " " + type.getName() + " " + fields1[i].getName() + ";");
			}
			
			System.out.println("-----------");
			
			// usage 8: // get all the public fields in super class or the interface implemented
			Field[] fields2 = clas.getFields();
			for(int i=0; i<fields2.length; i++) {
				int modifier = fields2[i].getModifiers();
				String priv = Modifier.toString(modifier); // privilege
				Class<?> type = fields2[i].getType();
				System.out.println(priv + " " + type.getName() + " " + fields2[i].getName() + ";");
			}
			
			System.out.println();
			
			// usage 9: get all the public methods declared in the class or the super classes
			Method[] methods1 = clas.getMethods();
			for(int i=0; i<methods1.length; i++) {
				Class<?> returnType = methods1[i].getReturnType();
				Class<?> params[] = methods1[i].getParameterTypes();
				System.out.print(Modifier.toString(methods1[i].getModifiers()) + " ");
				System.out.print(returnType.getName() + " ");
				System.out.print(methods1[i].getName() + "(");
				for(int j=0; j<params.length; j++) {
					System.out.print(params[j].getName() + " arg" + j);
					if(j != params.length-1)
						System.out.print(", ");
				}
				System.out.println(")");
			}
			
			System.out.println();
			
			// usage 10: get all the methods declared in the class including public/protected/private/etc
			Method[] methods2 = clas.getDeclaredMethods();
			for(int i=0; i<methods2.length; i++) {
				Class<?> returnType = methods2[i].getReturnType();
				Class<?> params[] = methods2[i].getParameterTypes();
				System.out.print(Modifier.toString(methods2[i].getModifiers()) + " ");
				System.out.print(returnType.getName() + " ");
				System.out.print(methods2[i].getName() + "(");
				for(int j=0; j<params.length; j++) {
					System.out.print(params[j].getName() + " arg" + j);
					if(j != params.length-1)
						System.out.print(", ");
				}
				System.out.println(")");
			}
			
			// usage 11: invoke a method
			Method method = clas.getMethod("test", double.class, String.class);
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}

class SampleClass extends SampleSuperClass implements SampleInterface1, SampleInterface2 {
	
	private String name;
	private static final String address = "Shanghai";
	public String phone = "150*******";
	protected String card = "aaaa";
	
	public SampleClass() {}
	
	public SampleClass(String name) {
		this.name = name;
	}
	
	@Override
	public void fun() {
		System.out.println("function");
	}
	
	void test(double amount, String name) {
		System.out.println("test");
	}
	
	public static void testPublicStatic() {
		System.out.println("This is public static function");
	}
	public void testPublic() {
		System.out.println("This is public function");
	}
	protected void testProtected() {
		System.out.println("This is protected function");
	}
	private void testPrivate() {
		System.out.println("This is private function");
	}

	@Override
	public String toString() {
		return "SampleClass [name=" + name + ", phone=" + phone + ", card=" + card + "]";
	}
	
}

class SampleSuperClass {
	
	protected String gender;
	private static final String mail = "abc@hotmail.com";
	public int age = 22;
	
	protected void setGender(String gender) {
		this.gender = gender;
	}
	public String getGender() {
		return gender;
	}
	public static void sayHi() {
		System.out.println("Hi");
	}
	public void sayHello() {
		System.out.println("Hello");
	}
	private void sayYes() {
		System.out.println("Yes");
	}
}

interface SampleInterface1 {
	void fun();
}

interface SampleInterface2 {
	public final String str = "ssds";
}