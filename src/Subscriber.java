import java.util.Set;

public interface Subscriber {
    void onNews(News news);
    Set<News.Category> getPreferences();
    String getId();
}
