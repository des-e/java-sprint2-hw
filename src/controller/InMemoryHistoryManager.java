package controller;

import model.Task;

import java.util.LinkedList;
import java.util.List;

/**
 *  История задач
 */
public class InMemoryHistoryManager implements HistoryManager {

    LinkedList<Task> history = new LinkedList<>();

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public void addTaskToHistory(Task task) {
        if (history.size() < 10) {
            history.add(task);
        } else {
            history.removeFirst();
            history.add(task);
        }
    }
}
