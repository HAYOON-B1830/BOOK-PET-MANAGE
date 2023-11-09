package member;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUp extends JDialog {

	// DB연동
	Connection con = null;
	PreparedStatement stmt = null; // sql구문 실행
	ResultSet result = null; // select구문 사용시 필요

	// DB연동 시 필요
	String url = "jdbc:mysql://localhost/nemberdb?serverTimezone=Asia/Seoul"; // dbstudy스키마
	String user = "root";
	String passwd = "1234"; // MySQL에 저장한 root 계정의 비밀번호를 적어주면 된다.

	private JPanel signUpPanel = new JPanel(new GridLayout(11, 0));
	private JLabel idlabel = new JLabel(" ID");
	private JTextField idText = new JTextField();
	private JPasswordField pwText = new JPasswordField();
	private JLabel pwlabel = new JLabel(" PW");
	private JPasswordField pwCheckText = new JPasswordField();
	private JLabel pwChecklabel = new JLabel(" PW 확인");
	private JTextField nameText = new JTextField();
	private JLabel namelabel = new JLabel(" 이름");
	private JTextField phoneNumberText = new JTextField();
	private JLabel phoneNumberlabel = new JLabel(" 핸드폰 번호");
	private JButton signUpbtn = new JButton("회원가입");

	private boolean membershipProgress = false;
	// private Connection con;

	public SignUp() {

		this.setTitle("회원가입");

		this.signUpPanel.add(idlabel);
		this.signUpPanel.add(idText);
		this.signUpPanel.add(pwlabel);
		this.signUpPanel.add(pwText);
		this.signUpPanel.add(pwChecklabel);
		this.signUpPanel.add(pwCheckText);
		this.signUpPanel.add(namelabel);
		this.signUpPanel.add(nameText);
		this.signUpPanel.add(phoneNumberlabel);
		this.signUpPanel.add(phoneNumberText);
		this.signUpPanel.add(signUpbtn);

		this.setContentPane(signUpPanel);
		this.setSize(800, 500);
		this.setLocationRelativeTo(null);

		connect();

		FocusEvent();
		checkValue();
	}

	private void FocusEvent() {
		idText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (idText.getText().trim().length() == 0) {
					idText.setText("ID");
				}
			}

			public void focusGained(FocusEvent e) {
				if (idText.getText().trim().equals("ID")) {
					idText.setText("");
				}
			}
		});

		nameText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (nameText.getText().trim().length() == 0) {
					nameText.setText("이름");
				}
			}

			public void focusGained(FocusEvent e) {
				if (nameText.getText().trim().equals("이름")) {
					nameText.setText("");
				}
			}
		});

		phoneNumberText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (phoneNumberText.getText().trim().length() == 0) {
					phoneNumberText.setText("핸드폰 번호");
				}
			}

			public void focusGained(FocusEvent e) {
				if (phoneNumberText.getText().trim().equals("핸드폰 번호")) {
					phoneNumberText.setText("");
				}
			}

		});

	}

	private void connect() {

		try {
			// 접속할 드라이버 메모리에 올리기
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 접속하기 위한 메서드(접속 url, 계정명, 계정암호)
			con = DriverManager.getConnection("jdbc:mysql://localhost/nemberdb?serverTimezone=Asia/Seoul", "root",
					"1234");
			System.out.println("접속 : " + con);
		} catch (Exception e) {
			System.out.println("DB접속 오류 : " + e);
		}
	}

	private void checkValue() {
		signUpbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idText.getText().trim().length() == 0 || idText.getText().trim().equals("아이디")) {
					JOptionPane.showMessageDialog(null, "ID를 입력해 주세요.", "아이디 입력", JOptionPane.WARNING_MESSAGE);
					idText.grabFocus();
					return;
				}

				if (pwText.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "PW를 입력해 주세요.", "비밀번호 입력", JOptionPane.WARNING_MESSAGE);
					pwText.grabFocus();
					return;
				}

				if (pwCheckText.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "PW 확인을 입력해 주세요.", "비밀번호 확인 입력", JOptionPane.WARNING_MESSAGE);
					pwCheckText.grabFocus();
					return;
				}

				if (!(pwText.getText().trim().equals(pwCheckText.getText().trim()))) {
					JOptionPane.showMessageDialog(null, "PW가 같지 않습니다.!!", "비밀번호 확인", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (nameText.getText().trim().length() == 0 || nameText.getText().trim().equals("이름")) {
					JOptionPane.showMessageDialog(null, "이름을 입력해 주세요.", "이름 입력", JOptionPane.WARNING_MESSAGE);
					nameText.grabFocus();
					return;
				}

				if (phoneNumberText.getText().trim().length() == 0
						|| phoneNumberText.getText().trim().equals("핸드폰 번호")) {
					JOptionPane.showMessageDialog(null, "핸드폰 번호를 입력해 주세요.", "핸드폰 번호 입력", JOptionPane.WARNING_MESSAGE);
					phoneNumberText.grabFocus();
					return;
				}

				// c드라이브에 book_member폴더, member.txt파일
				// 아이디랑 패스워드 구분하기 위해 사용
				String txt = idText.getText() + "|" + pwText.getText() + "|" + nameText.getText() + "|"
						+ phoneNumberText.getText();
				txt += "\n";

				String fileName = "C:\\book_member\\member.txt";

				try {
					BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));

					fw.write(txt);
					fw.flush();

					fw.close();

				} catch (Exception errmsg) {
					errmsg.printStackTrace();
				}

				membershipProgress = true;

				JOptionPane.showMessageDialog(null, "회원 가입이 완료 되었습니다.", "회원 가입 완료.", JOptionPane.WARNING_MESSAGE);

				setVisible(false);
			}
		});

	}

	public void insert() {
		// model에 저장한 데이터를 mysql테이블에 저장(insert)
		try {
//			idText pwText nameText phoneNumberText
			String insertStr = "SELECT * FROM nemberdb.member_table;(?,?,?,?)";
			stmt = con.prepareStatement(insertStr);
			stmt.setString(1, idText.getText());
			stmt.setString(2, pwText.getText());
			stmt.setString(3, nameText.getText());
			stmt.setString(4, phoneNumberText.getText());

			// 값 입력 안되면 넘어가지 않게
			if (idText.getText().equals("") || pwText.getText().equals("") || nameText.getText().equals("")
					|| phoneNumberText.getText().equals("")) {
			} else {
				Object data[] = { idText.getText(), pwText.getText(), nameText.getText(), phoneNumberText.getText() };

//				model.addRow(data);
//				clear();

			}

//			반환값
			int insert = stmt.executeUpdate();
			System.out.println("insert()성공 유무 : " + insert);

			// select조회
			result = stmt.executeQuery("SELECT * FROM nemberdb.member_table;");
			while (result.next()) {
				String id = result.getString(" ");
				String pw = result.getString("");
				String name = result.getString("");
				String tel = result.getString("");
				System.out.println(id + ", " + pw + ", " + name + ", " + tel); // 콘솔 출력
			}

		} catch (Exception e2) {
			System.out.println("nemberdb테이블에 데이터 입력 실패" + e2);
		}
	}

	public String getIdText() {
		return this.idText.getText().trim();
	}

	public String getPwText() {
		return this.pwText.getText().trim();
	}

	public String getPwCheckText() {
		return this.pwCheckText.getText().trim();
	}

	public String getNameText() {
		return this.nameText.getText().trim();
	}

	public String getPhoneNumberText() {
		return this.phoneNumberText.getText().trim();
	}

	public boolean memberCheck() {
		return membershipProgress;
	}

	public static void main(String[] args) {
		SignUp s = new SignUp();

	}
}