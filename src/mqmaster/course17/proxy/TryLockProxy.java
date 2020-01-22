package mqmaster.course17.proxy;

import java.util.concurrent.locks.ReentrantLock;


public class TryLockProxy {
    private final ReentrantLock lock = new ReentrantLock();

    public void lock(Locker locker) {
        try {
            lock.lock();
            locker.invoke();
        } finally {
            lock.unlock();
        }
    }
}
