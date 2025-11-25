import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

public class RealTimeNewsSubscriptionService {
    public static void main(String[] args) throws InterruptedException {
        NewsAgency agency = new NewsAgency(4);

        Set<News.Category> alicePrefs = new HashSet<>(Arrays.asList(News.Category.BREAKING, News.Category.TECHNOLOGY));
        ConcreteSubscriber alice = new ConcreteSubscriber("u1", "Alice", alicePrefs);

        ConcreteSubscriber bob = new ConcreteSubscriber("u2", "Bob", Collections.emptySet());

        Set<News.Category> carolPrefs = new HashSet<>(Arrays.asList(News.Category.SPORTS));
        ConcreteSubscriber carol = new ConcreteSubscriber("u3", "Carol", carolPrefs);

        agency.subscribe(alice);
        agency.subscribe(bob);
        agency.subscribe(carol);

        agency.publish(new News("Major Outage", "Nationwide service outage reported.", News.Category.BREAKING));
        agency.publish(new News("Local Team Wins", "An exciting finish in tonight's match.", News.Category.SPORTS));

        // Alice decides to update preferences to receive BUSINESS instead of TECHNOLOGY
        alice.setPreferences(new HashSet<>(Arrays.asList(News.Category.BUSINESS)));
        System.out.println("Alice updated preferences to BUSINESS only.");

        agency.publish(new News("Market Rally", "Stocks surge after optimistic earnings.", News.Category.BUSINESS));

        agency.unsubscribe(bob);

        agency.publish(new News("New Phone Release", "A major vendor announced a new flagship.", News.Category.TECHNOLOGY));

        // Wait for asynchronous notifications to finish before exiting the program (demo-friendly)
        agency.shutdownAndAwaitTermination(2000);
    }
}
