import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends Frame implements ItemListener, ActionListener {
	private static final long serialVersionUID = 1L;

	static String tit;
	Label label1;
	TextField searchtext;
	TextArea resulttext, clienttext;
	Button button1, button2, button3, button4, button5, button6;
	Box baseBox, box1, box2, box3, box4, box5, box6, box7;
	JList lst1, lst2;
	JTable table1, table2;
	DefaultTableModel model, model0;

	MainFrame(String s) {
		setTitle(s);
		tit = s;
		searchtext = new TextField(20);
		searchtext.setFont(new Font("黑体", Font.PLAIN, 20));
		button1 = new Button("搜索");
		button2 = new Button("下载所选项");
		button3 = new Button("刷新");
		button4 = new Button("上传文件");
		resulttext = new TextArea(null, 15, 60,
				TextArea.SCROLLBARS_VERTICAL_ONLY);
		resulttext.setFont(new Font("Serif", Font.PLAIN, 15));
		resulttext.append("Name  " + "Type  " + "Size  " + "User  "
				+ "Location  " + "Port  " + "UserOnline" + "\r\n");
		clienttext = new TextArea(null, 5, 10,
				TextArea.SCROLLBARS_VERTICAL_ONLY);
		clienttext.setFont(new Font("Serif", Font.PLAIN, 15));
		button5 = new Button("查看");
		button6 = new Button("删除所选项");

		String[] headers = { "Name", "Type", "Size", "User", "Location",
				"Port", "UserOnline" };
		model = new DefaultTableModel(headers, 0);
		model0 = new DefaultTableModel(headers, 0);

		table1 = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table1);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(500, 200));

		table2 = new JTable(model0);
		JScrollPane scrollPane0 = new JScrollPane(table2);
		scrollPane0
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane0.setPreferredSize(new Dimension(500, 100));

		box1 = Box.createHorizontalBox();
		box1.add(searchtext);
		box1.add(Box.createHorizontalStrut(10));
		box1.add(button1);

		box2 = Box.createHorizontalBox();
		box2.add(scrollPane);

		box3 = Box.createHorizontalBox();
		box3.add(button2);
		box3.add(Box.createHorizontalStrut(10));
		box3.add(button3);
		box3.add(Box.createHorizontalStrut(10));
		box3.add(button4);

		box4 = Box.createVerticalBox();
		box4.add(box1);
		box4.add(Box.createVerticalStrut(20));
		box4.add(box2);
		box4.add(Box.createVerticalStrut(10));
		box4.add(box3);
		box4.setBorder(BorderFactory.createTitledBorder("用户搜索"));

		box5 = Box.createHorizontalBox();
		box5.add(scrollPane0);

		box6 = Box.createHorizontalBox();
		box6.add(button5);
		box6.add(Box.createHorizontalStrut(50));
		box6.add(button6);

		box7 = Box.createVerticalBox();
		box7.add(box5);
		box7.add(Box.createVerticalStrut(10));
		box7.add(box6);
		box7.setBorder(BorderFactory.createTitledBorder("用户查看上传文件"));

		baseBox = Box.createVerticalBox();
		baseBox.add(box4);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(box7);
		add(baseBox);

		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);

		setBounds(600, 600, 600, 550);
		setLayout(new FlowLayout());
		setVisible(true);

		class MyWindowMonitor extends WindowAdapter {
			public void windowClosing(WindowEvent e) {
				if (JOptionPane
						.showConfirmDialog(null, "退出", "提示",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					client1 cli = new client1();
					cli.str1 = tit;
					cli.client("Close");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setVisible(false);
					//System.exit(0);
				}
			}
		}
		this.addWindowListener(new MyWindowMonitor());
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("null")
	@Override
	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub
		if (e.getSource() == button1) {// 搜索
			String name = searchtext.getText();
			client1 cli = new client1();
			cli.str1 = name;
			cli.str2 = this.getTitle();
			cli.client("Search");
			this.dispose();
		}
		if (e.getSource() == button2) {// 下载文件
			int[] row = { 0 };
			row = table1.getSelectedRows();
			int count = 0;
			while (count < row.length) {
				if (row[count] != 0) {
					int r = row[count];
					count++;
					Vector result = (Vector) model.getDataVector().elementAt(r);
					String name = (String) result.get(0);
					String ison = (String) result.get(6);
					if (ison.equals("No")) {
						JFrame frame_error = new JFrame("ERROR");
						JLabel label = new JLabel();
						label.setFont(new Font("Serif", Font.PLAIN, 30));
						frame_error.add(label);
						frame_error.setSize(500, 200);
						frame_error.setLocation(500, 500);
						frame_error.setVisible(true);
						label.setText("  该用户不在线，无法建立连接！");
						break;
					} else if (name.equals("null")) {
						JFrame frame_error = new JFrame("ERROR");
						JLabel label = new JLabel();
						label.setFont(new Font("Serif", Font.PLAIN, 30));
						frame_error.add(label);
						frame_error.setSize(500, 200);
						frame_error.setLocation(500, 500);
						frame_error.setVisible(true);
						label.setText("  该用户还没有共享文件，无法下载！");
						break;
					} else {
						client1 cli = new client1();
						cli.str1 = (String) result.get(3);
						cli.str2 = (String) result.get(5);
						cli.client("Download");
						int port = Integer.parseInt((String) result.get(5)) + 12;
						String ip = (String) result.get(4);
						Socket socket = null;
						try {
							socket = new Socket(ip, port);
							System.out.println("socket=" + socket);
							String savepath = "f:\\download\\";
							int bufferSize = 8192;
							byte[] buf = new byte[bufferSize];
							int passedlen = 0;
							long len = 0;
							DataInputStream getMessageStream = new DataInputStream(
									new BufferedInputStream(socket
											.getInputStream()));
							DataInputStream inputStream = getMessageStream;
							savepath += inputStream.readUTF();
							DataOutputStream fileOut = new DataOutputStream(
									new BufferedOutputStream(
											new BufferedOutputStream(
													new FileOutputStream(
															savepath))));
							len = ((DataInputStream) inputStream).readLong();
							while (true) {
								int read = 0;
								if (inputStream != null) {
									read = inputStream.read(buf);
								}
								passedlen += read;
								if (read == -1) {
									break;
								}
								System.out.println("文件接收了"
										+ (passedlen * 100 / len) + "%\n");
								fileOut.write(buf, 0, read);
							}
							System.out.println("接收完成，文件存为" + savepath + "\n");
							fileOut.close();
						} catch (UnknownHostException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

					}

				}
			}

		}
		if (e.getSource() == button3) {// 刷新
			client1 cli = new client1();
			String str1 = this.getTitle();
			System.out.println(str1);
			cli.str1 = str1;
			cli.client("Refresh");
			this.dispose();
		}
		if (e.getSource() == button4) {// 上传文件
			String filepath = "e:\\upload\\pic.jpg";
			File file = new File(filepath);
			String name = file.getName();
			int size = 0;// 获得文件大小
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				size = fis.available();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JFileChooser chooser = new JFileChooser();// 获取文件类型
			String type = chooser.getTypeDescription(file);
			String str = this.getTitle();
			String str1 = name;
			String str3 = Integer.toString(size);
			String str2 = type;
			client1 cli = new client1();
			cli.str1 = str1;
			cli.str2 = str2;
			cli.str3 = str3;
			cli.str4 = str;
			cli.client("Upload");
		}
		if (e.getSource() == button5) {// 用户查看
			client1 cli = new client1();
			String str1 = this.getTitle();
			cli.str1 = str1;
			cli.client("Cleck");
			this.dispose();

		}
		if (e.getSource() == button6) {// 用户删除所选
			int[] row = { 0 };
			row = table2.getSelectedRows();
			System.out.println(row.length);
			int count = 0;
			while (count < row.length) {
				if (row[count] != 0) {
					int r = row[count];
					count++;
					System.out.println(row.length);
					Vector result = (Vector) model0.getDataVector()
							.elementAt(r);
					String Name = (String) result.get(0);
					String User = (String) result.get(3);
					client1 cli = new client1();
					cli.str1 = Name;
					cli.str2 = User;
					System.out.println(User);
					cli.client("Delete");
				}
			}
		}
	}
}
