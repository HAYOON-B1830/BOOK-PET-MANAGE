package bookPackage;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//좌측 패널, 상단 코드 건드림

public class UserMain extends JFrame {
	public UserMain() {
		super("사용자 메인화면");

		setSize(800, 500);
		setVisible(false); // 사이즈조정 불가능
		setLocationRelativeTo(null); // 창위치 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JButton prebtn = new JButton("돌아가기");
		prebtn.setFont(new Font("굴림", Font.PLAIN, 15));
		prebtn.setBounds(479, 31, 135, 40);
		getContentPane().add(prebtn);

		JButton storebtn = new JButton("상점");
		storebtn.setFont(new Font("굴림", Font.PLAIN, 15));
		storebtn.setBounds(626, 32, 135, 40);
		getContentPane().add(storebtn);

		// 로그인 화면으로 돌아가게 하고 싶음
//		prebtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				new BookList();
//			}
//		});

		//////////////////////////////////////////////////////////////////////

		JLabel petimgLabel = new JLabel("펫이미지 자리");
		petimgLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		petimgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		petimgLabel.setBounds(479, 82, 282, 263);
		getContentPane().add(petimgLabel);

		JLabel lblNewLabel_1 = new JLabel("잔여포인트");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(479, 355, 282, 80);
		getContentPane().add(lblNewLabel_1);

		///////////////////////////////////////////////////

		// bookPanel 패널(대출예약도서, 대출 중인 도서, 독후감 게시판)
		/* 추후 수정 */

		JPanel bookPanel = new JPanel();
		bookPanel.setLayout(new GridLayout(3, 2));
		bookPanel.setBounds(23, 160, 444, 275);

		JButton Listbtn = new JButton("도서목록(대출/반납/검색)");
		Listbtn.setFont(new Font("굴림", Font.PLAIN, 15));
		Listbtn.setBounds(23, 31, 260, 40);
		getContentPane().add(Listbtn);

		Listbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new BookList();
				setVisible(false);
			}
		});

		JButton viewbtn = new JButton("내 대출/반납현황");
		viewbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CheckStatus();
				setVisible(false);
			}
		});
		viewbtn.setFont(new Font("굴림", Font.PLAIN, 15));
		viewbtn.setBounds(295, 31, 172, 40);
		getContentPane().add(viewbtn);

//		JLabel ListLabel = new JLabel("도서목록 확인");
//		bookPanel.add(ListLabel);
//		ListLabel.setFont(new Font("굴림", Font.BOLD, 15));
//		
//				JLabel viewLabel = new JLabel("내 대출/예약현황 확인");
//				bookPanel.add(viewLabel);
//				viewLabel.setFont(new Font("굴림", Font.BOLD, 15));

		JLabel writeLabel = new JLabel("독후감 게시판");
		bookPanel.add(writeLabel);
		writeLabel.setFont(new Font("굴림", Font.BOLD, 15));

		getContentPane().add(bookPanel);

		///////////////////////////////////////////////////

		setVisible(true);
	}

	public static void main(String[] args) {
		UserMain um = new UserMain();
	}
}
