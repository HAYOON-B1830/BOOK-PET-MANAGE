package member;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class UserMain extends JFrame {
	private JTextField textField;

	public UserMain() {
		super("사용자 메인화면");

		setSize(800, 500);
		setVisible(false); // 사이즈조정 불가능
		setLocationRelativeTo(null); // 창위치 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		/////////////////////////////////////////////////////////////////

		textField = new JTextField();
		textField.setFont(new Font("굴림", Font.PLAIN, 15));
		textField.setBounds(23, 32, 444, 40);
		getContentPane().add(textField);
		textField.setColumns(10);

		JButton searchbtn = new JButton("검색");
		searchbtn.setFont(new Font("굴림", Font.PLAIN, 15));
		searchbtn.setBounds(479, 32, 135, 40);
		getContentPane().add(searchbtn);

		JButton storebtn = new JButton("상점");
		storebtn.setFont(new Font("굴림", Font.PLAIN, 15));
		storebtn.setBounds(626, 32, 135, 40);
		getContentPane().add(storebtn);

		JLabel petimgLabel = new JLabel("펫이미지 자리");
		petimgLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		petimgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		petimgLabel.setBounds(479, 82, 282, 233);
		getContentPane().add(petimgLabel);

		JLabel lblNewLabel_1 = new JLabel("잔여포인트");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(470, 320, 207, 115);
		getContentPane().add(lblNewLabel_1);

		JButton signoutbtn = new JButton("로그아웃");
		signoutbtn.setFont(new Font("굴림", Font.PLAIN, 15));
		signoutbtn.setHorizontalAlignment(SwingConstants.CENTER);
		signoutbtn.setBounds(677, 395, 95, 40);
		getContentPane().add(signoutbtn);

		signoutbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.setVisible(true);

			}
		});

		///////////////////////////////////////////////////

		// bookPanel 패널(대출예약도서, 대출 중인 도서, 독후감 게시판)
		/* 추후 수정 */

		JPanel bookPanel = new JPanel();
		bookPanel.setLayout(new GridLayout(3, 2));
		bookPanel.setBounds(23, 82, 444, 353);

		JLabel reservationLabel = new JLabel("대출예약도서");
		bookPanel.add(reservationLabel);
		reservationLabel.setFont(new Font("굴림", Font.BOLD, 15));

		JLabel rentLabel = new JLabel("대출 중인 도서");
		bookPanel.add(rentLabel);
		rentLabel.setFont(new Font("굴림", Font.BOLD, 15));

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
