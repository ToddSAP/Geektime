package mqmaster.course17.proxy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BizClass implements Locker {
    int sum = 0;

    @Override
    public void invoke() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sum += 1;
    }

    public static void main(String[] args) throws InterruptedException {
        BizClass bizClass = new BizClass();
        TryLockProxy proxy = new TryLockProxy();
        CountDownLatch downLatch = new CountDownLatch(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
        for (int i = 0; i < 10; i++) {
            executor.execute(
                    () -> {
                        proxy.lock(bizClass);
                        //bizClass.invoke();
                        downLatch.countDown();
                    }
            );
        }
        downLatch.await();
        System.out.println(bizClass.sum);
        executor.shutdown();
    }
}
