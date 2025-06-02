package com.districtnet.view.screen;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.districtnet.view.TerminalManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoadingScreen {
    private final TerminalManager terminalManager;
    private final Runnable onFinish;

    public LoadingScreen(TerminalManager terminalManager, Runnable onFinish) {
        this.terminalManager = terminalManager;
        this.onFinish = onFinish;
        System.out.println("[DEBUG] LoadingScreen создан, onFinish: " + (onFinish != null ? "не null" : "null"));
    }

    public void show() {
        System.out.println("[DEBUG] Запуск LoadingScreen.show()...");
        BasicWindow window = new BasicWindow("Загрузка системы...");
        window.setHints(java.util.Arrays.asList(Window.Hint.CENTERED));

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
        Label statusLabel = new Label("Проверка окружения...");
        panel.addComponent(statusLabel);

        window.setComponent(panel);
        try {
            terminalManager.getGui().addWindow(window);
            terminalManager.getGui().setActiveWindow(window); // Устанавливаем окно загрузки как активное
            System.out.println("[DEBUG] Окно загрузки добавлено в GUI и установлено как активное.");
        } catch (Exception e) {
            System.out.println("[ERROR] Ошибка при добавлении окна загрузки: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        new Thread(() -> {
            System.out.println("[DEBUG] Запуск проверки Docker в отдельном потоке...");

            if (!isDockerInstalled()) {
                System.out.println("[DEBUG] Docker НЕ найден.");
                terminalManager.getGui().removeWindow(window);
                MessageDialog.showMessageDialog(
                        terminalManager.getGui(),
                        "Docker не найден",
                        "На этом устройстве не установлен Docker.\nПожалуйста, установите Docker с официального сайта:\nhttps://www.docker.com/get-started"
                );
                return;
            }

            System.out.println("[DEBUG] Docker найден. Запуск контейнеров...");

            boolean success = runDockerStartup();

            try {
                terminalManager.getGui().removeWindow(window);
                System.out.println("[DEBUG] Окно загрузки удалено.");
                if (success) {
                    System.out.println("[DEBUG] Контейнеры успешно запущены. Вызов onFinish...");
                    if (onFinish != null) {
                        onFinish.run();
                        System.out.println("[DEBUG] onFinish успешно выполнен.");
                        // Убедимся, что GUI переключает фокус на новое окно
                        terminalManager.getGui().getGUIThread().invokeLater(() -> {
                            System.out.println("[DEBUG] Проверка активности GUI после onFinish...");
                        });
                    } else {
                        System.out.println("[WARNING] onFinish = null. Не выполнен переход в главное меню.");
                        MessageDialog.showMessageDialog(
                                terminalManager.getGui(),
                                "Ошибка",
                                "Переход в главное меню не выполнен: onFinish не определён."
                        );
                    }
                } else {
                    System.out.println("[DEBUG] Ошибка запуска контейнеров.");
                    MessageDialog.showMessageDialog(
                            terminalManager.getGui(),
                            "Ошибка",
                            "Не удалось запустить контейнеры Docker."
                    );
                }
            } catch (Exception e) {
                System.out.println("[ERROR] Ошибка при выполнении GUI операций: " + e.getMessage());
                e.printStackTrace();
                MessageDialog.showMessageDialog(
                        terminalManager.getGui(),
                        "Ошибка",
                        "Произошла ошибка в процессе загрузки:\n" + e.getMessage()
                );
            }
        }).start();
    }

    private boolean isDockerInstalled() {
        try {
            System.out.println("[DEBUG] Проверка установки Docker...");
            ProcessBuilder builder = new ProcessBuilder("docker", "--version");
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[Docker Version Output] " + line);
            }

            int exitCode = process.waitFor();
            System.out.println("[DEBUG] Проверка Docker завершена с кодом: " + exitCode);
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            System.out.println("[ERROR] Проверка Docker завершилась с ошибкой: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean runDockerStartup() {
        try {
            System.out.println("[DEBUG] Запуск docker-compose...");
            ProcessBuilder builder = new ProcessBuilder("docker-compose", "up", "-d");
            builder.redirectErrorStream(true);

            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[Docker Compose Output] " + line);
                if (line.toLowerCase().contains("error")) {
                    System.out.println("[ERROR] Обнаружена ошибка в выводе docker-compose: " + line);
                }
            }

            int exitCode = process.waitFor();
            System.out.println("[DEBUG] docker-compose завершился с кодом: " + exitCode);
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            System.out.println("[ERROR] Ошибка запуска docker-compose: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}