package petPackage;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import bookPackage.UserMain;
import memberPackage.LoginScreen;

import java.awt.Toolkit;
import javax.swing.JScrollPane;

public class ShopApp {
    private JFrame sframe;
    private JLabel Label_N2;
    private JLabel Label_P2;
    private int Point;
    private int Energy;
    private String imagePath = "/images/cat03_r.png"; // 기본 이미지 경로
    private JButton btn_profile;
    private int Price;
    
    
    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ShopApp window = new ShopApp();
                    window.sframe.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ShopApp() {
        initialize();
        LoadUserData(); // 데이터 자동으로 가져오기

        
    }

    
    private void initialize() {
	
		//윈도우 창 설정
		sframe = new JFrame();
		sframe.setIconImage(Toolkit.getDefaultToolkit().getImage(PetApp.class.getResource("/images/icon.png")));
		sframe.setTitle("상점");
		sframe.setBounds(100, 100, 800, 500);
		sframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		sframe.setLocationRelativeTo(null);
		sframe.getContentPane().setLayout(null);
		sframe.setVisible(true);
		sframe.setResizable(false);
		
		JPanel PanelShop = new JPanel();
		PanelShop.setBounds(12, 68, 490, 643);
		sframe.getContentPane().add(PanelShop);
		PanelShop.setLayout(null);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(473, 0, 17, 406);
		PanelShop.add(scrollBar);
		
		//홈버튼
        JButton BtnHome = new JButton("Home");
        BtnHome.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        BtnHome.setToolTipText("누르면 홈으로 돌아갑니다");
        BtnHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sframe.setVisible(false);
                UserMain usermain = new UserMain();
                usermain.setVisible(true);
            }
        });

        
        BtnHome.setBounds(667, 10, 91, 23);
        sframe.getContentPane().add(BtnHome);

        //상품 이미지 표시
		JButton btn_f1 = new JButton("\r\n");
		btn_f1.setToolTipText("싱싱한 당근입니다 맛이 아주 좋죠");
		btn_f1.setIcon(new ImageIcon(ShopApp.class.getResource("/images/food01_r.png")));
		btn_f1.setBounds(23, 10, 179, 144);
		PanelShop.add(btn_f1);
		
		JButton btn_f2 = new JButton("");
		btn_f2.setToolTipText("묵직한 배추입니다 아삭아삭합니다");
		btn_f2.setIcon(new ImageIcon(ShopApp.class.getResource("/images/food02_r.png")));
		btn_f2.setBounds(273, 10, 179, 144);
		PanelShop.add(btn_f2);
		
		JButton btn_f3 = new JButton("");
		btn_f3.setToolTipText("아주 무난한 건식 사료입니다");
		btn_f3.setIcon(new ImageIcon(ShopApp.class.getResource("/images/food03_r.png")));
		btn_f3.setBounds(23, 207, 179, 144);
		PanelShop.add(btn_f3);
		
		JButton btn_f4 = new JButton("");
		btn_f4.setToolTipText("맛있는 습식 사료입니다");
		btn_f4.setIcon(new ImageIcon(ShopApp.class.getResource("/images/food04_r.png")));
		btn_f4.setBounds(273, 207, 179, 144);
		PanelShop.add(btn_f4);
		
		JButton btn_t2 = new JButton("");
		btn_t2.setToolTipText("물고기 낚시대입니다 물고기 모양 봉제 인형이 달려있습니다");
		btn_t2.setIcon(new ImageIcon(ShopApp.class.getResource("/images/toy02_r.png")));
		btn_t2.setBounds(273, 405, 179, 144);
		PanelShop.add(btn_t2);
		
		JButton btn_t1 = new JButton("");
		btn_t1.setToolTipText("부드럽고 폭신한 곰 인형입니다 ");
		btn_t1.setIcon(new ImageIcon(ShopApp.class.getResource("/images/toy01_r.png")));
		btn_t1.setBounds(23, 405, 179, 144);
		PanelShop.add(btn_t1);
		
		
		//상품 가격 표시 및 구입
		JButton btn_price_1 = new JButton("100");
		btn_price_1.setBounds(67, 164, 91, 23);
		PanelShop.add(btn_price_1);
		
		JButton btn_price_2 = new JButton("100");
		btn_price_2.setBounds(318, 164, 91, 23);
		PanelShop.add(btn_price_2);

		JButton btn_price_3 = new JButton("100");
		btn_price_3.setBounds(67, 361, 91, 23);
		PanelShop.add(btn_price_3);
		
		JButton btn_price_4 = new JButton("100");
		btn_price_4.setBounds(318, 361, 91, 23);
		PanelShop.add(btn_price_4);
		
		JButton btn_price_5 = new JButton("100");
		btn_price_5.setBounds(67, 559, 91, 23);
		PanelShop.add(btn_price_5);
		
		JButton btn_price_6 = new JButton("100");
		btn_price_6.setBounds(318, 559, 91, 23);
		PanelShop.add(btn_price_6);
		
		btn_price_1.addActionListener(BuyItem);
		btn_price_2.addActionListener(BuyItem);
		btn_price_3.addActionListener(BuyItem);
		btn_price_4.addActionListener(BuyItem);
		btn_price_5.addActionListener(BuyItem);
		btn_price_6.addActionListener(BuyItem);
		
		
		
		//상점 텍스트들
		JButton BtnPointdata = new JButton("포인트 이력");
		BtnPointdata.setToolTipText("포인트 이력을 보여줍니다");
		BtnPointdata.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
	    BtnPointdata.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	sframe.setVisible(false);
	        	PointApp pointApp = new PointApp();
	        }
	    });
		BtnPointdata.setBounds(526, 10, 101, 23);
		sframe.getContentPane().add(BtnPointdata);
		
		JPanel PanelUser = new JPanel();
		PanelUser.setBounds(514, 47, 260, 406);
		sframe.getContentPane().add(PanelUser);
		PanelUser.setLayout(null);
		
		Label_N2 = new JLabel("홍길동님의");
		Label_N2.setHorizontalAlignment(SwingConstants.CENTER);
		Label_N2.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		Label_N2.setBounds(0, 267, 260, 40);
		PanelUser.add(Label_N2);
		
		JLabel lblNewLabel_1_1 = new JLabel("포인트");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("맑은 고딕", Font.PLAIN, 27));
		lblNewLabel_1_1.setBounds(0, 304, 260, 47);
		PanelUser.add(lblNewLabel_1_1);
		
		Label_P2 = new JLabel("2023");
		Label_P2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Label_P2.setHorizontalAlignment(SwingConstants.CENTER);
		Label_P2.setBounds(46, 350, 181, 58);
		PanelUser.add(Label_P2);
		
		JLabel LabelShop = new JLabel("상점");
		LabelShop.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		LabelShop.setBounds(12, 10, 134, 27);
		sframe.getContentPane().add(LabelShop);
		
		//프로필 이미지
		JButton btn_profile = new JButton("");
		btn_profile.setToolTipText("상점 npc입니다");
		btn_profile.setIcon(new ImageIcon(ShopApp.class.getResource(imagePath)));
		btn_profile.setBounds(12, 10, 236, 247);
		PanelUser.add(btn_profile);
		
		
		//스크롤 관련 코드
		JScrollPane ScrollPane = new JScrollPane(PanelShop);
		ScrollPane.setBounds(12, 47, 490, 406); 
		ScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sframe.getContentPane().add(ScrollPane); 

		// panel 크기 조정
		PanelShop.setPreferredSize(new Dimension(490, 673));
		
		//버튼 설정
		btn_profile.setContentAreaFilled(false);
		btn_profile.setFocusPainted(false); 
		btn_f1.setContentAreaFilled(false);
		btn_f1.setFocusPainted(false); 
		btn_f2.setContentAreaFilled(false);
		btn_f2.setFocusPainted(false); 
		btn_f3.setContentAreaFilled(false);
		btn_f3.setFocusPainted(false); 
		btn_f4.setContentAreaFilled(false);
		btn_f4.setFocusPainted(false); 
		btn_t1.setContentAreaFilled(false);
		btn_t1.setFocusPainted(false); 
		btn_t2.setContentAreaFilled(false);
		btn_t2.setFocusPainted(false); 
    }

       // DB의 데이터를 연동하는 코드들
		 private void LoadUserData() {
			 	String uid = LoginScreen.id.getText();
			 
		        String jdbcUrl = "jdbc:mysql://localhost:3306/merge_db";
		        String username = "root";
		        String password = "0000";
		        try {
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection con = DriverManager.getConnection(jdbcUrl, username, password);

		            String sql = "SELECT ID, POINT, ENERGY FROM members WHERE ID = ?";
		            PreparedStatement ps = con.prepareStatement(sql);

		            ps.setString(1, uid);

		            ResultSet resultSet = ps.executeQuery();

		            if (resultSet.next()) {
		                String userName = resultSet.getString("ID");
		                int myPoint = resultSet.getInt("POINT");
		                int myEnergy = resultSet.getInt("ENERGY");
		                
		                Point = myPoint;
		                Energy = myEnergy;
		                
		                Label_N2.setText(userName + "님의");
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
		 
		 private void UpdateData(int newPoint, int newEnergy) {
			 	String uid = LoginScreen.id.getText();
			    String jdbcUrl = "jdbc:mysql://localhost:3306/merge_db";
			    String username = "root";
			    String password = "0000";

			    try {
			        Class.forName("com.mysql.cj.jdbc.Driver");
			        Connection con = DriverManager.getConnection(jdbcUrl, username, password);

			        String update = "UPDATE members SET POINT = ?, ENERGY = ? WHERE ID = ?";
			        PreparedStatement ps = con.prepareStatement(update);
			        
			        ps.setInt(1, newPoint);
			        ps.setInt(2, newEnergy);
			        String memberIdToUpdate = uid;
			        ps.setString(3, memberIdToUpdate);

			        int rowsUpdated = ps.executeUpdate();

			        ps.close();
			        con.close();
			    } catch (ClassNotFoundException | SQLException e) {
			        e.printStackTrace();
			    }
			    
				
			}
		 
		 private void UpdateData2(int newPrice) {
			    String uid = LoginScreen.id.getText();
			    String jdbcUrl = "jdbc:mysql://localhost:3306/merge_db";
			    String username = "root";
			    String password = "0000";

			    try {
			        Class.forName("com.mysql.cj.jdbc.Driver");
			        Connection con = DriverManager.getConnection(jdbcUrl, username, password);
			        
			        String insert = "INSERT INTO points (DATE, POINT, USER) VALUES (CURDATE(), ?, ?)";
			        PreparedStatement insertStatement = con.prepareStatement(insert);
			        
			        insertStatement.setInt(1, newPrice);
			        insertStatement.setString(2, uid);

			        insertStatement.executeUpdate();

			        insertStatement.close();
			        con.close();
			    } catch (ClassNotFoundException | SQLException e) {
			        e.printStackTrace();
			    }
			}
		 
		 //상품 구매 기능 간략화
		 ActionListener BuyItem = new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        if (Point >= 100) {
			        	Toolkit.getDefaultToolkit().beep();
			            JOptionPane.showMessageDialog(null, "구매 완료!");
			            Price = 100;
			            Point -= 100;
			            Energy += 100;
			            
			            UpdateData(Point, Energy);
			            Label_P2.setText(String.valueOf(Point));
			            UpdateData2(Price);
			        } else {
			            JOptionPane.showMessageDialog(null, "포인트가 부족합니다!");
			        }
			    }
			};

		}