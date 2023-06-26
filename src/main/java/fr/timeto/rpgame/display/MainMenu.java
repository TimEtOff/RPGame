package fr.timeto.rpgame.display;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.rpgame.core.Client;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import java.awt.*;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

public class MainMenu extends JPanel implements SwingerEventListener {

    STexturedButton connectButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/mainmenu/ConnectToServer-normal.png"), getResourceIgnorePath("/assets/rpgame/mainmenu/ConnectToServer-hover.png"));
    STexturedButton charactersButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/mainmenu/Characters-normal.png"), getResourceIgnorePath("/assets/rpgame/mainmenu/Characters-hover.png"));
    STexturedButton mapsButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/mainmenu/Maps-normal.png"), getResourceIgnorePath("/assets/rpgame/mainmenu/Maps-hover.png"));
    public final LoadingSpinner spinner = new LoadingSpinner(new Color(210, 214, 86), 65, 8);
    JLabel idLabel = new JLabel();

    public MainMenu() {
        this.setBackground(new Color(80, 10, 10));
        this.setLayout(null);

        this.add(spinner);
        spinner.setVisible(false);

        connectButton.setBounds(741, 318);
        connectButton.addEventListener(this);
        this.add(connectButton);

        charactersButton.setBounds(741, 487);
        charactersButton.addEventListener(this);
        this.add(charactersButton);

        mapsButton.setBounds(741, 656);
        mapsButton.addEventListener(this);
        this.add(mapsButton);

        idLabel.setText("Votre ID est " + Client.id);
        idLabel.setForeground(new Color(128, 15, 15));
        idLabel.setFont(CustomFonts.kollektifBoldFont.deriveFont(22f));
        idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(idLabel);
    }

    @Override
    public void paintComponent(Graphics g) {

        int x = this.getWidth()/2 - 220;
        int y1 = this.getHeight()/2 - 169 - 53;
        int y2 = y1 + 169;
        int y3 = y2 + 169;

        float widthFactor = 1;
        float heightFactor = 1;

        if (this.getWidth() < 1920) {
            widthFactor = (float)this.getWidth()/1920;
        }

        if (this.getHeight() < 1080) {
            heightFactor = (float)this.getHeight()/1080;
        }

        connectButton.setBounds(x, y1);
        connectButton.setEnabled(!Client.isConnectedToServer);
        charactersButton.setBounds(x, y2);
        mapsButton.setBounds(x, y3);
        spinner.setPreferredSize(new Dimension(Math.round(65*widthFactor), Math.round(65*widthFactor)));
        spinner.setThickness(Math.round(8*widthFactor));
        spinner.setBounds(Client.gameFrame.getWidth()-Math.round(120*widthFactor), Math.round(20*heightFactor), (int) Math.round(spinner.getPreferredSize().getWidth()+50*widthFactor), (int) Math.round(spinner.getPreferredSize().getHeight()+50*heightFactor));
        idLabel.setBounds(Math.round(50*widthFactor), Client.gameFrame.getHeight() - 75, Client.gameFrame.getWidth() - Math.round(100*widthFactor), 25);
        super.paintComponent(g);
    }

    @Override
    public void onEvent(SwingerEvent e) {
        Object src = e.getSource();

        if (src == connectButton) {
            if (!Client.isConnectedToServer) {
                spinner.setVisible(true);
                Client.connectToServer();
            }
        } else if (src == charactersButton) {
            Client.gameFrame.setContentPane(new CharacterCreator());
        } else if (src == mapsButton) {

        }

    }
}
