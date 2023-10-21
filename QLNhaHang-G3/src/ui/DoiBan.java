package ui;

import controllers.QLBan;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.SwingConstants.BOTTOM;
import static javax.swing.SwingConstants.CENTER;
import model.Ban;
import static ui.DatBan.listButton;
import utils.MsgBox;
import utils.XInit;

public class DoiBan extends JFrame {

    public DoiBan() {
        doiBan();
//        XInit.init(this);
    }

    public static void main(String[] args) {
        new DoiBan();
    }
    void addButton(JPanel pnl, JLabel lbl) {
        pnl.setBackground(new Color(255, 204, 102));
        int h = 0, w = 0;
        for (int i = 0; i < 20; i++) {
            String txt;
            if (i < 9) {
                txt = "Bàn số 0" + (i + 1);
            } else {
                txt = "Bàn số " + (i + 1);
            }
            listButton.add(new JButton(txt));
            addBtn(listButton.get(i), pnl, lbl);
        }
    }

    void addBtn(JButton btn, JPanel pnl, JLabel lbl) {
//        btn.setSize(50,50);
//        btn.setMargin(new Insets(5, 5, 5, 5));
//        btn.setMaximumSize(new Dimension(65, 70));
//        btn.setMinimumSize(new Dimension(65, 70));
        btn.setBackground(new Color(255, 255, 255));
        btn.setFont(new Font("Lucida Grande", 0, 18));
//        btn.setIcon(new ImageIcon(getClass().getResource("/icon/table-blue-32.png")));
//        btn.setToolTipText("table-blue-32.png");
        quanLyBan(btn, lbl);
        btn.setHorizontalTextPosition(CENTER);
        btn.setVerticalTextPosition(BOTTOM);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                switch (btn.getToolTipText()) {
                    case "table-blue-32.png":
                        giuCho(btn, lbl);
                        break;
                    case "table-organce-32.png":
                        if (MsgBox.confirm(null, "Khách hàng cần đặt món!")) {
                            btn.setIcon(new ImageIcon(getClass().getResource("/icon/table-red-32.png")));
                            btn.setToolTipText("table-red-32.png");
                            openDatMon(btn);
                        } else {
                            if (MsgBox.confirm(null, "Khách hàng hủy đặt bàn!")) {
                                huyCho(btn, lbl);
                            }
                        }
                        break;
                    case "table-red-32.png":
                        if (quanLyBan(btn, lbl)) {
                            if (MsgBox.confirm(null, "Bạn muốn chỉnh sửa hóa đơn cho " + btn.getText())) {
                                openDatMon(btn);
                            }
                        }

                        break;
//                    default:
//                        btn.setIcon(new ImageIcon(getClass().getResource("/icon/table-blue-32.png")));
//                        btn.setToolTipText("table-blue-32.png");
//                        break;
                }
            }

        });

        pnl.add(btn);
    }

    void openDatMon(JButton btn) {
        String ban = btn.getText();
        DatBan.soBan = Integer.parseInt(ban.substring(7));
        new DatMon().setVisible(true);
        dispose();
    }

    boolean quanLyBan(JButton btn, JLabel lbl) {

        String tenBan = btn.getText();
        String soBan = tenBan.substring(7);
        Boolean tinhTrang = false;

//        System.out.println(soBan);

        Ban ban = QLBan.dao.selectById(soBan);
        if (ban == null) {
            btn.setIcon(new ImageIcon(getClass().getResource("/icon/table-blue-32.png")));
            btn.setToolTipText("table-blue-32.png");
//            System.out.println(btn.getToolTipText());
        } else {
            tinhTrang = ban.isTinhTrang();
            if (tinhTrang) {
                btn.setIcon(new ImageIcon(getClass().getResource("/icon/table-red-32.png")));
                btn.setToolTipText("table-red-32.png");
//                System.out.println(btn.getToolTipText());

            } else {

                btn.setIcon(new ImageIcon(getClass().getResource("/icon/table-organce-32.png")));
                btn.setToolTipText("table-organce-32.png");
//                System.out.println(btn.getToolTipText());
            }
        }
        setTinhTrang(lbl);
        return tinhTrang;
    } // Neu tinhTrang false 

    void giuCho(JButton btn, JLabel lbl) {
        Ban entity = new Ban();

        String tenBan = btn.getText();
        int soBan = Integer.parseInt(tenBan.substring(7));
        entity.setMaBan(soBan);
        entity.setMaHoaDon(0);
        entity.setTinhTrang(false);

        QLBan.insert(entity);
        btn.setIcon(new ImageIcon(getClass().getResource("/icon/table-organce-32.png")));
        btn.setToolTipText("table-organce-32.png");
        setTinhTrang(lbl);
    }

    void huyCho(JButton btn, JLabel lbl) {
        String tenBan = btn.getText();
        QLBan.detele(tenBan.substring(7));
        btn.setIcon(new ImageIcon(getClass().getResource("/icon/table-blue-32.png")));
        btn.setToolTipText("table-blue-32.png");
        setTinhTrang(lbl);
    }

    void setTinhTrang(JLabel lbl) {

        int dem = 0;
        for (JButton btn : listButton) {
            if (btn.getToolTipText() != null) {
                if (btn.getToolTipText().equals("table-blue-32.png")) {
                    dem = dem + 1;
                }
            }
        }
        lbl.setText("Hiện Tại Nhà Hàng Còn " + dem + " Bàn Trống");
    }

    void doiBan() {
//        JFrame fr = new JFrame("CHỌN BÀN");
    this.setTitle("Bai1_Lab3");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        JScrollPane scroll = new JScrollPane();
        scroll.setSize(400, 400);
        
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 15));
//        pnl.setLayout(new GridLayout(5, 4));
        pnl.setSize(400, 400);
        JLabel lbl = new JLabel();
        addButton(pnl, lbl);
        pnl.add(lbl);
        setTinhTrang(lbl);
        
        scroll.add(pnl);
       
        this.add(scroll);
        this.setVisible(true);

//        QLBan.detele(String.valueOf(soBan));
        // xoa so ban cu, thay vao so ban moi, lam sao kho mo dat mon, thong tin hoa duoc dua theo bang dat ban show cung
    }
}
