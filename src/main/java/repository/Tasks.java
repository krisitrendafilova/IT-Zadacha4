package repository;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Обвиващ (wrapper) клас за списък от Task,
 * за да може JAXB да сериализира/десериализира <tasks> ... </tasks>.
 */
@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tasks {

    @XmlElement(name = "task")
    private List<Task> tasks;

    // Безаргументен конструктор (задължителен за JAXB)
    public Tasks() {
    }

     //Конструктор, който инициализира списъка
    public Tasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
