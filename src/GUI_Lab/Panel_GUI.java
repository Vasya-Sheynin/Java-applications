package GUI_Lab;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.regex.Pattern;

public class Panel_GUI extends JFrame {
    private JPanel mainPanel;
    private JTable table1;
    private JLabel label;
    private JScrollPane scrollPane;
    private JLabel fileLabel;
    private DefaultTableModel model;
    private final String[] columnNames = {"Full Name", "Phone Number", "Conversation Date", "Tariff", "Discount", "Begin Time", "End Time"};
    private Object[][] data = {{"", "", "", "", "", "", ""}};

    public Panel_GUI() {
        setTitle("GUI Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);

        label.setText("No command is selected");

        fileLabel.setBorder(new EmptyBorder(3, 5, 6, 5));

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu commandsMenu = new JMenu("Command");
        JMenu helpMenu = new JMenu("Help");

        menuBar.add(fileMenu);
        menuBar.add(commandsMenu);
        menuBar.add(helpMenu);

        fileMenu.setMnemonic('F');
        JMenuItem open = fileMenu.add(new JMenuItem("Open", 'O'));
        fileMenu.addSeparator();
        JMenuItem exit = fileMenu.add(new JMenuItem("Exit", 'x'));

        commandsMenu.setMnemonic('C');
        JMenuItem addData = commandsMenu.add(new JMenuItem("Add data", 'A'));
        JMenuItem delDataByKey = commandsMenu.add(new JMenuItem("Delete data by key", 'D'));
        commandsMenu.addSeparator();
        JMenu sort = new JMenu("Sort by key");
        sort.setMnemonic('K');
        commandsMenu.add(sort);
        JMenuItem sortByKey = sort.add(new JMenuItem("Straight", 'S'));
        JMenuItem sortByKeyRev = sort.add(new JMenuItem("Reverse", 'R'));
        commandsMenu.addSeparator();
        JMenu findByKey = new JMenu("Find by key");
        findByKey.setMnemonic('i');
        commandsMenu.add(findByKey);
        JMenuItem findByKeyGreater = findByKey.add(new JMenuItem("Greater", 'G'));
        JMenuItem findByKeyEqual = findByKey.add(new JMenuItem("Equal", 'E'));
        JMenuItem findByKeyLess = findByKey.add(new JMenuItem("Under", 'U'));

        helpMenu.setMnemonic('H');
        JMenuItem about = helpMenu.add(new JMenuItem("About", 'B'));

        open.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setCurrentDirectory(new File("."));
            fileChooser.setFileFilter(new FileNameExtensionFilter("DAT Files", "dat"));

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                Main.filename = String.valueOf((fileChooser.getSelectedFile()));
                fileLabel.setText("File Opened: " + Main.filename);
                try {
                    showDataInTable(Main.printFile());
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        exit.addActionListener(e -> System.exit(0));

        addData.addActionListener(e -> {
            label.setText("Adding New Data");

            JDialog dialog = new JDialog(this, "Add New Record", true);

            JTextField textField1 = new JTextField(20);
            JTextField textField2 = new JTextField(20);
            JTextField textField3 = new JTextField(20);
            JTextField textField4 = new JTextField(20);
            JTextField textField5 = new JTextField(20);
            JTextField textField6 = new JTextField(20);
            JTextField textField7 = new JTextField(20);

            textField1.setInputVerifier(new InputVerifier() {
                @Override
                public boolean verify(JComponent input) {
                    JTextField textField = (JTextField) input;
                    String text = textField.getText();
                    String regex = "^[A-Za-z]*_[A-Za-z]*_[A-Za-z]*$";

                    if(Pattern.matches(regex, text) || text.isBlank()) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Name can contain only letters and '_' symbols instead of whitespace");
                        return false;
                    }
                }
            });

            textField2.setInputVerifier(new InputVerifier() {
                @Override
                public boolean verify(JComponent input) {
                    JTextField textField = (JTextField) input;
                    String text = textField.getText();
                    String regex = "^\\+[0-9]+$";

                    if(Pattern.matches(regex, text) || text.isBlank()) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Phone number starts with '+' and uses only numbers afterwards");
                        return false;
                    }
                }
            });

            textField3.setInputVerifier(new InputVerifier() {
                @Override
                public boolean verify(JComponent input) {
                    JTextField textField = (JTextField) input;
                    String text = textField.getText();
                    String regex = "^\\d{2}/\\d{2}/\\d{4}$";

                    if((Pattern.matches(regex, text) && ConversData.validDate(text)) || text.isBlank()) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Date must be correct and match pattern: DD/MM/YYYY");
                        return false;
                    }
                }
            });

            textField4.setInputVerifier(new InputVerifier() {
                @Override
                public boolean verify(JComponent input) {
                    JTextField textField = (JTextField) input;
                    String text = textField.getText();

                    if(!text.contains(" ") || text.isBlank()) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Tariff cannot contain ' ' symbols");
                        return false;
                    }
                }
            });

            textField5.setInputVerifier(new InputVerifier() {
                @Override
                public boolean verify(JComponent input) {
                    JTextField textField = (JTextField) input;
                    String text = textField.getText();
                    String regex = "^\\d{1,2}%$";

                    if((Pattern.matches(regex, text) && ConversData.validSale(text)) || text.isBlank()) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Discount should be an integer value from 0 to 100 and end with '%'");
                        return false;
                    }
                }
            });

