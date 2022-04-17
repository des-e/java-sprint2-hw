/**
 *
 *  Подзадача
 *
 */

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, int id, String status) {
        super(name, description, status);
    }

    public Subtask(String name, String description, int id, String status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask: '" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                ", epicId='" + epicId + '\'' +
                '}';
    }

}
