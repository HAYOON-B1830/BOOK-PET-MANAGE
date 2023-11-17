package memberPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bookPackage.BookAMD;
import bookPackage.UserMain;
import petPackage.PetApp;

import java.awt.Font;
import java.awt.Toolkit;

//로그인화면 front_gui코드
public class LoginScreen extends JFrame {
	
	JPanel panel = new JPanel();
	JLabel idL = new JLabel("ID : ");
	JLabel pwL = new JLabel("PASSWORD : ");
	public static JTextField id = new JTextField(10);
	JPasswordField pw = new JPasswordField(10);
	JButton loginBtn = new JButton("로그인");
	JButton joinBtn = new JButton("회원가입");

	Operator o = null;

	public LoginScreen(Operator _o) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginScreen.class.getResource("/images/icon.png")));
		o = _o;
		setTitle("로그인");
		panel.setLayout(null);
		idL.setFont(new Font("돋움", Font.BOLD, 12));
		idL.setBounds(33, 9, 42, 15);

		panel.add(idL);
		id.setBounds(58, 6, 120, 23);
		panel.add(id);
		pwL.setFont(new Font("돋움", Font.BOLD, 12));
		pwL.setBounds(186, 9, 120, 15);
		panel.add(pwL);
		pw.setBounds(268, 6, 120, 23);
		panel.add(pw);
		loginBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		loginBtn.setBounds(101, 39, 89, 32);
		panel.add(loginBtn);
		joinBtn.setFont(new Font("돋움", Font.PLAIN, 12));
		joinBtn.setBounds(193, 39, 104, 32);
		panel.add(joinBtn);

		ButtonListener bl = new ButtonListener();
		loginBtn.addActionListener(bl);
		joinBtn.addActionListener(bl);

		////////////////////////////////////////////////

		getContentPane().add(panel);

		setVisible(true);
		setSize(422, 144);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/* Button 이벤트 리스너 */
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();

			/* TextField에 입력된 아이디와 비밀번호를 변수에 초기화 */
			String uid = id.getText();
			String upass = "";
			for (int i = 0; i < pw.getPassword().length; i++) {
				upass = upass + pw.getPassword()[i];
			}

			/* 회원가입 버튼 이벤트 */
			if (b.getText().equals("회원가입")) {
				new JoinScreen(o);
				setVisible(false);

			}

			/* 로그인 버튼 이벤트 */
	        if (b.getText().equals("로그인")) {
	            if (uid.equals("") || upass.equals("")) {
	                JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 모두 입력해주세요", "로그인 실패", JOptionPane.ERROR_MESSAGE);
	                System.out.println("로그인 실패 > 로그인 정보 미입력");
	            } else if (uid != null && upass != null) {
	                if (o.db.logincheck(uid, upass)) {
	                    System.out.println("로그인 성공");
	                    JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다");

	                    if (uid.equals("BM")) { // 사용자 아이디가 "BM"인 경우
	                        new BookAMD(); // BookinfoChange 창 열기
	                    } else {
	                        new UserMain(); // "BM"이 아닌 경우 UserMain 창 열기
	                    }

	                    setVisible(false);
	                } else {
	                    System.out.println("로그인 실패 > 로그인 정보 불일치");
	                    JOptionPane.showMessageDialog(null, "로그인에 실패하였습니다");
	                }
	            }
	        }
	    }
	}

}

