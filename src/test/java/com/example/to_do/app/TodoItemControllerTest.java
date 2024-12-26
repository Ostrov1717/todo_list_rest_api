package com.example.to_do.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoItemController.class)
public class TodoItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoItemService todoItemService;

    @Autowired
    private ObjectMapper objectMapper;

    private TodoItem todoItem;

    @BeforeEach
    void setUp() {
        todoItem = new TodoItem(1L, "Test Task", "Test Description");
    }

    @Test
    void getAllTodoItems_ShouldReturnListOfTodoItems() throws Exception {
        when(todoItemService.getAllTodoItems()).thenReturn(Arrays.asList(todoItem));

        mockMvc.perform(get("/api/todo-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(todoItem.getId()))
                .andExpect(jsonPath("$[0].title").value(todoItem.getTitle()))
                .andExpect(jsonPath("$[0].description").value(todoItem.getDescription()));

        verify(todoItemService, times(1)).getAllTodoItems();
    }

    @Test
    void getTodoItemById_ShouldReturnTodoItem_WhenIdExists() throws Exception {
        when(todoItemService.getTodoItemById(todoItem.getId())).thenReturn(Optional.of(todoItem));

        mockMvc.perform(get("/api/todo-items/{id}", todoItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoItem.getId()))
                .andExpect(jsonPath("$.title").value(todoItem.getTitle()))
                .andExpect(jsonPath("$.description").value(todoItem.getDescription()));

        verify(todoItemService, times(1)).getTodoItemById(todoItem.getId());
    }

    @Test
    void getTodoItemById_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        when(todoItemService.getTodoItemById(todoItem.getId())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/todo-items/{id}", todoItem.getId()))
                .andExpect(status().isNotFound());

        verify(todoItemService, times(1)).getTodoItemById(todoItem.getId());
    }

    @Test
    void createTodoItem_ShouldReturnCreatedTodoItem() throws Exception {
        when(todoItemService.saveTodoItem(Mockito.any(TodoItem.class))).thenReturn(todoItem);

        mockMvc.perform(post("/api/todo-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(todoItem.getId()))
                .andExpect(jsonPath("$.title").value(todoItem.getTitle()))
                .andExpect(jsonPath("$.description").value(todoItem.getDescription()));

        verify(todoItemService, times(1)).saveTodoItem(Mockito.any(TodoItem.class));
    }

    @Test
    void updateTodoItem_ShouldReturnUpdatedTodoItem_WhenIdExists() throws Exception {
        when(todoItemService.getTodoItemById(todoItem.getId())).thenReturn(Optional.of(todoItem));
        when(todoItemService.saveTodoItem(Mockito.any(TodoItem.class))).thenReturn(todoItem);

        mockMvc.perform(put("/api/todo-items/{id}", todoItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoItem.getId()))
                .andExpect(jsonPath("$.title").value(todoItem.getTitle()))
                .andExpect(jsonPath("$.description").value(todoItem.getDescription()));

        verify(todoItemService, times(1)).getTodoItemById(todoItem.getId());
        verify(todoItemService, times(1)).saveTodoItem(Mockito.any(TodoItem.class));
    }

    @Test
    void updateTodoItem_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        when(todoItemService.getTodoItemById(todoItem.getId())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/todo-items/{id}", todoItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoItem)))
                .andExpect(status().isNotFound());

        verify(todoItemService, times(1)).getTodoItemById(todoItem.getId());
    }

    @Test
    void deleteTodoItem_ShouldReturnNoContent_WhenIdExists() throws Exception {
        when(todoItemService.getTodoItemById(todoItem.getId())).thenReturn(Optional.of(todoItem));

        mockMvc.perform(delete("/api/todo-items/{id}", todoItem.getId()))
                .andExpect(status().isNoContent());

        verify(todoItemService, times(1)).getTodoItemById(todoItem.getId());
        verify(todoItemService, times(1)).deleteTodoItemById(todoItem.getId());
    }

    @Test
    void deleteTodoItem_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        when(todoItemService.getTodoItemById(todoItem.getId())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/todo-items/{id}", todoItem.getId()))
                .andExpect(status().isNotFound());

        verify(todoItemService, times(1)).getTodoItemById(todoItem.getId());
    }
}
