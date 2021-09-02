import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {

    private final String threadNamePrefix;
    private int counter = 0;

    public CustomThreadFactory(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }


    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, String.format("%s - %d", threadNamePrefix, counter++));
    }
}
