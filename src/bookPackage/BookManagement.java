package bookPackage;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class BookManagement extends JFrame {
	public BookManagement() {

		super("관리자_도서관리");

		setSize(800, 500);
		setVisible(false); // 사이즈조정 불가능
		setLocationRelativeTo(null); // 창위치 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		/////////////////////////////////////////////////////

		JLabel StringLabel = new JLabel("도서관리");
		StringLabel.setFont(new Font("굴림", Font.BOLD, 30));
		StringLabel.setBounds(39, 32, 428, 50);
		getContentPane().add(StringLabel);

		JButton fullbookbtn = new JButton("소장자료");
		fullbookbtn.setFont(new Font("굴림", Font.PLAIN, 15));
		fullbookbtn.setBounds(39, 100, 135, 40);
		getContentPane().add(fullbookbtn);

		fullbookbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new BookAMD();
			}
		});

		JTextField titleText = new JTextField();
		titleText.setFont(new Font("굴림", Font.PLAIN, 15));
		titleText.setBounds(172, 100, 365, 40);
		getContentPane().add(titleText);
		titleText.setColumns(10);

		/////////////////////////////////////////////////////////

		JButton glassbtn = new JButton();

		// 아이콘 생성
		ImageIcon icon = new ImageIcon("res/glass.png");
		// 이미지 크기조정
		Image img = icon.getImage();
		Image updateImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon updateIcon = new ImageIcon(updateImg);

		glassbtn.setIcon(updateIcon);
		glassbtn.setBounds(536, 100, 40, 40);
		glassbtn.setHorizontalAlignment(JLabel.CENTER);

		getContentPane().add(glassbtn);

		glassbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		//////////////////////////////////////////////////

		setVisible(true);

	}

	public static void main(String[] args) {
		BookManagement bm = new BookManagement();
	}

}
