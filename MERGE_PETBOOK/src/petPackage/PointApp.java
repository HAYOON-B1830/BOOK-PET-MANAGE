package petPackage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import bookPackage.UserMain;
import memberPackage.LoginScreen;

import javax.swing.JSeparator;

public class PointApp {

    private JFrame pframe;
    private JLabel Label_N2;
    private JLabel Label_P2;
    private JLabel DataLabel_1; 
    private LineBorder border = new LineBorder(Color.black, 1, true);
    
    /* 앱 구동 */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PointApp window = new PointApp();
                    window.pframe.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* 앱 생성 */
    public PointApp() {
        initialize();
        LoadUserData();
        History();
    }

    //SQL에서 유저 데이터 로드
    private void LoadUserData() {
    	String uid = LoginScreen.id.getText();
        String jdbcUrl = "jdbc:mysql://localhost:3306/merge_db";
        String username = "root";
        String password = "0000";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcUrl, username, password);

            String select = "SELECT ID, POINT FROM members WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(select);

            ps.setString(1, uid);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String retrievedName = resultSet.getString("ID");
                int myPoint = resultSet.getInt("POINT");
                Label_N2.setText(retrievedName + "님의");
                Label_P2.setText(String.valueOf(myPoint));
            } else {
                System.out.println("정보를 찾을 수 없습니다.");
            }

            resultSet.close();
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //SQL에서 포인트 이력 로드
    private void History() {
        String uid = LoginScreen.id.getText();
        String jdbcUrl = "jdbc:mysql://localhost:3306/merge_db";
        String username = "root";
        String password = "0000";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcUrl, username, password);

            String select = "SELECT DATE, POINT, USER FROM points WHERE USER = ? ORDER BY date DESC";

            PreparedStatement ps = con.prepareStatement(select);
            ps.setString(1, uid);

            ResultSet resultSet = ps.executeQuery();

            String pointHistoryText = "<html>";
            int count = 0;
            while (resultSet.next() && count < 8) {
                String date = resultSet.getString("date");
                int point = resultSet.getInt("point");
                String user = resultSet.getString("USER");
                pointHistoryText += date + " | -" + point + " 포인트 |" + user + "<br>";
                DataLabel_1.setBorder(border);
                count++;
            }
            pointHistoryText += "</html>";

            DataLabel_1.setText(pointHistoryText);

            resultSet.close();
            ps.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    
    /* GUI 구성 */
    private void initialize() {

        pframe = new JFrame();
        pframe.setIconImage(Toolkit.getDefaultToolkit().getImage(PetApp.class.getResource("/images/icon.png")));
        pframe.setTitle("포인트 이력");
        pframe.setBounds(100, 100, 800, 500);
        pframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pframe.setLocationRelativeTo(null);
        pframe.getContentPane().setLayout(null);
        pframe.setVisible(true);
        pframe.setResizable(false);

        JButton BtnHome = new JButton("Home");
        BtnHome.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        BtnHome.setToolTipText("누르면 홈으로 돌아갑니다");
        BtnHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 현재 창을 숨김
                pframe.setVisible(false);
                // Home 화면을 띄우기 위한 코드
                UserMain usermain = new UserMain();
                usermain.setVisible(true);
            }
        });

        //포인트 이력 GUI
        BtnHome.setBounds(667, 10, 91, 23);
        pframe.getContentPane().add(BtnHome);

        JLabel LabelPoint = new JLabel("포인트");
        LabelPoint.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        LabelPoint.setBounds(12, 10, 134, 27);
        pframe.getContentPane().add(LabelPoint);

        JPanel PanelData = new JPanel();
        PanelData.setBounds(12, 47, 490, 298);
        pframe.getContentPane().add(PanelData);
        PanelData.setLayout(null);

        DataLabel_1 = new JLabel("N"); 
        DataLabel_1.setVerticalAlignment(SwingConstants.TOP);
        DataLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
        DataLabel_1.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
        DataLabel_1.setBounds(0, 0, 490, 282);
        PanelData.add(DataLabel_1); 
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 404, 490, 2);
        PanelData.add(separator_1);

        JPanel PannelProfile = new JPanel();
        PannelProfile.setBounds(514, 47, 260, 406);
        pframe.getContentPane().add(PannelProfile);
        PannelProfile.setLayout(null);

        Label_N2 = new JLabel("홍길동님의");
        Label_N2.setHorizontalAlignment(SwingConstants.CENTER);
        Label_N2.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
        Label_N2.setBounds(0, 267, 260, 40);
        PannelProfile.add(Label_N2);

        Label_P2 = new JLabel("2023");
        Label_P2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        Label_P2.setHorizontalAlignment(SwingConstants.CENTER);
        Label_P2.setBounds(46, 350, 181, 58);
        PannelProfile.add(Label_P2);

        JButton btn_profile = new JButton("");
        btn_profile.setIcon(new ImageIcon(ShopApp.class.getResource("/images/rabbit03_r.png")));
        btn_profile.setBounds(12, 10, 236, 247);
        PannelProfile.add(btn_profile);

        btn_profile.setContentAreaFilled(false);
        btn_profile.setFocusPainted(false);

        JLabel lblNewLabel_1_1 = new JLabel("포인트");
        lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1.setFont(new Font("맑은 고딕", Font.PLAIN, 27));
        lblNewLabel_1_1.setBounds(0, 304, 260, 47);
        PannelProfile.add(lblNewLabel_1_1);
        
        JLabel LabelMsg = new JLabel("※ 포인트 이력은 최대 8건까지 볼 수 있습니다");
        LabelMsg.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
        LabelMsg.setBounds(12, 355, 490, 25);
        pframe.getContentPane().add(LabelMsg);
        
		JButton BtnShop = new JButton("상점");
		BtnShop.setToolTipText("상점으로 이동합니다");
		BtnShop.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
	    BtnShop.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	pframe.setVisible(false);
	        	ShopApp shopApp = new ShopApp();
	        }
	    });
		BtnShop.setBounds(526, 10, 101, 23);
		pframe.getContentPane().add(BtnShop);

        
        

    }
}
