package com.master.interv.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class CreatingThread {
	
	public static void main(String[] args) {
		// 1
		Thread creationImplementRunnable = new Thread(new CreationImplementsRunnable(), "Thread-creationImplementRunnable");
		creationImplementRunnable.start();
		
		// 2
		Thread creationExtendsThread = new CreationExtendsThread("Thread-creationExtendsThread");
		creationExtendsThread.start();
		
		// 3
		Thread creationImplementsCallable1 = new Thread(new FutureTask<String>(new CreationImplementsCallable()));
		creationImplementsCallable1.start();
		
		// 4
		ExecutorService executorService1 = Executors.newFixedThreadPool(1);
		executorService1.submit(new CreationImplementsCallable());
		executorService1.shutdown();
		
		// 5
		ExecutorService executorService2 = Executors.newFixedThreadPool(1);
		FutureTask<String> task = new FutureTask<String>(new CreationImplementsCallable());
		executorService2.submit(task);
		executorService2.shutdown();
		try {
			System.out.println(task.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	static class CreationImplementsRunnable implements Runnable {
		public void run() {
			System.out.println(Thread.currentThread().getName());
		}
	}
	
	static class CreationExtendsThread extends Thread {
		public CreationExtendsThread(String name) {
			super(name);
		}
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
		}
	}
	
	static class CreationImplementsCallable implements Callable<String> {
		public String call() throws Exception {
			System.out.println(Thread.currentThread().getName());
			return "return from CreationImplementsCallable";
		}
	}

}