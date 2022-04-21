package controller;

import model.Epic;
import model.Subtask;
import model.Task;
import model.Status;

/**
 *
 * Менеджер задач. Основной класс с методами трекера задач
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InMemoryTaskManager implements Manager {
        private int generatorId;
        private HashMap<Integer, Task> tasks = new HashMap<>();
        private HashMap<Integer, Epic> epics = new HashMap<>();
        private HashMap<Integer, Subtask> subtasks = new HashMap<>();
        private HistoryManager historyManager = Managers.getDefaultHistory();

        @Override
        public ArrayList<Task> getTasks() {
            return new ArrayList<>(tasks.values());
        }

        @Override
        public void deleteAllTask() {
                tasks.clear();
        }

        @Override
        public Task getTaskById(int id) {
            Task task = tasks.get(id);
            if (!tasks.containsKey(task.getId())) {
                historyManager.addTaskToHistory(task);
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
        public void updateTask(Task task) {
            if (!tasks.containsKey((task.getId()))) {
                 return;
            }
            tasks.put(task.getId(), task);
        }

        @Override
        public void deleteTask(int id) {
            tasks.remove(id);
        }

        @Override
        public ArrayList<Epic> getEpics() {
            return new ArrayList<>(epics.values());
        }

        @Override
        public void deleteAllEpics() {
            epics.clear();
            subtasks.clear();
        }

        @Override
        public Task getEpicById(int id) {
            Epic epic = epics.get(id);
            if(!epics.containsKey(epic.getId())) {
                historyManager.addTaskToHistory(epic);
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
            }
            epics.remove(id);
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
            subtasks.clear();
        }

        @Override
        public Subtask getSubtaskById(int id) {
            final Subtask subtask = subtasks.get(id);
            if (!subtasks.containsKey(subtask.getId())) {
                historyManager.addTaskToHistory(subtask);
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
            subtasks.remove(subtask.getId());
            subtasks.put(subtask.getId(), subtask);
            setEpicStatus(epics.get(subtask.getEpicId()));
        }

        @Override
        public void deleteSubtask(int id) {
            int epicId = subtasks.get(id).getEpicId();
            epics.get(epicId).getSubtasks().remove(getSubtaskById(id));
            subtasks.remove(id);
            setEpicStatus(epics.get(epicId));
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
