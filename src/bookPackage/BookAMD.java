package bookPackage;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BookAMD extends JFrame implements ActionListener {
	/* 관리자단 소장자료 조회/추가/변경/삭제 */

	JLabel noLabel, titleLabel, authorLabel, publisherLabel, yearLabel; // 제목, 저자, 출판사
	JTextField noText, titleText, authorText, publisherText, yearText;
	JButton insertbtn, deletebtn, updatebtn, searchbtn, homebtn;

	JPanel panel; // JLabel, JTextArea 부착
	int cnt;

	// JTable
	Object ob[][] = new Object[0][5]; // 데이터 열 표시
	DefaultTableModel model; // 데이터 저장부분
	JTable booktable;
	JScrollPane scrollPane;
	String str[] = { "도서번호", "도서명", "저자", "출판사", "출판일자" }; // 컬럼명

	// DB연동
	Connection con = null;
	PreparedStatement stmt = null; // sql구문 실행
	ResultSet result = null; // select구문 사용시 필요

	// DB연동 시 필요
	String url = "jdbc:mysql://localhost/bookdb_schema?serverTimezone=Asia/Seoul"; // dbstudy스키마
	String user = "root";
	String passwd = "0000"; // MySQL에 저장한 root 계정의 비밀번호를 적어주면 된다.

	public BookAMD() {
		super("관리자_도서조회/추가/수정/삭제");

		setSize(800, 500);
		setVisible(false);
		setResizable(false); // 사이즈조정 불가능
		setLocationRelativeTo(null); // 창위치 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		///////////////////////////////////////////////////
		// StringLabel
		// searchbtn, homebtn

		JLabel StringLabel = new JLabel("소장자료");
		StringLabel.setFont(new Font("굴림", Font.BOLD, 30));
		StringLabel.setBounds(39, 32, 160, 50);
		getContentPane().add(StringLabel);

		JButton searchbtn = new JButton("검색");
		searchbtn.setFont(new Font("굴림", Font.PLAIN, 12));
		searchbtn.setBounds(390, 32, 60, 40);
		getContentPane().add(searchbtn);

		JButton homebtn = new JButton("HOME");
		homebtn.setFont(new Font("굴림", Font.PLAIN, 15));
		homebtn.setBounds(609, 32, 135, 40);
		getContentPane().add(homebtn);

		connect();
		count();

		JLabel cntLabel = new JLabel("(총 도서수 : " + cnt + "권)");
		cntLabel.setFont(new Font("굴림", Font.PLAIN, 13));
		cntLabel.setBounds(39, 436, 135, 15);
		getContentPane().add(cntLabel);

		JLabel doubleclickLabel = new JLabel("*입력: 도서정보 입력 /삭제: 도서명만 '직접'입력/ 수정: 수정할 도서 클릭(자동입력) 후 수정사항 입력_도서번호 수정X");
		doubleclickLabel.setFont(new Font("굴림", Font.PLAIN, 11));
		doubleclickLabel.setBounds(151, 431, 593, 26);
		getContentPane().add(doubleclickLabel);

		homebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new BookManagement();
			}
		});

		/////////////////////////////////////////////////////////
		/* 도서명,저자,출판사 입력 - 추가버튼 */
