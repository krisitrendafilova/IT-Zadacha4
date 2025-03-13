package repository;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.Writer;

public class XMLTask {

    /**
     * Метод за сериализация на единична Task
     */
    public static void writeToXML(Writer writer, Task task) throws JAXBException {
        // Създаване на JAXBContext за класа Task
        JAXBContext context = JAXBContext.newInstance(Task.class);
        Marshaller marshaller = context.createMarshaller();
        // Форматиран XML за по-добра четимост
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // Сериализиране на единичния Task в изходния поток
        marshaller.marshal(task, writer);
    }

    /**
     * Метод за сериализация на списък от задачи (обвит в клас Tasks)
     */
    public static void writeToXML(Writer writer, Tasks tasksWrapper) throws JAXBException {
        // Създаване на JAXBContext за класа Tasks
        JAXBContext context = JAXBContext.newInstance(Tasks.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // Сериализиране на обвиващия клас (списък от Task) в изходния поток
        marshaller.marshal(tasksWrapper, writer);
    }

    /**
     * Метод за десериализация на единичен Task от XML (ако е нужен)
     */
    public static Task readerFromXML(String xml, Class<Task> taskClass) throws JAXBException {

        // Създаване на JAXB контекст
        JAXBContext context = JAXBContext.newInstance(Task.class);
        // Създаване на unmarshaller инстанция
        Unmarshaller um = context.createUnmarshaller();

        //Валидиране с xsd
//        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//        Schema schema = sf.newSchema(new File(xsdFile));
//        um.setSchema(schema);

        Task task = (Task) um.unmarshal(new StringReader(xml));
        return task;
    }
}
