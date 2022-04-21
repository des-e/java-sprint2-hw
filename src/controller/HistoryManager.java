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
    void addTaskToHistory (Task task);

    /**
     *
     * Получить историю просмотра задач
     */
    List<Task> getHistory();


}
