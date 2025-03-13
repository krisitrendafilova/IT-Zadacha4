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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@WebServlet("/tasks/add")
public class AddTaskServlet extends HttpServlet {
    private TaskRepository taskRepository;

    @Override
    public void init() throws ServletException {
        taskRepository = TaskRepository.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/xml");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();

        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        StringBuilder xmlData = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            xmlData.append(line);
        }
        reader.close();

        try {
            Task task = XMLTask.readerFromXML(xmlData.toString(), Task.class);
            TaskRepository.addTask(task);

            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<response>");
            pw.println("<message>Successfully added!</message>");
            pw.println("</response>");
            resp.setStatus(201);
        } catch (JAXBException e) {
            resp.setStatus(400);
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<response>");
            pw.println("<error>Error processing XML input: " + e.getMessage() + "</error>");
            pw.println("</response>");
        }
        pw.close();
    }
}

