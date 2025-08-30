package SubGUI;

import com.formdev.flatlaf.FlatLightLaf;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import model.CurrencyFormatter;

public class FinancialReport extends javax.swing.JFrame {
    
    private AddNewReport addNewReport;
    
    public FinancialReport(AddNewReport addNewReport) {
        this.addNewReport = addNewReport;
        
        initComponents();
        addNewReport.setVisible(false);
        loadReportData();
        this.setVisible(true);
        
    }
    
    private boolean loadReportData() {
        try {
            
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateTime = dateFormat.format(date);
            
            dateLabel.setText(currentDateTime);
            
            ResultSet resultSet = MySQL.execute("SELECT * FROM bill b "
                    + "INNER JOIN appointment a ON b.appointment_id = a.id "
                    + "INNER JOIN payment_method p ON b.payment_method_id = p.id "
                    + "INNER JOIN service s ON a.service_id = s.id ");
            
            DefaultTableModel model = (DefaultTableModel) repotItemsTable.getModel();
            model.setRowCount(0);
            int rowCount = repotItemsTable.getRowCount();
            double cashPayment = 0;
            double cardPayment = 0;
            double insurancePayment = 0;
            
            while (resultSet.next()) {
                
                String paymentMethod = resultSet.getString("p.method");
                double price = resultSet.getDouble("s.price");
                
                Vector v = new Vector();
                v.add(++rowCount);
                v.add(resultSet.getString("b.id"));
                v.add(CurrencyFormatter.formatCurrency(price));
                v.add(paymentMethod);
                v.add(resultSet.getString("b.date_time"));
                model.addRow(v);
                
                if (paymentMethod.equals("Cash")) {
                    cashPayment += price;
                } else if (paymentMethod.equals("Card")) {
                    cardPayment += price;
                    
                } else if (paymentMethod.equals("Insurance Claim")) {
                    insurancePayment += price;
                }
                
            }
            
            cashPaymentAmountLabel.setText(CurrencyFormatter.formatCurrency(cashPayment));
            cardPaymentAmountLabel.setText(CurrencyFormatter.formatCurrency(cardPayment));
            insurancePaymentLabel.setText(CurrencyFormatter.formatCurrency(insurancePayment));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        repotItemsTable = new javax.swing.JTable();
        totalTextLabel2 = new javax.swing.JLabel();
        totalTextLabel3 = new javax.swing.JLabel();
        totalTextLabel4 = new javax.swing.JLabel();
        discountTextLabel2 = new javax.swing.JLabel();
        insurancePaymentLabel = new javax.swing.JLabel();
        cardPaymentAmountLabel = new javax.swing.JLabel();
        cashPaymentAmountLabel = new javax.swing.JLabel();
        discountTextLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel2.setMaximumSize(new java.awt.Dimension(620, 765));
        jPanel2.setMinimumSize(new java.awt.Dimension(620, 765));

        jLabel2.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Financial Report");

        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/logo-extra-sm.png"))); // NOI18N
        jLabel1.setToolTipText("");

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        repotItemsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No.", "Bill Id", "Amount", "Payment Method", "Date Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        repotItemsTable.setEnabled(false);
        repotItemsTable.getTableHeader().setReorderingAllowed(false);

        // Center-align the first column (index 0)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        repotItemsTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        repotItemsTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        repotItemsTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        jScrollPane1.setViewportView(repotItemsTable);
        if (repotItemsTable.getColumnModel().getColumnCount() > 0) {
            repotItemsTable.getColumnModel().getColumn(0).setMinWidth(50);
            repotItemsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            repotItemsTable.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        totalTextLabel2.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        totalTextLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totalTextLabel2.setText("Financial Summery");

        totalTextLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totalTextLabel3.setText("Cash Payments");

        totalTextLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totalTextLabel4.setText("Card Payments");

        discountTextLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        discountTextLabel2.setText("Insurance Payments");

        insurancePaymentLabel.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        insurancePaymentLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        insurancePaymentLabel.setText(" ");

        cardPaymentAmountLabel.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        cardPaymentAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cardPaymentAmountLabel.setText(" ");

        cashPaymentAmountLabel.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        cashPaymentAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cashPaymentAmountLabel.setText(" ");

        discountTextLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        discountTextLabel.setText("Date");

        dateLabel.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        dateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateLabel.setText("Duahdjhajdh");

        jSeparator3.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(totalTextLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(discountTextLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(totalTextLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(totalTextLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cardPaymentAmountLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                    .addComponent(cashPaymentAmountLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(insurancePaymentLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(discountTextLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(discountTextLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateLabel)
                .addGap(21, 21, 21)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(totalTextLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(totalTextLabel3)
                        .addGap(1, 1, 1)
                        .addComponent(totalTextLabel4)
                        .addGap(2, 2, 2)
                        .addComponent(discountTextLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cashPaymentAmountLabel)
                        .addGap(1, 1, 1)
                        .addComponent(cardPaymentAmountLabel)
                        .addGap(0, 0, 0)
                        .addComponent(insurancePaymentLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FlatLightLaf.registerCustomDefaultsSource("style");
        FlatLightLaf.setup();
        UIManager.put("TextComponent.arc", 4);

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new DiagnosticResultReport().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cardPaymentAmountLabel;
    private javax.swing.JLabel cashPaymentAmountLabel;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JLabel discountTextLabel;
    private javax.swing.JLabel discountTextLabel2;
    private javax.swing.JLabel insurancePaymentLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable repotItemsTable;
    private javax.swing.JLabel totalTextLabel2;
    private javax.swing.JLabel totalTextLabel3;
    private javax.swing.JLabel totalTextLabel4;
    // End of variables declaration//GEN-END:variables
}
