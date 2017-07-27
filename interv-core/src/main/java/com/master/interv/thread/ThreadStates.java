package com.master.interv.thread;

public class ThreadStates {
	
	public static void main(String[] args) {
		/*stateNew();
		stateRunnable();
		stateTerminated();*/
	}
	
	/**
	 * {@link Thread.State#NEW State.NEW}
	 */
	public static void stateNew() {
		Thread t = new Thread();
		System.out.println(t.getState());
	}
	
	/**
	 * {@link Thread.State#RUNNABLE State.RUNNABLE}
	 */
	public static void stateRunnable() {
		Thread t = new Thread();
		t.start();
		System.out.println(t.getState());
	}
	
	/**
	 * {@link Thread.State#TERMINATED State.TERMINATED}
	 */
	public static void stateTerminated() {
		Thread t = new Thread();
		t.start();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(t.getState());
	}
	
}