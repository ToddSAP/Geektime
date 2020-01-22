package mqmaster.course16.lru;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;

public class LruCacheImplTest {
    LruCache<String, String> lruCache = new LruCacheImpl<>(2);

    @Test
    public void test_evict_success_with_few_elements() {
        //Given

        //When
        lruCache.put("1", "a");
        lruCache.put("2", "b");
        lruCache.get("1");
        lruCache.put("3", "c");
        System.out.println(lruCache.toString());

        //Then
        Assert.assertEquals(lruCache.size(), 2);
        Assert.assertEquals(lruCache.keys()[0].toString(), "1");
        Assert.assertEquals(lruCache.keys()[1].toString(), "3");
    }

    @Test
    public void test_evict_success_with_many_elements() {
        //Given
        lruCache = new LruCacheImpl<>(3);

        //When
        lruCache.put("1", "a");
        lruCache.put("2", "b");
        lruCache.put("3", "c");
        lruCache.put("4", "d");
        lruCache.put("5", "e");
        lruCache.get("5");
        lruCache.get("3");
        lruCache.put("6","f");

        //Then
        Assert.assertEquals(lruCache.keys()[0].toString(), "5");
        Assert.assertEquals(lruCache.keys()[1].toString(), "3");
        Assert.assertEquals(lruCache.keys()[2].toString(), "6");
    }

    @Test
    public void test_evict_success_with_large_elements() {
        //Given
        int capacity = 100000;
        long start = System.currentTimeMillis();
        lruCache = new LruCacheImpl<>(capacity);
        for (int i=0;i<capacity;i++) {
            lruCache.put(String.valueOf(i), String.valueOf(i));
        }
        System.out.println("Construction "+capacity+" elements cost "+(System.currentTimeMillis()-start)+"ms");

        //When
        start = System.currentTimeMillis();
        String result = lruCache.get("99999");
        System.out.println("Lookup through "+capacity+" elements cost "+(System.currentTimeMillis()-start)+"ms");

        //Then
        Assert.assertEquals("99999", result);
    }

    @Test
    public void test_get_failed() {
        //Given

        //When
        String result = lruCache.get("1");

        //Then
        Assert.assertNull(result);
    }

    @Test
    public void test_hugh_elements_with_linkedhashmap() {
        //Given
        int capacity = 1000000;
        LinkedHashMap<String, String> cache = new LinkedHashMap<>();
        for (int i=0;i<capacity;i++) {
            cache.put(String.valueOf(i), String.valueOf(i));
        }

        //When
        long start = System.currentTimeMillis();
        cache.get("999999");
        System.out.println("Lookup through "+capacity+" elements cost "+(System.currentTimeMillis()-start)+"ms");

        //Then
    }
}
