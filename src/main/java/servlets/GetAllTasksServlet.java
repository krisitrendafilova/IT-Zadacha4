package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBException;
import repository.Task;
import repository.TaskRepository;
import repository.Tasks;
import repository.XMLTask;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/tasks")
public class GetAllTasksServlet extends HttpServlet {
    private TaskRepository taskRepository;

    @Override
    public void init() throws ServletException {
        taskRepository = TaskRepository.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/xml");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();

        List<Task> tasksList = taskRepository.getTaskList();
        if (!tasksList.isEmpty()) {
            try {
                Tasks tasksWrapper = new Tasks(tasksList);  // Използвайте класа Tasks
                XMLTask.writeToXML(pw, tasksWrapper);         // Подаваме tasksWrapper, а не task
                resp.setStatus(200);
            } catch (JAXBException e) {
                resp.setStatus(500);
                pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                pw.println("<response><error>Error processing XML output: " + e.getMessage() + "</error></response>");
            }
        } else {
            resp.setStatus(404);
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<response><error>No tasks found!</error></response>");
        }

        pw.close();
    }
}

