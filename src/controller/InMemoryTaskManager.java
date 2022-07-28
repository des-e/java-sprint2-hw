package controller;

import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;

/**
 * Менеджер задач. Основной класс с методами трекера задач
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InMemoryTaskManager implements Manager {
    protected int generatorId;
    protected static HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected static HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTask() {
        for (Integer taskId : tasks.keySet()) {
            historyManager.removeFromHistory(taskId);
        }
        tasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.addToHistory(task);
        }
        return task;
    }

    @Override
    public Task createTask(Task task) {
        task.setId(++generatorId);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public void updateTask(Task task){
        if (!tasks.containsKey((task.getId()))) {
            return;
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void deleteTask(int id) {
        historyManager.removeFromHistory(id);
        tasks.remove(id);
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllEpics() {
        for (Integer epicId : epics.keySet()) {
            historyManager.removeFromHistory(epicId);
        }
        for (Integer subtaskId : subtasks.keySet()) {
            historyManager.removeFromHistory(subtaskId);
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Task getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.addToHistory(epic);
        }
        return epic;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(++generatorId);
        epics.put(epic.getId(), epic);
        setEpicStatus(epic);
        return epic;
    }

    @Override
    public void updateEpic(Epic epic) {
        if (!tasks.containsKey((epic.getId()))) {
            return;
        }
        tasks.put(epic.getId(), epic);
    }

    @Override
    public void deleteEpic(int id) {
        epics.get(id).getSubtasks().clear();
        for (Subtask subtask : epics.get(id).getSubtasks()) {
            subtasks.remove(subtask.getId());
            historyManager.removeFromHistory(subtask.getId());

        }
        epics.remove(id);
        historyManager.removeFromHistory(id);

    }

    @Override
    public ArrayList<Task> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllSubtask() {
        for (Epic epic : getEpics()) {
            epic.getSubtasks().clear();
            setEpicStatus(epic);
        }
        for (Integer subtaskId : subtasks.keySet()) {
            historyManager.removeFromHistory(subtaskId);
        }
        subtasks.clear();
    }

    @Override
    public Subtask getSubtaskById(int id) {
        final Subtask subtask = subtasks.get(id);
        if(subtask != null) {
            historyManager.addToHistory(subtask);
        }
        return subtask;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getEpicId())) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtasks().add(subtask);
            subtask.setId(++generatorId);
            subtasks.put(subtask.getId(), subtask);
            setEpicStatus(epics.get(subtask.getEpicId()));
        }
        return subtask;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (Objects.isNull(subtask.getEpicId())) {
            return;
        }
        if (!epics.containsKey(subtask.getEpicId())) {
            return;
        }
        if (!subtasks.containsKey(subtask.getEpicId())) {
            return;
        }
        epics.get(subtask.getEpicId()).getSubtasks().remove(subtask);
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).getSubtasks().add(subtask);

        setEpicStatus(epics.get(subtask.getEpicId()));
    }

    @Override
    public void deleteSubtask(int id) {
        int epicId = subtasks.get(id).getEpicId();
        epics.get(epicId).getSubtasks().remove(getSubtaskById(id));
        subtasks.remove(id);
        setEpicStatus(epics.get(epicId));
        historyManager.removeFromHistory(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


    /**
     *
     * Установка статуса для эпика
     */
    private void setEpicStatus(Epic epic) {
        int doneCount = 0;
        int newCount = 0;
        if (epic.getSubtasks().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        for (Subtask subtasks : epic.getSubtasks()) {
            if (subtasks.getStatus().equals(Status.IN_PROGRESS)) {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            } else if (subtasks.getStatus().equals(Status.DONE)) {
                doneCount++;
            } else {
                newCount++;
            }
        }

        if (doneCount == epic.getSubtasks().size()) {
            epic.setStatus(Status.DONE);
        } else if (newCount == epics.size()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }


}
