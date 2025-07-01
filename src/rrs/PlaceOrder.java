/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package rrs;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import dao.PlaceDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;
import model.Place;

/**
 *
 * @author Cairelle
 */
public class PlaceOrder extends javax.swing.JFrame {

    public int placeId = 1;
    public int contact = 0;
    public int numberOfGuuests = 0;
    public int time = 0;
    public String emailPattern = "^[a-zA0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$";
    public String contactPattern = "^[0-9]*$";
    public String userEmail;
    public String Category;
    private int id;
    private Object email;
    private Object numberOfGuests;
    private Object Name;
    private Object TimeName;
    private Object category;
    private Object appetizer;
    private Object mainCo;
    private Object dessert;
    private Vector<?> rowData;
    private Object createdby;
    //private Object selectedDate = "";

    /**
     * Creates new form video9
     */
    public PlaceOrder() {
        initComponents();
        txtCustomerName.setEditable(true);
        txtCustomerEmail.setEditable(true);
        jComboBox1.setEditable(true);
        jComboBox2.setEditable(true);
        jComboBox3.setEditable(true);
        jComboBox4.setEditable(true);
        txtCustomerContact.setEditable(true);
        jSpinner1.setEnabled(true);
        txtTimeName.setEditable(true);
        jDateChooser1.setMinSelectableDate(new Date()); 
        // SimpleDateFormat.setEnabled(false);
        // BttnCreate.setEnabled(true);
        JFormattedTextField tf = ((JSpinner.DefaultEditor) jSpinner1.getEditor()).getTextField();
        tf.setEnabled(true);
        userEmail = txtCustomerEmail.getText();
        //displayPlaces();

    }

    private PlaceOrder(Object createdby) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void displayReservations() {
        List<Place> places = PlaceDao.getAllPlaces(); // Fetch all places from DAO
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0); // Clear existing rows

