import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("Task #1", "Task description", 0,Status.NEW);
        Task task2 = new Task("Task #2", "Task description", 0,Status.NEW);
        Task task01 = manager.createTask(task1);
        Task task02 = manager.createTask(task2);

        Epic epic1 = new Epic("Epic #1", "Epic description", 0,Status.NEW, new ArrayList<>());
        Epic epic2 = new Epic("Epic #2", "Epic description", 0,Status.NEW, new ArrayList<>());
        Epic epic01 = manager.createEpic(epic1);
        Epic epic02 = manager.createEpic(epic2);

        Subtask subtask1 = new Subtask("Subtask #1", "Subtask description", 0, Status.NEW,
                epic01.getId());
        Subtask subtask2 = new Subtask("Subtask #2", "Subtask description", 0, Status.DONE,
                epic01.getId());
        Subtask subtask3 = new Subtask("Subtask #3", "Subtask description", 0, Status.NEW,
                epic02.getId());
        Subtask subtask01 = manager.createSubtask(subtask1);
        Subtask subtask02 = manager.createSubtask(subtask2);
        Subtask subtask03 = manager.createSubtask(subtask3);

        manager.updateEpic(epic01);
        manager.updateEpic(epic02);
        printAll(manager);


    }

    private static void printAll (Manager manager) {
        System.out.println("Tasks: ");
        for (Task task : manager.getTaskList()) {
            System.out.println(task);
        }
        System.out.println("Epics: ");
        for (Epic epic : manager.getEpicList()) {
            System.out.println(epic);
            System.out.println(epic.getName() + " subtasks:");
            for (Task subtask : epic.getSubtasks()) {
                System.out.println(subtask);
            }
        }
    }
}

