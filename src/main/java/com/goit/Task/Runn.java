package com.goit.Task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Runn {

    Gson gson = new Gson();

    HttpClient httpClient = HttpClient.newBuilder().build();
    User user = new User("Stepan", "Bandera", "BanderaStepan@gmail.com", "Chornobaiivka",
            "123 123 123 12");

    public static void main(String[] args) {
        Runn add = new Runn();
        try {
            add.createUser ();
            add.updateUsers ();
            add.deleteUser ();
            add.allUsersInfo ();
            add.userInfoById ();
            add.userInfoByName();
            add.userPosts();
            add.userTasks ();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void createUser () throws IOException, InterruptedException {
        HttpResponse<String> create = httpClient.send(Http.createUser (user),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("User: \n" + create.body() + "\n создан.");
    }

    public void updateUsers () throws IOException, InterruptedException {

        user.setName("Imya");
        user.setUsername("Familiya");
        user.setAddress ("GdetoVpole");
        user.setEmail ("dondon@gmail.com");

        HttpResponse<String> update = httpClient.send(Http.updateUsers ("5", user),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("User: \n" + update.body() + "\n обновлен.");
    }

    public void deleteUser () throws IOException, InterruptedException {

        HttpResponse<String> delete = httpClient.send(Http.deleteUser ("10", user),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("User: \n" + delete.statusCode () + "\n удален.");
    }

    public void allUsersInfo () throws IOException, InterruptedException {

        HttpResponse<String> info = httpClient.send(Http.getUsersInfo (),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("Информация о пользователях: \n" + info.body() + "\n");
    }

    public void userInfoById() throws IOException, InterruptedException {
        HttpResponse<String> userInfoById = httpClient.send(Http.getUserInfoById ("5"),
                HttpResponse.BodyHandlers.ofString());
        System.out.println("Информация по ID 5 " + userInfoById.body());
    }

    public void userInfoByName() throws IOException, InterruptedException {
        HttpResponse<String> getUserByName = httpClient.send (
                Http.getUserInfoByName ("name","Kurtis Weissnat"),
                HttpResponse.BodyHandlers.ofString()
        );
        System.out.println("Информация об выбраном имени: " + getUserByName.body());
    }

    public void userPosts() throws IOException, InterruptedException {
        int userId = 9;
        HttpResponse<String> getUsersPosts = httpClient.send(Http.getUserPosts (userId),
                HttpResponse.BodyHandlers.ofString());
        List<Post> posts = gson.fromJson(getUsersPosts.body(), new TypeToken<List<Post>>() {
        }.getType());
        Post maxPost = Collections.max(posts, Comparator.comparingInt(Post::getId));
        String fileName = String.format("user-%s-post-%s-comments.json", userId, maxPost.getId());
        httpClient.send(Http.getUserComments (maxPost),
                HttpResponse.BodyHandlers.ofFile(Path.of("src/" + fileName)));
    }

    public void userTasks() throws IOException, InterruptedException {
        int id = 3;
        HttpResponse<String> tasks = httpClient.send(Http.getUserTasks (id),
                HttpResponse.BodyHandlers.ofString());
        List<Task> userTasks = gson.fromJson(tasks.body(), new TypeToken<List<Task>>() {
        }.getType());
        System.out.printf("Открыть задачи для ID = %d: \n", id);
        System.out.println(userTasks.stream().filter(task -> !Task.isCompleted())
                .collect(Collectors.toList()));
    }
}