        // Populate jTable2 with fetched places
        for (Place place : places) {
            Object[] row = {
                place.getId(),
                place.getName(),
                place.getEmail(),
                place.getContact(),
                place.getDate(),
                place.getTime(),
                place.getNumberOfGuuests(),
                place.getCategory(),
                //  place.getCreatedby(),
                place.getAppetizer(),
                place.getMainCo(),
                place.getDessert(),};
            model.addRow(row);
        }
    }

    private void displayCurrentReservation() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0); // Clear existing rows

        // Get current reservation details
        Object[] currentReservation = new Object[]{
            null, // ID, leave it null as it is not yet saved
            txtCustomerName.getText(),
            txtCustomerEmail.getText(),
            txtCustomerContact.getText(),
            jDateChooser1.getDate() != null ? new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser1.getDate()) : "",
            txtTimeName.getText(),
            jSpinner1.getValue(),
            jComboBox1.getSelectedItem(),
            jComboBox4.getSelectedItem(),
            jComboBox2.getSelectedItem(),
            jComboBox3.getSelectedItem()
        };

        // Add the current reservation to the table
        model.addRow(currentReservation);
    }

    private void clearFields() {
        txtCustomerName.setText("");
        txtCustomerEmail.setText("");
        txtCustomerContact.setText("");
        jDateChooser1.setDate(null);
        txtTimeName.setText("");
        jSpinner1.setValue(0); // Reset number of guests spinner to default
        jComboBox1.setSelectedIndex(0); // Reset category combo box to default
        jComboBox4.setSelectedIndex(0); // Reset appetizer combo box to default
        jComboBox2.setSelectedIndex(0); // Reset main course combo box to default
        jComboBox3.setSelectedIndex(0); // Reset dessert combo box to default
    }

    private boolean validateFields() {
    String name = txtCustomerName.getText();
    String email = txtCustomerEmail.getText();
    String contact = txtCustomerContact.getText();
    String time = txtTimeName.getText();
    Object selectedOption1 = jComboBox1.getSelectedItem();
    Object selectedOption2 = jComboBox2.getSelectedItem();
    Object selectedOption3 = jComboBox3.getSelectedItem();
    Object selectedOption4 = jComboBox4.getSelectedItem();
    int numberOfGuests = (Integer) jSpinner1.getValue();

    // Validate name, email, contact, time fields
    if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || time.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields");
        return false;
    }

    // Validate email format
    if (!email.matches(".+@gmail\\.com")) {
        JOptionPane.showMessageDialog(this, "Please enter a valid Gmail address.");
        return false;
    }

    // Validate contact number length
    if (contact.length() != 11 || !contact.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, "Please enter a valid 11-digit contact number.");
        return false;
    }
    
    if (numberOfGuests < 1 || numberOfGuests > 30) {
        JOptionPane.showMessageDialog(this, "Please enter the number of guests between 1 and 30.");
        return false;
    }
    
     if (!isValidTimeFormat(time)) {
        JOptionPane.showMessageDialog(this, "Please enter the time in the format HH:MM AM/PM.");
        return false;
    }
     
      if (selectedOption1 == null || selectedOption2 == null || selectedOption3 == null || selectedOption4 == null) {
        JOptionPane.showMessageDialog(this, "Please choose an option from all Categories.");
        return false;
    }

    // Validate date
    Date selectedDate = jDateChooser1.getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please choose a date.");
            return false;
        }

        // Check if the selected date is in the past
        Date currentDate = new Date();
        if (selectedDate.before(currentDate)) {
            JOptionPane.showMessageDialog(this, "The selected date cannot be in the past.");
            return false;
        }
        
    boolean isAnyOptionSelected = selectedOption1.toString().equals("Private") || selectedOption1.toString().equals("Outdoor") || selectedOption1.toString().equals("Indoor") ||
                                  selectedOption2.toString().equals("Private") || selectedOption2.toString().equals("Outdoor") || selectedOption2.toString().equals("Indoor") ||
                                  selectedOption3.toString().equals("Private") || selectedOption3.toString().equals("Outdoor") || selectedOption3.toString().equals("Indoor") ||
                                  selectedOption4.toString().equals("Private") || selectedOption4.toString().equals("Outdoor") || selectedOption4.toString().equals("Indoor");
    if (!isAnyOptionSelected) {
        JOptionPane.showMessageDialog(this, "Please choose at least one option from Private, Outdoor, or Indoor.");
        return false;
    }

    return true;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel34 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtTimeName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCustomerEmail = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        txtCustomerContact = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnGenerate = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bgbgbg.png"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back_1.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 20, 40, 40));

        jLabel2.setFont(new java.awt.Font("Perpetua", 1, 16)); // NOI18N
        jLabel2.setText("Categories:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 210, 100, -1));

        txtTimeName.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        txtTimeName.setText("Hour:Min AM/PM");
        txtTimeName.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(170, 154, 46)));
        txtTimeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimeNameActionPerformed(evt);
            }
        });
        txtTimeName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimeNameKeyReleased(evt);
            }
        });
        getContentPane().add(txtTimeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 240, 20));

        jLabel4.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel4.setText("Email:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, -1));

        txtCustomerEmail.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        txtCustomerEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(170, 154, 46)));
        txtCustomerEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustomerEmailActionPerformed(evt);
            }
        });
        txtCustomerEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCustomerEmailKeyReleased(evt);
            }
        });
        getContentPane().add(txtCustomerEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 240, 20));

        jComboBox1.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Private", "Indoor", "Outdoor" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(170, 154, 46)));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 210, 200, -1));

        jLabel7.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel7.setText("Name:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, -1, -1));

        txtCustomerName.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        txtCustomerName.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(171, 156, 45)));
        txtCustomerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustomerNameActionPerformed(evt);
            }
        });
        txtCustomerName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCustomerNameKeyReleased(evt);
            }
        });
        getContentPane().add(txtCustomerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 240, 20));

        jLabel8.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel8.setText("Contact:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, -1));

        jSpinner1.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jSpinner1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(170, 154, 46)));
        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });
        getContentPane().add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 160, -1));

        jLabel9.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel9.setText("Number of Guest(s)");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, -1, 20));

        txtCustomerContact.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        txtCustomerContact.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(170, 154, 46)));
        txtCustomerContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustomerContactActionPerformed(evt);
            }
        });
        txtCustomerContact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCustomerContactKeyReleased(evt);
            }
        });
        getContentPane().add(txtCustomerContact, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 240, 20));

        jLabel10.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel10.setText("Time:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 60, -1));

        jLabel11.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel11.setText("Date:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, -1, 20));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Email", "Contact", "Date", "Time", "No. of Guest(s)", "Category", "Appetizer", "Main Course", "Dessert"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, 1150, 160));

        jLabel12.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel12.setText("Customer Details");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 310, 70));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/linte1.png"))); // NOI18N
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, 320, 120));

        btnGenerate.setBackground(new java.awt.Color(219, 200, 142));
        btnGenerate.setFont(new java.awt.Font("Perpetua", 1, 16)); // NOI18N
        btnGenerate.setText("Create & Generate PDF Copy");
        btnGenerate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(186, 169, 61), null, null));
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });
        getContentPane().add(btnGenerate, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 660, 270, 40));

        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });
        jDateChooser1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooser1KeyReleased(evt);
            }
        });
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 240, -1));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/typeofdining and appetisir.png"))); // NOI18N
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, -1, 140));

        jLabel3.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel3.setText("Outdoor");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, -1, -1));

        jLabel16.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel16.setText("Private");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 180, -1, -1));

        jLabel17.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel17.setText("Indoor");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 180, -1, 20));

        jComboBox2.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---", "Adobo", "Kare - Kare", "Sinigang" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 450, 200, -1));

        jComboBox3.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---", "Halo - Halo", "Leche Flan", "Maja Blanca" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 450, 200, -1));

        jComboBox4.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---", "Turon", "Garlic Bread", "Calamares" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 210, 200, -1));

        jLabel6.setFont(new java.awt.Font("Perpetua", 1, 16)); // NOI18N
        jLabel6.setText("Categories:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 450, 100, -1));

        jLabel13.setFont(new java.awt.Font("Perpetua", 1, 16)); // NOI18N
        jLabel13.setText("Categories:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 450, 100, -1));

        jLabel18.setFont(new java.awt.Font("Perpetua", 1, 16)); // NOI18N
        jLabel18.setText("Categories:");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 210, 100, -1));

        jLabel19.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel19.setText("Turon");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 180, -1, -1));

        jLabel20.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel20.setText("Garlic Bread");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 180, -1, -1));

        jLabel21.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel21.setText("Calamares");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 180, -1, -1));

        jLabel22.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel22.setText("Adobo");
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, -1, -1));

        jLabel23.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel23.setText("Kare - Kare");
        getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 420, -1, -1));

        jLabel24.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel24.setText("Sinigang");
        getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 420, -1, -1));

        jLabel25.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel25.setText("Halo - Halo");
        getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 420, -1, -1));

        jLabel26.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel26.setText("Leche Flan");
        getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 420, -1, -1));

        jLabel27.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel27.setText("Maja Blanca");
        getContentPane().add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 420, -1, -1));

        jLabel28.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel28.setText("Type of Dining");
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, -1));

        jLabel29.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel29.setText("Appetizer");
        getContentPane().add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 20, -1, 20));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/maincourse and disirt.png"))); // NOI18N
        getContentPane().add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 280, -1, 150));

        jLabel31.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel31.setText("Main Course");
        getContentPane().add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 260, 100, 20));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-130, 150, -1, -1));

        jLabel32.setFont(new java.awt.Font("Perpetua", 1, 14)); // NOI18N
        jLabel32.setText("Dessert");
        getContentPane().add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 260, -1, -1));

        jLabel1.setFont(new java.awt.Font("Perpetua", 1, 20)); // NOI18N
        jLabel1.setText("View Reservation Details");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 450, 280, 30));

        jLabel33.setFont(new java.awt.Font("Perpetua", 3, 18)); // NOI18N
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/linte2.png"))); // NOI18N
        getContentPane().add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, -1, 50));

        jLabel35.setFont(new java.awt.Font("Perpetua", 0, 12)); // NOI18N
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bgbgbg.png"))); // NOI18N
        getContentPane().add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, -1, 780));

        jLabel36.setFont(new java.awt.Font("Perpetua", 3, 18)); // NOI18N
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/linte2.png"))); // NOI18N
        getContentPane().add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, -1, 50));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        new Home(userEmail).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtTimeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimeNameActionPerformed
        // TODO add your handling code here: 

    }//GEN-LAST:event_txtTimeNameActionPerformed

    private void txtCustomerEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustomerEmailActionPerformed
        // TODO add your handling code here: 

    }//GEN-LAST:event_txtCustomerEmailActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        displayCurrentReservation();
