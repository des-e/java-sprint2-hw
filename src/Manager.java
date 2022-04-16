/**
 *
 * Менеджер задач. Основной класс с методами трекера задач
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Manager {
        private int generatorId = 0;
        public HashMap<Integer,Task> tasks = new HashMap<>();
        public HashMap<Integer,Epic> epics = new HashMap<>();
        public HashMap<Integer, Subtask> subtasks = new HashMap<>();

        /**
         *
         * Получаем список всех задач
         */
        public ArrayList<Task> getTaskList() {
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
        public ArrayList<Epic> getEpicList() {
            return new ArrayList<>(epics.values());
        }

        /**
         *
         * Удаление всех эпиков
         */
        public void deleteAllEpics() {
            epics.clear();
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
            epics.remove(id);
        }

        /**
         *
         * Получаем список всех подзадач
         */
        public ArrayList<Task> getSubtaskList() {
            return new ArrayList<>(subtasks.values());
        }

        /**
         *
         * Удаление всех подзадач
         */
        public void deleteAllSubtask() {
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
            subtask.setId(++generatorId);
            subtasks.put(subtask.getId(), subtask);

            if (epics.containsKey(subtask.epicId)) {
                Epic epic = epics.get(subtask.getEpicId());
                epic.getSubtasks().add(subtask);

            }
            setEpicStatus(epics.get(subtask.epicId));
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
            if (!epics.containsKey(subtask.epicId)) {
                return;
            }
            if (!subtasks.containsKey(subtask.epicId)) {
                return;
            }
                subtasks.put(subtask.getId(), subtask);
                setEpicStatus(epics.get(subtask.getEpicId()));
        }

        /**
         *
         * Удаление подзадачи по идентификатору
         */
        public void deleteSubtask(int id) {
            subtasks.remove(id);
        }

        /**
         *
         * Установка статуса для эпика
         */
        private void setEpicStatus(Epic epic) {
                int subtaskCount = 0;
                if (epic.getSubtasks() == null) {
                    epic.setStatus(Status.NEW);
                }
                for (Subtask subtasks : epic.getSubtasks()) {
                    if (subtasks.getStatus().equals(epic.getStatus())) {
                        epic.setStatus(subtasks.getStatus());
                    } else {
                        epic.setStatus(Status.IN_PROGRESS);
                    }
                }
                for (Subtask subtasks : epic.getSubtasks()) {
                    if (subtasks.getStatus().equals(Status.DONE)) {
                        subtaskCount++;
                    }
                    if (subtaskCount == epic.getSubtasks().size()) {
                        epic.setStatus(Status.DONE);
                    }
                }
        }



}
