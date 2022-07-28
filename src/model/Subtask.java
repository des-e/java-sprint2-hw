package model;

import java.util.Objects;

/**
 *
 *  Подзадача
 *
 */

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return "Subtask name: '" + getName() + '\'' +
                ", description ='" + getDescription() + '\'' +
                ", id =" + getId() +
                ", status ='" + getStatus() + '\'' +
                ", epicId ='" + epicId + '\'' +
                '.';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return this.getId() == subtask.getId();
    }


}
