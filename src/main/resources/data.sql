insert into department(id, name) values
(nextval('department_id_seq'), 'Отдел Разработки'),
(nextval('department_id_seq'), 'Отдел Тестирования'),
(nextval('department_id_seq'), 'Бухгалтерия');

insert into employee(id, name, salary, role, department_id) values
(nextval('employee_id_seq'), 'Иванов Иван Иванович', 500, 'Младший Программист', 1),
(nextval('employee_id_seq'), 'Петров Петр Петрович', 1500, 'Старший Программист', 1),
(nextval('employee_id_seq'), 'Соколовская Ольга Петровна', 3000, 'Ведущий Тестировщик', 2),
(nextval('employee_id_seq'), 'Сидоров Вадим Сергеевич', 1000, 'Старший Тестировщик', 2),
(nextval('employee_id_seq'), 'Баранова Ирина Олеговна', 1500, 'Главный Бухгалтер', 3);