//		라벨 noLabel, titleLabel, authorLabel, publisherLabel 
//		텍스트필드 noText, titleText, authorText, publisherText

		JLabel noLabel = new JLabel("번호");
		noLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		noLabel.setBounds(39, 92, 31, 40);
		getContentPane().add(noLabel);

		noText = new JTextField();
		noText.setFont(new Font("굴림", Font.PLAIN, 15));
		noText.setBounds(70, 92, 69, 40);
		getContentPane().add(noText);
		noText.setColumns(10);

		JLabel titleLabel = new JLabel("도서명");
		titleLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		titleLabel.setBounds(151, 92, 46, 40);
		getContentPane().add(titleLabel);

		titleText = new JTextField();
		titleText.setFont(new Font("굴림", Font.PLAIN, 15));
		titleText.setBounds(197, 92, 160, 40);
		getContentPane().add(titleText);
		titleText.setColumns(10);

		JLabel authorLabel = new JLabel("저자");
		authorLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		authorLabel.setBounds(361, 92, 31, 40);
		getContentPane().add(authorLabel);

		authorText = new JTextField();
		authorText.setFont(new Font("굴림", Font.PLAIN, 15));
		authorText.setBounds(393, 93, 69, 40);
		getContentPane().add(authorText);
		authorText.setColumns(10);

		JLabel publisherLabel = new JLabel("출판사");
		publisherLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		publisherLabel.setBounds(472, 91, 46, 40);
		getContentPane().add(publisherLabel);

		publisherText = new JTextField();
		publisherText.setFont(new Font("굴림", Font.PLAIN, 15));
		publisherText.setBounds(519, 93, 69, 40);
		getContentPane().add(publisherText);
		publisherText.setColumns(10);

		JLabel yearLabel = new JLabel("출판일자");
		yearLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		yearLabel.setBounds(595, 92, 61, 40);
		getContentPane().add(yearLabel);

		yearText = new JTextField("0000-01-01");
		yearText.setFont(new Font("굴림", Font.PLAIN, 15));
		yearText.setBounds(655, 93, 89, 40);
		getContentPane().add(yearText);
		yearText.setColumns(10);

		JButton insertbtn = new JButton("추가");
		insertbtn.setBounds(174, 32, 60, 40);
		getContentPane().add(insertbtn);

		JButton deletebtn = new JButton("삭제");
		deletebtn.setBounds(246, 32, 60, 40);
		getContentPane().add(deletebtn);

		JButton updatebtn = new JButton("수정");
		updatebtn.setBounds(318, 32, 60, 40);
		getContentPane().add(updatebtn);

		insertbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connect();
				insert();
			}
		});

		deletebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connect();
				delete();
			}
		});

		updatebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connect();
				update();
			}
		});

		searchbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connect();
				search();
			}
		});

		////////////////////////////////////////////////////////
		/* 테이블 */
		// JTable가운데 배치
		model = new DefaultTableModel(ob, str);
		booktable = new JTable(model);
		scrollPane = new JScrollPane(booktable);

		booktable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = booktable.getSelectedRow(); // 선택된 행
				if (row < 0) {
					return;
				}
				if (e.getClickCount() == 1) { // 클릭하면 도서명에 자동입력되는 마우스이벤트
					// int col = booktable.getSelectedColumn();
					DefaultTableModel tm = (DefaultTableModel) booktable.getModel();
					String no = (String) tm.getValueAt(row, 0); // 선택된 행, 선택된 열
					String title = (String) tm.getValueAt(row, 1);
					String author = (String) tm.getValueAt(row, 2);
					String publisher = (String) tm.getValueAt(row, 3);
					String year = (String) tm.getValueAt(row, 4);
					System.out.printf(no, title);
					System.out.printf("\n");
					noText.setText(no);
					titleText.setText(title);
					authorText.setText(author);
					publisherText.setText(publisher);
					yearText.setText(year);

					searchbtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							connect();
							search();
						}
					});
				}
			}
		});

		getContentPane().add("Center", scrollPane);
		scrollPane.setBounds(39, 142, 705, 290);

		JButton rentlistbtn = new JButton("대출목록조회");
		rentlistbtn.setBounds(462, 32, 135, 40);
		getContentPane().add(rentlistbtn);

		rentlistbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				new CheckRentList();
			}
		});

//		JButton reservelistbtn = new JButton("예약목록조회");
//		reservelistbtn.setBounds(483, 49, 114, 23);
//		getContentPane().add(reservelistbtn);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		reservelistbtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//
//				new CheckReserveList();
//			}
//		});

		// setVisible(true);

		/////////////////////////////////////////////////////////
		/* 이벤트 등록 */

		// 버튼에 ActionListener 등록
		insertbtn.addActionListener(this);
		deletebtn.addActionListener(this);
		updatebtn.addActionListener(this);
		searchbtn.addActionListener(this);

		// 텍스트필드에 addActionListener 등록
		noText.addActionListener(this);
		titleText.addActionListener(this);
		authorText.addActionListener(this);
		publisherText.addActionListener(this);
		yearText.addActionListener(this);

