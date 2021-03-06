package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.mapper.WorkoutMapper;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.UserService;
import by.zadziarnouski.workoutlog.service.WorkoutService;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/workout-log")
public class WorkoutLogController {
    public final WorkoutService workoutService;
    public final WorkoutMapper workoutMapper;
    public final UserService userService;


    @Autowired
    public WorkoutLogController(WorkoutService workoutService, WorkoutMapper workoutMapper, UserService userService) {
        this.workoutService = workoutService;
        this.workoutMapper = workoutMapper;
        this.userService = userService;
    }

    @GetMapping
    public String getWorkoutLogPage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        workoutService.findAll().stream().filter(workout -> workout.getTitle() == null).forEach(workout -> workoutService.delete(workout.getId()));
        model.addAttribute("workouts", workoutService.findAll().stream().filter(workout -> workout.getUser().getId().equals(currentUser.getId())).map(workoutMapper::toDTO).collect(Collectors.toList()));
        return "workout-log";
    }

    @GetMapping("/delete/{id}")
    public String deleteWorkut(@PathVariable Long id) {
        workoutService.delete(id);
        return "redirect:/workout-log";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePageForWorkout(@PathVariable Long id, Model model) {
        model.addAttribute("workout", workoutMapper.toDTO(workoutService.findById(id)));
        return "update-workout-before-saving";
    }
}
