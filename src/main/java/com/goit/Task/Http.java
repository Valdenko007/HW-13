package com.goit.Task;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpRequest;

public class Http {
    private static final Gson gson = new Gson();

    public static HttpRequest createUser(User user) {
        return HttpRequest.newBuilder()
                .header("Content-type", "application/json; charset=UTF-8")
                .uri(URI.create(Todos.getURL() + Todos.getUsers ()))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
                .build();
    }

    public static HttpRequest updateUsers(String id, User user) {
        return HttpRequest.newBuilder()
                .header("Content-type", "application/json; charset=UTF-8")
                .uri(URI.create(Todos.getURL() + Todos.getUsers () + "/" + id))
                .method("PUT", HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
                .build();
    }

    public static HttpRequest deleteUser(String id, User user) {
        return HttpRequest.newBuilder()
                .header("Content-type", "application/json; charset=UTF-8")
                .uri(URI.create(Todos.getURL() + Todos.getUsers () + "/" + id))
                .method("DELETE", HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
                .build();
    }

    public static HttpRequest getUsersInfo() {
        return HttpRequest.newBuilder()
                .uri(URI.create(Todos.getURL() + Todos.getUsers ()))
                .GET()
                .build();
    }

    public static HttpRequest getUserInfoById(String id) {
        return HttpRequest.newBuilder()
                .uri(URI.create(Todos.getURL() + Todos.getUsers () + "/" + id))
                .GET()
                .build();
    }

    public static HttpRequest getUserInfoByName(String parameter, String value) {
        return HttpRequest.newBuilder()
                .header("Content-type", "application/json; charset=UTF-8")
                .uri(URI.create(String.format("%s%s?%s=%s",
                        Todos.getURL(),
                        Todos.getUsers (),
                        parameter,
                        value.replace(" ", "%20"))))
                .build();
    }

    public static HttpRequest getUserPosts(int id) {
        return HttpRequest.newBuilder()
                .uri(URI.create(
                        Todos.getURL() + Todos.getUsers () + "/" + id + "/" + "posts"))
                .GET()
                .build();
    }

    public static HttpRequest getUserComments(Post post) {
        return HttpRequest.newBuilder()
                .uri(URI.create(
                        Todos.getURL() + Todos.getPosts () + "/" + post.getId() + Todos.getComments ()
                ))
                .GET()
                .build();
    }

    public static HttpRequest getUserTasks(int id) {

        return HttpRequest.newBuilder()
                .uri(URI.create(
                        Todos.getURL() + Todos.getUsers () + "/" + id + Todos.getTodos ()
                ))
                .GET()
                .build();
    }
}