import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class News {
    public enum Category { BREAKING, POLITICS, SPORTS, TECHNOLOGY, ENTERTAINMENT, BUSINESS }

    private final String id;
    private final String title;
    private final String body;
    private final Category category;
    private final Instant publishedAt;

    public News(String title, String body, Category category) {
        this.id = UUID.randomUUID().toString();
        this.title = Objects.requireNonNull(title);
        this.body = Objects.requireNonNull(body);
        this.category = Objects.requireNonNull(category);
        this.publishedAt = Instant.now();
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public Category getCategory() { return category; }
    public Instant getPublishedAt() { return publishedAt; }

    @Override
    public String toString() {
        return String.format("[%s] %s", category, title);
    }
}
