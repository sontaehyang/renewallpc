package saleson.shop.order.pg.kspay.domain;

import java.io.*;
import java.net.*;

import com.onlinepowers.framework.common.ServiceType;

/*
	Class Name : KSPaySocketBean
	
	최초 작성일자 : 2003/11/24
	최초 작성자   : 이훈구
	최종 수정일자 : 
	최종 수정자   : 
*/

public class KSPaySocketBean {

	private	Socket				socket;				//IPG_Server(C-Daemon)과 연결 소켓
	private DataInputStream		in;
  	private DataOutputStream	out;
  	private  String				IPAddr;
  	private  int					Port;
  	
  	private  String				SendURL;


	/*
	**	통신 전담 Private 메쏘드들 
	*/

	public KSPaySocketBean(String IPAddr, int Port)	
	{
		this.IPAddr = IPAddr;
		this.Port   = Port;
	}

	public KSPaySocketBean()	
	{
		this.IPAddr = null;
		this.Port   = 0;
	}

	public boolean setSendURL(String SendURL, String ReceiveMsg) throws IOException
	{
		if(SendURL == null || SendURL.equals("")) 
			return true;
		
        SendURL = SendURL+"?data="+URLEncoder.encode(ReceiveMsg, "KSC5601");

		BufferedReader br = null;
		try
		{
			URL home = new URL(SendURL);


			String line;
			br = new BufferedReader(new InputStreamReader(home.openStream()));
			while((line = br.readLine()) != null)
			{
				if(ServiceType.LOCAL)
					System.out.println(SendURL+":"+line);
			}
		}
		catch(Exception e) {
			if(ServiceType.LOCAL)
				System.out.println("setSendURL ERROR="+e.toString());
			return false;
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return true;
	}
	
	// IPG_Server와 연결을 맺는다.	
	public void ConnectSocket() throws IOException 
	{
		try
		{
			socket = new Socket(this.IPAddr, this.Port);
			in     = new DataInputStream(socket.getInputStream());
			out    = new DataOutputStream(socket.getOutputStream());
			if(ServiceType.LOCAL)
				System.out.println("addr = [" + this.IPAddr + "]  ,  port = [" + this.Port + "]");
		}
		catch( IOException e )
		{
			throw new IOException("[KSPaySocketBean] cannot connect server : (" + this.IPAddr + " , " + this.Port + ")");
		}
	}

	// IPG_Server에 데이타를 전달한다..
	public void write(byte[] msg) throws IOException
	{
		try
		{
			out.write(msg);
			out.flush();
		}
		catch( IOException e )
		{
			throw new IOException("[KSPaySocketBean] cannot write to socket");
		}
	}

	// IPG_Server로 부터 데이타를 얻는다.
	public byte[] read(int size) throws IOException
	{
		try
		{
			byte[] msg = new byte[size];

			while (true) {
				int count = in.read(msg);
				if (count == -1) {
					break;
				}
			}

			return msg;
		}
		catch( IOException e )
		{
			throw new IOException("[KSPaySocketBean] cannot read from socket");
		}
	}

	// 연결 소켓을 닫는다.
	public void CloseSocket() throws IOException
	{
		try
		{
			socket.close();
		}
		catch( IOException e )
		{
			throw new IOException("[KSPaySocketBean] cannot close socket");
		}
	}

	public String format(String str, int len, char ctype)
	{
		String formattedstr = new String();
		byte[] buff;
		int filllen = 0;
		
		buff = str.getBytes();
		
		filllen = len - buff.length;
		formattedstr = "";
		if(ctype == '9'){// 숫자열인 경우
			for(int i = 0; i<filllen;i++)
			{
				formattedstr += "0";
			}
			formattedstr = formattedstr + str;
		}
		else 
		{ // 문자열인 경우
			for(int i = 0; i<filllen;i++)
			{
				formattedstr += " ";
			}
			formattedstr = str + formattedstr;
		}
		return formattedstr;
	}
        
	public String setTrim(String str, int len)
	{
		byte[] subbytes;
		String tmpStr;
		subbytes = new byte[len];
		
		System.arraycopy(str.getBytes(), 0, subbytes, 0, len);
		tmpStr = new String(subbytes);
		if(tmpStr.length() == 0) {
			subbytes = new byte[len-1];
			System.arraycopy(str.getBytes(), 0, subbytes, 0, len-1);
			tmpStr = new String(subbytes);
		}
		return tmpStr;
	}
	
	public String setLogMsg(String str)
	{
		String strBuf = "";
		for(int i=0; i < str.length(); i++)
		{
			if(str.substring(i, i+1).equals(" "))
				strBuf = strBuf + "_";
			else
				strBuf = strBuf + str.substring(i, i+1);
		}
		return strBuf;
	}
}