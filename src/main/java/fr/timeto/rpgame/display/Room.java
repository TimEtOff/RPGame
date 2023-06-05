package fr.timeto.rpgame.display;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;
import fr.timeto.timutilslib.CustomFonts;
import org.imgscalr.Scalr;

import javax.swing.*;
import java.awt.*;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

public class Room extends JPanel implements SwingerEventListener {

    STexturedButton quitButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/room/quit-normal.png"), getResourceIgnorePath("/assets/rpgame/room/quit-hover.png"));

    public Room() {
        this.setBackground(new Color(80, 10, 10));
        this.setLayout(null);

        quitButton.setBounds(20, 20);
        quitButton.addEventListener(this);
        this.add(quitButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        float widthFactor = 1;
        float heightFactor = 1;

        if (this.getWidth() < 1920) {
            widthFactor = (float)this.getWidth()/1920;
        }

        if (this.getHeight() < 1080) {
            heightFactor = (float)this.getHeight()/1080;
        }

        int quitButtonWidth = Math.round(72*widthFactor);
        int quitButtonHeight = Math.round(54*heightFactor);
        quitButton.setTexture(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/quit-normal.png"), quitButtonWidth, quitButtonHeight));
        quitButton.setTextureHover(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/quit-hover.png"), quitButtonWidth, quitButtonHeight));
        quitButton.setBounds(Math.round(20*widthFactor), Math.round(20*heightFactor));

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRoundRect(Math.round(960*widthFactor),
                Math.round(99*heightFactor),
                Math.round(899*widthFactor),
                Math.round(932*heightFactor),
                10,
                10);

        g2d.setColor(new Color(61, 9, 9));
        g2d.fillRoundRect(Math.round(963*widthFactor),
                Math.round(102*heightFactor),
                Math.round(893*widthFactor),
                Math.round(926*heightFactor),
                10,
                10);

        if (Client.connectedClients != null) {
            int i = 0;
            int y = 125;
            while (i != Client.connectedClients.size()) {
                ConnectedClient client = Client.connectedClients.get(i);

                g2d.setColor(new Color(52, 8, 8));
                g2d.fillRoundRect(Math.round(990*widthFactor),
                        Math.round(y*heightFactor),
                        Math.round(840*widthFactor),
                        Math.round(133*heightFactor),
                        10,
                        10);

                Font font = CustomFonts.kollektifBoldFont.deriveFont(30f);
                String text; // = Client.connectedClients.get(i).getCharacter()
                if (client.getCharacter() == null) {
                    text = "Personnage non choisi (" + client.getId() + ")";
                } else {
                    text = client.getCharacter().getFullName() + " (" + client.getId() + ")";
                }

                JLabel testLabel = new JLabel(text);
                testLabel.setFont(font);
                Dimension dimension = GameFrame.getStringSize(testLabel, text, Math.round(27*2), Math.round(7*2));
                int nameZoneX = 1004;
                int nameZoneY = Math.round((y + 14)*heightFactor);
                g2d.setColor(new Color(80, 10, 10));
                g2d.fillRoundRect(nameZoneX, nameZoneY, dimension.width, dimension.height, 30, 30);

                g2d.setColor(Color.WHITE);
                g2d.setFont(font);
                g2d.drawString(text, nameZoneX+27, nameZoneY+7);

                y += 162;
                i++;
            }
        }

    }

    @Override
    public void onEvent(SwingerEvent e) {
        Object src = e.getSource();

        if (src == quitButton) {
            Client.disconnect();
            Client.gameFrame.setContentPane(new MainMenu());
        }

    }
}
