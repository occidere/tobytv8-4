package org.occidere;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

public class LoadTest {
	static Logger logger = LoggerFactory.getLogger(LoadTest.class);
	
	static AtomicInteger counter = new AtomicInteger(0);
	
	public static void main(String[] args) throws Exception {
		ExecutorService es = Executors.newFixedThreadPool(100);
		
		RestTemplate rt = new RestTemplate();
		String url = "http://localhost:8080/dr"; // /async, /callable, /dr
		
		StopWatch main = new StopWatch();
		main.start();
		
		for(int i=0;i<100;i++) {
			es.execute(()-> {
				int idx = counter.addAndGet(1);
				logger.info("Thread {}", idx);
				
				StopWatch sw = new StopWatch();
				sw.start();
				
				rt.getForObject(url, String.class);
				
				sw.stop();
				logger.info("Elapsed: {} {}", idx, sw.getTotalTimeSeconds());
			});
		}
		
		es.shutdown();
		es.awaitTermination(100, TimeUnit.SECONDS);
		
		main.stop();
		logger.info("Total: {}", main.getTotalTimeSeconds());
	}
}