//		// 테이블에 MouseListener 등록
//		booktable.addMouseListener(this);

		///////////////////////////////////////////////////////

		// DB접속해 select문장 이용해 jtable에 보여주는 구문
		connect();
		select();

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

		//////////////////////////////////////////////////

		setVisible(true);
	}

	@Override // 텍스트필드에 번호,도서명,저자,출판사,출판년도 입력하면 DB에 저장된다.
	public void actionPerformed(ActionEvent e) {
//		ActionEvent 발생 시 실행되는 메소드 -> 버튼 클릭시, 입력 칸에 입력 후 엔터키를 쳤을 때
		Object o = e.getSource(); // 이벤트가 발생한 객체 리턴
		if (o == insertbtn) {
			insert();
		} else if (o == deletebtn) {
			delete();
		} else if (o == updatebtn) {
			update();
		} else if (o == searchbtn) {
			search();
		}
	}

	public void clear() {
		// 재입력을 위한 필드 초기화
		noText.setText("");
		titleText.setText("");
		authorText.setText("");
		publisherText.setText("");
		yearText.setText("");
		noText.requestFocus();
	}

	private void connect() {
		try {
			// 접속할 드라이버 메모리에 올리기
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 접속하기 위한 메서드(접속 url, 계정명, 계정암호)
			con = DriverManager.getConnection("jdbc:mysql://localhost/bookdb_schema?serverTimezone=Asia/Seoul", "root",
					"0000");
			// stmt = con.createStatement(); // 추가함
			System.out.println("접속 : " + con);
		} catch (Exception e) {
			System.out.println("DB접속 오류 : " + e);
		}
	}

	public void select() {
		try {
			// 실행할 sql문장 작성
			String sql = "select * from books";
			stmt = con.prepareStatement(sql);
			result = stmt.executeQuery(sql); // select문장

			// books테이블에서 불러오기
			while (result.next()) {
				String no = result.getString("BOOK_NO");
				String title = result.getString("BOOK_TITLE");
				String author = result.getString("AUTHOR");
				String publisher = result.getString("PUBLISHER");
				String year = result.getString("PUBLISHER_YEAR");

				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
				Object data[] = { no, title, author, publisher, year };
				model.addRow(data);
				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year);// 콘솔출력

			}
		} catch (Exception e) {
			System.out.println("select() 실행오류 : " + e);
		}
	}

	public void insert() {
		// model에 저장한 데이터를 mysql테이블에 저장(insert)
		try {
			// values('1','데이터베이스','김영희','한빛출판사','2022-02-22');
			String insertStr = "insert into books VALUES(?,?,?,?,?)";
			stmt = con.prepareStatement(insertStr);
			stmt.setString(1, noText.getText());
			stmt.setString(2, titleText.getText());
			stmt.setString(3, authorText.getText());
			stmt.setString(4, publisherText.getText());
			stmt.setString(5, yearText.getText());

			// 값 입력 안되면 넘어가지 않게
			if (noText.getText().equals("") || titleText.getText().equals("") || authorText.getText().equals("")
					|| publisherText.getText().equals("") || yearText.getText().equals("")) {
			} else {
				Object data[] = { noText.getText(), titleText.getText(), authorText.getText(), publisherText.getText(),
						yearText.getText() };
				model.addRow(data);
				clear();
			}

//			반환값
			int insert = stmt.executeUpdate();
			System.out.println("insert()성공 유무 : " + insert);

			// select조회
			result = stmt.executeQuery("select * from books");
			while (result.next()) {
				String no = result.getString("BOOK_NO");
				String title = result.getString("BOOK_TITLE");
				String author = result.getString("AUTHOR");
				String publisher = result.getString("PUBLISHER");
				String year = result.getString("PUBLISHER_YEAR");
				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year); // 콘솔 출력
			}

		} catch (Exception e2) {
			System.out.println("books테이블에 데이터 입력 실패" + e2);
		}
	}

	public void delete() {
		try { // 책제목으로 삭제
			model.setNumRows(0);
			String deleteStr = "DELETE FROM books where BOOK_TITLE = \'" + titleText.getText() + "\'";
			stmt = con.prepareStatement(deleteStr);

			int delete = stmt.executeUpdate();
			System.out.println("delete()성공 유무 : " + delete);

			clear();

			// books테이블에서 불러오기
			result = stmt.executeQuery("select * from books");
			while (result.next()) {
				String no = result.getString("BOOK_NO");
				String title = result.getString("BOOK_TITLE");
				String author = result.getString("AUTHOR");
				String publisher = result.getString("PUBLISHER");
				String year = result.getString("PUBLISHER_YEAR");

				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
				Object data[] = { no, title, author, publisher, year };
				model.addRow(data);
				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year);// 콘솔출력

			}

		} catch (Exception e2) {
			System.out.println("books테이블에 데이터 삭제 실패" + e2);
		}

	}

	public void update() { // where조건 걸어주지 않으면 전체 도서정보 변경됨!
		try { // 도서번호 기준으로 수정
			model.setNumRows(0);
			String inputno = noText.getText();

			String updateStr = "UPDATE books SET BOOK_TITLE=\'" + titleText.getText() + "\', AUTHOR=\'"
					+ authorText.getText() + "\', PUBLISHER=\'" + publisherText.getText() + "\', PUBLISHER_YEAR=\'"
					+ yearText.getText() + "\' WHERE BOOK_NO = \'" + inputno + "\'";
			stmt = con.prepareStatement(updateStr);

			int update = stmt.executeUpdate();
			System.out.println("update()성공 유무 : " + update);

			clear();

			// books테이블에서 불러오기
			result = stmt.executeQuery("select * from books");
			while (result.next()) {
				String no = result.getString("BOOK_NO");
				String title = result.getString("BOOK_TITLE");
				String author = result.getString("AUTHOR");
				String publisher = result.getString("PUBLISHER");
				String year = result.getString("PUBLISHER_YEAR");

				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
				Object data[] = { no, title, author, publisher, year };
				model.addRow(data);
				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year);// 콘솔출력

			}

		} catch (Exception e2) {
			System.out.println("books테이블에 데이터 수정 실패" + e2);
		}
	}

	/////////////////////////////////////////////////

	// *버튼 누르면 검색결과 나옴*//
	public void search() {
		/* 도서명으로 검색 */
		try

		{
			// 실행할 sql문장 작성
			model.setNumRows(0);
			String title_searchStr = "select * from books WHERE BOOK_TITLE LIKE '%" + titleText.getText() + "%'";
			stmt = con.prepareStatement(title_searchStr);
			result = stmt.executeQuery(title_searchStr);

			// books테이블에서 불러오기
			while (result.next()) {
				String no = result.getString("BOOK_NO");
				String title = result.getString("BOOK_TITLE");
				String author = result.getString("AUTHOR");
				String publisher = result.getString("PUBLISHER");
				String year = result.getString("PUBLISHER_YEAR");

				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
				Object data[] = { no, title, author, publisher, year };
				model.addRow(data);
				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year);// 콘솔출력

			}
		} catch (Exception e) {
			System.out.println("search() 실행오류 : " + e);
		}
