    package model;

    import com.fasterxml.jackson.core.type.TypeReference;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import controller.DatabaseUtil;

    import java.io.File;
    import java.io.IOException;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    public class User {
        public String username;
        public String password;
        public List<Task> tasks;
        public List<LinkedTask> linkedTasks;
        private int userId;

        private static final ObjectMapper objectMapper = new ObjectMapper();

        public User() {
            tasks = new ArrayList<>();
            linkedTasks = new ArrayList<>();
        }

        public User(String username, String password, int userId) {
            this.username = username;
            this.password = password;
            this.userId = userId;
            tasks = new ArrayList<>();
            linkedTasks = new ArrayList<>();
        }

        public int getUserId() {
            return userId;
        }
        public void refreshUserData() throws SQLException {
            this.tasks = getTasks();
            this.linkedTasks = getLinkedTasks();
        }

        private List<LinkedTask> getLinkedTasks() throws SQLException {
            List<LinkedTask> linkedTasks = new ArrayList<>();
            String sql = "SELECT * FROM linked_tasks WHERE user_id = ?";
            try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, getUserId());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    LinkedTask linkedTask = new LinkedTask();
                    linkedTask.name = rs.getString("name");
                    linkedTask.description = rs.getString("description");
                    linkedTask.state = TaskState.valueOf(rs.getString("state"));
                    linkedTask.StartDay = rs.getDate("start_day");
                    linkedTask.EndDay = rs.getDate("end_day");

                    // Retrieve the previous task ID
                    int previousTaskId = rs.getInt("previous_task_id");
                    if (previousTaskId != 0) {
                        // Fetch the previous task from the database
                        Task previousTask = getTaskById(previousTaskId); // Implement this method
                        linkedTask.previousTask = previousTask;
                    }

                    linkedTasks.add(linkedTask);
                }
            }
            return linkedTasks;
        }

        private Task getTaskById(int taskId) throws SQLException {
            String sql = "SELECT * FROM tasks WHERE id = ? AND user_id = ?";
            try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, taskId);
                stmt.setInt(2, getUserId());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Task task = new Task();
                    task.id = rs.getInt("id"); // Додайте це рядок
                    task.name = rs.getString("name");
                    task.description = rs.getString("description");
                    task.state = TaskState.valueOf(rs.getString("state"));
                    task.StartDay = rs.getDate("start_day");
                    task.EndDay = rs.getDate("end_day");
                    return task;
                }
            }
            return null;
        }



        public List<Task> getTasks() throws SQLException {
            List<Task> tasks = new ArrayList<>();
            String sql = "SELECT * FROM tasks WHERE user_id = ?";
            try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, getUserId());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Task task = new Task();
                    task.id = rs.getInt("id"); // Додайте це рядок
                    task.name = rs.getString("name");
                    task.description = rs.getString("description");
                    task.state = TaskState.valueOf(rs.getString("state"));
                    task.StartDay = rs.getDate("start_day");
                    task.EndDay = rs.getDate("end_day");
                    tasks.add(task);
                }
            }
            return tasks;
        }

        public void addLinkedTask(LinkedTask linkedTask) throws SQLException {
            String sql = "INSERT INTO linked_tasks (name, description, state, start_day, end_day, previous_task_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, linkedTask.name);
                stmt.setString(2, linkedTask.description);
                stmt.setString(3, linkedTask.state.toString());
                stmt.setDate(4, new java.sql.Date(linkedTask.StartDay.getTime()));
                stmt.setDate(5, new java.sql.Date(linkedTask.EndDay.getTime()));
                stmt.setInt(6, linkedTask.previousTaskId); // Set the previousTaskId here
                stmt.setInt(7, linkedTask.userId); // Set the user ID here
                stmt.executeUpdate();
            }
        }



        public void removeTask(Task task) throws IOException {
            this.tasks.remove(task);
            saveUsersToFile();
        }

        public void removeLinkedTask(LinkedTask linkedTask) throws IOException {
            this.linkedTasks.remove(linkedTask);
            saveUsersToFile();
        }

        public boolean removeTaskByName(String taskName) throws IOException {
            for (Task task : tasks) {
                if (task.name.equals(taskName)) {
                    tasks.remove(task);
                    UpdateUserData();
                    return true;
                }
            }
            return false;
        }

        public void UpdateUserData() throws IOException {
            List<User> users = loadUsersFromFile();

            for (User user : users) {
                if (user.username.equals(this.username)) {
                    user.tasks = this.tasks;
                    user.linkedTasks = this.linkedTasks;
                    break;
                }
            }

            saveUsersToFile(users);
        }

        private void saveUsersToFile() throws IOException {
            List<User> users = loadUsersFromFile();

            for (User user : users) {
                if (user.username.equals(this.username)) {
                    user.tasks = this.tasks;
                    user.linkedTasks = this.linkedTasks;
                }
            }

            saveUsersToFile(users);
        }

        private List<User> loadUsersFromFile() {
            try {
                return objectMapper.readValue(new File("users.json"), new TypeReference<List<User>>() {});
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

        public Task findTaskByName(String taskName) {
            for (Task task : tasks) {
                if (task.name.equals(taskName)) {
                    return task;
                }
            }
            return null;
        }

        public LinkedTask findLinkedTaskByName(String taskName) {
            for (LinkedTask linkedTask : linkedTasks) {
                if (linkedTask.name.equals(taskName)) {
                    return linkedTask;
                }
            }
            return null;
        }

        private void saveUsersToFile(List<User> users) throws IOException {
            objectMapper.writeValue(new File("users.json"), users);
        }
    }
