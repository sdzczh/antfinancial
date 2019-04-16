package com.ant.app.controller;

import com.ant.app.service.inte.AppNoticeService;
import com.ant.base.BaseController;
import com.ant.glob.ResponseResult;
import com.ant.pojo.SystemParam;
import com.ant.service.inte.SystemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @描述 系统公告<br>
 * @author 陈之晶
 * @版本 v1.0.0
 * @日期 2017-6-9
 */
@RequestMapping("/file")
@Controller
public class FileUploadController extends BaseController{

	@Autowired
	private AppNoticeService service;

    @Autowired
    private SystemParamService systemParamService;

    @ResponseBody
    @RequestMapping("/upload.action")
    public void uploadPicture(@RequestParam(value="file",required=false) MultipartFile file, HttpServletRequest request){
        ResponseResult result = new ResponseResult();
        Map<String, Object> map = new HashMap<>();
        File targetFile=null;
        String url="";//返回存储路径
        int code=1;
        System.out.println(file);
        String fileName=file.getOriginalFilename();//获取文件名加后缀
        if(fileName!=null&&fileName!=""){
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/imgs/";//存储路径
            String path = request.getSession().getServletContext().getRealPath("upload/imgs"); //文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileF;//新的文件名

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 =new File(path+"/"+fileAdd);
            //如果文件夹不存在则创建
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                url=returnUrl+fileAdd+"/"+fileName;
                code=0;
                result.setCode(code);
                result.setMessage("图片上传成功");
                map.put("url", url);
                result.setResult(map);
            } catch (Exception e) {
                e.printStackTrace();
                result.setMessage("系统异常，图片上传失败");
            }
        }
        String keyName = "RECHANGE_IMG_URL";
        SystemParam systemParam = systemParamService.queryByKeyName(keyName);
        systemParam.setVal(url);
        systemParamService.paramModify(systemParam);
        writeJson(response, result);
    }

    @RequestMapping("info.action")
    public String toParamAdd(){
        return "systemManage/fileUpload";
    }
}
