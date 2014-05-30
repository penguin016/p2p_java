import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class client1 {

	String str1, str2, str3, str4;

	public void client(String s) {
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ip = addr.getHostAddress().toString();
		try {
			// 命令传送
			socket = new Socket("127.0.0.1", 25);
			System.out.println("socket=" + socket);
			br = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())));
			String str = br.readLine();
			int port = Integer.parseInt(str);
			pw.println("OVER");
			pw.flush();
			br.close();
			pw.close();
			socket.close();
			// 数据传输
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			socket = new Socket("127.0.0.1", port);
			System.out.println("socket=" + socket);
			br = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())));
			if (s.equals("Userlogin")) {
				pw.println(s);
				pw.flush();
				pw.println(str1);
				pw.flush();
				pw.println(str2);
				pw.flush();
				pw.println(ip);
				pw.flush();
				pw.println("END");
				pw.flush();
			}
			if (s.equals("Register")) {
				pw.println(s);
				pw.flush();
				pw.println(str1);
				pw.flush();
				pw.println(str2);
				pw.flush();
				pw.println(ip);
				pw.flush();
				pw.println("END");
				pw.flush();
			}
			if (s.equals("Upload")) {
				pw.println(s);
				pw.flush();
				pw.println(str1);
				pw.flush();
				pw.println(str2);
				pw.flush();
				pw.println(str3);
				pw.flush();
				pw.println(str4);
				pw.flush();
				pw.println("END");
				pw.flush();
			}
			if (s.equals("Refresh")) {
				pw.println(s);
				pw.flush();
				pw.println(str1);
				pw.flush();
				pw.println("END");
				pw.flush();
			}
			if (s.equals("Download")) {
				pw.println(s);
				pw.flush();
				pw.println(str1);
				pw.flush();
				pw.println(str2);
				pw.flush();
				pw.println("END");
				pw.flush();
			}
			if (s.equals("Cleck")) {
				pw.println(s);
				pw.flush();
				pw.println(str1);
				pw.flush();
				pw.println("END");
				pw.flush();
			}
			if (s.equals("Delete")) {
				pw.println(s);
				pw.flush();
				pw.println(str1);
				pw.flush();
				pw.println(str2);
				pw.flush();
				pw.println("END");
				pw.flush();
			}
			if (s.equals("Search")) {
				pw.println(s);
				pw.flush();
				pw.println(str1);
				pw.flush();
				pw.println(str2);
				pw.flush();
				pw.println("END");
				pw.flush();
			}
			if (s.equals("Close")) {
				pw.println(s);
				pw.flush();
				pw.println(str1);
				pw.flush();
				pw.println("END");
				pw.flush();
			}
			if(br.readLine().equals("END")){
				System.out.println(str);
				System.out.println("close......");
				br.close();
				pw.close();
				socket.close();
			}	
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
