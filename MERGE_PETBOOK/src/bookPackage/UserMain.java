package bookPackage;

import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import memberPackage.LoginScreen;
import petPackage.PetApp;
import petPackage.ShopApp;


//좌측 패널, 상단 코드 건드림

public class UserMain extends JFrame {
	private JLabel lblNewLabel_2;

	public UserMain() {
		super("사용자 메인화면");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserMain.class.getResource("/images/icon.png")));

		setSize(800, 500);
		setVisible(false); 
		setResizable(false);// 사이즈조정 불가능
		setLocationRelativeTo(null); // 창위치 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JButton storebtn = new JButton("상점");
		storebtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		storebtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	        	setVisible(false);
	        	ShopApp shopApp = new ShopApp();
	        }
	    });
		storebtn.setBounds(482, 31, 135, 40);
		getContentPane().add(storebtn);

		// 로그인 화면으로 돌아가게 하고 싶음
//		prebtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				new BookList();
//			}
//		});

		//////////////////////////////////////////////////////////////////////

//		JLabel petimgLabel = new JLabel("");
//		petimgLabel.setIcon(null);
//		petimgLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
//		petimgLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		petimgLabel.setBounds(479, 82, 282, 263);
//		getContentPane().add(petimgLabel);

		JLabel lblNewLabel_1 = new JLabel("잔여포인트");
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(23, 81, 112, 80);
		getContentPane().add(lblNewLabel_1);

		///////////////////////////////////////////////////

		// bookPanel 패널(대출예약도서, 대출 중인 도서, 독후감 게시판)
		/* 추후 수정 */

		JPanel bookPanel = new JPanel();
		bookPanel.setBounds(23, 160, 444, 275);

		JButton Listbtn = new JButton("도서목록(대출/반납/검색)");
		Listbtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		Listbtn.setBounds(23, 31, 260, 40);
		getContentPane().add(Listbtn);

		Listbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				earnPoints(100);
				new BookList();
				setVisible(false);
			}
		});

		JButton viewbtn = new JButton("내 대출/반납현황");
		viewbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				earnPoints(100);
                new CheckStatus();
				setVisible(false);
			}
		});
		viewbtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		viewbtn.setBounds(295, 31, 172, 40);
		getContentPane().add(viewbtn);

		JButton quitbtn = new JButton("종료");
		quitbtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		quitbtn.setHorizontalAlignment(SwingConstants.CENTER);
		quitbtn.setBounds(629, 31, 135, 40);
		getContentPane().add(quitbtn);

		quitbtn.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        setVisible(false);
		        System.exit(0); // Close the application
		    }
		});
		
		bookPanel.setLayout(null);

		getContentPane().add(bookPanel);
		
		JLabel lblNewLabel = new JLabel("New label");
		
		lblNewLabel.setIcon(new ImageIcon(UserMain.class.getResource("/images/background.png")));
		lblNewLabel.setBounds(0, 0, 444, 275);
		bookPanel.add(lblNewLabel);
		
		JButton petbtn = new JButton("");
		petbtn.setIcon(new ImageIcon(UserMain.class.getResource("/images/profile.png")));
		petbtn.setBounds(492, 115, 269, 264);
		petbtn.setToolTipText("나의 펫 상태를 볼 수 있습니다");
		getContentPane().add(petbtn);
		petbtn.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	setVisible(false);
	        	PetApp petApp = new PetApp();
	        	petApp.frame.setVisible(true);
	        }
	    });
		petbtn.setContentAreaFilled(false);
		petbtn.setFocusPainted(false);
		
		lblNewLabel_2 = new JLabel("1234");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_2.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
        lblNewLabel_2.setBounds(147, 81, 269, 80);
        getContentPane().add(lblNewLabel_2);

		LoadUserData();
		
		JLabel lblNewLabel_3 = new JLabel("펫 상태를 보려면 이미지를 클릭하세요");
		lblNewLabel_3.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(492, 400, 282, 15);
		getContentPane().add(lblNewLabel_3);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(49, 82, 681, 15);
		getContentPane().add(separator);
		
		///////////////////////////////////////////////////

		setVisible(true);
	}
	
	public static void main(String[] args) {
		System.setProperty("sun.java2d.uiScale", "1.0"); //화질 개선 코드
		UserMain um = new UserMain();
		
	}
	
	
	
  //SQL에서 유저 데이터 로드
  private void LoadUserData() {
	  String uid = LoginScreen.id.getText();
      String jdbcUrl = "jdbc:mysql://localhost:3306/merge_db";
      String username = "root";
      String password = "0000";
      try {
          Class.forName("com.mysql.cj.jdbc.Driver");
          Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

          String selectQuery = "SELECT POINT FROM members WHERE ID = ?";
          PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

          preparedStatement.setString(1, uid);

          ResultSet resultSet = preparedStatement.executeQuery();

          if (resultSet.next()) {
              int myPOINT = resultSet.getInt("POINT");
              lblNewLabel_2.setText(String.valueOf(myPOINT));
          } else {
              System.out.println("정보를 찾을 수 없습니다.");
          }

          resultSet.close();
          preparedStatement.close();
          connection.close();
      } catch (ClassNotFoundException | SQLException e) {
          e.printStackTrace();
      }
  }
  
  
  //포인트 적립 기능 구현
  private void earnPoints(int points) {
      String uid = LoginScreen.id.getText();
      String jdbcUrl = "jdbc:mysql://localhost:3306/merge_db";
      String username = "root";
      String password = "0000";

      try {
          Class.forName("com.mysql.cj.jdbc.Driver");
          Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

          String selectQuery = "SELECT POINT FROM members WHERE ID = ?";
          PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
          selectStatement.setString(1, uid);
          ResultSet resultSet = selectStatement.executeQuery();

          int currentPoints = 0;
          if (resultSet.next()) {
              currentPoints = resultSet.getInt("POINT");
          }

          int newPoints = currentPoints + points;

          String updateQuery = "UPDATE members SET POINT = ? WHERE ID = ?";
          PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
          updateStatement.setInt(1, newPoints);
          updateStatement.setString(2, uid);
          updateStatement.executeUpdate();

          Toolkit.getDefaultToolkit().beep();
          JOptionPane.showMessageDialog(null, "포인트 " + points + " 획득!");

          resultSet.close();
          selectStatement.close();
          updateStatement.close();
          connection.close();
      } catch (ClassNotFoundException | SQLException e) {
          e.printStackTrace();
      }
  }
  
}