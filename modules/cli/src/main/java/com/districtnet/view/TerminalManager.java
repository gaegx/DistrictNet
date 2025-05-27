package com.districtnet.view;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;

import java.io.IOException;

public class TerminalManager {
    private final Terminal terminal;
    private final Screen screen;
    private final MultiWindowTextGUI gui;

    public TerminalManager() throws IOException {
        System.out.println("[DEBUG] Инициализация TerminalManager...");
        try {
            System.out.println("[DEBUG] Создание терминала...");
            this.terminal = new DefaultTerminalFactory().createTerminal();
            System.out.println("[DEBUG] Терминал создан: " + terminal.getClass().getSimpleName());

            System.out.println("[DEBUG] Создание экрана...");
            this.screen = new TerminalScreen(terminal);
            System.out.println("[DEBUG] Экран создан: " + screen.getClass().getSimpleName());

            System.out.println("[DEBUG] Создание GUI...");
            this.gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace());
            System.out.println("[DEBUG] GUI создан: " + gui.getClass().getSimpleName());

            System.out.println("[DEBUG] Запуск экрана...");
            screen.startScreen();
            System.out.println("[DEBUG] Экран успешно запущен.");
        } catch (IOException e) {
            System.out.println("[ERROR] Ошибка инициализации TerminalManager: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("[ERROR] Неожиданная ошибка при инициализации TerminalManager: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Неожиданная ошибка при инициализации", e);
        }
    }

    public MultiWindowTextGUI getGui() {
        return gui;
    }

    public void close() throws IOException {
        System.out.println("[DEBUG] Закрытие TerminalManager...");
        try {
            screen.stopScreen();
            terminal.close();
            System.out.println("[DEBUG] Терминал закрыт.");
        } catch (Exception e) {
            System.out.println("[ERROR] Ошибка при закрытии TerminalManager: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}