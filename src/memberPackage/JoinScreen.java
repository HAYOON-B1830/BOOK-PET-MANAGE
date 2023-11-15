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

public class JoinScreen extends JFrame {

	JPanel panel = new JPanel();
	JLabel idL = new JLabel("ID : ");
	JLabel pwL = new JLabel("PASSWORD : ");
	JTextField id = new JTextField(10);
	JPasswordField pw = new JPasswordField(10);

	JButton cancleBtn = new JButton("가입취소");
	JButton joinBtn = new JButton("가입하기");

	Operator o = null;

	public JoinScreen(Operator _o) {
		o = _o;
		setTitle("회원가입");

		panel.add(idL);
		panel.add(id);
		panel.add(pwL);
		panel.add(pw);

		panel.add(cancleBtn);
		panel.add(joinBtn);

		/* 버튼 이벤트 리스너 추가 */
		ButtonListener bl = new ButtonListener();
		joinBtn.addActionListener(bl);
		cancleBtn.addActionListener(bl);
//		
//		joinBtn.addActionListener(new ActionListener() { // 회원가입 버튼 클릭 시 로그인 화면으로 넘어가는 동작
//			public void actionPerformed(ActionEvent arg0) {
//				new Database(); // 입력한 id, password가 db에 입력됨
//				JOptionPane.showMessageDialog(null, "회원가입되었습니다.");
//
//			}
//		});

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

			/* 가입취소 버튼 이벤트 */
			if (b.getText().equals("가입취소")) {
				// dispose();
				new LoginScreen(o);
				setVisible(false);

			}

			/* 가입하기 버튼 이벤트 */
			else if (b.getText().equals("가입하기")) {
				if (uid.equals("") || upass.equals("")) {
					JOptionPane.showMessageDialog(null, "모든 정보를 기입해주세요", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
					System.out.println("회원가입 실패 > 회원정보 미입력");
				}

				else if (!uid.equals("") && !upass.equals("")) {
					if (o.db.joinCheck(uid, upass)) { // 이 부분이 데이터베이스에 접속해 로그인 정보를 확인하는 부분이다.
						System.out.println("회원가입 성공");
						JOptionPane.showMessageDialog(null, "회원가입에 성공하였습니다");
						dispose();

					} else {
						System.out.println("회원가입 실패");
						JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다");
						id.setText("");
						pw.setText("");
					}
				}
			}
		}
	}

}
