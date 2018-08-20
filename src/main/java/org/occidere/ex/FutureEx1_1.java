package org.occidere.ex;

import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureEx1_1 {
	static Logger logger = LoggerFactory.getLogger(FutureEx1_1.class);
	public static void main(String[] args) throws Exception {
		ExecutorService es = Executors.newCachedThreadPool();
		
		Future<String> f = es.submit(()-> {
			Thread.sleep(2000);
			logger.info("Async");
			return "Hello";
		});
		
		logger.info("isDone: {}", f.isDone());
		logger.info("res: {}", f.get());
		
		es.shutdown();
		
		
	}
}