//		/* 번호로 검색 */
//		try {
//			// 실행할 sql문장 작성
//			model.setNumRows(0);
//			String no_searchStr = "select * from books WHERE BOOK_NO LIKE '%" + noText.getText() + "%'";
//			stmt = con.prepareStatement(no_searchStr);
//			result = stmt.executeQuery(no_searchStr);
//
//			// books테이블에서 불러오기
//			while (result.next()) {
//				String no = result.getString("BOOK_NO");
//				String title = result.getString("BOOK_TITLE");
//				String author = result.getString("AUTHOR");
//				String publisher = result.getString("PUBLISHER");
//				String year = result.getString("PUBLISHER_YEAR");
//
//				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
//				Object data[] = { no, title, author, publisher, year };
//				model.addRow(data);
//				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year);// 콘솔출력
//
//			}
//		} catch (Exception e) {
//			System.out.println("search() 실행오류 : " + e);
//		}

//		/* 작가로 검색 */
//		try {
//			// 실행할 sql문장 작성
//			model.setNumRows(0);
//			String author_searchStr = "select * from books WHERE AUTHOR LIKE '%" + authorText.getText() + "%'";
//			stmt = con.prepareStatement(author_searchStr);
//			result = stmt.executeQuery(author_searchStr);
//
//			// books테이블에서 불러오기
//			while (result.next()) {
//				String no = result.getString("BOOK_NO");
//				String title = result.getString("BOOK_TITLE");
//				String author = result.getString("AUTHOR");
//				String publisher = result.getString("PUBLISHER");
//				String year = result.getString("PUBLISHER_YEAR");
//
//				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
//				Object data[] = { no, title, author, publisher, year };
//				model.addRow(data);
//				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year);// 콘솔출력
//
//			}
//		} catch (Exception e) {
//			System.out.println("search() 실행오류 : " + e);
//		}
//
//		/* 출판사로 검색 */
//		try {
//			// 실행할 sql문장 작성
//			model.setNumRows(0);
//			String publisher_searchStr = "select * from books WHERE PUBLISHER LIKE '%" + publisherText.getText() + "%'";
//			stmt = con.prepareStatement(publisher_searchStr);
//			result = stmt.executeQuery(publisher_searchStr);
//
//			// books테이블에서 불러오기
//			while (result.next()) {
//				String no = result.getString("BOOK_NO");
//				String title = result.getString("BOOK_TITLE");
//				String author = result.getString("AUTHOR");
//				String publisher = result.getString("PUBLISHER");
//				String year = result.getString("PUBLISHER_YEAR");
//
//				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
//				Object data[] = { no, title, author, publisher, year };
//				model.addRow(data);
//				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year);// 콘솔출력
//
//			}
//		} catch (Exception e) {
//			System.out.println("search() 실행오류 : " + e);
//		}

	}

	public void count() {
		// 기본정보 출력
		try {
			// 실행할 sql문장 작성
			String sql = "select * from books;";
			stmt = con.prepareStatement(sql);
			result = stmt.executeQuery(sql); // select문장

			cnt = 0;
			while (result.next()) {
				String no = result.getString("BOOK_NO");
				cnt = cnt + 1;
			}
			System.out.println("도서 총 개수 : " + cnt);

		} catch (Exception e) {
			System.out.println("select() 실행오류 : " + e);
		}
	}

	public static void main(String[] args) {
		BookAMD b = new BookAMD();
	}
}
