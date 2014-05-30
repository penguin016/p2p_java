import java.awt.*;
import java.awt.event.*;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RegisterFrame extends Frame implements ActionListener {

	private static final long serialVersionUID = 1L;

	Label label1, label2, label3;
	TextField text1, text2, text3;
	Button button1, button2;
	Box baseBox, box1, box2, box3, box4;
	
	RegisterFrame(String s) {
		setTitle(s);
		this.setBackground(Color.white);
		label1 = new Label("用户：", Label.LEFT);
		label1.setFont(new Font("Serif", Font.PLAIN, 20));
		label2 = new Label("密码：", Label.LEFT);
		label2.setFont(new Font("Serif", Font.PLAIN, 20));
		label3 = new Label("确认密码：", Label.LEFT);
		label3.setFont(new Font("Serif", Font.PLAIN, 20));
		button1 = new Button("提交");
		button2 = new Button("重填");

		text1 = new TextField(12);
		text1.setFont(new Font("Serif", Font.PLAIN, 20));
		text2 = new TextField(12);
		text2.setFont(new Font("Serif", Font.PLAIN, 20));
		text3 = new TextField(12);
		text3.setFont(new Font("Serif", Font.PLAIN, 20));

		text2.setEchoChar('*');
		text3.setEchoChar('*');

		box1 = Box.createHorizontalBox();
		box1.add(label1);
		box1.add(Box.createHorizontalStrut(10));
		box1.add(text1);

		box2 = Box.createHorizontalBox();
		box2.add(label2);
		box2.add(Box.createHorizontalStrut(10));
		box2.add(text2);

		box3 = Box.createHorizontalBox();
		box3.add(label3);
		box3.add(Box.createHorizontalStrut(10));
		box3.add(text3);

		box4 = Box.createHorizontalBox();
		box4.add(button1);
		box4.add(Box.createHorizontalStrut(40));
		box4.add(button2);

		baseBox = Box.createVerticalBox();// 垂直支撑
		baseBox.add(Box.createVerticalStrut(30));
		baseBox.add(box1);
		baseBox.add(Box.createVerticalStrut(30));
		baseBox.add(box2);
		baseBox.add(Box.createVerticalStrut(30));
		baseBox.add(box3);
		baseBox.add(Box.createVerticalStrut(50));
		baseBox.add(box4);

		add(baseBox);

		button1.addActionListener(this);
		button2.addActionListener(this);

		setBounds(300, 300, 500, 400);
		setLayout(new FlowLayout());
		setVisible(true);
		// validate();

		class MyWindowMonitor extends WindowAdapter {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}
		}
		this.addWindowListener(new MyWindowMonitor());
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button1)// 处理登录界面
		{
			String label1 = text1.getText();
			String label2 = text2.getText();
			String label3 = text3.getText();
			if (label1 == null) {
				JFrame frame_error = new JFrame("ERROR");
				JLabel label = new JLabel();
				label.setFont(new Font("Serif", Font.PLAIN, 20));
				frame_error.add(label);
				frame_error.setSize(400, 200);
				frame_error.setLocation(500, 500);
				frame_error.setVisible(true);
				label.setText("  用户名不能为空！");
			} else {
				if (label2.equals(label3)) {
					client1 cli = new client1();
					cli.str1 = label1;
					cli.str2 = label3;
					cli.client("Register");
					this.dispose();
				} else {
					JFrame frame_error = new JFrame("ERROR");
					JLabel label = new JLabel();
					label.setFont(new Font("Serif", Font.PLAIN, 20));
					frame_error.add(label);
					frame_error.setSize(400, 200);
					frame_error.setLocation(500, 500);
					frame_error.setVisible(true);
					label.setText("  密码输入有误！");
					text1.setText(null);
					text2.setText(null);
					text3.setText(null);
				}
			}
		}
		if (e.getSource() == button2) {
			text1.setText(null);
			text2.setText(null);
			text3.setText(null);
		}
	}
}
