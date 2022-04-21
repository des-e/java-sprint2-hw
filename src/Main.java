import controller.*;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {
        Manager manager = Managers.getDefault();
        HistoryManager history = Managers.getDefaultHistory();

        Task task1 = new Task("model.Task #1", "model.Task description",Status.NEW);
        Task task2 = new Task("model.Task #2", "model.Task description", Status.NEW);
        Task task01 = manager.createTask(task1);
        Task task02 = manager.createTask(task2);

        Epic epic1 = new Epic("model.Epic #1", "model.Epic description");
        Epic epic2 = new Epic("model.Epic #2", "model.Epic description");
        Epic epic01 = manager.createEpic(epic1);
        Epic epic02 = manager.createEpic(epic2);

        Subtask subtask1 = new Subtask("model.Subtask #1", "model.Subtask description", Status.NEW,
                epic01.getId());
        Subtask subtask2 = new Subtask("model.Subtask #2", "model.Subtask description", Status.IN_PROGRESS,
                epic01.getId());
        Subtask subtask3 = new Subtask("model.Subtask #3", "model.Subtask description", Status.DONE,
                epic02.getId());
        Subtask subtask01 = manager.createSubtask(subtask1);
        Subtask subtask02 = manager.createSubtask(subtask2);
        Subtask subtask03 = manager.createSubtask(subtask3);

        manager.updateEpic(epic01);
        manager.updateEpic(epic02);
        printAll((InMemoryTaskManager) manager);

    }

    private static void printAll (InMemoryTaskManager manager) {
        System.out.println("Tasks: ");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Epics: ");
        for (Epic epic : manager.getEpics()) {
            System.out.println(epic);
            System.out.println(epic.getName() + " subtasks:");
            for (Task subtask : epic.getSubtasks()) {
                System.out.println(subtask);
            }
        }
    }
}

