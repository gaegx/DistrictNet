# Архитектура распределённого ПО для сисадмина (DistrictNet)

## Общая концепция

Целью системы является создание сети узлов с возможностью выполнения распределённых вычислений. Это ПО позволит сисадминам управлять вычислительными задачами на разных узлах через интерфейсы SSH, FTP и Kafka. Также реализуется **удалённое управление узлами** с центрального сервера — запуск, остановка процессов, перезагрузка сервисов и передача конфигураций.

## Архитектура системы

### Основные компоненты:

1. **Ядро системы**:
   - Управление узлами.
   - Регистрирует и управляет состоянием каждого узла.
   - Интерфейсы для связи с узлами (SSH, FTP).
   - REST API для взаимодействия с пользователем.
   - Kafka для асинхронного обмена сообщениями между компонентами.
   - **Удалённое управление узлами** — через SSH и Kafka выполняются административные команды на узлах.

2. **Узел**:
   - Сервер, подключённый к сети.
   - Обрабатывает задания, полученные от ядра.
   - Возможности для принятия задач через FTP или SSH.
   - Поддержка Redis для хранения состояния.
   - Получает задачи и команды через Kafka.
   - **Обрабатывает управляющие команды** от ядра (например, `systemctl restart service`, `kill`, `update`).

3. **API**:
   - REST API для управления задачами, мониторинга узлов и получения статуса.
   - Механизмы авторизации и аутентификации.
   - Получение и отправка информации через Kafka.
   - **Интерфейс для отправки управляющих команд** на узлы.

4. **CLI**:
   - Утилита командной строки для администраторов.
   - Позволяет:
     - Выполнять команды на узлах.
     - Просматривать список и статус узлов.
     - Отправлять задания и получать логи.
     - Развёртывать конфигурации.
   - Работает через REST API и Kafka.

5. **База данных**:
   - PostgreSQL для хранения статистики, задач и состояния узлов.
   - Логи управляющих команд и их статусы.

6. **Kafka**:
   - Каналы:
     - `tasks` — задачи.
     - `status` — статусы выполнения.
     - `admin_commands` — управляющие команды (перезагрузка, обновление, настройка).
     - `logs` — логи от узлов.
   - Обеспечивает масштабируемый и асинхронный обмен сообщениями.

### Взаимодействие компонентов (архитектура в псевдографике)

```text
+--------------------------------------+
|            Пользователь             |
|     (Взаимодействие через API/CLI)  |
+----------------------+--------------+
                       |
              +--------v--------+
              |     API Server  |
              +--------+--------+
                       |
              +--------v---------+
              | Core Management  |
              |     Server       |
              +--------+---------+
                       |
       +---------------+--------------+
       |                              |
+------v-------+             +--------v--------+
|   Redis      |             |   FTP-сервер     |
| (Очередь)    |             +--------+--------+
+--------------+                      |
                                      |
                               +------v------+
                               |    Узлы      |
                               | вычислений   |
                               +------+-------+
                                      |
                        +-------------v-------------+
                        | SSH (управление задачами) |
                        +---------------------------+
                                      |
                         +------------v-------------+
                         | Мониторинг и логирование |
                         +--------------------------+