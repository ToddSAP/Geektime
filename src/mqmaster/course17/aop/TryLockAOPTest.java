package mqmaster.course17.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TryLockAOPTest {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                new String[] { "mqmaster/course17/aop/custom.xml" });
        // can inject proxy object via annotation
        BusinessClass businessClass = (BusinessClass) appContext.getBean("tryLockAOPProxy");

        int times = 5000;
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(times,times,1000,
                        TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(times));

        run(times, executor, businessClass);
        executor.shutdown();
    }

    public static void run(int times, ThreadPoolExecutor executor, BusinessClass businessClass) {
        for (int i=0;i<times;i++) {
            executor.execute(
                    ()-> {
                        businessClass.increase();
                    }
            );
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(businessClass.getSum());
    }
}
