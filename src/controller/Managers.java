package controller;

/**
     *
     * Менеджеры
     */

public class Managers {

    private static InMemoryTaskManager inMemoryTaskManager;
    private static InMemoryHistoryManager inHistoryManager;

    static {
        inMemoryTaskManager = new InMemoryTaskManager();
        inHistoryManager = new InMemoryHistoryManager();
    }

    public static Manager getDefault() {
        return inMemoryTaskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return inHistoryManager;
    }



}
