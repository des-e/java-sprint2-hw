package controller;

import model.*;
import java.util.List;

/**
 *  Интерфейс просмотра истории задачи
 */

public interface HistoryManager {
    /**
     *
     * Добавить задачу в историю просмотра
     */
    void addToHistory(Task task);

    /**
     *
     * Удалить задачу из истории просмотра задач
     */
    void removeFromHistory(int id);

    /**
     *
     * Получить историю просмотра задач
     */
    List<Task> getHistory();

}
