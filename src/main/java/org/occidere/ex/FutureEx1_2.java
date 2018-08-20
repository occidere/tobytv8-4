package org.occidere.ex;

import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureEx1_2 {
	static Logger logger = LoggerFactory.getLogger(FutureEx1_2.class);
	
	public static void main(String[] args) throws Exception {
		ExecutorService es = Executors.newCachedThreadPool();
		
		// Future 인터페이스의 구현체 
		FutureTask<String> f = new FutureTask<String>(()-> {
			Thread.sleep(2000);
			logger.info("Async");
			return "Hello";
		}) {
			@Override
			protected void done() {	// FutureTask 내부에 done()이 있어서 
				try {
					System.out.println(get()); // get() 을 통한 결과를 받아올 수 있다. 
				} catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch(ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		es.execute(f);
		es.shutdown();
	}
}