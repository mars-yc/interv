package com.master.interv.thread;

public class BeCarefulInThread {
	
	private static int number;
	private static volatile int vol;
	
	public static void main(String[] args) {
		//crossMonitor();
		checkUnVolatile();
		checkVolatile();
	}
	
	static class Lock {}
	
	private static Lock lock1 = new Lock();
	private static Lock lock2 = new Lock();
	
	//synchronized/wait/notify should based on the same monitor
	public static void crossMonitor() {
		synchronized(lock1) {
			try {
				lock2.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//the result is not as expected since the ++ operation is not atomic
	public static void checkUnVolatile() {
		ThreadGroup group = new ThreadGroup("unvolatile-demo-group");
		
		
		Thread t1 = new Thread() {
			@Override
			public void run() {
				for(int i=0; i<100000000; i++) {
					number++;
				}
			}
		};
		new Thread(group, t1, "thread-1").start();
		
		Thread t2 = new Thread() {
			@Override
			public void run() {
				for(int i=0; i<100000000; i++) {
					number++;
				}
			}
		};
		new Thread(group, t2, "thread-2").start();
		
		while(group.activeCount() > 0) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("unvolatile - " + number);
	}
	
	//the result is not as expected since the ++ operation is not atomic
	public static void checkVolatile() {
		ThreadGroup group = new ThreadGroup("volatile-demo-group");
		
		
		Thread t1 = new Thread() {
			@Override
			public void run() {
				for(int i=0; i<100000000; i++) {
					vol++;
				}
			}
		};
		new Thread(group, t1, "thread-1").start();
		
		Thread t2 = new Thread() {
			@Override
			public void run() {
				for(int i=0; i<100000000; i++) {
					vol++;
				}
			}
		};
		new Thread(group, t2, "thread-2").start();
		
		while(group.activeCount() > 0) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("volatile - " + vol);
	}

}