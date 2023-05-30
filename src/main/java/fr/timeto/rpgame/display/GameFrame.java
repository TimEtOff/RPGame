package fr.timeto.rpgame.display;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.timeto.rpgame.core.Client;
import net.harawata.appdirs.AppDirsFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

public class GameFrame extends JFrame {

    public static final String filesFolder =  AppDirsFactory.getInstance().getUserDataDir("Tim's RPGame", null, null, true);

    public static final File RP_GAMEFOLDER = new File(filesFolder);
    public static final File RP_CHARACTERSFOLDER = new File(RP_GAMEFOLDER, "characters");
    public static final File RP_MAPSFOLDER = new File(RP_GAMEFOLDER, "maps");
    public static final File RP_CONFIGFILE = new File(RP_GAMEFOLDER, "infos.properties");
    public static final Saver RP_CONFIGFILE_SAVER = new Saver(RP_CONFIGFILE.toPath());

    public GameFrame() {
        this.setTitle("Tim's RPGame");
        this.setSize(1280, 720);
        this.setMinimumSize(new Dimension(978, 550));
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(exitListener);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);
    //    this.setIconImage(Swinger.getResourceIgnorePath("/character/launcher/main/logo.png"));
        super.setContentPane(new MainMenu());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.setVisible(true);
    }

    @Override
    public void setContentPane(Container container) {
        super.setContentPane(container);
        this.revalidate();
        this.repaint();
    }

    public static WindowListener exitListener = new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
            Client.disconnect();
            System.exit(0);
        }
    };
}
