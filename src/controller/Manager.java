package controller;

import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;
import java.util.List;

    /**
     *
     * Интерфейс менеджера задач
     */

public interface Manager {

    /**
     *
     * Получить список всех задач
     */
    List<Task> getTasks();

    /**
     *
     * Удалить все задачи
     */
    void deleteAllTask();

    /**
     *
     * Получить задачу по идентификатору
     */
    Task getTaskById(int id);

    /**
     *
     * Создать задачу
     */
    Task createTask(Task task);

    /**
     *
     * Обновление задачи. Новая версия объекта
     */
    void updateTask(Task task);

    /**
     *
     * Удаление задачи по идентификатору
     */
    void deleteTask(int id);

    /**
     *
     * Получаем список всех эпиков
     */
    List<Epic> getEpics();

    /**
     *
     * Удаление всех эпиков
     */
    void deleteAllEpics();

    /**
     *
     * Получение эпика по идентификатору
     */
    Task getEpicById(int id);

    /**
     *
     * Создать эпик
     */
    Epic createEpic(Epic epic);

    /**
     *
     * Обновление эпика. Новая версия объекта
     */
    void updateEpic(Epic epic);

    /**
     *
     * Удаление эпика по идентификатору
     */
    void deleteEpic(int id);

    /**
     *
     * Получаем список всех подзадач
     */
    List<Task> getSubtasks();

    /**
     *
     * Удаление всех подзадач
     */
    void deleteAllSubtask();

    /**
     *
     * Получение подзадачи по идентификатору
     */
    Subtask getSubtaskById(int id);

    /**
     *
     * Создать подзадачу
     */
    Subtask createSubtask(Subtask subtask);

    /**
     *
     * Обновление подзадачи. Новая версия объекта
     */
    void updateSubtask(Subtask subtask);

    /**
     *
     * Удаление подзадачи по идентификатору
     */
    void deleteSubtask(int id);

    /**
     *
     * Получить список истории.
     */
    List<Task> getHistory();

}
