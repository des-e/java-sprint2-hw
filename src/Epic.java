/**
 *
 *  Эпик. Хранит в себе подзадачи.
 *
 */

import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Subtask> listOfSubtasks;

    public Epic(String name, String description, int id, String status) {
        super(name, description, status);
    }

    public Epic(String name, String description, int id, String status, ArrayList<Subtask> subtasks) {
        super(name, description, status);
        this.listOfSubtasks = subtasks;
    }

    public ArrayList<Subtask> getSubtasks() {
        return listOfSubtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.listOfSubtasks = subtasks;
    }

    @Override
    public String toString() {
        return "Name: '" + getName() + '\'' +
                ", Description: '" + getDescription() + '\'' +
                ", id = " + getId() +
                ", Status: '" + getStatus() + '\'' +
                ", Total subtasks: '" + getSubtasks().size() + '\'' +
                '}';
    }
}
