import java.awt.Font;
import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;

class MyThread extends Thread {

}

class R implements Runnable {

	static String Name;
	static int Port;
	ServerSocket server = null;
	Socket socket = null;
	BufferedReader br = null;
	PrintWriter pw = null;

	public void run() {
		while (true) {
			try {
				// 传输数据
				System.out.println(Port);
				server = new ServerSocket(Port);

				socket = server.accept();
				System.out.println("ServerSocket Start:" + server);
				System.out.println("Connection accept socket:" + socket);
				br = new BufferedReader(new InputStreamReader(socket
						.getInputStream()));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream())), true);
				String str;
				String str1 = null, str2 = null, str3 = null, str4 = null;
				int count = 0;
				while (true) {
					str = br.readLine();
					System.out.println("Client Socket Message:" + str);
					pw.println("Message Received");
					pw.flush();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (str.equals("Userlogin")) {
						count = 1;
						continue;
					}
					if (str.equals("Register")) {
						count = 2;
						continue;
					}
					if (str.equals("END")) {
						pw.println("END");
						pw.flush();
						break;
					}
					if (str.equals("Upload")) {
						count = 3;
						continue;
					}
					if (str.equals("Refresh")) {
						count = 4;
						continue;
					}
					if (str.equals("Download")) {
						count = 5;
						continue;
					}
					if (str.equals("Cleck")) {
						count = 6;
						continue;
					}
					if (str.equals("Delete")) {
						count = 7;
						continue;
					}
					if (str.equals("Search")) {
						count = 8;
						continue;
					}
					if (str.equals("Close")) {
						count = 9;
						continue;
					}
					if (str1 == null) {
						str1 = str;
						continue;
					}
					if (str2 == null) {
						str2 = str;
						continue;
					}
					if (str3 == null) {
						str3 = str;
					}
					str4 = str;
				}
				pw.println("OVER");
				pw.flush();
				if (count == 1)
					login(str1, str2, str3);
				if (count == 2)
					register(str1, str2, str3);
				if (count == 3)
					upload(str1, str2, str3, str4);
				if (count == 4)
					refresh(str1);
				if (count == 5)
					download(str1, str2);
				if (count == 6)
					cleck(str1);
				if (count == 7)
					delete(str1, str2);
				if (count == 8)
					search(str1, str2);
				if (count == 9)
					close(str1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				System.out.println("Close.....");
				try {
					br.close();
					pw.close();
					socket.close();
					server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void close(String str1) {// 关闭
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/share";
		String user = "root";
		String password = "000";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Connection success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query = null;
		query = "update clientinf set UserOnline='No' where User='" + str1
				+ "'";
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void search(String str1, String str2) {// 用户搜索
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/share";
		String user = "root";
		String password = "000";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Connection success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query = null;
		query = "select* from clientinf where Name='" + str1 + "'";
		ResultSet result = null;
		try {
			result = statement.executeQuery(query);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int row = 0;
			if (result == null) {
				JFrame frame_error = new JFrame("ERROR");
				JLabel label = new JLabel();
				label.setFont(new Font("Serif", Font.PLAIN, 30));
				frame_error.add(label);
				frame_error.setSize(500, 200);
				frame_error.setLocation(500, 500);
				frame_error.setVisible(true);
				label.setText("     还没有共享此文件的用户！");
			} else {
				MainFrame frame = new MainFrame(str2);
				while (result.next()) {
					Vector rowData = null;
					frame.model.addRow(rowData);
					frame.table1.setValueAt(result.getString(1), row, 0);
					frame.table1.setValueAt(result.getString(2), row, 1);
					frame.table1.setValueAt(result.getString(3), row, 2);
					frame.table1.setValueAt(result.getString(4), row, 3);
					frame.table1.setValueAt(result.getString(6), row, 4);
					frame.table1.setValueAt(result.getString(7), row, 5);
					frame.table1.setValueAt(result.getString(8), row, 6);
					row++;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void delete(String str1, String str2) {// 用户删除
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/share";
		String user = "root";
		String password = "000";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Connection success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query1 = null, query2 = null, query3 = null;
		query1 = "select* from clientinf where User='" + str2 + "'";
		int count = 0;
		ResultSet result = null;
		try {
			result = statement.executeQuery(query1);
			result.last();
			count = result.getRow();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (count == 1) {
			query2 = "update set Name=null,Size=null,Type=null where Name='"
					+ str1 + "' and User='" + str2 + "'";
			try {
				statement.executeUpdate(query2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			query3 = "delete from clientinf where Name='" + str1
					+ "' and User='" + str2 + "'";
			try {
				statement.executeUpdate(query3);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void cleck(String str1) {// 用户查看
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/share";
		String user = "root";
		String password = "000";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Connection success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query = null;
		query = "select* from clientinf where User='" + str1 + "'";
		ResultSet result = null;
		try {
			result = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainFrame frame = new MainFrame(str1);
		try {
			int row = 0;
			while (result.next()) {
				Vector rowData = null;
				frame.model0.addRow(rowData);
				frame.table2.setValueAt(result.getString(1), row, 0);
				frame.table2.setValueAt(result.getString(2), row, 1);
				frame.table2.setValueAt(result.getString(3), row, 2);
				frame.table2.setValueAt(result.getString(4), row, 3);
				frame.table2.setValueAt(result.getString(6), row, 4);
				frame.table2.setValueAt(result.getString(7), row, 5);
				frame.table2.setValueAt(result.getString(8), row, 6);
				row++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void download(String str1, String str2) {// 用户下载
		// TODO Auto-generated method stub
		String filepath = "e:\\upload\\pic.jpg";
		File file = new File(filepath);
		ServerSocket server = null;
		Socket socket = null;
		int port = Integer.parseInt(str2);
		try {
			server = new ServerSocket(port + 12);
			System.out.println(port + 12);
			socket = server.accept();
			System.out.println("ServerSocket Start:" + server);
			System.out.println("Connection accept socket:" + socket);
			DataInputStream fiss = new DataInputStream(new BufferedInputStream(
					new FileInputStream(filepath)));
			DataOutputStream ps = new DataOutputStream(socket.getOutputStream());
			ps.writeUTF(file.getName());
			ps.flush();
			ps.writeLong((long) file.length());
			ps.flush();

			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];
			while (true) {
				int read = 0;
				if (fiss != null) {
					read = fiss.read(buf);
				}
				if (read == -1) {
					break;
				}
				ps.write(buf, 0, read);
			}
			ps.flush();
			fiss.close();
			server.close();
			socket.close();
			System.out.println("文件传输完成");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	private static void refresh(String name) {// 刷新操作
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/share";
		String user = "root";
		String password = "000";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Connection success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query = null;
		query = "select* from clientinf";
		ResultSet result = null;
		try {
			result = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainFrame frame = new MainFrame(name);
		try {
			int row = 0;
			while (result.next()) {
				Vector rowData = null;
				frame.model.addRow(rowData);
				frame.table1.setValueAt(result.getString(1), row, 0);
				frame.table1.setValueAt(result.getString(2), row, 1);
				frame.table1.setValueAt(result.getString(3), row, 2);
				frame.table1.setValueAt(result.getString(4), row, 3);
				frame.table1.setValueAt(result.getString(6), row, 4);
				frame.table1.setValueAt(result.getString(7), row, 5);
				frame.table1.setValueAt(result.getString(8), row, 6);
				row++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void upload(String str1, String str2, String str3,
			String str4) {// 用户上传文件
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/share";
		String user = "root";
		String password = "000";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Connection success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query1 = null, query2 = null, query3 = null;
		query1 = "select* from clientinf where User='" + str4 + "'";
		ResultSet result = null;
		String ip = null;
		int port = 0;
		String pass = null;
		String name = null;
		try {
			result = statement.executeQuery(query1);
			while (result.next()) {
				ip = result.getString(6);
				port = result.getInt(7);
				pass = result.getString(5);
				name = result.getString(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (name.equals("null")) {
				query2 = "update clientinf set Name='" + str1 + "',Type='"
						+ str2 + "',Size='" + str3 + "' where User='" + str4
						+ "'";
				statement.executeUpdate(query2);
			} else if (name.equals(str1)) {
				JFrame frame_error = new JFrame("ERROR");
				JLabel label = new JLabel();
				label.setFont(new Font("Serif", Font.PLAIN, 30));
				frame_error.add(label);
				frame_error.setSize(500, 200);
				frame_error.setLocation(500, 500);
				frame_error.setVisible(true);
				label.setText("     您已经上传了该文件！");
			} else {
				query3 = "insert into clientinf(Name,Type,Size,User,password,Location,Port,UserOnline)values('"
						+ str1
						+ "','"
						+ str2
						+ "','"
						+ str3
						+ "','"
						+ str4
						+ "','" + pass + "','" + ip + "'," + port + ",'YES')";
				statement.executeUpdate(query3);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void register(String str1, String str2, String str3) {// 处理注册用户
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/share";
		String user = "root";
		String password = "000";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Connection success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query = null, query0 = null;
		query0 = "select* from clientinf where User='" + str1 + "'";
		ResultSet result = null;
		try {
			result = statement.executeQuery(query0);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (result.next() == true) {
				JFrame frame_error = new JFrame("ERROR");
				JLabel label = new JLabel();
				label.setFont(new Font("Serif", Font.PLAIN, 30));
				frame_error.add(label);
				frame_error.setSize(500, 200);
				frame_error.setLocation(500, 500);
				frame_error.setVisible(true);
				label.setText("  重名用户，请重新命名！");
			} else {
				query = "insert into clientinf(Name,Type,Size,User,password,Location,Port,UserOnline)values('null','null','null','"
						+ str1
						+ "','"
						+ str2
						+ "','"
						+ str3
						+ "',"
						+ Port
						+ ",'No')";
				statement.executeUpdate(query);
				JFrame frame_error = new JFrame("SUCCESS");
				JLabel label = new JLabel();
				label.setFont(new Font("Serif", Font.PLAIN, 30));
				frame_error.add(label);
				frame_error.setSize(500, 200);
				frame_error.setLocation(500, 500);
				frame_error.setVisible(true);
				label.setText("  注册成功！");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void login(String name, String userpassword, String ip) {// 处理登陆用户
		Name = name;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/share";
		String user = "root";
		String password = "000";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Connection success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query1 = null, query2 = null, query3 = null;
		query1 = "select* from clientinf where User='" + name
				+ "'and password='" + userpassword + "'";
		ResultSet result = null;

		try {
			result = statement.executeQuery(query1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (result.next() == false) {
				JFrame frame_error = new JFrame("ERROR");
				JLabel label = new JLabel();
				label.setFont(new Font("Serif", Font.PLAIN, 30));
				frame_error.add(label);
				frame_error.setSize(500, 200);
				frame_error.setLocation(500, 500);
				frame_error.setVisible(true);
				label.setText("  该用户不存在，新用户请先注册！");
			} else {
				query3 = "update clientinf set UserOnline='YES',Port=" + Port
						+ " ,Location='" + ip + "' where User='" + name + "'";
				statement.executeUpdate(query3);
				query2 = "select* from clientinf";
				try {
					result = statement.executeQuery(query2);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MainFrame frame = new MainFrame(name);
				try {
					int row = 0;
					while (result.next()) {
						Vector rowData = null;
						frame.model.addRow(rowData);
						frame.table1.setValueAt(result.getString(1), row, 0);
						frame.table1.setValueAt(result.getString(2), row, 1);
						frame.table1.setValueAt(result.getString(3), row, 2);
						frame.table1.setValueAt(result.getString(4), row, 3);
						frame.table1.setValueAt(result.getString(6), row, 4);
						frame.table1.setValueAt(result.getString(7), row, 5);
						frame.table1.setValueAt(result.getString(8), row, 6);
						row++;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

public class server {
	public static void main(String[] agrs) {
		ServerSocket server = null;
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		int port = 0;
		while (true) {
			try {
				server = new ServerSocket(25);
				socket = server.accept();
				System.out.println("ServerSocket Start:" + server);
				System.out.println("Connection accept socket:" + socket);
				br = new BufferedReader(new InputStreamReader(socket
						.getInputStream()));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream())), true);
				port = new ServerSocket(0).getLocalPort() + 5;
				pw.println(Integer.toString(port));
				pw.flush();
				String str = br.readLine();
				if (str.equals("OVER")) {
					br.close();
					pw.close();
					socket.close();
					server.close();
					R r = new R();
					r.Port = port;
					Thread t = new Thread(r);
					t.start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}