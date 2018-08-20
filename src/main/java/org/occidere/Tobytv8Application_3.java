package org.occidere;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@SpringBootApplication
@EnableAsync
public class Tobytv8Application_3 {
	static Logger logger = LoggerFactory.getLogger(Tobytv8Application_3.class);
	
	@RestController
	public static class MyController {
		/* servlet 스레드가 요청받은 작업을 직접 처리해서 반환 */
		@GetMapping("/async")
		public String async() throws InterruptedException {
			logger.info("async");
			Thread.sleep(2000);
			return "async hello";
		}
		
		/* servlet 스레드가 요청받은 작업을 작업 스레드가 받아서 처리하고 servlet 스레드에게 전달 */
		@GetMapping("/callable")
		public Callable<String> callable() throws InterruptedException {
			return ()-> {
				logger.info("callable");
				Thread.sleep(2000);
				return "callable hello";
			};
		}
		
		/** ################################## */
		
		/* DeferredResult를 담을 큐. */
		Queue<DeferredResult<String>> results = new ConcurrentLinkedQueue<>();
		
		/* 1개의 DeferredResult 처리 스레드에서, 특정 트리거로 반응하는 다중요청을 한번에 처리 */ 
		@GetMapping("/dr")
		public DeferredResult<String> deferredResult() throws InterruptedException {
			logger.info("dr");
			DeferredResult<String> dr = new DeferredResult<>(600000L);
			results.add(dr); // 큐에 추가 
			return dr;
		}
		
		@GetMapping("/dr/count")
		public String drcount() {
			return String.valueOf(results.size()); // 큐 사이즈 
		}
		
		@GetMapping("/dr/event")
		public String drevent(String msg) {
			for(DeferredResult<String> dr : results) {
				dr.setResult("/dr/event " + msg);
				results.remove(dr);
			}
			return "OK";
		}
		
		
		/** ################################## */
		
		/* Stream 방식으로 처리 */
		@GetMapping("/emitter")
		public ResponseBodyEmitter emitter() throws InterruptedException {
			ResponseBodyEmitter emitter = new ResponseBodyEmitter(600000L);
			
			/* 요청이 넘어오는대로 바로 리턴 */
			Executors.newSingleThreadExecutor().submit(()-> {
				try {
					for(int i=1;i<=50;i++) {
						emitter.send("<p>Stream " + i + "</p>");
						Thread.sleep(100);
					}
				} catch(Exception e) {
				}
			});
			
			return emitter;
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Tobytv8Application_3.class);
	}
}
