    package com.ant.test;  
      
    import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

    /** 
     *  
     * @Description 图片上传模拟HTTP请求 
     * @author nier_ni 
     * @date 2015-9-26 上午11:17:24  
     * @version 
     */  
    public class FileUploadTest {  
        private static final int TIME_OUT = 10 * 10000000; // 超时时间  
        private static final String CHARSET = "utf-8"; // 设置编码  
        public static final String SUCCESS = "1";  
        public static final String FAILURE = "0";  
      
        public static void main(String[] args) {
        	File file = new File("E:/IMG/bg.jpg");
        	System.out.println(file.getName());
			System.out.println(uploadFile(file));
		}
        public static String uploadFile(File file) {  
            String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成  
            String PREFIX = "--", LINE_END = "\r\n";  
            String CONTENT_TYPE = "multipart/form-data"; // 内容类型  
            String RequestURL = "http://172.16.1.66/antfinancial/file/upload.action";  
            try {  
                URL url = new URL(RequestURL);  
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
                conn.setReadTimeout(TIME_OUT);  
                conn.setConnectTimeout(TIME_OUT);  
                conn.setDoInput(true); // 允许输入流  
                conn.setDoOutput(true); // 允许输出流  
                conn.setUseCaches(false); // 不允许使用缓存  
                conn.setRequestMethod("POST"); // 请求方式  
                conn.setRequestProperty("Charset", CHARSET); // 设置编码  
                conn.setRequestProperty("connection", "keep-alive");  
                conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="  
                        + BOUNDARY);  
                conn.setRequestProperty("OrderNumber", "546");
                if (file != null) {  
                    /** 
                     * 当文件不为空，把文件包装并且上传 
                     */  
      
                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());  
                    
                   
                    StringBuffer params = new StringBuffer();  
                    /*** 
                     * 以下是用于上传参数 
                     */  
                    params.append(PREFIX).append(BOUNDARY).append(LINE_END);  
                    params.append("Content-Disposition: form-data; name=\"").append("params1").append("\"").append(LINE_END).append(LINE_END);  
                    params.append("params22").append(LINE_END);  
                    params.append(PREFIX).append(BOUNDARY).append(LINE_END);  
                    params.append("Content-Disposition: form-data; name=\"").append("params66").append("\"").append(LINE_END).append(LINE_END);  
                    params.append("params66").append(LINE_END);  
                    dos.write(params.toString().getBytes());  
                    
                    StringBuffer sb = new StringBuffer();   
                    sb.append(PREFIX);  
                    sb.append(BOUNDARY);  
                    sb.append(LINE_END);  
                    /** 
                     * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 
                     * filename是文件的名字，包含后缀名的 比如:abc.png 
                     */  
                    sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""  
                            + file.getName() + "\"" + LINE_END);  
                    sb.append("Content-Type: application/octet-stream; charset="  
                            + CHARSET + LINE_END);  
                    sb.append(LINE_END);  
                    dos.write(sb.toString().getBytes());  
                    InputStream is = new FileInputStream(file);  
                    byte[] bytes = new byte[1024];  
                    int len = 0;  
                    while ((len = is.read(bytes)) != -1) {  
                        dos.write(bytes, 0, len);  
                    }  
                    is.close();  
                    dos.write(LINE_END.getBytes());  
                    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)  
                            .getBytes();  
                    dos.write(end_data);  
                    dos.flush();  
                    /** 
                     * 获取响应码 200=成功 当响应成功，获取响应的流 
                     */  
                    int res = conn.getResponseCode();  
                    if (res == 200) {  
                    	String s = conn.getResponseMessage();
                    	System.out.println(s);
                        return SUCCESS;  
                    }  
                }  
            } catch (MalformedURLException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            return FAILURE;  
        }  
    }  