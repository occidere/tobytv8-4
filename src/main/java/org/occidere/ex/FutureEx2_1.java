package org.occidere.ex;

import java.util.Objects;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureEx2_1 {
	static Logger logger = LoggerFactory.getLogger(FutureEx2_1.class);
	
	@FunctionalInterface
	interface SuccessCallback {
		void onSuccess(String result);
	}
	
	@FunctionalInterface
	interface ExceptionCallback {
		void onError(Throwable t);
	}
	
	public static class CallbackFutureTask extends FutureTask<String> {
		SuccessCallback sc;
		ExceptionCallback ec;
		
		public CallbackFutureTask(Callable<String> callable, SuccessCallback sc, ExceptionCallback ec) {
			super(callable);
			this.sc = Objects.requireNonNull(sc); // 자동 Null check
			this.ec = Objects.requireNonNull(ec);
		}
		
		@Override
		protected void done() { // FutureTask 내의 done()
			try {
				sc.onSuccess(get()); // get() 으로 결과값 호출. 
			} catch(InterruptedException e) {
				// 종료 시그널이긴 하나, 강제성은 없음 -> 현재 스레드에게 인터럽트를 직접 줌.
				Thread.currentThread().interrupt();
			} catch(ExecutionException e) {
				ec.onError(e); // 발생한 예외를 호출한 곳으로 넘김. 
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		ExecutorService es = Executors.newCachedThreadPool();
		
		CallbackFutureTask f = new CallbackFutureTask(()-> {
			Thread.sleep(2000);
			logger.info("Async");
			
//			if(true) throw new RuntimeException("Error!");
			
			return "Hello";
		},
				s-> System.out.println("Result: " + s),
				e-> System.err.println("Error: " + e.getMessage()));
		
		es.execute(f);
		es.shutdown();
	}
}