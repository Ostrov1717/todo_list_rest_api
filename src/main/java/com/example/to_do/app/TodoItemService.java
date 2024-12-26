package com.example.to_do.app;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoItemService {
    private final TodoItemRepository todoItemRepository;

    public TodoItemService(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    // Получить все элементы списка
    public List<TodoItem> getAllTodoItems() {
        return todoItemRepository.findAll();
    }

    // Получить элемент по ID
    public Optional<TodoItem> getTodoItemById(Long id) {
        return todoItemRepository.findById(id);
    }

    // Создать или обновить элемент
    public TodoItem saveTodoItem(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }

    // Удалить элемент по ID
    public void deleteTodoItemById(Long id) {
        todoItemRepository.deleteById(id);
    }
}
