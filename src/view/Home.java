/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DataBase.LaptopManager;
import DataBase.PhoneManager;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.k33ptoo.components.KButton;
import com.k33ptoo.components.KGradientPanel;
import controller.AddProductController;
import database.DatabaseConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.dnd.DragSourceListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.Laptop;
import model.Phone;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author AN
 */
public class Home extends javax.swing.JFrame {
    
    public Home() {
        initComponents();
        displayLaptop();
    }
    


    
    int width =180 ;
    int height = 60;

    private void openSettingBar() {
        int initialX = 970;

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = width; i >= 0; i--) {
                    int newX = initialX - (width - i);
                    setting.setBounds(newX, setting.getY(), width - i, height);
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    private void closeSettingBar() {
        int initialX =810;

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < width; i++) {
                    int newX = initialX + i;
                    setting.setBounds(newX, setting.getY(), width - i, height);
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    private void displayLaptop() {
        Color mainColor = new Color(51, 153, 255);
        LaptopManager laptopManager = new LaptopManager();
        List<model.Laptop> laptops = laptopManager.getLaptop();

        JPanel displayPanel = new JPanel(new GridLayout(0, 3, 120, 50));
        displayPanel.setBackground(Color.WHITE);
       

        for (model.Laptop laptop : laptops) {
            final String laptopId = String.valueOf(laptop.getId());
            KGradientPanel laptopPanel = new KGradientPanel();
            laptopPanel.setLayout(new BoxLayout(laptopPanel, BoxLayout.Y_AXIS));
            laptopPanel.setPreferredSize(new Dimension(30, 260));
            laptopPanel.setBackground(Color.WHITE);
            laptopPanel.setkStartColor(mainColor);
            laptopPanel.setkEndColor(Color.white);
            laptopPanel.setkBorderRadius(100);
            laptopPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    id.setText(laptopId);
                }
            });
            laptopPanel.setBackground(Color.WHITE);

            ImageIcon imageIcon = new ImageIcon(laptop.getImage());
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            laptopPanel.add(Box.createVerticalStrut(10));
            laptopPanel.add(imageLabel);

            // Name
            JLabel nameLabel = new JLabel(laptop.getName());
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setFont(new Font("Segoe UI", 1, 12));
            laptopPanel.add(Box.createVerticalStrut(10));
            laptopPanel.add(nameLabel);

            // Description
            JLabel descriptionLabel = new JLabel(laptop.getDescription());
            descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            descriptionLabel.setFont(new Font("Segoe UI", 1, 12));
            laptopPanel.add(Box.createVerticalStrut(10));
            laptopPanel.add(descriptionLabel);

            // Price
            JLabel priceLabel = new JLabel(laptop.getPrice());
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            priceLabel.setFont(new Font("Segoe UI", 1, 12));
            priceLabel.setForeground(Color.red);
            laptopPanel.add(Box.createVerticalStrut(10));
            laptopPanel.add(priceLabel);

            
            KButton buyButton = new KButton();
            buyButton.setText("BUY");
            buyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Buy detailFrame = new Buy(laptop.getName(), laptop.getPrice());
                    detailFrame.setVisible(true);
                }
            });
            buyButton.setBackground(mainColor);
            buyButton.setkAllowGradient(false);
            buyButton.setkBorderRadius(30);
            buyButton.setkBackGroundColor(mainColor);
            buyButton.setkSelectedColor(Color.WHITE);
            buyButton.setkHoverForeGround(Color.BLACK);
            laptopPanel.add(Box.createVerticalStrut(10));
            buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            laptopPanel.add(buyButton);

            displayPanel.add(laptopPanel);
        }
        
        JScrollPane scrollPane = new JScrollPane(displayPanel);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(scrollPane);
    }
    private void displayPhone() {
        Color mainColor = new Color(51, 153, 255);
        PhoneManager phoneManager = new PhoneManager();
        List<model.Phone> phones = phoneManager.getPhone();

        JPanel displayPanel = new JPanel(new GridLayout(0, 3, 120, 50));
        displayPanel.setBackground(Color.WHITE);

        for (model.Phone phone : phones) {
            final String phoneId = String.valueOf(phone.getId());
            KGradientPanel phonePanel = new KGradientPanel();
            phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.Y_AXIS));
            phonePanel.setPreferredSize(new Dimension(30, 250));
            phonePanel.setBackground(Color.WHITE);
            phonePanel.setkStartColor(mainColor);
            phonePanel.setkEndColor(Color.white);
            phonePanel.setkBorderRadius(100);
            phonePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    id.setText(phoneId);
                }
            });
            phonePanel.setBackground(Color.WHITE);
          

            ImageIcon imageIcon = new ImageIcon(phone.getImage());
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            phonePanel.add(Box.createVerticalStrut(10));
            phonePanel.add(imageLabel);

            // Name
            JLabel nameLabel = new JLabel(phone.getName());
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            nameLabel.setFont(new Font("Segoe UI", 1, 12));
            phonePanel.add(Box.createVerticalStrut(10));
            phonePanel.add(nameLabel);

            // Description
            JLabel descriptionLabel = new JLabel(phone.getDescription());
            descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            descriptionLabel.setFont(new Font("Segoe UI", 1, 12));
            phonePanel.add(Box.createVerticalStrut(10));
            phonePanel.add(descriptionLabel);

            // Price
            JLabel priceLabel = new JLabel(phone.getPrice());
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            priceLabel.setFont(new Font("Segoe UI", 1, 12));
            priceLabel.setForeground(Color.red);
            phonePanel.add(Box.createVerticalStrut(10));
            phonePanel.add(priceLabel);

            KButton buyButton = new KButton();
            buyButton.setText("BUY");
            buyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Buy detailFrame = new Buy(phone.getName(), phone.getPrice());
                    detailFrame.setVisible(true);
                }
            });
            buyButton.setBackground(mainColor);
            buyButton.setkAllowGradient(false);
            buyButton.setkBorderRadius(30);
            buyButton.setkBackGroundColor(mainColor);
            buyButton.setkSelectedColor(Color.WHITE);
            buyButton.setkHoverForeGround(Color.BLACK);
            buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            phonePanel.add(Box.createVerticalStrut(10));
            phonePanel.add(buyButton);

            displayPanel.add(phonePanel);
        }

        JScrollPane scrollPane = new JScrollPane(displayPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(scrollPane);
    }
    
    
    private void displayDailyTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product Name");
        model.addColumn("Quantity");
        model.addColumn("Price");
        model.addColumn("Date");
        model.addColumn("Total");

        int totalRevenue = 0;
        String query = "SELECT nameproduct, quantity, price,date FROM `order`";

        try (
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
    )   {
        while (resultSet.next()) {
            String nameproduct = resultSet.getString("nameproduct");
            int quantity = resultSet.getInt("quantity");
            int price = Integer.parseInt(resultSet.getString("price").replaceAll("[^\\d]", ""));
            Date date = resultSet.getDate("date");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            String formattedDate = dateFormat.format(date);
            int total = quantity * price;
            totalRevenue += total;
            model.addRow(new Object[]{nameproduct, quantity, price + "đ", formattedDate, total + "đ"});
        }
        } catch (SQLException e) {
        e.printStackTrace();
        }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        Color mainColor = new Color(51, 153, 255);
        KGradientPanel totalRevenuepanel = new KGradientPanel();
        totalRevenuepanel.setPreferredSize(new Dimension(250,50));
        totalRevenuepanel.setBackground(Color.WHITE);
        totalRevenuepanel.setkStartColor(mainColor);
        totalRevenuepanel.setkEndColor(Color.white);

        JLabel totalRevenuelabel = new JLabel("Total Revenue: " + totalRevenue + "đ");
        totalRevenuelabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalRevenuepanel.add(totalRevenuelabel);
        JTextField searchField = new JTextField(10);
        totalRevenuepanel.add(searchField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            int totalRevenue1 =0;
            String searchText = searchField.getText();
            System.out.println(searchText);
        if (!searchText.isEmpty()) {
            try { 
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date searchDate = dateFormat.parse(searchText);
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
                table.setRowSorter(sorter);
                int dateColumnIndex = model.findColumn("Date");
                RowFilter<DefaultTableModel, Integer> rowFilter = RowFilter.regexFilter(searchText, dateColumnIndex);
                sorter.setRowFilter(rowFilter);
                for (int i = 0; i < table.getRowCount(); i++) {
                int quantity = (int) table.getValueAt(i, 1);
                String value = table.getValueAt(i, 2).toString();
                value = value.replaceAll("\\D", "");
                int price = Integer.parseInt(value);
                int total = quantity * price;
                totalRevenue1 += total;
                }
                totalRevenuelabel.setText("Total Revenue: " + totalRevenue1 + "đ");
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        } else {
            TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
            if (sorter != null) {
                sorter.setRowFilter(null);
            }
        }
        });
        totalRevenuepanel.add(searchButton);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(totalRevenuepanel, BorderLayout.SOUTH);
        jScrollPane1.setViewportView(panel);
    }
    
    
    private void displayUserTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Customer Name");
        model.addColumn("ID");
        model.addColumn("Phone Number");
        String query = "SELECT name, id, phone FROM `customer`";

        try (
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
    )   {
        while (resultSet.next()) {
            String namecustomer = resultSet.getString("name");
            String id = resultSet.getString("id");
            String phone = resultSet.getString("phone");
            model.addRow(new Object[]{namecustomer, id, phone});
        }
        } catch (SQLException e) {
        e.printStackTrace();
        }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        Color mainColor = new Color(51, 153, 255);
        KGradientPanel totalRevenuepanel = new KGradientPanel();
        totalRevenuepanel.setPreferredSize(new Dimension(250,50));
        totalRevenuepanel.setBackground(Color.WHITE);
        totalRevenuepanel.setkStartColor(mainColor);
        totalRevenuepanel.setkEndColor(Color.white);
        
        JTextField searchField = new JTextField(10);
        totalRevenuepanel.add(searchField);
        
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            System.out.println(searchText);
        if (!searchText.isEmpty()) {
           
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
                table.setRowSorter(sorter);
                int dateColumnIndex = model.findColumn("ID");
                RowFilter<DefaultTableModel, Integer> rowFilter = RowFilter.regexFilter(searchText, dateColumnIndex);
                sorter.setRowFilter(rowFilter);
        } else {
            TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
            if (sorter != null) {
                sorter.setRowFilter(null);
            }
        }
        });
        totalRevenuepanel.add(searchButton);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(totalRevenuepanel, BorderLayout.SOUTH);
        jScrollPane1.setViewportView(panel);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        flatMenuUI1 = new com.formdev.flatlaf.ui.FlatMenuUI();
        jPanel1 = new javax.swing.JPanel();
        Menu = new com.k33ptoo.components.KGradientPanel();
        btCustomer = new com.k33ptoo.components.KButton();
        btLaptop = new com.k33ptoo.components.KButton();
        btPhone = new com.k33ptoo.components.KButton();
        btDaily = new com.k33ptoo.components.KButton();
        logOut = new javax.swing.JLabel();
        kGradientPanel1 = new com.k33ptoo.components.KGradientPanel();
        settingbt = new javax.swing.JLabel();
        setting = new com.k33ptoo.components.KGradientPanel();
        add = new javax.swing.JLabel();
        closelb = new javax.swing.JLabel();
        deletelb = new javax.swing.JLabel();
        updatelb = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        typetxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Menu.setBackground(new java.awt.Color(255, 255, 255));
        Menu.setkBorderRadius(70);
        Menu.setkEndColor(new java.awt.Color(51, 153, 255));
        Menu.setkStartColor(new java.awt.Color(51, 153, 255));
        Menu.setPreferredSize(new java.awt.Dimension(190, 740));

        btCustomer.setText("CUSTOMER INFORMATION");
        btCustomer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btCustomer.setkAllowGradient(false);
        btCustomer.setkBackGroundColor(new java.awt.Color(51, 153, 255));
        btCustomer.setkBorderRadius(20);
        btCustomer.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btCustomer.setkHoverStartColor(new java.awt.Color(0, 0, 0));
        btCustomer.setkSelectedColor(new java.awt.Color(255, 255, 255));
        btCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCustomerActionPerformed(evt);
            }
        });

        btLaptop.setBackground(new java.awt.Color(51, 153, 255));
        btLaptop.setText("LAPTOP");
        btLaptop.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btLaptop.setkAllowGradient(false);
        btLaptop.setkBackGroundColor(new java.awt.Color(51, 153, 255));
        btLaptop.setkBorderRadius(20);
        btLaptop.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btLaptop.setkHoverStartColor(new java.awt.Color(0, 0, 0));
        btLaptop.setkSelectedColor(new java.awt.Color(255, 255, 255));
        btLaptop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btLaptopMouseClicked(evt);
            }
        });
        btLaptop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLaptopActionPerformed(evt);
            }
        });

        btPhone.setText("SMARTPHONE");
        btPhone.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btPhone.setkAllowGradient(false);
        btPhone.setkBackGroundColor(new java.awt.Color(51, 153, 255));
        btPhone.setkBorderRadius(20);
        btPhone.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btPhone.setkHoverStartColor(new java.awt.Color(0, 0, 0));
        btPhone.setkSelectedColor(new java.awt.Color(255, 255, 255));
        btPhone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btPhoneMouseClicked(evt);
            }
        });

        btDaily.setText("DAILY STATISTICS");
        btDaily.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btDaily.setkAllowGradient(false);
        btDaily.setkBackGroundColor(new java.awt.Color(51, 153, 255));
        btDaily.setkBorderRadius(20);
        btDaily.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btDaily.setkHoverStartColor(new java.awt.Color(0, 0, 0));
        btDaily.setkSelectedColor(new java.awt.Color(255, 255, 255));
        btDaily.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btDailyMouseClicked(evt);
            }
        });
        btDaily.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDailyActionPerformed(evt);
            }
        });

        logOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logout.png"))); // NOI18N
        logOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout MenuLayout = new javax.swing.GroupLayout(Menu);
        Menu.setLayout(MenuLayout);
        MenuLayout.setHorizontalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuLayout.createSequentialGroup()
                        .addGroup(MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btCustomer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btDaily, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btLaptop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuLayout.createSequentialGroup()
                        .addComponent(logOut)
                        .addGap(82, 82, 82))))
        );
        MenuLayout.setVerticalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(btLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btDaily, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
                .addComponent(logOut)
                .addGap(50, 50, 50))
        );

        jPanel1.add(Menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 0, 220, 720));

        kGradientPanel1.setkEndColor(new java.awt.Color(51, 153, 255));
        kGradientPanel1.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        settingbt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/settings (2).png"))); // NOI18N
        settingbt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingbtMouseClicked(evt);
            }
        });
        kGradientPanel1.add(settingbt, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 10, 40, 40));

        setting.setkBorderRadius(0);
        setting.setkEndColor(new java.awt.Color(51, 153, 255));
        setting.setkStartColor(new java.awt.Color(51, 153, 255));
        setting.setkTransparentControls(false);

        add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/plus.png"))); // NOI18N
        add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addMouseClicked(evt);
            }
        });

        closelb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/next.png"))); // NOI18N
        closelb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closelbMouseClicked(evt);
            }
        });

        deletelb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/minus.png"))); // NOI18N
        deletelb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletelbMouseClicked(evt);
            }
        });

        updatelb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pen.png"))); // NOI18N
        updatelb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updatelbMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout settingLayout = new javax.swing.GroupLayout(setting);
        setting.setLayout(settingLayout);
        settingLayout.setHorizontalGroup(
            settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(closelb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updatelb, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deletelb, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(add)
                .addContainerGap())
        );
        settingLayout.setVerticalGroup(
            settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deletelb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingLayout.createSequentialGroup()
                        .addGap(0, 7, Short.MAX_VALUE)
                        .addGroup(settingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(updatelb)
                            .addComponent(closelb, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)))
                .addContainerGap())
        );

        kGradientPanel1.add(setting, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 0, 0, 60));

        id.setVisible(false);
        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });
        kGradientPanel1.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        typetxt.setVisible(false);
        kGradientPanel1.add(typetxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        jPanel1.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 1060, 60));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1058, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 658, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel2);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 1060, 660));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCustomerActionPerformed
        displayUserTable();
    }//GEN-LAST:event_btCustomerActionPerformed

    private void btLaptopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLaptopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btLaptopActionPerformed

    private void btLaptopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btLaptopMouseClicked
        displayLaptop();
        typetxt.setText(btLaptop.getText());
        
    }//GEN-LAST:event_btLaptopMouseClicked

    private void btPhoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btPhoneMouseClicked
        displayPhone();
        typetxt.setText(btPhone.getText());
    }//GEN-LAST:event_btPhoneMouseClicked

    private void btDailyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDailyActionPerformed
        displayDailyTable();
    }//GEN-LAST:event_btDailyActionPerformed

    private void btDailyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btDailyMouseClicked
    }//GEN-LAST:event_btDailyMouseClicked

    private void settingbtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingbtMouseClicked
        openSettingBar();
    }//GEN-LAST:event_settingbtMouseClicked

    private void closelbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closelbMouseClicked
        closeSettingBar();
    }//GEN-LAST:event_closelbMouseClicked

    private void addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addMouseClicked
        AddProduct addProduct = new AddProduct();
        addProduct.setVisible(true);
    }//GEN-LAST:event_addMouseClicked

    private void logOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutMouseClicked

        Login lg = new Login();
        lg.setVisible(true);
        dispose();
    }//GEN-LAST:event_logOutMouseClicked

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
       
    }//GEN-LAST:event_idActionPerformed

    private void deletelbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletelbMouseClicked
        String types = typetxt.getText();
        if(types.equals("LAPTOP")){
            Laptop laptops = new Laptop();
            final int ids = Integer.valueOf(id.getText());
            laptops.setId(ids);
            LaptopManager.delete(laptops);
            displayLaptop();
        }else if(types.equals("SMARTPHONE")){
            Phone phones = new Phone();
            final int ids = Integer.valueOf(id.getText());
            phones.setId(ids);
            PhoneManager.delete(phones);
            displayPhone();
        }
    }//GEN-LAST:event_deletelbMouseClicked

    private void updatelbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updatelbMouseClicked
        UpdateProduct update = new UpdateProduct(id.getText(),typetxt.getText());
        String types = typetxt.getText();
        update.setVisible(true);
    }//GEN-LAST:event_updatelbMouseClicked

    public static void main(String args[]) { 
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        FlatMacLightLaf.setup();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Home().setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.k33ptoo.components.KGradientPanel Menu;
    private javax.swing.JLabel add;
    private com.k33ptoo.components.KButton btCustomer;
    private com.k33ptoo.components.KButton btDaily;
    private com.k33ptoo.components.KButton btLaptop;
    private com.k33ptoo.components.KButton btPhone;
    private javax.swing.JLabel closelb;
    private javax.swing.JLabel deletelb;
    private com.formdev.flatlaf.ui.FlatMenuUI flatMenuUI1;
    private javax.swing.JTextField id;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.k33ptoo.components.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel logOut;
    private com.k33ptoo.components.KGradientPanel setting;
    private javax.swing.JLabel settingbt;
    private javax.swing.JTextField typetxt;
    private javax.swing.JLabel updatelb;
    // End of variables declaration//GEN-END:variables


}

