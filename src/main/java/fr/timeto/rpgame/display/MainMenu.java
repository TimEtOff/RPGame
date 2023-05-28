package fr.timeto.rpgame.display;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.rpgame.core.Client;

import javax.swing.*;
import java.awt.*;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

public class MainMenu extends JPanel implements SwingerEventListener {

    STexturedButton connectButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/mainmenu/ConnectToServer-normal.png"), getResourceIgnorePath("/assets/rpgame/mainmenu/ConnectToServer-hover.png"));
    STexturedButton charactersButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/mainmenu/Characters-normal.png"), getResourceIgnorePath("/assets/rpgame/mainmenu/Characters-hover.png"));
    STexturedButton mapsButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/mainmenu/Maps-normal.png"), getResourceIgnorePath("/assets/rpgame/mainmenu/Maps-hover.png"));

    public MainMenu() {
        this.setBackground(new Color(80, 10, 10));
        this.setLayout(null);

        connectButton.setBounds(741, 318);
        connectButton.addEventListener(this);
        this.add(connectButton);

        charactersButton.setBounds(741, 487);
        charactersButton.addEventListener(this);
        this.add(charactersButton);

        mapsButton.setBounds(741, 656);
        mapsButton.addEventListener(this);
        this.add(mapsButton);
    }

    @Override
    public void paintComponent(Graphics g) {

        int x = this.getWidth()/2 - 220;
        int y1 = this.getHeight()/2 - 169 - 53;
        int y2 = y1 + 169;
        int y3 = y2 + 169;

        connectButton.setBounds(x, y1);
        connectButton.setEnabled(!Client.isConnectedToServer);
        charactersButton.setBounds(x, y2);
        mapsButton.setBounds(x, y3);

        super.paintComponent(g);
    }

    @Override
    public void onEvent(SwingerEvent e) {
        Object src = e.getSource();

        if (src == connectButton) {
            if (!Client.isConnectedToServer) {
                Client.connectToServer();
            }
        } else if (src == charactersButton) {

        } else if (src == mapsButton) {

        }

    }
}
