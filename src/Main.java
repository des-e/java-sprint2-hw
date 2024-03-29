import controller.*;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {
        Manager manager = Managers.getDefault();

        // создайте две задачи, эпик с тремя подзадачами и эпик без подзадач;
        Task task1 = new Task("Task #1", "model.Task description",Status.NEW);
        Task task2 = new Task("Task #2", "model.Task description", Status.NEW);
        Task task01 = manager.createTask(task1);
        Task task02 = manager.createTask(task2);

        Epic epic1 = new Epic("Epic #1", "model.Epic description");
        Epic epic2 = new Epic("Epic #2", "model.Epic description");
        Epic epic01 = manager.createEpic(epic1);
        Epic epic02 = manager.createEpic(epic2);

        Subtask subtask1 = new Subtask("Subtask #1", "model.Subtask", Status.NEW,
                epic01.getId());
        Subtask subtask2 = new Subtask("Subtask #2", "model.Subtask", Status.IN_PROGRESS,
                epic01.getId());
        Subtask subtask3 = new Subtask("Subtask #3", "model.Subtask", Status.DONE,
                epic01.getId());
        Subtask subtask01 = manager.createSubtask(subtask1);
        Subtask subtask02 = manager.createSubtask(subtask2);
        Subtask subtask03 = manager.createSubtask(subtask3);
        manager.updateEpic(epic01);
        manager.updateEpic(epic02);

        // запросите созданные задачи несколько раз в разном порядке;
        printAll(manager);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getEpicById(3);
        manager.getEpicById(4);
        manager.getSubtaskById(5);
        manager.getSubtaskById(6);
        manager.getSubtaskById(7);

        // после каждого запроса выведите историю и убедитесь, что в ней нет повторов;
        printHistory(manager);

        manager.getEpicById(3);
        manager.getEpicById(4);
        manager.getSubtaskById(5);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getSubtaskById(6);
        manager.getSubtaskById(7);

        // после каждого запроса выведите историю и убедитесь, что в ней нет повторов;
        printHistory(manager);

        // удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться;
        manager.deleteTask(2);

        printHistory(manager);

        // удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
        manager.deleteEpic(3);

        printHistory(manager);

    }

    private static void printAll (Manager manager) {
        System.out.println("\n" + "TASKS: ");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("\n" + "EPICS: ");
        for (Epic epic : manager.getEpics()) {
            System.out.println(epic);
            System.out.println(epic.getName() + " SUBTASKS:");
            for (Task subtask : epic.getSubtasks()) {
                System.out.println(subtask);
            }
        }
    }
    private static void printHistory(Manager manager) {
        System.out.println("\n" + "HISTORY:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}

