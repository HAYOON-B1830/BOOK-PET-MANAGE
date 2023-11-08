package pet_book;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import java.awt.Color;


public class Home extends JFrame {
    
	private JTextField textField;
	private LineBorder border = new LineBorder(Color.black, 1, true);
	
	public Home() {
		super("사용자 메인화면");
		//화질 저하 방지 코드

		setSize(800, 500);
		setVisible(false); // 사이즈조정 불가능
		setLocationRelativeTo(null); // 창위치 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(PetApp.class.getResource("/images/icon.png")));
		setResizable(false);

		/////////////////////////////////////////////////////////////////

		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField.setBounds(23, 32, 444, 40);
		getContentPane().add(textField);
		textField.setColumns(10);

		JButton searchbtn = new JButton("검색");
		searchbtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		searchbtn.setBounds(479, 32, 135, 40);
		getContentPane().add(searchbtn);

		JButton storebtn = new JButton("상점");
		storebtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		storebtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	        	setVisible(false);
	        	ShopApp shopApp = new ShopApp();
	        }
	    });
		storebtn.setBounds(626, 32, 135, 40);
		getContentPane().add(storebtn);

		JButton petimgbtn = new JButton("");
		petimgbtn.setIcon(new ImageIcon(Home.class.getResource("/images/dog01_r.png")));
		petimgbtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		petimgbtn.setHorizontalAlignment(SwingConstants.CENTER);
		petimgbtn.setBorder(border);
	    petimgbtn.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	setVisible(false);
	        	PetApp petApp = new PetApp();
	        	petApp.frame.setVisible(true);
	        }
	    });
	    	petimgbtn.setBounds(479, 82, 282, 263);
		getContentPane().add(petimgbtn);
		petimgbtn.setContentAreaFilled(false);
		petimgbtn.setFocusPainted(false);
		
		JLabel lblNewLabel_1 = new JLabel("잔여포인트");
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(479, 355, 282, 80);
		getContentPane().add(lblNewLabel_1);

		///////////////////////////////////////////////////

		// bookPanel 패널(대출예약도서, 대출 중인 도서, 독후감 게시판)
		/* 추후 수정 */

		JPanel bookPanel = new JPanel();
		bookPanel.setLayout(new GridLayout(3, 2));
		bookPanel.setBounds(23, 82, 444, 353);

		JLabel reservationLabel = new JLabel("대출예약도서");
		bookPanel.add(reservationLabel);
		reservationLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		JLabel rentLabel = new JLabel("대출 중인 도서");
		bookPanel.add(rentLabel);
		rentLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		JLabel writeLabel = new JLabel("* 테스트용 화면, 수정 필요");
		writeLabel.setForeground(new Color(255, 0, 0));
		bookPanel.add(writeLabel);
		writeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));

		getContentPane().add(bookPanel);

		///////////////////////////////////////////////////

		setVisible(true);
		
	}

    // 다른 클래스에서 Home 화면을 띄우기 위한 메서드
    public static void createAndShowGUI() {
        SwingUtilities.invokeLater(() -> {
            Home homeFrame = new Home();
            homeFrame.setVisible(true);
            
        });
    }
	
	public static void main(String[] args) {
		System.setProperty("sun.java2d.uiScale", "1.0");
		createAndShowGUI();
//		UserMain um = new UserMain();
	}
}
