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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

//!!변경사항 idText의 접근지정자 public으로 변경함
//회원기능 완성되면 rent_inserst()수정해야함

public class BookList extends JFrame implements ActionListener, TableCellRenderer {
	/* 사용자단 도서목록 조회/검색/대출/반납 */

	JLabel noLabel, titleLabel, authorLabel, publisherLabel; // 제목, 저자, 출판사
	JTextField noText, titleText, authorText, publisherText;
	JButton searchbtn, homebtn, rentbtn;
	JPanel panel; // JLabel, JTextArea 부착
	String rentable;
	int cnt;
	String rent_cnt; // 대여번호 카운트

	// JTable *******테이블 도서번호를 넣어야할지 고민해봐야함/ 도서코드로 해서 나중에 입력가능하게? 아닌데**********
	Object ob[][] = new Object[0][7];
	DefaultTableModel model; // 데이터 저장부분
	JTable booktable;
	JScrollPane scrollPane;
	String str[] = { "도서번호", "도서명", "저자", "출판사", "출판일자", "대출가능여부" }; // 컬럼명
	// 대출/예약 버튼을 JTABLE 안에 구현하는 게 안됨->버튼으로 대체함

	// DB연동
	Connection con = null;
	PreparedStatement stmt = null; // sql구문 실행
	ResultSet result = null; // select구문 사용시 필요

	// DB연동 시 필요
	String url = "jdbc:mysql://localhost/bookdb_schema?serverTimezone=Asia/Seoul"; // dbstudy스키마
	String user = "root";
	String passwd = "0000"; // MySQL에 저장한 root 계정의 비밀번호를 적어주면 된다.

	public BookList() {
		super("도서목록");

		setSize(800, 500);
		setVisible(false);
		setResizable(false); // 사이즈조정 불가능
		setLocationRelativeTo(null); // 창위치 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		///////////////////////////////////////////////
		// StringLabel 도서목록
		// homebtn, rentbtn, reservebtn 대출, 예약, 홈버튼

		JLabel StringLabel = new JLabel("도서목록");
		StringLabel.setFont(new Font("굴림", Font.BOLD, 30));
		StringLabel.setBounds(39, 32, 135, 50);
		getContentPane().add(StringLabel);

		JButton clearbtn = new JButton("X");
		clearbtn.setFont(new Font("굴림", Font.PLAIN, 15));
		clearbtn.setBounds(296, 32, 50, 40);
		getContentPane().add(clearbtn);

		clearbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();

			}
		});

		JButton rentbtn = new JButton("대출");
		rentbtn.setFont(new Font("굴림", Font.PLAIN, 15));
		rentbtn.setBounds(358, 32, 80, 40);
		getContentPane().add(rentbtn);

		JButton returnbtn = new JButton("반납");
		returnbtn.setFont(new Font("굴림", Font.PLAIN, 15));
		returnbtn.setBounds(450, 32, 80, 40);
		getContentPane().add(returnbtn);

		JButton homebtn = new JButton("HOME");
		homebtn.setFont(new Font("굴림", Font.PLAIN, 15));
		homebtn.setBounds(664, 32, 80, 40);
		getContentPane().add(homebtn);

		homebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new UserMain();
				setVisible(false); // 버튼 클릭 시 기존 창 닫기
			}
		});

		/////////////////////////////////////////////////////////
		/* 도서명,저자,출판사 입력 - 검색버튼 */
