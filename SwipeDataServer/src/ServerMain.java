import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lock.service.JsonData.DeviceData;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

public class ServerMain {

	private final static int port=2017;
	
	public static void main(String[] args) throws IOException{
        try {
            // setup the socket address
            InetSocketAddress address = new InetSocketAddress(port);

            // initialise the HTTPS server
            HttpsServer httpsServer = HttpsServer.create(address, 100000);
            SSLContext sslContext = SSLContext.getInstance("TLS");

            // initialise the keystore
            char[] password = "password".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream("testkey.jks");
            ks.load(fis, password);

            // setup the key manager factory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);

            // setup the trust manager factory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            // setup the HTTPS context and parameters
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    try {
                        // initialise the SSL context
                        SSLContext c = SSLContext.getDefault();
                        SSLEngine engine = c.createSSLEngine();
                        params.setNeedClientAuth(false);
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());

                        // get the default parameters
                        SSLParameters defaultSSLParameters = c.getDefaultSSLParameters();
                        params.setSSLParameters(defaultSSLParameters);

                    } catch (Exception ex) {
                        System.out.println("Failed to create HTTPS port");
                    }
                }
            });
//            httpsServer.createContext("/test", new MyHandler());
//            httpsServer.setExecutor(null); // creates a default executor
            httpsServer.createContext("/", new MyHandler());
            httpsServer.setExecutor(Executors.newCachedThreadPool());
            httpsServer.start();
    	    System.out.println("Server is listening on port" +address.getPort() );

        } catch (Exception exception) {
            System.out.println("Failed to create HTTPS server on port " + port + " of localhost");
            exception.printStackTrace();

        }
	}
}


class MyHandler implements HttpHandler {
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		System.out.println(Thread.currentThread().getName());
		  
	    String requestMethod = exchange.getRequestMethod();
	    if (!requestMethod.equalsIgnoreCase("POST")) {
	    	return;
	    }
	      System.out.println("MyHandler");
	      Headers responseHeaders = exchange.getResponseHeaders();
	      responseHeaders.set("Content-Type", "text/json");
	      exchange.sendResponseHeaders(200, 0);
	      OutputStream responseBody = exchange.getResponseBody();
	      //OutputStream outputStream = exchange.getResponseBody();
	      //DataOutputStream responseBody=new DataOutputStream(outputStream);
	      Headers requestHeaders = exchange.getRequestHeaders();
	      
	      InputStream requestBody=exchange.getRequestBody();

          InputStreamReader inputStreamReader=new InputStreamReader(requestBody,"UTF-8");
          BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
          String line="";
          String result="";
          while((line=bufferedReader.readLine())!=null){
              result+=line+"\n";
          }
	      String method=exchange.getRequestMethod();
	      URI uri=exchange.getRequestURI();
	      System.out.println("method    "+method);
	      //System.out.println("uri  "+uri.getPath().length());
	      System.out.println("result 结果"+result);

	      Gson gson=new Gson();
	      DatabaseUtil databaseUtil=new DatabaseUtil();
    	  JsonObject jsonResponse = null;

    	  JsonObject jsonRequest=(JsonObject) new JsonParser().parse(result);
	      int sign=jsonRequest.get("sign").getAsInt();
	      String phoneNumber=jsonRequest.get("ownerPhoneNumber").getAsString();

