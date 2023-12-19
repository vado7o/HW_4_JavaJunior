package org.example.task;

import org.example.models.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Program {
    /**
     Создайте базу данных (например, SchoolDB).
     В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
     Настройте Hibernate для работы с вашей базой данных.
     Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
     Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
     Убедитесь, что каждая операция выполняется в отдельной транзакции.
     */
    public static void main(String[] args) throws SQLException {
        createDatabase();

        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        // Создание сессии
        Session session = sessionFactory.getCurrentSession();

        try {
            // Начало транзакции
            session.beginTransaction();

            // Создание объекта
            Course course = Course.create();

            // Сохранение объекта в базе данных
            session.save(course);
            System.out.println("Новый курс добавлен успешно");

            // Чтение курса из базы данных
            Course retrievedCourse = session.get(Course.class, course.getId());
            System.out.println("Курс выгружен успешно");
            System.out.println("Получен объект курса: " + retrievedCourse);

            // Обновление объекта
            retrievedCourse.updateTitle();
            retrievedCourse.updateDuration();
            session.update(retrievedCourse);
            System.out.println("Информация о курсе обновлена успешно");

            // Удаление курса
//            session.delete(retrievedCourse);
//            System.out.println("Курс удалён успешно");

            session.getTransaction().commit();
            System.out.println("Транзакция успешно выполнена");
        }
        finally {
            // Закрытие фабрики сессий
            sessionFactory.close();
        }
    }

    private static void createDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "password";

        Connection connection = DriverManager.getConnection(url, user, password);

        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS schoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(createDatabaseSQL)){
            statement.execute();
        }
        String useDatabaseSQL = "USE schoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)){
            statement.execute();
        }
        String createTableSQL = "CREATE TABLE IF NOT EXISTS courses (id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), duration INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)){
            statement.execute();
        }
    }
}
