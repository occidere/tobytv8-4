//package org.occidere;
//
//import java.util.concurrent.Future;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.AsyncResult;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.stereotype.Component;
//
//@SpringBootApplication
//@EnableAsync
//public class Tobytv8Application_1 {
//	static Logger logger = LoggerFactory.getLogger(Tobytv8Application_1.class);
//	
//	@Component
//	public static class MyService {
//		@Async
//		public Future<String> hello() throws InterruptedException { // 리턴 타입이 Future
//			logger.info("hello()"); // main 스레드가 아닌 다른 별도의 스프링 스레드에서 실행됨. 
//			Thread.sleep(2000);
//			return new AsyncResult<>("Hello"); // AsyncResult 로 감싸서 리턴. 
//		}
//	}
//	
//	@Autowired
//	MyService myService;
//	
//	@Bean
//	ApplicationRunner run() {
//		return args -> {
//			logger.info("run()");
//			Future<String> f = myService.hello();
//			logger.info("exit : {}", f.isDone());
//			logger.info("result: {}", f.get());
//		};
//	}
//	
//
//	public static void main(String[] args) {
//		try(ConfigurableApplicationContext c = SpringApplication.run(Tobytv8Application_1.class, args)){
//		}
//	}
//}
