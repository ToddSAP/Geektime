package mqmaster.course17.aop;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockAOP implements MethodBeforeAdvice, AfterReturningAdvice {
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        if (needLock(method)) {
            lock.lock();
            //System.out.println(Thread.currentThread().getName() +" try to lock in method " + method.getName());
        }
    }


    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        if (needLock(method)) {
            lock.unlock();
            //System.out.println(Thread.currentThread().getName() +" unlocked in method " + method.getName());
        }
    }

    private boolean needLock(Method method) {
        return Arrays.stream(method.getDeclaredAnnotations()).anyMatch(a->a instanceof TryLock);
    }
}
