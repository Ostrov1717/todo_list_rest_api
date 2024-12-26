package com.example.to_do.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TodoItemServiceTest {
    private TodoItemRepository todoItemRepository;
    private TodoItemService todoItemService;

    @BeforeEach
    void setUp() {
        todoItemRepository = mock(TodoItemRepository.class);
        todoItemService = new TodoItemService(todoItemRepository);
    }

    @Test
    void getAllTodoItems_ReturnsListOfTodoItems() {
        // Arrange
        TodoItem item1 = new TodoItem(1L, "Task 1", "Description 1");
        TodoItem item2 = new TodoItem(2L, "Task 2", "Description 2");
        when(todoItemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        // Act
        List<TodoItem> items = todoItemService.getAllTodoItems();

        // Assert
        assertEquals(2, items.size());
        assertEquals("Task 1", items.get(0).getTitle());
        verify(todoItemRepository, times(1)).findAll();
    }

    @Test
    void getTodoItemById_ReturnsTodoItem_WhenIdExists() {
        // Arrange
        TodoItem item = new TodoItem(1L, "Task 1", "Description 1");
        when(todoItemRepository.findById(1L)).thenReturn(Optional.of(item));

        // Act
        Optional<TodoItem> result = todoItemService.getTodoItemById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Task 1", result.get().getTitle());
        verify(todoItemRepository, times(1)).findById(1L);
    }

    @Test
    void getTodoItemById_ReturnsEmptyOptional_WhenIdDoesNotExist() {
        // Arrange
        when(todoItemRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<TodoItem> result = todoItemService.getTodoItemById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(todoItemRepository, times(1)).findById(1L);
    }

    @Test
    void saveTodoItem_SavesAndReturnsTodoItem() {
        // Arrange
        TodoItem item = new TodoItem(null, "Task 1", "Description 1");
        TodoItem savedItem = new TodoItem(1L, "Task 1", "Description 1");
        when(todoItemRepository.save(item)).thenReturn(savedItem);

        // Act
        TodoItem result = todoItemService.saveTodoItem(item);

        // Assert
        assertNotNull(result.getId());
        assertEquals("Task 1", result.getTitle());
        verify(todoItemRepository, times(1)).save(item);
    }

    @Test
    void deleteTodoItemById_DeletesTodoItem_WhenIdExists() {
        // Act
        todoItemService.deleteTodoItemById(1L);

        // Assert
        verify(todoItemRepository, times(1)).deleteById(1L);
    }

}
