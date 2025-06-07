package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AuthService authService;

	@GetMapping("/index")
	public String showHomePage() {
    	return "index"; // This will render src/main/resources/templates/index.html
	}


    // ---------- REGISTER ----------
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email,
                                @RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String confirmPassword,
                                Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "register";
        }

        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("error", "Email already exists.");
            return "register";
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password); // NOTE: plain text, insecure!
        userRepository.save(user);

        model.addAttribute("message", "Registration successful. Please log in.");
        return "login";
    }

    // ---------- LOGIN ----------
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }

        authService.login(user); // store user in AuthService

        return "redirect:/dashboard";
    }

    // ---------- DASHBOARD ----------
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User currentUser = authService.getLoggedInUser();
        if (currentUser == null) return "redirect:/login";

        List<Task> tasks = taskRepository.findByUser(currentUser);
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", currentUser);
        return "dashboard";
    }

    // ---------- ADD TASK ----------

@GetMapping("/edit-task/{id}")
public String editTask(@PathVariable Long id, Model model) {
    Task task = taskRepository.findById(id).orElse(null);
    if (task == null) return "redirect:/dashboard";
    model.addAttribute("task", task);
    return "edit"; // refers to edit.html
}

@PostMapping("/add-task")
public String addTask(
        @RequestParam String title,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
        @RequestParam(required = false) String priority,
        @RequestParam(required = false) String tags) {

    User user = authService.getLoggedInUser();
    if (user == null) return "redirect:/login";

    Task task = new Task();
    task.setTitle(title);
    task.setDescription(description);
    task.setDueDate(dueDate);
    task.setPriority(priority);
    task.setTags(tags);
    task.setUser(user);

    taskRepository.save(task);
    return "redirect:/dashboard";
}


    // ---------- DELETE TASK ----------
    @GetMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/dashboard";
    }

   @PostMapping("/update-task")
public String updateTask(
        @RequestParam Long id,
        @RequestParam String title,
        @RequestParam(required = false) String description,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
        @RequestParam(required = false) String priority,
        @RequestParam(required = false) String tags) {

    Task task = taskRepository.findById(id).orElse(null);

    if (task != null) {
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);
        task.setTags(tags);
        taskRepository.save(task);
    }

    return "redirect:/dashboard";
}

@GetMapping("/user-dashboard")
public String userDashboard(Model model) {
    User currentUser = authService.getLoggedInUser();
    if (currentUser == null) return "redirect:/login";

    List<Task> tasks = taskRepository.findByUser(currentUser);

    long totalTasks = tasks.size();
    long completedTasks = tasks.stream().filter(task -> "COMPLETED".equalsIgnoreCase(task.getStatus())).count();
    long pendingTasks = totalTasks - completedTasks;

    model.addAttribute("user", currentUser);
    model.addAttribute("totalTasks", totalTasks);
    model.addAttribute("completedTasks", completedTasks);
    model.addAttribute("pendingTasks", pendingTasks);

    return "user-dashboard";
}


    // ---------- LOGOUT ----------
    @GetMapping("/logout")
    public String logout() {
        authService.logout();
        return "redirect:/login";
    }
}
