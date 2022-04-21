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

    @Override
    public String toString() {
        return "Name: '" + getName() + '\'' +
                ", Description: '" + getDescription() + '\'' +
                ", id = " + getId() +
                ", model.Status: '" + getStatus() + '\'' +
                ", Total subtasks: '" + getSubtasks().size() + '\'' +
                '}';
    }
}