	      System.out.println("sign  "+sign);
	      System.out.println("ownerPhoneNumber  "+phoneNumber);

	      
	      switch (sign){
	       case 1:    // 注册时分配owner 数据库所在ip
				  jsonResponse=new JsonObject();
				  boolean phoneNumberRepeat=databaseUtil.judgePhoneNumberRepeat(phoneNumber);
				  System.out.println(phoneNumberRepeat);
				  if(phoneNumberRepeat){  // phoneNumber已分配wnerIp
					  String ownerIpAssign=databaseUtil.getOwnerIp(phoneNumber);
					  if(ownerIpAssign!=null){
						  jsonResponse.addProperty("result", 0);
					  }
					  else {
						  jsonResponse.addProperty("result", 1);
					  }
					  jsonResponse.addProperty("ownerIp", ownerIpAssign);
				  }
				  else{
					  String ownerIpAssign=databaseUtil.setOwnerIp(phoneNumber, jsonRequest.get("timetag").getAsString());
					  if(ownerIpAssign!=null){
						  jsonResponse.addProperty("result", 0);
					  }
					  else {
						  jsonResponse.addProperty("result", 1);
					  }
					  jsonResponse.addProperty("ownerIp", ownerIpAssign);
				  }
		    	  responseBody.write(jsonResponse.toString().getBytes());
				  break;

	      case 2:          // 注册新用户
		      String ownerName=jsonRequest.get("ownerName").getAsString();
		      String ownerPassword=jsonRequest.get("ownerPassword").getAsString();
		      System.out.println("ownerName    "+ownerName);
		      System.out.println("ownerPhoneNumber   "+phoneNumber);
		      System.out.println("ownerPassword   "+ownerPassword);
		      boolean repetition = false;
	    	  jsonResponse=new JsonObject(); 
			try {
				repetition = databaseUtil.judgePhoneNumberUnique(phoneNumber);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		      if(repetition){  // phoneNumber already register
		    	  jsonResponse.addProperty("result", 2);
		      }
		      else {
		    	  boolean isRegister = false;
				try {
					isRegister = databaseUtil.registerOwner(ownerName, phoneNumber, ownerPassword,
							jsonRequest.get("timetag").getAsString(),jsonRequest.get("imei").getAsString());
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    	  if(isRegister){
			    	  jsonResponse.addProperty("result", 0);       // register success
		    	  }
		    	  else{
			    	  jsonResponse.addProperty("result", 1);     // register  failed
		    	  }
		      }
	    	  responseBody.write(jsonResponse.toString().getBytes());
		      break;
		      
	       case 3:  // 获取owner所在IP
				  jsonResponse=new JsonObject();
				  String ownerIp=databaseUtil.getOwnerIp(phoneNumber);
				  if(ownerIp!=null){
					  jsonResponse.addProperty("result", 0);
				  }
				  else {
					  jsonResponse.addProperty("result", 1);
				  }
				  jsonResponse.addProperty("ownerIp", ownerIp);
			      responseBody.write(jsonResponse.toString().getBytes());
			      break;

	      case 4:          // 登录验证
		      String password=jsonRequest.get("ownerPassword").getAsString();
		      String imei=jsonRequest.get("imei").getAsString();
		      jsonResponse=new JsonObject();
		      try {
				String s=databaseUtil.loginVerify(phoneNumber, password,imei,jsonRequest.get("timetag").getAsString());
		    	  System.out.println("s  "+s);

		    	  responseBody.write(s.getBytes());
		    	 
			} catch (SQLException e) {
				e.printStackTrace();
			}   
	    	  break;
	    	  
	       case 5:    //添加网关前，先获取网关所在服务器的IP 
				  jsonResponse=new JsonObject();
	    	   String gatewayIp=databaseUtil.getGatewayIp(jsonRequest.get("gatewayCode").getAsString());
	    	   if(gatewayIp!=null){
	    		   jsonResponse.addProperty("result", 0);
	    		   jsonResponse.addProperty("gatewayIp", gatewayIp);
	    	   }
	    	   else {
	    		   jsonResponse.addProperty("result", 1);
	    	   }
	    	   responseBody.write(jsonResponse.toString().getBytes());
	    	   break;

	    	 
	      case 6:    //  判断该网关是否已经被添加
		      String stringResponse = null;
			try {
				stringResponse = databaseUtil.judgeGatewayUnique(phoneNumber, jsonRequest.get("gatewayCode").getAsString());
			} catch (SQLException e2) {
				e2.printStackTrace();
			}                                 
			System.out.println("stringResponse   "+stringResponse);
		      responseBody.write(stringResponse.getBytes());
		      break;
	    	
	      case 7:   // 判断网关验证码是否正确
		      jsonResponse=new JsonObject();
		      System.out.println("case 5");
			try {
				int i = databaseUtil.judgeGatewayVerifyCode(jsonRequest.get("gatewayCode").getAsString(), 
						  jsonRequest.get("opCode").getAsString());
			      System.out.println("i  "+i);

				jsonResponse.addProperty("result", i);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			System.out.println("jsonResponse   "+jsonResponse);

		      responseBody.write(jsonResponse.toString().getBytes());
	    	  break;
	    	  
	      case 8:        //添加网关
		      jsonResponse=new JsonObject();
		      String gatewayName=jsonRequest.get("gatewayName").getAsString();
		      String gatewayCode=jsonRequest.get("gatewayCode").getAsString();
		      String gatewayLocation=jsonRequest.get("gatewayLocation").getAsString();
		      String gatewayCommnet=jsonRequest.get("gatewayComment").getAsString();
		      String opCode=jsonRequest.get("opCode").getAsString();
			    System.out.println("case 6"+gatewayName+gatewayCode+gatewayLocation+gatewayCommnet+opCode);
 
			try {
				int i = databaseUtil.addGateway(phoneNumber, gatewayName, gatewayCode, gatewayLocation, gatewayCommnet, opCode,jsonRequest.get("timetag").getAsString());
				jsonResponse.addProperty("result", i);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			    System.out.println("case 6"+jsonResponse.toString());
		    	responseBody.write(jsonResponse.toString().getBytes());
		    	break;
		    	
	      case 9:      // 修改网关
		      jsonResponse=new JsonObject();
		      String gatewayName1=jsonRequest.get("gatewayName").getAsString();
		      String gatewayCode1=jsonRequest.get("gatewayCode").getAsString();
		      String gatewayLocation1=jsonRequest.get("gatewayLocation").getAsString();
		      String gatewayCommnet1=jsonRequest.get("gatewayComment").getAsString();
			try {
				int r = databaseUtil.modifyGateway(phoneNumber,gatewayCode1,gatewayName1,gatewayLocation1,
						gatewayCommnet1,jsonRequest.get("timetag").getAsString());
				  jsonResponse.addProperty("result", r);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      responseBody.write(jsonResponse.toString().getBytes());
		      break;
		      
	      case 10:   // 删除网关
		      jsonResponse=new JsonObject();
		      try {
				int i=databaseUtil.deleteGateway(phoneNumber, jsonRequest.get("gatewayCode").getAsString(),jsonRequest.get("timetag").getAsString());
				  jsonResponse.addProperty("result", i);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      responseBody.write(jsonResponse.toString().getBytes());
		      break;
		      
	      case 11:   //  判断门锁是否已经被添加
		      try {
				String s=databaseUtil.judgeLockUnique(jsonRequest.get("lockCode").getAsString());
			    responseBody.write(s.getBytes());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      break;
		      
	      case 12:    //  增加门锁
			  jsonResponse=new JsonObject();
	    	  try {
				int i=databaseUtil.addLock(jsonRequest.get("lockCode").getAsString(), jsonRequest.get("gatewayCode").getAsString(),
						  phoneNumber, jsonRequest.get("timetag").getAsString(), jsonRequest.get("lockName").getAsString(), 
						  jsonRequest.get("lockLocation").getAsString(), jsonRequest.get("lockComment").getAsString());
				  jsonResponse.addProperty("result", i);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		      responseBody.write(jsonResponse.toString().getBytes());
		      break;
		      
	      case 13:    //   修改门锁信息    
			  jsonResponse=new JsonObject();
			  try {
				int i=databaseUtil.modifyLock(jsonRequest.get("lockCode").getAsString(), phoneNumber, 
						jsonRequest.get("lockName").getAsString(), jsonRequest.get("lockLocation").getAsString(), 
						jsonRequest.get("lockComment").getAsString(),jsonRequest.get("timetag").getAsString());
				  jsonResponse.addProperty("result", i);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      responseBody.write(jsonResponse.toString().getBytes());
		      break;
		      
	      case 14:    //  删除门锁
			  jsonResponse=new JsonObject();
			  try {
				int i=databaseUtil.deleteLock(jsonRequest.get("lockCode").getAsString(), phoneNumber,jsonRequest.get("timetag").getAsString());
				  jsonResponse.addProperty("result", i);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      responseBody.write(jsonResponse.toString().getBytes());
		      break;
		      
				
	       case 15:  // 获取全部gateway数据所在IP
	    	   String re=databaseUtil.getAllGatewayIp(phoneNumber); 
	    	   responseBody.write(re.getBytes());
	    	   break;

	      case 16:     // 获取设备 信息(包含网关、门锁)
		      try {
				DeviceData deviceData=databaseUtil.getDevicesData(phoneNumber);
				String response=gson.toJson(deviceData);
				System.out.println("reponse  "+response);
				responseBody.write(response.getBytes());
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	  break;

	      case 17:    //获取门锁开锁者信息
	    	  try {
				String s=databaseUtil.getUnlockUserData(jsonRequest.get("lockCode").getAsString(), jsonRequest.get("gatewayCode").getAsString(), phoneNumber);
			      responseBody.write(s.getBytes());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      break;
	    	
	      case  18:   	//  增加身份证开锁授权
			  jsonResponse=new JsonObject();
	    	  try {
				int i=databaseUtil.addUnlockAuthorization(jsonRequest.get("lockCode").getAsString(), jsonRequest.get("gatewayCode").getAsString(), jsonRequest.get("serviceNumb").getAsString(), 
						  phoneNumber, jsonRequest.get("name").getAsString(),jsonRequest.get("cardNumb").getAsString(), jsonRequest.get("dnCode").getAsString(), jsonRequest.get("startTime").getAsString(), 
						  jsonRequest.get("endTime").getAsString(), jsonRequest.get("timetag").getAsString());
				  jsonResponse.addProperty("result", i);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		      responseBody.write(jsonResponse.toString().getBytes());
		      break;
		      
	      case  19:     // 取消身份证开锁授权   
			  jsonResponse=new JsonObject();
			  try {
				int i=databaseUtil.cancelUnlockAuthorization(phoneNumber, jsonRequest.get("lockCode").getAsString(), 
						  jsonRequest.get("cardNumb").getAsString(), jsonRequest.get("serviceNumb").getAsString(),jsonRequest.get("timetag").getAsString());
				  jsonResponse.addProperty("result", i);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      responseBody.write(jsonResponse.toString().getBytes());
		      break;
		      
	      case 20:  // 获取智能锁开锁密码
	    	  try {
				String s=databaseUtil.getUnlockPasswordData(jsonRequest.get("lockCode").getAsString(), 
						jsonRequest.get("gatewayCode").getAsString(), phoneNumber);
			      responseBody.write(s.getBytes());
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	  break;
	    	  
	      case 21:  // 增加智能锁开锁密码
			  jsonResponse=new JsonObject();
	    	  try {
				int i=databaseUtil.addUnlockPassword(jsonRequest.get("lockCode").getAsString(),
						  jsonRequest.get("gatewayCode").getAsString(), jsonRequest.get("serviceNumb").getAsString(), 
						  phoneNumber, jsonRequest.get("startTime").getAsString(), jsonRequest.get("endTime").getAsString(), 
						  jsonRequest.get("timetag").getAsString(),jsonRequest.get("password").getAsString());
				  jsonResponse.addProperty("result", i);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      responseBody.write(jsonResponse.toString().getBytes());
		      break;
		      
	      case 22:  // 取消智能锁开锁密码
			  jsonResponse=new JsonObject();
	    	  try {
				int i=databaseUtil.cancelUnlockPassword(jsonRequest.get("lockCode").getAsString(), 
						jsonRequest.get("gatewayCode").getAsString(), jsonRequest.get("serviceNumb").getAsString(), phoneNumber,jsonRequest.get("timetag").getAsString());
				  jsonResponse.addProperty("result", i);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      responseBody.write(jsonResponse.toString().getBytes());
		      break;
		      
	      case 23:   //登录密码重置   
			  jsonResponse=new JsonObject();
			  String newPassword=jsonRequest.get("newPassword").getAsString();

			  try {
				int i=databaseUtil.modifyOwnerPassword(phoneNumber, newPassword,jsonRequest.get("timetag").getAsString());
				  jsonResponse.addProperty("result", i);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      responseBody.write(jsonResponse.toString().getBytes());
		      break;
		      
	      case 24:  //owner名称修改
			  jsonResponse=new JsonObject();
			  String newName=jsonRequest.get("newName").getAsString();
			  try {
				int i=databaseUtil.modifyOwnerName(phoneNumber, newName,jsonRequest.get("timetag").getAsString());
				  jsonResponse.addProperty("result", i);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      responseBody.write((jsonResponse.toString()+result).getBytes());
	    	  break;
		      
	       case 25:  // 定期获取Imei
				  jsonResponse=new JsonObject();
			     String imei1=databaseUtil.getImei(phoneNumber);
			     if(imei1==null){
				      jsonResponse.addProperty("result", 1);
			     }
			     else{
				      jsonResponse.addProperty("result", 0);
			     }
			      jsonResponse.addProperty("imei", imei1);
			      responseBody.write(jsonResponse.toString().getBytes());
			      break;
			      
			
		    case 26:  // 获取开锁记录
				  jsonResponse=new JsonObject();
				  String response=databaseUtil.getRecordData(phoneNumber,jsonRequest.get("startTime").getAsString()
						  ,jsonRequest.get("endTime").getAsString());
				  System.out.println(response);
			      responseBody.write(response.getBytes());
			      break;
			      
		    case 27:  // web端登录专用(密码 java TEA 加密)
			      String encryptPassword=jsonRequest.get("ownerPassword").getAsString();
			      String decryptPassword=new Tea().decryptByTea(encryptPassword);
			      System.out.println("decryptPassword   "+decryptPassword);
			  	   TcpDataService picconvert=null;
					picconvert=new TcpDataService();
					byte[] recvbuflen = new byte[200];
					picconvert.ConvertWlt(recvbuflen, decryptPassword);
					System.out.println("MessageMAIN pass 3, recvbuflen="+printHexString(recvbuflen, 16));
					String hexPassword=printHexString(recvbuflen, 16).replaceAll(" ", "");
			      jsonResponse=new JsonObject();
			      try {
					String s=databaseUtil.loginVerify(phoneNumber, hexPassword,"",jsonRequest.get("timetag").getAsString());
			    	  System.out.println("s  "+s);
			    	  responseBody.write(s.getBytes());
			    	
				} catch (SQLException e) {
					e.printStackTrace();
				} 

		    default:
			break;

	      }
	      
	      try {
	    	  responseBody.close();
	    	  requestBody.close();
			databaseUtil.getConnecntion().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  }
	
	public static String printHexString( byte[] b, int len) 
	{  
		String bstr="";
		   for (int i = 0; i < len; i++) { 
		     String hex = Integer.toHexString(b[i] & 0xFF); 
		     if (hex.length() == 1) { 
		       hex = '0' + hex; 
		     } 
		     bstr=bstr+" "+hex.toUpperCase();
		   } 
		return bstr;
	}

	}
