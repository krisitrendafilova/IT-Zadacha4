package servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBException;
import repository.Task;
import repository.TaskRepository;
import repository.XMLTask;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/tasks/view")
public class GetTaskServlet extends HttpServlet {
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

        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(400);
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<response><error>Missing 'id' parameter.</error></response>");
            pw.close();
            return;
        }

        Task task = taskRepository.getTaskById(Integer.parseInt(idParam));
        if (task != null) {
            try {
                XMLTask.writeToXML(pw, task);
                resp.setStatus(200);
            } catch (JAXBException e) {
                resp.setStatus(500);
                pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                pw.println("<response><error>Error processing XML output: " + e.getMessage() + "</error></response>");
            }
        } else {
            resp.setStatus(404);
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<response><error>Task not found!</error></response>");
        }
        pw.close();
    }
}