//		라벨 noLabel, titleLabel, authorLabel, publisherLabel 
//		텍스트필드 noText, titleText, authorText, publisherText

		JLabel noLabel = new JLabel("번호");
		noLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		noLabel.setBounds(39, 92, 31, 40);
		getContentPane().add(noLabel);

		noText = new JTextField();
		noText.setFont(new Font("굴림", Font.PLAIN, 15));
		noText.setBounds(70, 92, 90, 40);
		getContentPane().add(noText);
		noText.setColumns(10);

		JLabel titleLabel = new JLabel("도서명");
		titleLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		titleLabel.setBounds(172, 92, 46, 40);
		getContentPane().add(titleLabel);

		titleText = new JTextField();
		titleText.setFont(new Font("굴림", Font.PLAIN, 15));
		titleText.setBounds(230, 93, 183, 40);
		getContentPane().add(titleText);
		titleText.setColumns(10);

		JLabel authorLabel = new JLabel("저자");
		authorLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		authorLabel.setBounds(425, 92, 31, 40);
		getContentPane().add(authorLabel);

		authorText = new JTextField();
		authorText.setFont(new Font("굴림", Font.PLAIN, 15));
		authorText.setBounds(468, 93, 96, 40);
		getContentPane().add(authorText);
		authorText.setColumns(10);

		JLabel publisherLabel = new JLabel("출판사");
		publisherLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		publisherLabel.setBounds(576, 92, 52, 40);
		getContentPane().add(publisherLabel);

		publisherText = new JTextField();
		publisherText.setFont(new Font("굴림", Font.PLAIN, 15));
		publisherText.setBounds(628, 93, 116, 40);
		getContentPane().add(publisherText);
		publisherText.setColumns(10);

		JButton searchbtn = new JButton("도서명 검색");
		searchbtn.setFont(new Font("굴림", Font.PLAIN, 12));
		searchbtn.setBounds(542, 32, 110, 40);
		getContentPane().add(searchbtn);

		searchbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connect();
				search();
			}
		});

		////////////////////////////////////////////////////////////////////
		/* 테이블 */
		// JTable가운데 배치
		model = new DefaultTableModel(ob, str);
		booktable = new JTable(model);

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
					System.out.printf(no, title);
					System.out.printf("\n");
					noText.setText(no);
					titleText.setText(title);
					authorText.setText(author);
					publisherText.setText(publisher);

					rentbtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							connect();
							rent();
						}
					});

					returnbtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							connect();
							turn();
						}
					});

					searchbtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							connect();
							search();
						}
					});

				}
			}
		});

		scrollPane = new JScrollPane(booktable);
		getContentPane().add("Center", scrollPane);
		scrollPane.setBounds(39, 142, 705, 290);

		connect();
		count();
		JLabel cntLabel = new JLabel("(총 도서수 : " + cnt + "권)");
		cntLabel.setFont(new Font("굴림", Font.PLAIN, 13));
		cntLabel.setBounds(39, 438, 135, 15);
		getContentPane().add(cntLabel);

		JLabel stringLabel = new JLabel("*대출/반납: 해당 행 클릭 후 대출/반납 버튼 클릭 *검색: 도서번호/도서명/저자/출판사로 검색가능");
		stringLabel.setFont(new Font("굴림", Font.PLAIN, 13));
		stringLabel.setBounds(172, 438, 572, 15);
		getContentPane().add(stringLabel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 버튼에 ActionListenr 등록
		searchbtn.addActionListener(this);
		homebtn.addActionListener(this);
		rentbtn.addActionListener(this);
		clearbtn.addActionListener(this);

		// 텍스트필드에 addActionLinstener 등록
		noText.addActionListener(this);
		titleText.addActionListener(this);
		authorText.addActionListener(this);
		publisherText.addActionListener(this);

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

		setVisible(true);

	}

	@Override // 텍스트필드에 번호,도서명,저자,출판사,출판년도 입력하면 DB에 저장된다.
	public void actionPerformed(ActionEvent e) {
//		ActionEvent 발생 시 실행되는 메소드 -> 버튼 클릭시, 입력 칸에 입력 후 엔터키를 쳤을 때
		Object o = e.getSource(); // 이벤트가 발생한 객체 리턴
		if (o == searchbtn) { // 검색
			connect();
			search();
		} else if (o == rentbtn) { // 대출
			connect();
			rent();
		}
	}

	public void clear() {
		// 재입력을 위한 필드 초기화
		noText.setText("");
		titleText.setText("");
		authorText.setText("");
		publisherText.setText("");
		titleText.requestFocus();
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

	public void select() {
		// 기본정보 출력
		try {
			// 실행할 sql문장 작성
			String sql = "select * from books order by BOOK_NO";
			stmt = con.prepareStatement(sql);
			result = stmt.executeQuery(sql); // select문장

			// books테이블에서 불러오기
			while (result.next()) {
//				String str[] = {"도서번호", "도서명", "저자", "출판사" ,"출판년도", "대출가능여부", "대출/예약"}; // 컬럼명
				String no = result.getString("BOOK_NO");
				String title = result.getString("BOOK_TITLE");
				String author = result.getString("AUTHOR");
				String publisher = result.getString("PUBLISHER");
				String year = result.getString("PUBLISHER_YEAR");
				String isrent = result.getString("IS_RENT");
				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
				Object data[] = { no, title, author, publisher, year, isrent };
				model.addRow(data);
				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year + ", " + isrent);// 콘솔출력

			}

		} catch (Exception e) {
			System.out.println("select() 실행오류 : " + e);
		}
	}

	// *버튼 누르면 검색결과 나옴*//
	// 도서번호,도서명,저자,출판사로 검색가능
	public void search() {
		/* 도서명으로 검색 */
		try {
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
				String able = result.getString("IS_RENT");

				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
				Object data[] = { no, title, author, publisher, year, able };
				model.addRow(data);
				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year + ", " + able);// 콘솔출력
			}

		} catch (Exception e) {
			System.out.println("search() 실행오류 : " + e);
		}

