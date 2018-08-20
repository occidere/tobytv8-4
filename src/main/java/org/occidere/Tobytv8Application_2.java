//package org.occidere;
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
//import org.springframework.util.concurrent.ListenableFuture;
//
//@SpringBootApplication
//@EnableAsync
//public class Tobytv8Application_2 {
//	static Logger logger = LoggerFactory.getLogger(Tobytv8Application_2.class);
//	
//	@Component
//	public static class MyService {
//		@Async
//		public ListenableFuture<String> hello() throws InterruptedException { // 리턴 타입이 ListenableFuture
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
//			ListenableFuture<String> f = myService.hello();
//			f.addCallback(s-> System.out.println(s), e-> System.err.println(e));
//			logger.info("exit");
//		};
//	}
//	
//
//	public static void main(String[] args) {
//		try(ConfigurableApplicationContext c = SpringApplication.run(Tobytv8Application_2.class, args)){
//		}
//	}
//}
