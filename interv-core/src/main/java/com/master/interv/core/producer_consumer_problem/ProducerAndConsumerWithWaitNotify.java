package com.master.interv.core.producer_consumer_problem;

import java.util.Vector;

public class ProducerAndConsumerWithWaitNotify {
	
	public static void main(String[] args) {
		Vector<Integer> vector = new Vector<Integer>();
		int maxSize = 5;
		Thread producer = new Thread(new Producer(vector, maxSize), "Producer");
		Thread consumer = new Thread(new Consumer(vector), "Consumer");
		producer.start();
		consumer.start();
	}
	
	static class Producer implements Runnable {
		private final Vector<Integer> vector;
		private final int maxSize;
		public Producer(Vector<Integer> vector, int maxSize) {
			this.vector = vector;
			this.maxSize = maxSize;
		}
		public void run() {
			for(int i=1; i<=10; i++) {
				try {
					produce(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		private void produce(int i) throws InterruptedException {
			while(vector.size() == maxSize) {
				synchronized(vector) {
					System.out.println("vector is full " + Thread.currentThread().getName() + " is waiting");
					vector.wait();
				}
			}
			synchronized(vector) {
				vector.add(i);
				vector.notifyAll();
			}
		}
	}
	
	static class Consumer implements Runnable {
		private final Vector<Integer> vector;
		public Consumer(Vector<Integer> vector) {
			this.vector = vector;
		}
		public void run() {
			while(true) {
				try {
					System.out.println("Consumed: " + consume());
					Thread.sleep(50); //make an opportunity for producer to fill the vector
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		private Integer consume() throws InterruptedException {
			while(vector.isEmpty()) {
				synchronized(vector) {
					System.out.println("vector is empty " + Thread.currentThread().getName() + " is waiting");
					vector.wait();
				}
			}
			synchronized(vector) {
				vector.notifyAll(); //notify all producers
				return vector.remove(0);
			}
		}
	}

}