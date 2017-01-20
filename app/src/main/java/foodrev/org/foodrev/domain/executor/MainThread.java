package foodrev.org.foodrev.domain.executor;

public interface MainThread {

    void post(final Runnable runnable);
}
