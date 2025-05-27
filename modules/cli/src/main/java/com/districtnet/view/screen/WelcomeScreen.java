package com.districtnet.view.screen;

import com.districtnet.view.TerminalManager;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import java.io.IOException;

public class WelcomeScreen {
    private final TerminalManager terminalManager;
    private final String[] logo = {
            "  ____  _           _        _      _   _      _   ",
            " |  _ \\| |         (_)      | |    | \\ | |    | |  ",
            " | | | | |__   __ _ _  ___  | |_   |  \\| | ___| |_ ",
            " | | | | '_ \\ / _` | |/ __| | __|  | . ` |/ _ \\ __|",
            " | |_| | | | | (_| | | (__  | |_   | |\\  |  __/ |_ ",
            " |____/|_| |_|\\__,_|_|\\___|  \\__|  |_| \\_|\\___|\\__|",
            "                                                  ",
            "                                                    ",
            ""
    };

    public WelcomeScreen(TerminalManager terminalManager) {
        this.terminalManager = terminalManager;
        System.out.println("[DEBUG] WelcomeScreen создан.");
    }

    public void show() throws IOException {
        System.out.println("[DEBUG] Запуск WelcomeScreen.show()...");
        BasicWindow window = new BasicWindow("DistrictNet - Приветственное меню");

        window.setHints(java.util.Arrays.asList(Window.Hint.CENTERED));

        Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        mainPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        Panel centerPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        centerPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        for (String line : logo) {
            Label label = new Label(line);
            label.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
            centerPanel.addComponent(label);
        }

        centerPanel.addComponent(new EmptySpace());

        Button startButton = new Button("Начать работу", () -> {
            System.out.println("[DEBUG] Нажата кнопка 'Начать работу'. Закрытие WelcomeScreen...");
            window.close();
            System.out.println("[DEBUG] Создание LoadingScreen...");
            new LoadingScreen(terminalManager, () -> {
                System.out.println("[DEBUG] Вызов onFinish для перехода в MainScreen...");
                try {
                    MainScreen mainScreen = new MainScreen(terminalManager);
                    mainScreen.show();
                    System.out.println("[DEBUG] MainScreen.show() вызван.");
                    // Явное ожидание для MainScreen
                    terminalManager.getGui().waitForWindowToClose(mainScreen.getWindow());
                    System.out.println("[DEBUG] MainScreen закрыт пользователем.");
                } catch (Exception e) {
                    System.out.println("[ERROR] Ошибка при вызове MainScreen: " + e.getMessage());
                    e.printStackTrace();
                    MessageDialog.showMessageDialog(
                            terminalManager.getGui(),
                            "Ошибка",
                            "Не удалось открыть главное меню:\n" + e.getMessage()
                    );
                }
            }).show();
        });

        Button exitButton = new Button("Выход", () -> {
            System.out.println("[DEBUG] Нажата кнопка 'Выход'. Закрытие приложения...");
            try {
                terminalManager.close();
            } catch (IOException e) {
                System.out.println("[ERROR] Ошибка при закрытии терминала: " + e.getMessage());
                e.printStackTrace();
            }
            System.exit(0);
        });

        startButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        exitButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        centerPanel.addComponent(startButton);
        centerPanel.addComponent(exitButton);

        mainPanel.addComponent(centerPanel);
        window.setComponent(mainPanel);

        System.out.println("[DEBUG] Добавление WelcomeScreen в GUI...");
        try {
            terminalManager.getGui().addWindow(window);
            terminalManager.getGui().setActiveWindow(window); // Устанавливаем окно приветствия как активное
            System.out.println("[DEBUG] WelcomeScreen добавлен в GUI и установлен как активное окно.");

            // Явное ожидание, чтобы окно оставалось активным
            terminalManager.getGui().waitForWindowToClose(window);
            System.out.println("[DEBUG] WelcomeScreen закрыт пользователем.");
        } catch (Exception e) {
            System.out.println("[ERROR] Ошибка при отображении WelcomeScreen: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Не удалось отобразить WelcomeScreen", e);
        }
    }
}