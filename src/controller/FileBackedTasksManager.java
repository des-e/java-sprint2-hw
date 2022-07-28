package controller;

import exceptions.ManagerSaveException;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static model.TaskType.*;

public class FileBackedTasksManager extends InMemoryTaskManager{
    public static String fileName = "tasks.csv";
    public static File file = new File("src" + File.separator + fileName);

    public static void main(String[] args) throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        Task task02 = new Task("TASK#2", "Task#2 description", Status.NEW);
        fileBackedTasksManager.createTask(task02);

        Epic epic01 = new Epic("EPIC#1", "Epic#1 description");
        fileBackedTasksManager.createEpic(epic01);
        Epic epic02 = new Epic("EPIC#2", "Epic#2 description");
        fileBackedTasksManager.createEpic(epic02);
        Task task01 = new Task("TASK#1", "Task#1 description", Status.IN_PROGRESS);
        fileBackedTasksManager.createTask(task01);

        Subtask subtask01 = new Subtask("SUBTASK1", "Subtask#1 description", Status.NEW, 3);
        fileBackedTasksManager.createSubtask(subtask01);

        fileBackedTasksManager.getTaskById(1);
        fileBackedTasksManager.getEpicById(3);
        fileBackedTasksManager.getSubtaskById(5);

        fileBackedTasksManager.loadFromFile(file);
    }

    @Override
    public ArrayList<Task> getTasks() {
        final ArrayList<Task> tasks = super.getTasks();
        save();
        return tasks;

    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public Task getTaskById(int id) {
        final Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Task createTask(Task task) {
        final Task createTask = super.createTask(task);
        save();
        return createTask;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public ArrayList<Epic> getEpics() {
        final ArrayList<Epic> epics = super.getEpics();
        save();
        return epics;
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Task getEpicById(int id) {
        final Task epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public Epic createEpic(Epic epic) {
        final Epic createEpic = super.createEpic(epic);
        save();
        return createEpic;
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public ArrayList<Task> getSubtasks() {
        final ArrayList<Task> subtasks = super.getSubtasks();
        save();
        return subtasks;
    }

    @Override
    public void deleteAllSubtask() {
        super.deleteAllSubtask();
        save();
    }

    @Override
    public Subtask getSubtaskById(int id) {
        final Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        final Subtask createSubtask = super.createSubtask(subtask);
        save();
        return createSubtask;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public List<Task> getHistory() {
        final List<Task> history = super.getHistory();
        save();
        return history;
    }

    /**
     *
     * Сохранение задачи в строку
     */
    private static String toString(Task task) {
        StringBuilder sb = new StringBuilder();
        if (task.getType().equals(TASK)) {
            sb.append(task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + "," +
                    task.getDescription() + ",");
        } else if (task.getType().equals(EPIC)) {
            sb.append(task.getId() + "," + task.getType() + "," + task.getName() + "," + task.getStatus() + "," +
                    task.getDescription() + ",");
        }
        return sb.toString();
    }

    /**
     *
     * Сохранение подзадачи в строку
     */
    private static String toString(Subtask subtask) {
        StringBuilder sb = new StringBuilder();
        sb.append(subtask.getId() + "," + subtask.getType() + "," + subtask.getName() + "," +subtask.getStatus() + "," +
                subtask.getDescription() + ","+ subtask.getEpicId());
        return sb.toString();
    }

    /**
     *
     * Сохранение Истории в строку
     */
    private String toString(List<Task> history) {
        StringBuilder sb = new StringBuilder();

        for (Task pair : historyManager.getHistory()) {
            sb.append(pair.getId() + ",");
        }

        sb.reverse();
        sb.delete(0,1);
        return sb.toString();
    }

    /**
     *
     * Чтение задачи из строки
     */
    private Task fromString (String str) {
        String[] split = str.split(",");

        int id = Integer.parseInt(split[0]);
        TaskType taskType = TaskType.valueOf(split[1]);
        String name = split[2];
        Status status = Status.valueOf(split[3]);
        String description = split[4];

        switch (taskType) {
            case TASK:
                return new Task(name, description, status);
            case EPIC:
                return new Epic(name, description);
            case SUBTASK:
                int epicId = Integer.parseInt(split[5]);
                return new Subtask(name,description, status, epicId);

        }
        str.split(",");
        Task task = new Task(str,str);

        return task;
    }

    /**
     *
     * Чтение истории из строки
     */
    private List<Integer> histroryFromString (String str) {
        String [] split = str.split(",");
        List<Integer> list = new ArrayList<>();
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }


    /**
     *
     * Сохранение в файл
     */
    public void save() {
        try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,StandardCharsets.UTF_8))){
            bufferedWriter.write("id,type,name,status,description,epic\n");
            for (Task task : tasks.values()) {
                bufferedWriter.append(toString(task));
                bufferedWriter.newLine();
            }
            for (Epic epic : epics.values()) {
                bufferedWriter.append(toString(epic));
                bufferedWriter.newLine();
            }
            for (Subtask subtask : subtasks.values()) {
                bufferedWriter.append(toString(subtask));
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            bufferedWriter.write(toString(historyManager.getHistory()));
        } catch (IOException e) {
            System.out.println("Ошибка сохранения в файл");
            e.printStackTrace();
        }

    }

    private void load() throws ManagerSaveException {
        try(BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                System.out.println(reader.readLine());
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    private void loadFromFile (File file) throws ManagerSaveException {
        int maxId = 0;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {

            String line = bufferedReader.readLine();
            while (line != null) {
                line = bufferedReader.readLine();
                if (line.isEmpty()) {
                    break;
                }

                int id = fromString(line).getId();

                if (fromString(line).getType().equals(TASK)) {
                    tasks.put(id, fromString(line));
                } else if (fromString(line).getType().equals(EPIC)){
                    epics.put(id, (Epic) fromString(line));
                } else if (fromString(line).getType().equals(SUBTASK)) {
                    subtasks.put(id, (Subtask) fromString(line));
                }

                if (maxId < id) {
                    maxId = id;
                }
            }
            line = bufferedReader.readLine();
            histroryFromString(line);

        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }



}
