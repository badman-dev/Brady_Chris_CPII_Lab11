import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ListerFrame extends JFrame {
    JPanel northPnl;
    JLabel titleLbl;

    JPanel centerPnl;
    JButton openBtn;
    JTextArea outputArea;
    JScrollPane outputScroll;

    JPanel southPnl;
    JButton quitBtn;
    public ListerFrame() {
        createNorthPanel();
        createCenterPanel();
        createSouthPanel();

        Toolkit tk=Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();

        setTitle("Random Product Search");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createNorthPanel() {
        northPnl = new JPanel();
        titleLbl = new JLabel("Directory Lister");
        titleLbl.setFont(new Font("Serif", Font.PLAIN, 32
        ));

        northPnl.add(titleLbl);
        add(northPnl, BorderLayout.NORTH);
    }
    private void createCenterPanel() {
        centerPnl = new JPanel();
        openBtn = new JButton("Select Folder");
        outputArea = new JTextArea(40, 40);
        outputScroll = new JScrollPane(outputArea);

        openBtn.addActionListener((ActionEvent ae) -> fileHandler());

        centerPnl.add(openBtn);
        centerPnl.add(outputScroll);
        add(centerPnl, BorderLayout.CENTER);
    }

    private void createSouthPanel() {
        southPnl = new JPanel();
        quitBtn = new JButton("Quit");

        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));

        southPnl.add(quitBtn);
        add(southPnl, BorderLayout.SOUTH);
    }

    private void fileHandler() {
        outputArea.setText("");

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());
            try {
                File file = chooser.getSelectedFile();
                outputArea.append(file.getName() + "\n");
                readDirectory(file, 1);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void readDirectory(File dir, int level) {
        File[] files = dir.listFiles();
        for (File file : files) {
            String newLine = "";
            for (int i = 0; i < level; i++) {
                newLine += "     ";
            }
            newLine += file.getName() + "\n";
            outputArea.append(newLine);
            if (file.isDirectory()) {
                readDirectory(file, level++);
            }
        }
    }
}
