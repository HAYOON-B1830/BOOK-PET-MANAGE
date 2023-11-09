package member;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {
	private JPanel loginPanel = new JPanel(new GridLayout(5, 5));
	private JLabel idLabel = new JLabel("ID");
	private JLabel pwLabel = new JLabel("PW");
	private JTextField idText = new JTextField();
	private JPasswordField pwText = new JPasswordField();
	private JButton loginBtn = new JButton("로그인");
	private JButton memberbtn = new JButton("회원가입");

	public Login() {
		super("로그인");

		this.setContentPane(loginPanel);
		loginPanel.add(idLabel);
		idLabel.setHorizontalAlignment(NORMAL);
		loginPanel.add(idText);
		loginPanel.add(pwLabel);
		pwLabel.setHorizontalAlignment(NORMAL);
		loginPanel.add(pwText);
		loginPanel.add(loginBtn);
		loginPanel.add(memberbtn);

		setSize(800, 500);
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id = idText.getText().trim();
				String pw = pwText.getText().trim();
				String login = "";

				if (id.length() == 0 || pw.length() == 0) {
					JOptionPane.showMessageDialog(null, "ID PW를 입력 하셔야 됩니다.", "ID PW을 입력!", JOptionPane.DEFAULT_OPTION);
					return;
				}

				try {
					BufferedReader reader = new BufferedReader(new FileReader("C:\\book_member\\member.txt"));

					String str;
					ArrayList<String> txtmember = new ArrayList<>();
					while ((str = reader.readLine()) != null) {
						txtmember.add(str);
					}

					reader.close();

					HashMap<String, String> memberlist = new HashMap<>();

					for (int i = 0; i < txtmember.size(); i++) {
						String[] tempresult = txtmember.get(i).toString().split("\\|");
						memberlist.put(tempresult[0], tempresult[1]);
					}

					for (String key : memberlist.keySet()) {
						if (id.equals(key.trim()) && pw.equals(memberlist.get(key))) {

							login = "성공";
						}
					}

				} catch (Exception errmsg) {
					errmsg.printStackTrace();
				}

				if (login.equals("완료")) {
					JOptionPane.showMessageDialog(null, "로그인 실패", "로그인 확인!", JOptionPane.DEFAULT_OPTION);
					return;
				} else {
					JOptionPane.showMessageDialog(null, "로그인 완료", "계정 정보를 확인해 주세요.", JOptionPane.DEFAULT_OPTION);
				}

				loginBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						UserMain usermain = new UserMain();
						usermain.setVisible(true);

					}
				});

			}

		});

		memberbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SignUp signup = new SignUp();
				signup.setVisible(true);

			}
		});

	}

	public static void main(String[] args) {
		new Login();
	}
}