            textField6.setInputVerifier(new InputVerifier() {
                @Override
                public boolean verify(JComponent input) {
                    JTextField textField = (JTextField) input;
                    String text = textField.getText();
                    String regex = "^\\d{2}:\\d{2}$";

                    if((Pattern.matches(regex, text) && ConversData.validTime(text)) || text.isBlank()) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Begin Time should be correct and match the format HH:MM");
                        return false;
                    }
                }
            });

            textField7.setInputVerifier(new InputVerifier() {
                @Override
                public boolean verify(JComponent input) {
                    JTextField textField = (JTextField) input;
                    String text = textField.getText();
                    String regex = "^\\d{2}:\\d{2}$";

                    if((Pattern.matches(regex, text) && ConversData.validTime(text)) || text.isBlank()) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "End Time should be correct and match the format HH:MM");
                        return false;
                    }
                }
            });

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(8, 2));
            panel.add(new JLabel("Full Name:"));
            panel.add(textField1);
            panel.add(new JLabel("Phone Number:"));
            panel.add(textField2);
            panel.add(new JLabel("Conversation Date:"));
            panel.add(textField3);
            panel.add(new JLabel("Tariff:"));
            panel.add(textField4);
            panel.add(new JLabel("Discount:"));
            panel.add(textField5);
            panel.add(new JLabel("Begin Time:"));
            panel.add(textField6);
            panel.add(new JLabel("End Time:"));
            panel.add(textField7);

            int option = JOptionPane.showOptionDialog(dialog, panel, "Add New Record", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

            if (option == JOptionPane.OK_OPTION) {
                Object[] vals = {textField1.getText(), textField2.getText(), textField3.getText(),
                        textField4.getText(), textField5.getText(), textField6.getText(), textField7.getText(),};

                Object[][] tmp = data;

                data = new Object[tmp.length + 1][7];
                System.arraycopy(tmp, 0, data, 0, tmp.length);
                data[data.length - 1] = vals;

                try {
                    Main.appendFile(vals, false);
                    showDataInTable(Main.printFile());
                    label.setText("New Record Successfully Added");
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                this.label.setText("Adding Record Discontinued");
            }

            dialog.dispose();
        });

        delDataByKey.addActionListener(e -> {
            label.setText("Removing Record By Key");

            JDialog dialog = new JDialog(this, "Remove Record By Key", true);

            String[] comboBoxValues = {"Full Name", "Phone Number", "Date"};
            JComboBox<String> comboBox = new JComboBox<>(comboBoxValues);
            JLabel label = new JLabel("Enter Key:");
            JTextField textField = new JTextField(20);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));
            panel.add(comboBox);
            panel.add(label);
            panel.add(textField);

            int option = JOptionPane.showOptionDialog(dialog, panel, "Remove Record By Key", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            String selectedValue = "";
            String enteredValue = "";

            if (option == JOptionPane.OK_OPTION) {
                switch ((String) comboBox.getSelectedItem()) {
                    case "Full Name" -> selectedValue = "f";
                    case "Phone Number" -> selectedValue = "t";
                    case "Date" -> selectedValue = "d";
                }
                enteredValue = textField.getText();
            }
            else {
                this.label.setText("Removing Data Discontinued");
            }

            dialog.dispose();

            String[] args = {"-dk", selectedValue, enteredValue};
            try {
                Main.deleteFile(args);
                showDataInTable(Main.printFile());
                label.setText("Record Successfully Removed");
            } catch (ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        sortByKey.addActionListener(e -> {
            label.setText("Sorting Data By Key");

            JDialog dialog = new JDialog(this, "Select Key", true);

            String[] comboBoxValues = {"Full Name", "Phone Number", "Date"};
            JComboBox<String> comboBox = new JComboBox<>(comboBoxValues);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1, 2));
            panel.add(comboBox);

            int option = JOptionPane.showOptionDialog(dialog, panel, "Select Key", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            String selectedValue = "";

            if (option == JOptionPane.OK_OPTION) {
                switch ((String) comboBox.getSelectedItem()) {
                    case "Full Name" -> selectedValue = "f";
                    case "Phone Number" -> selectedValue = "t";
                    case "Date" -> selectedValue = "d";
                }
            }
            else {
                this.label.setText("Sorting Discontinued");
            }

            dialog.dispose();

            try {
                showDataInTable(Main.printFileData(new String[]{"-ps", selectedValue}, false));
                label.setText("Data Successfully Sorted");
            } catch (ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        sortByKeyRev.addActionListener(e -> {
            label.setText("Sorting Data By Key (Reversed)");

            JDialog dialog = new JDialog(this, "Select Key", true);

            String[] comboBoxValues = {"Full Name", "Phone Number", "Date"};
            JComboBox<String> comboBox = new JComboBox<>(comboBoxValues);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1, 2));
            panel.add(comboBox);

            int option = JOptionPane.showOptionDialog(dialog, panel, "Select Key", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            String selectedValue = "";

            if (option == JOptionPane.OK_OPTION) {
                switch ((String) comboBox.getSelectedItem()) {
                    case "Full Name" -> selectedValue = "f";
                    case "Phone Number" -> selectedValue = "t";
                    case "Date" -> selectedValue = "d";
                }
            }
            else {
                this.label.setText("Sorting Discontinued");
            }

            dialog.dispose();

            try {
                showDataInTable(Main.printFileData(new String[]{"-psr", selectedValue}, true));
                label.setText("Data Successfully Sorted (Reversed)");
            } catch (ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        findByKeyEqual.addActionListener(e -> {
            label.setText("Searching Data By Key (Equal)");

            JDialog dialog = new JDialog(this, "Searching Records By Key", true);

            String[] comboBoxValues = {"Full Name", "Phone Number", "Date"};
            JComboBox<String> comboBox = new JComboBox<>(comboBoxValues);
            JLabel label = new JLabel("Enter Key:");
            JTextField textField = new JTextField(20);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));
            panel.add(comboBox);
            panel.add(label);
            panel.add(textField);

            int option = JOptionPane.showOptionDialog(dialog, panel, "Searching Records By Key", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            String selectedValue = "";
            String enteredValue = "";

            if (option == JOptionPane.OK_OPTION) {
                switch ((String) comboBox.getSelectedItem()) {
                    case "Full Name" -> selectedValue = "f";
                    case "Phone Number" -> selectedValue = "t";
                    case "Date" -> selectedValue = "d";
                }
                enteredValue = textField.getText();
            }
            else {
                this.label.setText("Searching Discontinued");
            }

            dialog.dispose();

            try {
                showDataInTable(Main.findByKeyData(new String[]{"-f", selectedValue, enteredValue}));
                label.setText("Data Successfully Found (Equal)");
            } catch (ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        findByKeyGreater.addActionListener(e -> {
            label.setText("Searching Data By Key (Greater)");

            JDialog dialog = new JDialog(this, "Searching Records By Key", true);

            String[] comboBoxValues = {"Full Name", "Phone Number", "Date"};
            JComboBox<String> comboBox = new JComboBox<>(comboBoxValues);
            JLabel label = new JLabel("Enter Key:");
            JTextField textField = new JTextField(20);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));
            panel.add(comboBox);
            panel.add(label);
            panel.add(textField);

            int option = JOptionPane.showOptionDialog(dialog, panel, "Searching Records By Key", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            String selectedValue = "";
            String enteredValue = "";

            if (option == JOptionPane.OK_OPTION) {
                switch ((String) comboBox.getSelectedItem()) {
                    case "Full Name" -> selectedValue = "f";
                    case "Phone Number" -> selectedValue = "t";
                    case "Date" -> selectedValue = "d";
                }
                enteredValue = textField.getText();
            }
            else {
                this.label.setText("Searching Discontinued");
            }

            dialog.dispose();

            try {
                showDataInTable(Main.findByKeyData(new String[]{"-fg", selectedValue, enteredValue}, new KeyCompReverse()));
                label.setText("Data Successfully Found (Greater)");
            } catch (ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        findByKeyLess.addActionListener(e -> {
            label.setText("Searching Data By Key (Under)");

            JDialog dialog = new JDialog(this, "Searching Records By Key", true);

            String[] comboBoxValues = {"Full Name", "Phone Number", "Date"};
            JComboBox<String> comboBox = new JComboBox<>(comboBoxValues);
            JLabel label = new JLabel("Enter Key:");
            JTextField textField = new JTextField(20);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));
            panel.add(comboBox);
            panel.add(label);
            panel.add(textField);

            int option = JOptionPane.showOptionDialog(dialog, panel, "Searching Records By Key", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            String selectedValue = "";
            String enteredValue = "";

            if (option == JOptionPane.OK_OPTION) {
                switch ((String) comboBox.getSelectedItem()) {
                    case "Full Name" -> selectedValue = "f";
                    case "Phone Number" -> selectedValue = "t";
                    case "Date" -> selectedValue = "d";
                }
                enteredValue = textField.getText();
            }
            else {
                this.label.setText("Searching Discontinued");
            }

            dialog.dispose();

            try {
                showDataInTable(Main.findByKeyData(new String[]{"-fl", selectedValue, enteredValue}, new KeyComp()));
                label.setText("Data Successfully Found (Under)");
            } catch (ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        about.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Application developed by Sheynin Vasily", "Information", JOptionPane.INFORMATION_MESSAGE));

        setJMenuBar(menuBar);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Panel_GUI();
    }

    private void createUIComponents() {
        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table1 = new JTable(model);
        table1.setFillsViewportHeight(true);
    }

    private void showDataInTable(ArrayList<String> arr)
    {
        data = new Object[arr.size()][7];

        if (data.length == 0)
        {
            data = new Object[][]{{"", "", "", "", "", "", ""}};
            model.setDataVector(data, columnNames);
        }
        else {
            for (int j = 0; j < arr.size(); j++) {
                String[] words = arr.get(j).split("\n");
                System.arraycopy(words, 0, data[j], 0, words.length);
                model.setDataVector(data, columnNames);
            }
        }
    }
}
