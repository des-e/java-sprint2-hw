/**
 *
 * Менеджер задач. Основной класс с методами трекера задач
 *
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class Manager {
        private int generatorId;
        private HashMap<Integer,Task> tasks = new HashMap<>();
        private HashMap<Integer,Epic> epics = new HashMap<>();
        private HashMap<Integer, Subtask> subtasks = new HashMap<>();

        /**
         *
         * Получаем список всех задач
         */
        public ArrayList<Task> getTasks() {
            return new ArrayList<>(tasks.values());
        }

        /**
         *
         * Удаление всех задач
         */
        public void deleteAllTask() {
                tasks.clear();
        }

        /**
         *
         * Получение задачи по идентификатору
         */
        public Task getTaskById(int id) {
            return tasks.get(id);
        }

        /**
         *
         * Создать задачу
         */
        public Task createTask(Task task) {
            task.setId(++generatorId);
            tasks.put(task.getId(), task);
            return task;
        }

        /**
         *
         * Обновление задачи. Новая версия объекта
         */
        public void updateTask(Task task) {
            if (!tasks.containsKey((task.getId()))) {
                 return;
            }
            tasks.put(task.getId(), task);
        }

        /**
         *
         * Удаление задачи по идентификатору
         */
        public void deleteTask(int id) {
            tasks.remove(id);
        }

        /**
         *
         * Получаем список всех эпиков
         */
        public ArrayList<Epic> getEpics() {
            return new ArrayList<>(epics.values());
        }

        /**
         *
         * Удаление всех эпиков
         */
        public void deleteAllEpics() {
            epics.clear();
            subtasks.clear();
        }

        /**
         *
         * Получение эпика по идентификатору
         */
        public Task getEpicById(int id) {
            return epics.get(id);
        }

        /**
         *
         * Создать эпик
         */
        public Epic createEpic(Epic epic) {
            epic.setId(++generatorId);
            epics.put(epic.getId(), epic);
            epic.setSubtasks(new ArrayList<>());
            setEpicStatus(epic);
            return epic;
        }

        /**
         *
         * Обновление эпика. Новая версия объекта
         */
        public void updateEpic(Epic epic) {
            if (!tasks.containsKey((epic.getId()))) {
                return;
            }
            tasks.put(epic.getId(), epic);
        }

        /**
         *
         * Удаление задачи по идентификатору
         */
        public void deleteEpic(int id) {
            epics.get(id).getSubtasks().clear();
            epics.remove(id);
        }

        /**
         *
         * Получаем список всех подзадач
         */
        public ArrayList<Task> getSubtasks() {
            return new ArrayList<>(subtasks.values());
        }

        /**
         *
         * Удаление всех подзадач
         */
        public void deleteAllSubtask() {
            for (Epic epic : getEpics()) {
                epic.getSubtasks().clear();
                setEpicStatus(epic);
            }
            subtasks.clear();
        }

        /**
         *
         * Получение подзадачи по идентификатору
         */
        public Subtask getSubtaskById(int id) {
            return subtasks.get(id);
        }

        /**
         *
         * Создать подзадачу
         */
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

        /**
         *
         * Обновление подзадачи. Новая версия объекта
         */
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
            subtasks.put(subtask.getId(), subtask);
            updateEpic(epics.get(subtask.getEpicId()));
            setEpicStatus(epics.get(subtask.getEpicId()));
        }

        /**
        *
        * Удаление подзадачи по идентификатору
        */
        public void deleteSubtask(int id) {
            int epicId = subtasks.get(id).getEpicId();
            epics.get(epicId).getSubtasks().remove(getSubtaskById(id));
            subtasks.remove(id);
            setEpicStatus(epics.get(epicId));
        }

        /**
        *
        * Установка статуса для эпика
        */
        private void setEpicStatus(Epic epic) {
            int doneCount = 0;
            if (epic.getSubtasks().isEmpty()) {
                epic.setStatus(Status.NEW);
            }
            for (Subtask subtasks : epic.getSubtasks()) {
                if (subtasks.getStatus().equals(Status.IN_PROGRESS)) {
                    epic.setStatus(Status.IN_PROGRESS);
                    break;
                }
                if (subtasks.getStatus().equals(Status.DONE)) {
                    doneCount++;
                }
                if (doneCount == epic.getSubtasks().size()) {
                    epic.setStatus(Status.DONE);
                }
                else {
                    epic.setStatus(Status.IN_PROGRESS);
                } if (subtasks.getStatus().equals(Status.NEW) && !(subtasks.getStatus().equals(Status.DONE))) {
                    epic.setStatus(Status.NEW);
                }
            }
        }



}