// again no category raw sabi ni ruth in case 1:12:20 will be continuing at 1:13:20

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txtCustomerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustomerNameActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtCustomerNameActionPerformed

    private void txtCustomerContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustomerContactActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtCustomerContactActionPerformed

    private boolean isValidTimeFormat(String time) {
        // Define the expected time format
        String timePattern = "hh:mm AM/PM";
        Pattern pattern = Pattern.compile("^\\d{1,2}:\\d{2} [AP]M$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed
        // TODO add your handling code here:
        if (!validateFields()) {
        return; // If fields are not valid, return without proceeding further
    }
        String name = txtCustomerName.getText();
        String category = jComboBox1.getSelectedItem().toString();
        String appetizer = jComboBox4.getSelectedItem().toString();
        String mainCo = jComboBox2.getSelectedItem().toString();
        String dessert = jComboBox3.getSelectedItem().toString();
        String email = txtCustomerEmail.getText();
        String contact = txtCustomerContact.getText();
        String time = txtTimeName.getText();

        //for date
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date selectedDate = jDateChooser1.getDate();
        String selectedDateString = (selectedDate != null) ? dFormat.format(selectedDate) : "";
        Date date = new Date();
        String todaydate = dFormat.format(date);

        String createdBy = userEmail;
        Place place = new Place();
        place.setId(placeId);
        place.setName(txtCustomerName.getText());
        place.setEmail(txtCustomerEmail.getText());
        place.setContact(txtCustomerContact.getText());
        place.setDate(selectedDateString);
        int numberOfGuuests = (Integer) jSpinner1.getValue();
        place.setNumberOfGuuests((Integer) jSpinner1.getValue());
        place.setCategory(jComboBox1.getSelectedItem().toString());
        place.setAppetizer(jComboBox4.getSelectedItem().toString());
        place.setMainCo(jComboBox2.getSelectedItem().toString());
        place.setDessert(jComboBox3.getSelectedItem().toString());
        place.setCreatedby(createdBy);
        place.setTime(time);
        PlaceDao.save(place);

        //reset table
        txtCustomerName.setText("");
        txtCustomerEmail.setText("");
        txtCustomerContact.setText("");
        jDateChooser1.setDate(null);
        txtTimeName.setText("");
        jSpinner1.setValue(0);
        jComboBox1.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        Object[] rowData = {name, email, contact, selectedDateString, time, numberOfGuuests, category, appetizer, mainCo, dessert};
        model.addRow(rowData);

        //for creating document
        String path = "C:\\Users\\Mary Ruth Batac\\Documents\\NetBeansProjects\\";
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path + +placeId + ".pdf"));
            doc.open();
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.BLACK);
            Font contentFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
           /* Image logo = Image.getInstance("logo.png");
            logo.scaleAbsolute(100, 100);
            logo.setAlignment(Element.ALIGN_CENTER);
            doc.add(logo);*/
            // Title
            Paragraph title = new Paragraph("Pamana Hiraya's Kitchen", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);

            doc.add(new Paragraph("\nSUMMARY DETAILS\n"));
            

            // Table for data
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            Font attributeFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
            Font valueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
            String[] attributes = {"Name:", "Email:", "Contact:", "Date:", "Time:", "No. of Guests:", "Category:", "Appetizer:", "Main Course:", "Dessert:"};
            String[] values = {name, email, contact, selectedDateString, time, String.valueOf(numberOfGuuests), category, appetizer, mainCo, dessert};

            // Add data to table
            for (int i = 0; i < attributes.length; i++) {
                PdfPCell attributeCell = new PdfPCell(new Phrase(attributes[i], contentFont));
                PdfPCell valueCell = new PdfPCell(new Phrase(values[i], contentFont));

                table.addCell(attributeCell);
                table.addCell(valueCell);
            }

            doc.add(table);
            doc.add(Chunk.NEWLINE);
            Paragraph closingMsg = new Paragraph("Thank you, Please visit Again.", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLDITALIC, BaseColor.GREEN));
            closingMsg.setAlignment(Element.ALIGN_CENTER);
            doc.add(closingMsg);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        try {
            doc.close();
            // setVisible(false);
            // new PlaceOrder(createdby).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGenerateActionPerformed

    private void txtCustomerNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustomerNameKeyReleased
        // TODO add your handling code here:
        displayCurrentReservation();

    }//GEN-LAST:event_txtCustomerNameKeyReleased

    private void txtCustomerEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustomerEmailKeyReleased
        // TODO add your handling code here:
        displayCurrentReservation();

    }//GEN-LAST:event_txtCustomerEmailKeyReleased

    private void txtCustomerContactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustomerContactKeyReleased
        // TODO add your handling code here:
        displayCurrentReservation();
    }//GEN-LAST:event_txtCustomerContactKeyReleased

    private void txtTimeNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimeNameKeyReleased
        // TODO add your handling code here:
        displayCurrentReservation();
    }//GEN-LAST:event_txtTimeNameKeyReleased

    private void jDateChooser1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooser1KeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jDateChooser1KeyReleased

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        displayCurrentReservation();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        displayCurrentReservation();
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
        displayCurrentReservation();
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        // TODO add your handling code here
        displayCurrentReservation();
    }//GEN-LAST:event_jSpinner1StateChanged

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jDateChooser1PropertyChange

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(PlaceOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlaceOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlaceOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlaceOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PlaceOrder().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerate;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtCustomerContact;
    private javax.swing.JTextField txtCustomerEmail;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtTimeName;
    // End of variables declaration//GEN-END:variables

}
