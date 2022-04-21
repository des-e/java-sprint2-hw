package controller;

/**
     *
     * Менеджеры
     */

public class Managers {

    private static HistoryManager inHistoryManager;
    private static Manager inMemoryTaskManager;


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
