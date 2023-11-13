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

public class CheckRentList extends JFrame {
	/* 관리자단 대출목록 조회 */

	// JTable *******테이블 도서번호를 넣어야할지 고민해봐야함/ 도서코드로 해서 나중에 입력가능하게? 아닌데**********
//	Object ob[][] = new Object[0][5]; // 데이터 열 표시
	Object rent_ob[][] = new Object[0][5];
	Object reserve_ob[][] = new Object[0][4];
	DefaultTableModel rent_model, reserve_model; // 데이터 저장부분
	JTable rent_booktable, reserve_booktable;
	JScrollPane rent_scrollPane, reserve_scrollPane;
	// 도서번호->도서명으로
	String rent_str[] = { "대출번호", "도서명", "대출회원", "대출일자", "반납일자" }; // 대출테이블 컬럼

	// DB연동
	Connection con = null;
	PreparedStatement stmt = null; // sql구문 실행
	ResultSet result = null; // select구문 사용시 필요

	// DB연동 시 필요
	String url = "jdbc:mysql://localhost/bookdb_schema?serverTimezone=Asia/Seoul"; // dbstudy스키마
	String user = "root";
	String passwd = "0000"; // MySQL에 저장한 root 계정의 비밀번호를 적어주면 된다.

	public CheckRentList() {
		super("대출현황 조회/연장/예약");

		setSize(800, 500);
		setVisible(false); // 사이즈조정 불가능
		setLocationRelativeTo(null); // 창위치 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		/////////////////////////////////////////////////////////

		JLabel StringLabel = new JLabel("대출목록조회");
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
		// rent테이블 출력
		rent_model = new DefaultTableModel(rent_ob, rent_str);
		rent_booktable = new JTable(rent_model);

		rent_scrollPane = new JScrollPane(rent_booktable);
		getContentPane().add("Center", rent_scrollPane);
		rent_scrollPane.setBounds(39, 92, 705, 350);
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
			// 실행할 sql문장 작성
			String sql = "select * from rent order by return_date";
			stmt = con.prepareStatement(sql);
			result = stmt.executeQuery(sql); // select문장

			// books테이블에서 불러오기
			while (result.next()) {
				String no = result.getString("RENT_NUMBER");
				// String book = result.getString("RENT_BOOK_NUMBER"); // 도서명 출력으로 변경
				String book = result.getString("RENT_BOOK_TITLE"); // 도서명 출력
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
		new CheckRentList();
	}
}