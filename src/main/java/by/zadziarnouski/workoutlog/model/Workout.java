package by.zadziarnouski.workoutlog.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "exercises_in_workout",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "workout_id"))
    private List<Exercise> exercises = new ArrayList<>();

    private String comments;

    private int restBetweenExercise;

    private int rating;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalTime duration;
}
