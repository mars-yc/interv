package com.master.interv.core.producer_consumer_problem;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerAndConsumerWIthBlockingQueue {

	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
		Thread producer = new Producer(queue);
		Thread consumer = new Consumer(queue);
		producer.start();
		consumer.start();
	}
	
	static class Producer extends Thread {
		private final BlockingQueue<Integer> queue;
		public Producer(BlockingQueue<Integer> queue) {
			this.queue = queue;
		}
		@Override
		public void run() {
			for(int i=1; i<=10; i++) {
				System.out.println("Produced: " + i);
				try {
					queue.put(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class Consumer extends Thread {
		private final BlockingQueue<Integer> queue;
		public Consumer(BlockingQueue<Integer> queue) {
			this.queue = queue;
		}
		@Override
		public void run() {
			while(true) {
				try {
					System.out.println("Consumed: " + queue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}