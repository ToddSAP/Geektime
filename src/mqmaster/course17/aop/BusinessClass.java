package mqmaster.course17.aop;

public class BusinessClass {
    int sum = 0;

    @TryLock
    public void increase() {
        sum += 1;
    }

    public int getSum() {
        return sum;
    }
}
