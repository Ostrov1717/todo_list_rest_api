Легко ли было выполнить задачу с помощью ИИ? 
В целом было достаточно несложно. Нужно отметить, что мы в Java Labaratory разрабатывали похожую задачу по созданию Rest API (она сложнее, имеет больше требований). Понимание этапов разработки, особенностей взаимодействия с чатом очень помогло в выполнении этого задания. 

Сколько времени у вас заняло выполнение задачи?
Приблизительно 5 часов.

Был ли код готов к запуску после генерации? Что вам пришлось изменить, чтобы сделать его пригодным для использования? 
В целом да, после генерации код был готов к запуску. Я пытался использовать проект lombok для генерации шаблонного кода, но его инструменты вели себя странно, периодически при запуске приложения или тестов приложение не могло найти конструкторов или методов, которые должен был сгенерировать фреймворк lombok. В конце концов, я удалил этот фреймворк и сгенерировал вручную необходимые конструкторы, геттеры и сеттеры.

С какими трудностями вы столкнулись во время выполнения задачи?
Все остальное, кроме перечисленного выше, было без трудностей. 

Какие конкретные подсказки вы извлекли в качестве хорошей практики для выполнения задачи?
1. Самое важное как мне показалось самому понимать что ты хочешь создать. Тогда ты сможешь точно поставить чату задачу, и он великовероятно с ней справиться. Например я, при работе с другим подобным учебным проетом, обратил внимание что важно точно обозначить версии Spring Boot и Swagger (Open API), с которыми ты хочешь работать.
2. Важно понимать:
-  общие принципы разработки приложения на Java, этапы, интеграцию сторонних библиотек;
- этапность разработки того вида приложения, над которым ты работаешь в этом случае REST API.