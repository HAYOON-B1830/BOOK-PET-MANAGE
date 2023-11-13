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

public class CheckReserveList extends JFrame {

	// JTable *******테이블 도서번호를 넣어야할지 고민해봐야함/ 도서코드로 해서 나중에 입력가능하게? 아닌데**********
	Object reserve_ob[][] = new Object[0][4];
	DefaultTableModel reserve_model; // 데이터 저장부분
	JTable reserve_booktable;
	JScrollPane reserve_scrollPane;
	// 도서번호->도서명으로
	String reserve_str[] = { "예약번호", "도서번호", "예약회원", "예약일자" }; // 예약테이블 컬럼

	// DB연동
	Connection con = null;
	PreparedStatement stmt = null; // sql구문 실행
	ResultSet result = null; // select구문 사용시 필요

	// DB연동 시 필요
	String url = "jdbc:mysql://localhost/bookdb_schema?serverTimezone=Asia/Seoul"; // dbstudy스키마
	String user = "root";
	String passwd = "0000"; // MySQL에 저장한 root 계정의 비밀번호를 적어주면 된다.

	public CheckReserveList() {
		super("대출현황 조회/연장/예약");

		setSize(800, 500);
		setVisible(false); // 사이즈조정 불가능
		setLocationRelativeTo(null); // 창위치 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		/////////////////////////////////////////////////////////

		JLabel StringLabel = new JLabel("예약목록조회");
		StringLabel.setFont(new Font("굴림", Font.BOLD, 30));
		StringLabel.setBounds(39, 32, 428, 50);
		getContentPane().add(StringLabel);

		JButton prebtn = new JButton("돌아가기");
		prebtn.setFont(new Font("굴림", Font.PLAIN, 15));
		prebtn.setBounds(462, 32, 135, 40);
		getContentPane().add(prebtn);

		JButton homebtn = new JButton("HOME");
		homebtn.setFont(new Font("굴림", Font.PLAIN, 15));
		homebtn.setBounds(609, 32, 135, 40);
		getContentPane().add(homebtn);

		prebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new BookAMD();
			}
		});

		homebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new UserMain();
			}
		});

		////////////////////////////////////////////////////////////////////
		/* 테이블 */
		// reserve테이블 출력
		reserve_model = new DefaultTableModel(reserve_ob, reserve_str);
		reserve_booktable = new JTable(reserve_model);

		reserve_scrollPane = new JScrollPane(reserve_booktable);
		getContentPane().add("Center", reserve_scrollPane);
		reserve_scrollPane.setBounds(39, 92, 705, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		connect();
		reserve_select();

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

	// String reserve_str[] = { "도서명", "예약여부 ", "반납예정일자" }; // 예약테이블 컬럼
	public void reserve_select() {
		try {
			// 실행할 sql문장 작성
			String sql = "select * from reserve";
			stmt = con.prepareStatement(sql);
			result = stmt.executeQuery(sql); // select문장

			// books테이블에서 불러오기
			while (result.next()) {
				int no = result.getInt("RESERVE_NUMBER");
				String book = result.getString("RESERVE_BOOK_NUMBER"); // 도서명 출력으로 변경
				String user = result.getString("RESERVE_USER_ID");
				String reservedate = result.getString("RESERVE_DATE");

				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
				Object data[] = { no, book, user, reservedate };
				reserve_model.addRow(data);
				System.out.println(no + ", " + book + ", " + user + ", " + reservedate);// 콘솔출력

			}
		} catch (Exception e) {
			System.out.println("reserve_select() 실행오류 : " + e);
		}
	}

	public static void main(String[] args) {
		new CheckReserveList();
	}
}
