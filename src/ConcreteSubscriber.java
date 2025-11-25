import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ConcreteSubscriber implements Subscriber {
    private final String id;
    private final String name;
    private volatile Set<News.Category> preferences;

    public ConcreteSubscriber(String id, String name, Set<News.Category> preferences) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        if (preferences == null || preferences.isEmpty()) {
            this.preferences = Collections.emptySet();
        } else {
            this.preferences = new HashSet<>(preferences);
        }
    }

    @Override
    public void onNews(News news) {
        // Simplified output: no timestamps
        System.out.printf("%s received: %s%n", name, news);
    }

    @Override
    public Set<News.Category> getPreferences() {
        return preferences;
    }

    public void setPreferences(Set<News.Category> newPrefs) {
        if (newPrefs == null || newPrefs.isEmpty()) {
            this.preferences = Collections.emptySet();
        } else {
            this.preferences = new HashSet<>(newPrefs);
        }
    }

    @Override
    public String getId() { return id; }

    @Override
    public String toString() { return String.format("Subscriber(%s:%s)", id, name); }
}
