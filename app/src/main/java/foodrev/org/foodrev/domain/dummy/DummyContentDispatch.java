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
public class DummyContentDispatch extends DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItemDispatch> ITEMS = new ArrayList<DummyItemDispatch>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItemDispatch> ITEM_MAP = new HashMap<>();
    /**
     * An array of sample (dummy) items.
     */

    private static final int COUNT = 1;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItemDispatch item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItemDispatch createDummyItem(int position) {
        return new DummyItemDispatch(String.valueOf(position), "Dispatch Item " + position, makeDetails(position));
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
    public static class DummyItemDispatch extends DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItemDispatch(String id, String content, String details) {
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
