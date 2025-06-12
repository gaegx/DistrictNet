package com.districtnet.view.screen;

import com.districtnet.view.TerminalManager;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import java.io.IOException;

public class MainScreen {
    private final TerminalManager terminalManager;
    private BasicWindow window;

    public MainScreen(TerminalManager terminalManager) {
        this.terminalManager = terminalManager;
        System.out.println("[DEBUG] MainScreen создан.");
    }

    public void show() {
        System.out.println("[DEBUG] Запуск MainScreen.show()...");
        window = new BasicWindow("DistrictNet - Основной интерфейс");
        window.setHints(java.util.Arrays.asList(Window.Hint.CENTERED));

        Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        mainPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        Panel centerPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        centerPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        Label welcomeLabel = new Label("Добро пожаловать в основной интерфейс!");
        Label infoLabel = new Label("Здесь будут добавлены функции.");

        welcomeLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
        infoLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        centerPanel.addComponent(welcomeLabel);
        centerPanel.addComponent(infoLabel);
        centerPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        Button backButton = new Button("Назад", this::goBack);
        backButton.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        centerPanel.addComponent(backButton);
        mainPanel.addComponent(centerPanel);
        mainPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        window.setComponent(mainPanel);

        try {
            terminalManager.getGui().addWindow(window);
            terminalManager.getGui().setActiveWindow(window);
            System.out.println("[DEBUG] MainScreen добавлен в GUI и установлен как активное окно.");
            terminalManager.getGui().updateScreen();
            System.out.println("[DEBUG] Экран обновлён для MainScreen.");
        } catch (Exception e) {
            System.out.println("[ERROR] Ошибка при добавлении MainScreen в GUI: " + e.getMessage());
            e.printStackTrace();
            terminalManager.getGui().getGUIThread().invokeLater(() -> {
                MessageDialog.showMessageDialog(
                        terminalManager.getGui(),
                        "Ошибка",
                        "Не удалось отобразить главное меню:\n" + e.getMessage()
                );
            });
        }
    }

    private void goBack() {
        System.out.println("[DEBUG] Нажата кнопка 'Назад'. Закрытие MainScreen...");

        if (window != null) {
            window.close();
            terminalManager.getGui().removeWindow(window);
            System.out.println("[DEBUG] MainScreen удалён из GUI.");
        }

        terminalManager.getGui().getGUIThread().invokeLater(() -> {
            try {
                new WelcomeScreen(terminalManager).show();
                System.out.println("[DEBUG] WelcomeScreen показан.");
            } catch (IOException e) {
                System.out.println("[ERROR] Ошибка при открытии WelcomeScreen: " + e.getMessage());
                e.printStackTrace();
                MessageDialog.showMessageDialog(
                        terminalManager.getGui(),
                        "Ошибка",
                        "Не удалось вернуться в приветственное меню:\n" + e.getMessage()
                );
            }
        });
    }



    public BasicWindow getWindow() {
        return window;
    }
}
