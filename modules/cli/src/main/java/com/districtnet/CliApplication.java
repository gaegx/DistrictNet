package com.districtnet;

import com.districtnet.view.TerminalManager;
import com.districtnet.view.screen.WelcomeScreen;

import java.io.IOException;

public class CliApplication {

    public static void main(String[] args) throws IOException {
        TerminalManager terminalManager = new TerminalManager();
        WelcomeScreen welcomeScreen = new WelcomeScreen(terminalManager);
        welcomeScreen.show();
    }
}