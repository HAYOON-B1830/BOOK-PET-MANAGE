package pet_book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class PetApp {

    public JFrame frame;
    private JLabel LabelName2;
    private JLabel LabelPoint2;
    private int Energy;
    private JButton BtnPet;
    private String imagePath = "/images/dog01_s.png"; 

    /*앱 구동*/
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PetApp window = new PetApp();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*앱 생성*/
    public PetApp() {
        initialize();
        LoadUserData();
    }

    /*SQL 유저 데이터 연동*/
    private void LoadUserData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/petbook_db";
            String username = "root";
            String password = "0000";

            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT member_name, point, pet_energy FROM member_table WHERE member_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "test1234");

            ResultSet resultSet = preparedStatement.executeQuery();

            int Energy = 0;

            if (resultSet.next()) {
                String memberName = resultSet.getString("member_name");
                int point = resultSet.getInt("point");
                Energy = resultSet.getInt("pet_energy");

                LabelName2.setText(memberName);
                LabelPoint2.setText(String.valueOf(point));
            }

            if (BtnPet != null) {
                if (Energy >= 1000) {
                    imagePath = "/images/dog03_r.png";
                } else if (Energy >= 300) {
                    imagePath = "/images/dog02_r.png";
                } else {
                    imagePath = "/images/dog01_r.png";
                }
            }

            if (BtnPet != null) {
                BtnPet.setIcon(new ImageIcon(PetApp.class.getResource(imagePath)));
            }

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /*GUI 구성*/
    private void initialize() {
        //화질 저하 방지 코드
        System.setProperty("sun.java2d.uiScale", "1.0");

        frame = new JFrame();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(PetApp.class.getResource("/images/icon.png")));
        frame.setTitle("펫 상태 확인");
        frame.setBounds(100, 100, 800, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);

        JPanel PanelAll = new JPanel();
        PanelAll.setBounds(0, 0, 786, 463);
        frame.getContentPane().add(PanelAll);
        PanelAll.setLayout(null);

        JButton BtnHome = new JButton("Home");
        BtnHome.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        BtnHome.setToolTipText("누르면 홈으로 돌아갑니다");
        BtnHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 현재 창을 숨김
                frame.setVisible(false);
                // Home 화면을 띄우기 위한 코드
                Home.createAndShowGUI();
            }
        });

        BtnHome.setBounds(650, 10, 124, 44);
        PanelAll.add(BtnHome);

        String name = "이름";

        JPanel Panelpet = new JPanel();
        Panelpet.setToolTipText("현재 펫 이미지입니다");
        Panelpet.setBounds(45, 22, 255, 255);
        PanelAll.add(Panelpet);
        Panelpet.setLayout(null);

        BtnPet = new JButton("");
        BtnPet.setToolTipText("현재 펫의 모습입니다");
        BtnPet.setBounds(0, 0, 255, 255);
        Panelpet.add(BtnPet);
        BtnPet.setBackground(new Color(0, 0, 0));
        BtnPet.setIcon(new ImageIcon(PetApp.class.getResource(imagePath)));

        BtnPet.setContentAreaFilled(false);
        BtnPet.setFocusPainted(false);

        JPanel PanelBaby = new JPanel();
        PanelBaby.setBounds(45, 300, 142, 139);
        PanelAll.add(PanelBaby);
        PanelBaby.setLayout(null);

        JButton Btnbaby = new JButton("");
        Btnbaby.setToolTipText("유아");
        Btnbaby.setBounds(0, 0, 142, 139);
        PanelBaby.add(Btnbaby);
        Btnbaby.setBackground(new Color(0, 0, 0));
        Btnbaby.setIcon(new ImageIcon(PetApp.class.getResource("/images/dog01_s.png")));
        Btnbaby.setContentAreaFilled(false);
        Btnbaby.setFocusPainted(false);

        JPanel PanelAdult = new JPanel();
        PanelAdult.setBounds(604, 300, 142, 139);
        PanelAll.add(PanelAdult);
        PanelAdult.setLayout(null);

        JButton BtnAdult = new JButton("");
        BtnAdult.setToolTipText("성체");
        BtnAdult.setBounds(0, 0, 142, 139);
        PanelAdult.add(BtnAdult);
        BtnAdult.setIcon(new ImageIcon(PetApp.class.getResource("/images/dog03_s.png")));
        BtnAdult.setFocusPainted(false);
        BtnAdult.setContentAreaFilled(false);
        BtnAdult.setBackground(Color.BLACK);

        JButton BtnArr1 = new JButton("");
        BtnArr1.setIcon(new ImageIcon(PetApp.class.getResource("/images/Arrow_r.png")));
        BtnArr1.setBounds(188, 338, 150, 70);
        PanelAll.add(BtnArr1);
        BtnArr1.setContentAreaFilled(false);
        BtnArr1.setBorderPainted(false);
        BtnArr1.setFocusPainted(false);

        JLabel LabelName = new JLabel("이름");
        LabelName.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
        LabelName.setBounds(370, 89, 91, 85);
        PanelAll.add(LabelName);

        JLabel LabelPoint = new JLabel("잔여 포인트");
        LabelPoint.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
        LabelPoint.setBounds(370, 206, 180, 32);
        PanelAll.add(LabelPoint);

        LabelName2 = new JLabel("홍길동");
        LabelName2.setToolTipText("당신의 이름");
        LabelName2.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
        LabelName2.setBounds(555, 115, 231, 32);
        PanelAll.add(LabelName2);

        LabelPoint2 = new JLabel("2023");
        LabelPoint2.setToolTipText("현재 포인트");
        LabelPoint2.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
        LabelPoint2.setBounds(562, 206, 224, 32);
        PanelAll.add(LabelPoint2);

        JPanel PanelYouth = new JPanel();
        PanelYouth.setBounds(333, 300, 142, 139);
        PanelAll.add(PanelYouth);
        PanelYouth.setLayout(null);

        JButton btn_pet2 = new JButton("");
        btn_pet2.setToolTipText("청년");
        btn_pet2.setBounds(0, 0, 142, 139);
        PanelYouth.add(btn_pet2);
        btn_pet2.setBackground(new Color(0, 0, 0));
        btn_pet2.setIcon(new ImageIcon(PetApp.class.getResource("/images/dog02_s.png")));
        btn_pet2.setContentAreaFilled(false);
        btn_pet2.setFocusPainted(false);

        JButton BtnArr2 = new JButton("");
        BtnArr2.setIcon(new ImageIcon(PetApp.class.getResource("/images/Arrow_r.png")));
        BtnArr2.setFocusPainted(false);
        BtnArr2.setContentAreaFilled(false);
        BtnArr2.setBorderPainted(false);
        BtnArr2.setBounds(490, 338, 102, 70);
        PanelAll.add(BtnArr2);

        JSeparator Separator = new JSeparator();
        Separator.setBounds(357, 158, 371, 2);
        PanelAll.add(Separator);

        JSeparator Separator2 = new JSeparator();
        Separator2.setBounds(357, 248, 371, 10);
        PanelAll.add(Separator2);
    }
}
