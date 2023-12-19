package org.example.models;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "courses")
public class Course {
    private static final String[] titles = new String[] { "Алгебра", "Геометрия", "Физика", "Химия", "Литература", "Русский язык", "Астрономия", "Английский язык", "Природоведение", "История"};
    private static final Random random = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int duration;

    public static Course create() {
        return new Course(titles[random.nextInt(titles.length)], random.nextInt(15, 90));
    }

    public Course() {}

    public Course(int id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    public Course(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public void updateDuration() {
        this.duration = random.nextInt(15, 90);
    }
    public void updateTitle() {
        this.title = titles[random.nextInt(titles.length)];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public int getAge() {
        return duration;
    }

    public void setAge(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}
