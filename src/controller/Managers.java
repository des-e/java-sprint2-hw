package controller;

/**
     *
     * Менеджеры
     */

public class Managers {

    private static HistoryManager inHistoryManager;
    private static Manager inMemoryTaskManager;


    static {
        inHistoryManager = new InMemoryHistoryManager();
        inMemoryTaskManager = new InMemoryTaskManager();
    }

    public static Manager getDefault() {
        return inMemoryTaskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return inHistoryManager;
    }



}
