package com.yishu.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yishu.model.json.LockOperateJson;
import com.yishu.service.LockOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/lockoperate")
public class LockOperateController {
	@Autowired
	private LockOperateService lockOperateService;
	
	@RequestMapping("/findInDB.do")
	@ResponseBody
	public static String lockOperateReocord() throws JsonSyntaxException, ClassNotFoundException{
/*	
 * 自己的数据库，Jackson封装.
	public String RecordLockOperate() throws JsonGenerationException, JsonMappingException, IOException, JsonSyntaxException, ClassNotFoundException{
	
		ObjectMapper objectMapper = new ObjectMapper();
        Map map=new HashMap();
        List<LockOperate> list=new ArrayList<LockOperate>();
        list.addAll(lockOperateService.findByUserid("RuJia_Num00000001"));
                
        map.put("rows",list);
        map.put("total",list.size());
        List footerList=new ArrayList();
        Map footMap=new HashMap();
        footMap.put("lockcode", "总计");
        footMap.put("timetag", "总数");
        Map footMap2=new HashMap();
        footMap2.put("lockcode", "总计");
        footMap2.put("timetag",map.get("total"));
        footerList.add(0, footMap);
        footerList.add(1, footMap2);
        map.put("footer",footerList);
        System.out.println(map);
        
        Iterator it = list.iterator();
        while(it.hasNext()){
        	LockOperate str=(LockOperate) it.next();
        System.out.println(str);
        System.out.println(str.getOpenmode());
        }
        return objectMapper.writeValueAsString(map);
*/

/*		
//公司数据库，拼接Json字串
		String str=LockOperateUtil.getLockOperate();
		String strJson = null;
		boolean result=str.contains("\"result\":0");
		if(result){
			String str1=str.split("\\[")[1];
			String str2=str1.split("\\]")[0];
			String str3=str2.substring(0, str2.lastIndexOf("}"));System.out.println("str3"+str3);
			String[] strArr=null;
			
			strArr=str3.split("\\}\\,");
			String[] strArr2=new String[strArr.length];
			int i=0;
			for(String x:strArr){
				System.out.println(x);
//				String t=x.replace("\"cardInfo\"\\:\\{\"","t");
				String t=x.replace("\"cardInfo\":{","");
				strArr2[i]=t.replace("\"passwordInfo\":{","");
				System.out.println(strArr2[i]);
				i++;
			}
			StringBuffer data=new StringBuffer("{\"totals\":"+strArr2.length+','+"\"rows\":[");
			for(int j=0;j<20;j++){
				for(String x:strArr2){				
					data.append(x+",");
				}
			}
			data.deleteCharAt(data.length()-1);
			data.append("]}");
			System.out.println("data:"+data);
			strJson=new String(data);
		}
*/		
		String strJson = null;		
		return strJson;
	}
	
	@RequestMapping("/findByTime.do")
	@ResponseBody
	public String lockOperateByTime(HttpServletRequest request) throws UnsupportedEncodingException{
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String data=lockOperateService.findLockOperateRecordByTime(ownerPhoneNumber,startTime,endTime);
		return data;
//		response.getWriter().write(data);
	}
	
	@RequestMapping("/findByTimeOnPage.do")
	@ResponseBody
	public String lockOperateByTimeOnPage(HttpServletRequest request) throws UnsupportedEncodingException{
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String oriData=lockOperateService.findLockOperateRecordByTime(ownerPhoneNumber,startTime,endTime);

		String page=request.getParameter("pageNumber");
		String pageSize=request.getParameter("pageSize");
		System.out.println("page:"+page);System.out.println("rows:"+pageSize);
		String data=doPage(oriData,page,pageSize);
		return data;
//		response.getWriter().write(data);
	}	
	
	@RequestMapping("/findByNodes.do")
	@ResponseBody
	public String lockOperateByNodes(HttpServletRequest request) throws UnsupportedEncodingException{
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String nodes=request.getParameter("nodes");
		String data=lockOperateService.findLockOperateRecordByNodes(ownerPhoneNumber,startTime,endTime,nodes);
		return data;
	}
	@RequestMapping("/findByNodesOnPage.do")
	@ResponseBody
	public String lockOperateByNodesOnPage(HttpServletRequest request) throws UnsupportedEncodingException{
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String nodes=request.getParameter("nodes");
		String oriData=lockOperateService.findLockOperateRecordByNodes(ownerPhoneNumber,startTime,endTime,nodes);
		String page=request.getParameter("pageNumber");
		String pageSize=request.getParameter("pageSize");
		System.out.println("page:"+page);System.out.println("rows:"+pageSize);
		String data=doPage(oriData,page,pageSize);
		return data;
	}
	
	@RequestMapping("/findByID.do")
	@ResponseBody
	public String lockOperateById(HttpServletRequest request) throws UnsupportedEncodingException{
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String idText=request.getParameter("idText");
		System.out.println("Controller-idText:"+idText);
		String data=lockOperateService.findLockOperateRecordByID(ownerPhoneNumber,startTime,endTime,idText);
		return data;
	}
	
	@RequestMapping("/findByPwd.do")
	@ResponseBody
	public String lockOperateByPwd(HttpServletRequest request) throws UnsupportedEncodingException{		
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String pwdText=request.getParameter("pwdText");
		String data=lockOperateService.findLockOperateRecordByPwd(ownerPhoneNumber,startTime,endTime,pwdText);
		return data;
	}
	
	@RequestMapping("/findDeviceTree.do")
	@ResponseBody
	public String getDeviceTreeNode(HttpServletRequest request){
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String data=lockOperateService.getDeviceTree(ownerPhoneNumber);
		System.out.println("data:"+data);
		return data;
	}
	
	private String doPage(String oriData,String page,String pageSize) {
		int intPage=Integer.parseInt(page.trim());
		int intPageSize=Integer.parseInt(pageSize.trim());
		Gson gson=new Gson();
		LockOperateJson lockOperateJson=gson.fromJson(oriData, LockOperateJson.class);
		LockOperateJson lockOperateJsonSeg=new LockOperateJson();
		lockOperateJsonSeg.totals=lockOperateJson.rows.size();
//		for(int i=0;i<lockOperateJson.rows.size();i++){
//			System.out.println("lockOperateRecord[i]:"+lockOperateJson.rows.get(i).lockCode);
//		};
		for(int i=(intPage-1)*intPageSize;i<lockOperateJson.rows.size()&&i<intPage*intPageSize;i++){
			lockOperateJsonSeg.rows.add(lockOperateJson.rows.get(i));
			int index=i-(intPage-1)*intPageSize+1-1;
			System.out.println("lockOperateJsonSeg.rows["+index+"]:"+lockOperateJsonSeg.rows.get(index));
		};
		System.out.println("lockOperateJsonSeg:"+gson.toJson(lockOperateJsonSeg));
		return gson.toJson(lockOperateJsonSeg);
	}
}
