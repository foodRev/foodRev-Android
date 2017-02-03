package foodrev.org.foodrev.domain.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContentCare extends DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItemCare> ITEMS = new ArrayList<DummyItemCare>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItemCare> ITEM_MAP = new HashMap<String, DummyItemCare>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItemCare item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItemCare createDummyItem(int position) {
        return new DummyItemCare(String.valueOf(position), "Care Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItemCare extends DummyContent.DummyItem{
        public final String id;
        public final String content;
        public final String details;

        public DummyItemCare(String id, String content, String details) {
            super(id, content, details);
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
