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

import bookPackage.UserMain;

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
		o = _o;
		setTitle("로그인");

		panel.add(idL);
		panel.add(id);
		panel.add(pwL);
		panel.add(pw);
		panel.add(loginBtn);
		panel.add(joinBtn);

		ButtonListener bl = new ButtonListener();
		loginBtn.addActionListener(bl);
		joinBtn.addActionListener(bl);

		////////////////////////////////////////////////

		add(panel);

		setVisible(true);
		setSize(350, 120);
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
					JOptionPane.showMessageDialog(null, "아이디와 비밀번호 모두 입력해주세요", "로그인 실패", JOptionPane.ERROR_MESSAGE);
					System.out.println("로그인 실패 > 로그인 정보 미입력");
				}

				else if (uid != null && upass != null) {
					if (o.db.logincheck(uid, upass)) { // 이 부분이 데이터베이스에 접속해 로그인 정보를 확인하는 부분이다.
						System.out.println("로그인 성공");
						JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다");
						new UserMain();
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
