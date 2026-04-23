package xyz.bitsquidd.ninja.ui.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class PacketLogStore {
    private static final int MAX_ENTRIES = 500;

    private static final List<PacketLogEntry> entries = new ArrayList<>();
    private static final List<Runnable> listeners = new CopyOnWriteArrayList<>();

    private PacketLogStore() {}

    public static synchronized void add(PacketLogEntry entry) {
        entries.add(entry);
        if (entries.size() > MAX_ENTRIES) {
            entries.remove(0);
        }
        for (Runnable listener : listeners) {
            listener.run();
        }
    }

    public static synchronized List<PacketLogEntry> getEntries() {
        return Collections.unmodifiableList(new ArrayList<>(entries));
    }

    public static synchronized void clear() {
        entries.clear();
    }

    public static void addListener(Runnable listener) {
        listeners.add(listener);
    }

    public static void removeListener(Runnable listener) {
        listeners.remove(listener);
    }
}
