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

public class BookinfoChange extends JFrame {
	public BookinfoChange() {

		super("관리자_도서정보 수정 및 삭제");

		setSize(800, 500);
		setVisible(false); // 사이즈조정 불가능
		setLocationRelativeTo(null); // 창위치 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		///////////////////////////////////////////////

		JLabel StringLabel = new JLabel("도서정보");
		StringLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		StringLabel.setBounds(39, 32, 264, 50);
		getContentPane().add(StringLabel);

		JButton delbtn = new JButton("삭제");
		delbtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		delbtn.setBounds(315, 32, 135, 40);
		getContentPane().add(delbtn);

		JButton chbtn = new JButton("수정");
		chbtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		chbtn.setBounds(462, 32, 135, 40);
		getContentPane().add(chbtn);

		JButton savebtn = new JButton("저장");
		savebtn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		savebtn.setBounds(609, 32, 135, 40);
		getContentPane().add(savebtn);

		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new BookAMD();
			}
		});

		////////////////////////////////////////////////

		JLabel bookimgLabel = new JLabel(); // 라벨생성
		// 아이콘 생성, 라벨에 아이콘 설정
		ImageIcon icon = new ImageIcon("res/bookcover.jpg");

		// 이미지크기조정: ImageIcon객체에서 Image추출, 추출한 이미지 크기 조절
		Image img = icon.getImage();
		Image updateImg = img.getScaledInstance(200, 280, Image.SCALE_SMOOTH);
		ImageIcon updateIcon = new ImageIcon(updateImg);

		//
		bookimgLabel.setIcon(updateIcon);
		bookimgLabel.setBounds(39, 92, 264, 308);
		bookimgLabel.setHorizontalAlignment(JLabel.CENTER);

		getContentPane().add(bookimgLabel);
		//////////////////////////////////////////////////////////

		JLabel titleLabel = new JLabel("도서명");
		titleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		titleLabel.setBounds(315, 155, 66, 40);
		getContentPane().add(titleLabel);

		JTextField titleText = new JTextField();
		titleText.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		titleText.setBounds(410, 155, 334, 40);
		getContentPane().add(titleText);
		titleText.setColumns(10);

		JLabel authorLabel = new JLabel("저자");
		authorLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		authorLabel.setBounds(315, 215, 66, 40);
		getContentPane().add(authorLabel);

		JTextField authorText = new JTextField();
		authorText.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		authorText.setBounds(410, 215, 334, 40);
		getContentPane().add(authorText);
		authorText.setColumns(10);

		JLabel publisherLabel = new JLabel("출판사");
		publisherLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		publisherLabel.setBounds(315, 275, 66, 40);
		getContentPane().add(publisherLabel);

		JTextField publisherText = new JTextField();
		publisherText.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		publisherText.setBounds(410, 275, 334, 40);
		getContentPane().add(publisherText);
		publisherText.setColumns(10);

		JLabel yearLabel = new JLabel("출판년도");
		yearLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		yearLabel.setBounds(315, 333, 83, 40);
		getContentPane().add(yearLabel);

		JTextField yearText = new JTextField();
		yearText.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		yearText.setBounds(410, 333, 334, 40);
		getContentPane().add(yearText);
		publisherText.setColumns(10);

		////////////////////////////////////////////////

		setVisible(true);

	}

	public static void main(String[] args) {
		new BookinfoChange();

	}

}
