package cc.concurrent.mango.benchmark;

import cc.concurrent.mango.benchmark.dao.JdbcUserDao;
import cc.concurrent.mango.benchmark.dao.UserDao;
import cc.concurrent.mango.benchmark.util.DataSourceUtil;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ash
 */
public class JdbcInsertTest extends BenchmarkTemplate {

    @Override
    void doRun(int taskNumPerThread, AtomicInteger successNum, AtomicInteger exceptionNum, AtomicLong totalCost) {
        UserDao userDao = new JdbcUserDao(DataSourceUtil.getDataSource());
        for (int i = 0; i < taskNumPerThread; i++) {
            long t = System.nanoTime();
            boolean ok = false;
            try {
                userDao.insert(100, "test", 1000, new Date());
                ok = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                totalCost.addAndGet(System.nanoTime() - t);
                if (ok) {
                    successNum.incrementAndGet();
                } else {
                    exceptionNum.incrementAndGet();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new JdbcInsertTest().run();
    }

}
