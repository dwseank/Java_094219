import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Thread-safe database implemented using a HashMap.
 * Can have multiple concurrent readers (up to a set max) or 1 writer at a time.
 */
public class Database {
    private final Map<String, String> data;
    private final int maxReaders;
    private final Lock lock;
    private final Condition canRead;
    private final Condition canWrite;
    private final Set<Thread> readers;
    private Thread writer;

    /**
     * Initializes an empty database with a set maximum number of concurrent readers.
     * @param maxNumOfReaders maximum numbers of readers
     */
    public Database(int maxNumOfReaders) {
        data = new HashMap<>();
        maxReaders = maxNumOfReaders;
        lock = new ReentrantLock();
        canRead = lock.newCondition();
        canWrite = lock.newCondition();
        readers = new HashSet<>();
        writer = null;
    }

    /**
     * Writes to the database.
     * @param key the key in the database
     * @param value the value to write
     */
    public void put(String key, String value) {
        data.put(key, value);
    }

    /**
     * Reads from the database.
     * @param key the key in the database
     * @return the value at key
     */
    public String get(String key) {
        return data.get(key);
    }

    /**
     * Tries to acquire reading permission from the database.
     * To be used before reading from the database.
     * @return true if acquired read
     */
    public boolean readTryAcquire() {
        lock.lock();
        try {
            if (readers.size() >= maxReaders || writer != null) {
                return false;
            }
            readers.add(Thread.currentThread());
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Acquires reading permission from the database.
     * To be used before reading from the database.
     * If the reader can't acquire read then it will wait until it can read.
     */
    public void readAcquire() {
        lock.lock();
        try {
            while (readers.size() >= maxReaders || writer != null) {
                canRead.await();
            }
            readers.add(Thread.currentThread());
        } catch (InterruptedException e) {
            // ignored
        } finally {
            lock.unlock();
        }
    }

    /**
     * Releases the reading permission from the reader.
     * To be used after reading from the database.
     * @throws IllegalMonitorStateException if called by a thread that didn't acquire read
     */
    public void readRelease() {
        lock.lock();
        try {
            Thread currentThread = Thread.currentThread();
            if (!readers.contains(currentThread)) {
                throw new IllegalMonitorStateException("Illegal read release attempt");
            }
            readers.remove(currentThread);
            canRead.signal();
            if (readers.isEmpty()) {
                canWrite.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Acquires writing permission from the database.
     * To be used before writing to the database.
     * If the writer can't acquire write then it will wait until it can write.
     */
    public void writeAcquire() {
        lock.lock();
        try {
            while (!readers.isEmpty() || writer != null) {
                canWrite.await();
            }
            writer = Thread.currentThread();
        } catch (InterruptedException e) {
            // ignored
        } finally {
            lock.unlock();
        }
    }

    /**
     * Tries to acquire writing permission from the database.
     * To be used before writing to the database.
     * @return true if acquired write
     */
    public boolean writeTryAcquire() {
        lock.lock();
        try {
            if (!readers.isEmpty() || writer != null) {
                return false;
            }
            writer = Thread.currentThread();
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Releases the writing permission from the writer.
     * To be used after writing to the database.
     * @throws IllegalMonitorStateException if called by a thread that didn't acquire write
     */
    public void writeRelease() {
        lock.lock();
        try {
            if (writer != Thread.currentThread()) {
                throw new IllegalMonitorStateException("Illegal write release attempt");
            }
            writer = null;
            canWrite.signal();
            canRead.signalAll();
        } finally {
            lock.unlock();
        }
    }
}