package model; /**
 *
 *  Эпик. Хранит в себе подзадачи.
 *
 */

import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Subtask> listOfSubtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Subtask> getSubtasks() {
        return listOfSubtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.listOfSubtasks = subtasks;
    }

    public TaskType getType() {
        return TaskType.EPIC;
    }


    @Override
    public String toString() {
        return "Epic name: '" + getName() + '\'' +
                ", description = '" + getDescription() + '\'' +
                ", id = " + getId() +
                ", Status: '" + getStatus() + '\'' +
                ", Total subtasks: '" + getSubtasks().size() + '\'' +
                '.';
    }
}
