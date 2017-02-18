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
public class DummyContentDonor extends DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItemDonor> ITEMS = new ArrayList<DummyItemDonor>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItemDonor> ITEM_MAP = new HashMap<String, DummyItemDonor>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItemDonor item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItemDonor createDummyItem(int position) {
        return new DummyItemDonor(String.valueOf(position), "Donor Item " + position, makeDetails(position));
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
    public static class DummyItemDonor extends DummyContent.DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItemDonor(String id, String content, String details) {
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
