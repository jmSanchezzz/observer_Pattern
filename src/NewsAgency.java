import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NewsAgency {
    private final CopyOnWriteArrayList<Subscriber> subscribers = new CopyOnWriteArrayList<>();
    private final ExecutorService notifyPool;

    public NewsAgency(int notificationThreads) {
        this.notifyPool = Executors.newFixedThreadPool(Math.max(1, notificationThreads));
    }

    public void subscribe(Subscriber s) {
        if (s == null) return;
        subscribers.addIfAbsent(s);
        System.out.printf("%s subscribed%n", s);
    }

    public void unsubscribe(Subscriber s) {
        if (s == null) return;
        boolean removed = subscribers.removeIf(sub -> sub.getId().equals(s.getId()));
        if (removed) System.out.printf("%s unsubscribed%n", s);
    }

    public void publish(News news) {
        Objects.requireNonNull(news);
        System.out.printf("NewsAgency: publishing %s%n", news);

        int matched = 0;
        for (Subscriber sub : subscribers) {
            Set<News.Category> prefs = sub.getPreferences();
            boolean interested = prefs.isEmpty() || prefs.contains(news.getCategory());
            if (!interested) continue;
            matched++;
            notifyPool.execute(() -> {
                try {
                    sub.onNews(news);
                } catch (Exception e) {
                    System.err.printf("Error notifying %s: %s%n", sub, e.getMessage());
                }
            });
        }

        if (matched == 0) {
            System.out.printf("No subscribers matched the category %s for this news.%n", news.getCategory());
        }
    }

    public void shutdown() {
        notifyPool.shutdown();
    }

    /**
     * Helper to shutdown and await termination for a short time (useful in demos)
     */
    public void shutdownAndAwaitTermination(long timeoutMillis) {
        notifyPool.shutdown();
        try {
            if (!notifyPool.awaitTermination(timeoutMillis, TimeUnit.MILLISECONDS)) {
                notifyPool.shutdownNow();
            }
        } catch (InterruptedException ie) {
            notifyPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public int subscriberCount() { return subscribers.size(); }
}