//		////////////////////////////////////////////
//		/* 2.도서번호로 검색 */
//		try {
//			// 실행할 sql문장 작성
//			model.setNumRows(0);
//
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
//				String able = result.getString("IS_RENT");
//
//				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
//				Object data[] = { no, title, author, publisher, year, able };
//				model.addRow(data);
//				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year + ", " + able);// 콘솔출력
//			}
//
//		} catch (Exception e) {
//			System.out.println("search() 실행오류 : " + e);
//		}
//
//		//////////////////////////////////////////////////////////
//		/* 3.저자로 검색 */
//		try {
//			// 실행할 sql문장 작성
//			model.setNumRows(0);
//
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
//				String able = result.getString("IS_RENT");
//
//				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
//				Object data[] = { no, title, author, publisher, year, able };
//				model.addRow(data);
//				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year + ", " + able);// 콘솔출력
//			}
//
//		} catch (Exception e) {
//			System.out.println("search() 실행오류 : " + e);
//		}
//
//		//////////////////////////////////////////////////////////
//		/* 4.출판사로 검색 */
//		try {
//			// 실행할 sql문장 작성
//			model.setNumRows(0);
//
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
//				String able = result.getString("IS_RENT");
//
//				// object[]를 생성저장 해 model에 추가->JTable에서 결과 확인
//				Object data[] = { no, title, author, publisher, year, able };
//				model.addRow(data);
//				System.out.println(no + ", " + title + ", " + author + ", " + publisher + ", " + year + ", " + able);// 콘솔출력
//			}
//
//		} catch (Exception e) {
//			System.out.println("search() 실행오류 : " + e);
//		}

	}

	///////////////////////////////////////////////////////////////////////////

	public void rent() { // 도서 클릭 후 대출 버튼 누르면 (도서 클릭 시 정보 읽는)
		// 1. 대출 버튼 누르면
		// rent테이블에 레코드 추가(여기까지는 구현)
		// books테이블 대출중 변경
		books_update(); // books테이블 대출중 변경
		rent_insert(); // rent테이블에 레코드 추가
		model.setNumRows(0);
		select();
	}

	public void turn() { // 반납버튼 구현안됨********************
		// 2. 반납 버튼
		// rent테이블에 반납시간 추가
		// books 테이블 대출가능 변경
		books_update_able(); // books테이블 대출가능 변경
		rent_add(); // rent테이블에 레코드 추가
		model.setNumRows(0);
		select();

	}

	//////////////////////////////////////////////////////////////////////////
	/* 대출 */
	public void rent_insert() {
		try {

			// 현재날짜 불러옴
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd");
			Calendar now = Calendar.getInstance();
			String rtime = sdf.format(now.getTime());

			// 로그인한 회원id(Login.java) 가져옴
			// 다른 패키지에 있는 Login클래스ㅇ 변수 가져옴
//			Login lo = new Login();
//			String memberid = lo.idText.getText();

			// 책제목 자동입력된 거 가져와서
			String insertno = noText.getText();
			rent_cnt = "R" + insertno;
			String inserttitle = titleText.getText();

//			String rentsql = "insert into rent(rent_number, rent_book_number, rent_book_title, rent_user_id, rent_date) values(\'"
//					+ rent_cnt + "\', \'" + insertno + "\', \'" + inserttitle + "\' , \'" + memberid + "\', \'" + rtime
//					+ "\')on duplicate key update rent_date=\'" + rtime + "\', return_date=NULL";
//			// IF. 키가 중복될 경우 대출일자 현재일자로 업데이트, 반납일자는 NULL값으로

			String rentsql = "insert into rent(rent_number, rent_book_number, rent_book_title, rent_user_id, rent_date) values(\'"
					+ rent_cnt + "\', \'" + insertno + "\', \'" + inserttitle + "\' , 'hyuna2398', \'" + rtime
					+ "\')on duplicate key update rent_date=\'" + rtime + "\', return_date=NULL";
			// IF. 키가 중복될 경우 대출일자 현재일자로 업데이트, 반납일자는 NULL값으로

			stmt = con.prepareStatement(rentsql);
			int rentinsert = stmt.executeUpdate();
			System.out.println("rent삽입()성공 유무 : " + rentinsert);

		} catch (Exception e) {
			System.out.println("rent_insert() 실행오류 : " + e);
		}
	}

	public void books_update() {
		try {
			// 책제목 자동입력된 거 가져와서
			String insertno = noText.getText();

			String booksql = "UPDATE books SET IS_RENT='대출중' WHERE BOOK_NO=\'" + insertno + "\'";
			stmt = con.prepareStatement(booksql);
			while (result.next())
				if ("대출중" == result.getString("IS_RENT")) {
					return;
				}

			int bookrent = stmt.executeUpdate();
			System.out.println("book변경()성공 유무 : " + bookrent);

		} catch (Exception e) {
			System.out.println("books_update() 실행오류 : " + e);
		}
	}

	//////////////////////////////////////////////////////////////////////////
	/* 반납 */

	public void rent_add() {
		try {

			// 책제목 자동입력된 거 가져와서
			String insertno = noText.getText();
			rent_cnt = "R" + insertno;

			// 현재날짜 불러옴
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd");
			Calendar now = Calendar.getInstance();
			String rtime = sdf.format(now.getTime());

			// rent테이블에 추가
			String rentsql = "UPDATE rent SET return_date=\'" + rtime + "\' WHERE rent_book_number=\'" + insertno
					+ "\'";

			stmt = con.prepareStatement(rentsql);
			int rentinsert = stmt.executeUpdate();
			System.out.println("rent삽입()성공 유무 : " + rentinsert);

		} catch (Exception e) {
			System.out.println("rent_add() 실행오류 : " + e);
		}
	}

	public void books_update_able() {
		try {
			// 책제목 자동입력된 거 가져와서
			String insertno = noText.getText();

			String booksql = "UPDATE books SET IS_RENT='대출가능' WHERE BOOK_NO=\'" + insertno + "\'";
			stmt = con.prepareStatement(booksql);
			while (result.next())
				if ("대출가능" == result.getString("IS_RENT")) {
					return;
				}

			int bookrent = stmt.executeUpdate();
			System.out.println("book변경()성공 유무 : " + bookrent);

		} catch (Exception e) {
			System.out.println("books_update_able() 실행오류 : " + e);
		}
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
		new BookList();

	}
}
