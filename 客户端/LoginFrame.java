import java.awt.*;
import java.awt.event.*;
import javax.swing.Box;
import javax.swing.JOptionPane;

public class LoginFrame extends Frame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String DO_NOTHING_ON_CLOSE = null;

	Label label1, label2, label3;
	TextField text1, text2;
	Button button1, button2;
	Box baseBox, box1, box2, box3, box4;

	LoginFrame(String s) {
		setTitle(s);
		this.setBackground(Color.white);
		label1 = new Label("用户：", Label.CENTER);
		label1.setFont(new Font("Serif", Font.PLAIN, 20));
		label2 = new Label("密码：", Label.CENTER);
		label2.setFont(new Font("Serif", Font.PLAIN, 20));
		label3 = new Label("登录系统", Label.CENTER);
		label3.setFont(new Font("Serif", Font.PLAIN, 30));
		button1 = new Button("登录");
		button2 = new Button("注册");
		text1 = new TextField(15);
		text1.setFont(new Font("Serif", Font.PLAIN, 20));
		text2 = new TextField(15);
		text2.setFont(new Font("Serif", Font.PLAIN, 20));
		text2.setEchoChar('*');

		box1 = Box.createHorizontalBox();
		box1.add(label3);

		box2 = Box.createHorizontalBox();
		box2.add(label1);
		box2.add(Box.createHorizontalStrut(10));
		box2.add(text1);

		box3 = Box.createHorizontalBox();
		box3.add(label2);
		box3.add(Box.createHorizontalStrut(10));
		box3.add(text2);

		box4 = Box.createHorizontalBox();
		box4.add(button1);
		box4.add(Box.createHorizontalStrut(40));
		box4.add(button2);

		baseBox = Box.createVerticalBox();
		baseBox.add(box1);
		baseBox.add(Box.createVerticalStrut(30));
		baseBox.add(box2);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(box3);
		baseBox.add(Box.createVerticalStrut(20));
		baseBox.add(box4);
		add(baseBox);

		button1.addActionListener(this);
		button2.addActionListener(this);

		setBounds(100, 100, 500, 300);
		setLayout(new FlowLayout());
		setVisible(true);
		// validate();
		class MyWindowMonitor extends WindowAdapter {
			public void windowClosing(WindowEvent e) {
				if (JOptionPane
						.showConfirmDialog(null, "退出", "提示",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					setVisible(false);
					//System.exit(0);
				}
			}
		}
		this.addWindowListener(new MyWindowMonitor());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1)// 处理登录界面
		{
			String str1 = text1.getText();
			String str2 = text2.getText();
			client1 cli = new client1();
			cli.str1 = str1;
			cli.str2 = str2;
			cli.client("Userlogin");
			this.dispose();
		}
		if (e.getSource() == button2)// 处理登录界面
		{
			new RegisterFrame("新用户注册");
			this.dispose();
		}
	}

	public static void main(String[] args) throws Exception {
		LoginFrame log = new LoginFrame("欢迎登陆");
	}
}
