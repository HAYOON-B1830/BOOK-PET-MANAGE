package memberPackage;

public class Operator {
	Database db = null;
	LoginScreen ls = null;
	JoinScreen js = null;

	public static void main(String[] args) {
		System.setProperty("sun.java2d.uiScale", "1.0");

		Operator opt = new Operator();
		opt.db = new Database();
		opt.ls = new LoginScreen(opt);
//		opt.js = new JoinScreen(opt);
		
	}
}