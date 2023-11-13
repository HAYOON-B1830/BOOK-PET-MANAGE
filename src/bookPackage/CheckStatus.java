package bookPackage;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//사용자화면-로그인한 계정의 대출현황 출력(확인)
//추후수정사항: 로그인한 계정id받아와서 select시 사용

public class CheckStatus extends JFrame {
	/* 사용자단 내 대출/반납현황 조회 */

	// JTable
	Object rent_ob[][] = new Object[0][5]; // 데이터 열 표시
	DefaultTableModel rent_model; // 데이터 저장부분
	JTable rent_booktable;
	JScrollPane rent_scrollPane;
	String rent_str[] = { "대출번호", "도서명", "대출회원", "대출일자", "반납일자" }; // 대출테이블 컬럼

	// DB연동
	Connection con = null;
	PreparedStatement stmt = null; // sql구문 실행
	ResultSet result = null; // select구문 사용시 필요

	// DB연동 시 필요
	String url = "jdbc:mysql://localhost/bookdb_schema?serverTimezone=Asia/Seoul"; // dbstudy스키마
	String user = "root";
	String passwd = "0000"; // MySQL에 저장한 root 계정의 비밀번호를 적어주면 된다.

	public CheckStatus() {
		super("내 대출/반납현황 조회");

		setSize(800, 500);
		setVisible(false); // 사이즈조정 불가능
		setLocationRelativeTo(null); // 창위치 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		/////////////////////////////////////////////////////////

		JLabel StringLabel = new JLabel("내 대출/반납현황 조회"); // 대출현황 조회/연장/예약
		StringLabel.setFont(new Font("굴림", Font.BOLD, 30));
		StringLabel.setBounds(39, 32, 428, 50);
		getContentPane().add(StringLabel);

		JButton bookactivebtn = new JButton("대출/반납 화면으로 이동");
		bookactivebtn.setFont(new Font("굴림", Font.PLAIN, 15));
		bookactivebtn.setBounds(386, 32, 210, 40);
		getContentPane().add(bookactivebtn);

		JButton homebtn = new JButton("HOME");
		homebtn.setFont(new Font("굴림", Font.PLAIN, 15));
		homebtn.setBounds(609, 32, 135, 40);
		getContentPane().add(homebtn);

		bookactivebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new BookList();
				setVisible(false);
			}
		});

		homebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new UserMain();
				setVisible(false);
			}
		});

		////////////////////////////////////////////////////////////////

		JLabel rentLabel = new JLabel("대출");
		rentLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		rentLabel.setBounds(39, 92, 46, 40);
		getContentPane().add(rentLabel);

		////////////////////////////////////////////////////////////////////
		/* 테이블 */
		// rent테이블 출력
		rent_model = new DefaultTableModel(rent_ob, rent_str);
		rent_booktable = new JTable(rent_model);

		rent_scrollPane = new JScrollPane(rent_booktable);
		getContentPane().add("Center", rent_scrollPane);
		rent_scrollPane.setBounds(39, 131, 705, 311);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		connect();
		rent_select();

		/////////////////////////////////////////////////////////////////

		// DB접속해 select문장 이용해 jtable에 보여주는 구문

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if (result != null)
						result.close();
					if (stmt != null)
						stmt.close();
					if (con != null)
						con.close();

				} catch (Exception e2) {
					System.exit(0);
				}
			}

		});

		////////////////////////////////////////////////////////////////

		setVisible(true);

	}

	public void connect() {
		try {
			// 접속할 드라이버 메모리에 올리기
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 접속하기 위한 메서드(접속 url, 계정명, 계정암호)
			con = DriverManager.getConnection("jdbc:mysql://localhost/bookdb_schema?serverTimezone=Asia/Seoul", "root",
					"0000");
			System.out.println("접속 : " + con);
		} catch (Exception e) {
			System.out.println("DB접속 오류 : " + e);
		}
	}

	// String rent_str[] = { "도서명", "대출일자", "반납예정일자" }; // 대출테이블 컬럼
	public void rent_select() {
		try {
			// 로그인한 회원id(Login.java) 가져옴
			// 다른 패키지에 있는 Login클래스의 idText 가져옴
//			Login lo = new Login();
//			String memberid = lo.idText.getText();

			// 실행할 sql문장 작성
			String sql = "select * from rent where rent_user_id = 'hyuna2398' order by return_date";
			// String sql = "select * from rent where rent_user_id =\'" + memberid + "\'
			// order by return_date";
			stmt = con.prepareStatement(sql);
			result = stmt.executeQuery(sql); // select문장

			// books테이블에서 불러오기
			while (result.next()) {
				String no = result.getString("RENT_NUMBER");
				// String book = result.getString("RENT_BOOK_NUMBER"); // 도서명 출력으로 변경
				String book = result.getString("RENT_BOOK_TITLE");
				String user = result.getString("RENT_USER_ID");
				String rentdate = result.getString("RENT_DATE");
				String returndate = result.getString("RETURN_DATE");

				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
				Object data[] = { no, book, user, rentdate, returndate };
				rent_model.addRow(data);
				System.out.println(no + ", " + book + ", " + user + ", " + rentdate + ", " + returndate);// 콘솔출력

			}
		} catch (Exception e) {
			System.out.println("rent_select() 실행오류 : " + e);
		}
	}

	public static void main(String[] args) {
		new CheckStatus();
	}
}
