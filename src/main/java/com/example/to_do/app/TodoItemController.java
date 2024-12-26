package com.example.to_do.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todo-items")
@Tag(name = "Todo Items", description = "CRUD-операции для управления элементами списка дел")
public class TodoItemController {
    private final TodoItemService todoItemService;

    public TodoItemController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @GetMapping
    @Operation(summary = "Получить все элементы", description = "Возвращает список всех элементов списка дел")
    public List<TodoItem> getAllTodoItems() {
        return todoItemService.getAllTodoItems();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить элемент по ID", description = "Возвращает элемент списка дел по заданному ID")
    public ResponseEntity<TodoItem> getTodoItemById(@PathVariable Long id) {
        Optional<TodoItem> todoItem = todoItemService.getTodoItemById(id);
        return todoItem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать элемент", description = "Создает новый элемент списка дел")
    public ResponseEntity<TodoItem> createTodoItem(@RequestBody TodoItem todoItem) {
        TodoItem createdTodoItem = todoItemService.saveTodoItem(todoItem);
        return ResponseEntity.status(201).body(createdTodoItem);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить элемент", description = "Обновляет существующий элемент списка дел")
    public ResponseEntity<TodoItem> updateTodoItem(@PathVariable Long id, @RequestBody TodoItem todoItem) {
        if (!todoItemService.getTodoItemById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        todoItem.setId(id);
        TodoItem updatedTodoItem = todoItemService.saveTodoItem(todoItem);
        return ResponseEntity.ok(updatedTodoItem);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить элемент", description = "Удаляет элемент списка дел по заданному ID")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Long id) {
        if (!todoItemService.getTodoItemById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        todoItemService.deleteTodoItemById(id);
        return ResponseEntity.noContent().build();
    }
}
