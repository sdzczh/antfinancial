package com.ant.web.common;  
  
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject;
import com.ant.app.model.AntResult;
import com.ant.app.model.AntType;
import com.ant.glob.GolbParams;
  
/**
 * @描述 图片上传Servlet<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-14
 */
@SuppressWarnings("unchecked")
public class FileUploadServlet extends HttpServlet {    
    private static final long serialVersionUID = -4903483985922185852L;  
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) {  
    	AntResult antResult = new AntResult();
    	
    	String realPath = this.getServletContext().getRealPath(GolbParams.FILE_UPLOAD_PATH);
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);    
		if(isMultipart){    
			HashMap<String,Object> res = uploadImg(realPath,request); 
			Integer type = (Integer) res.get("type");
			if(type==0){
				antResult.setImgName(res.get("imgName").toString());
				antResult.setType(AntType.ANT_100);
			}else{
				antResult.setType(AntType.ANT_301);
			}
		}else{
			antResult.setType(AntType.ANT_205);
		} 
		JSONObject json = (JSONObject) JSONObject.toJSON(antResult);
    	responseOutWithJson(response,json);  
    	
    }    

    /**  
     *  
     * @Description 上传图片 
     * @param realPath 
     * @param request 
     */  
	public HashMap<String,Object> uploadImg(String realPath, HttpServletRequest request){
        HashMap<String,Object> res = new HashMap<String, Object>();  
        String fileName = null;  
        File dir = new File(realPath);  
        DiskFileItemFactory factory = new DiskFileItemFactory();    
        ServletFileUpload upload = new ServletFileUpload(factory);    
        upload.setSizeMax(1024*1024*5*10);  
        upload.setHeaderEncoding("utf-8");    
        try {    
            List<FileItem> items = upload.parseRequest(request);    
            for(FileItem item : items){    
                if(item.isFormField()){
                    String id = item.getFieldName();    
                    String value = item.getString("utf-8");    
                    res.put(id, value);  
                } else { //文件    
                    String name = item.getName();  
                    fileName = System.currentTimeMillis() + name.substring(name.lastIndexOf("."));  
                    item.write(new File(dir, fileName));    
                    res.put("imgName",fileName);  
                }   
            } 
            
            res.put("type", 0);
            return res;
        } catch (Exception e) {    
            e.printStackTrace();  
            res.put("type", 1);
            return res;
        }   
    }  
    /**  
     * 以JSON格式输出  
     * @param response  
     */    
    protected void responseOutWithJson(HttpServletResponse response, JSONObject json) {    
        //将实体对象转换为JSON Object转换    
        response.setCharacterEncoding("UTF-8");    
        response.setContentType("application/json; charset=utf-8");    
        PrintWriter out = null;    
        try {    
            out = response.getWriter();    
            out.append(json.toString());    
        } catch (IOException e) {    
            e.printStackTrace();    
        } finally {    
            if (out != null) {    
                out.close();    
            }    
        }    
    }  
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    
        this.doGet(request,response);  
    }  
} 