<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/7/19
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>zTree</title>
</head>
<body>

    <div>
        <button onclick="treeInit()">zTree</button>
    </div>
    <div>
        <ul class="ztree" id="tree_device"></ul>
    </div>

    <!--
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#unlockAuthModal" data-whatever="@mdo">Open modal for @mdo</button>
    <div class="modal fade" id="unlockAuthModal" tabindex="-1" role="dialog" aria-labelledby="unlockAuthModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">New message</h4>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="recipient-name" class="control-label">Recipient:</label>
                            <input type="text" class="form-control" id="recipient-name">
                        </div>
                        <div class="form-group">
                            <label for="message-text" class="control-label">Message:</label>
                            <textarea class="form-control" id="message-text"></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Send message</button>
                </div>
            </div>
        </div>
    </div>
    -->

    <div id="dd" class="easyui-dialog" style="padding:0px;width:829px;height:500px;border:5px #95b8e7"
         title="开锁授权" iconCls="icon-ok" buttons="#dlg-buttons" closed="true">
        <div id="unlockAuthBar" style="width:800px;height:40px;background: #c2cfef;">
            <span id='theGateway' style="display:inline-block;width:210px;height:40px;line-height:40px;vertical-align:middle;font-size:30px;overflow:hidden;">网关:${cookie.gatewayCode.value}</span>
            <span id='theLock' style="display:inline-block;width:210px;height:40px;line-height:40px;vertical-align:middle;font-size:30px;overflow:hidden;">门锁:${cookie.lockCode.value}</span>
            <span id='lockLoc' style="display:inline-block;width:240px;height:40px;line-height:40px;vertical-align:middle;font-size:30px;overflow:hidden;"></span><!-- 门锁位置:${cookie.lockLocation.value} -->
            <span id='defaultLockPwd' style="display:inline-block;width:120px;height:40px;line-height:40px;vertical-align:middle;font-size:30px;text-align:justify;">默认密码:yishutech</span>
        </div>
        <div style="clear:both;padding: 0px;margin: 0px;background: #95b8e7;width:800px;height:10px"></div>
        <div style="position:relative;width:800px;height:280px;background: rgba(0, 0, 0, 0) linear-gradient(to bottom, #eff5ff 0px, #e0ecff 100%) repeat-x scroll 0 0;">
            <div id="unlockAuthBt">
                <div style="float:left;width:400px;height:250px;position:relative;">
                    <div style="width:200px;height:250px;position:absolute;top:50%;left:50%;margin-top:-100px;margin-left:-100px;cursor:pointer;border:2px;" onclick="javascript:certiUnlockAuthBt()">
                        <!-- <input class="button" type="button" value="添加开锁身份证" style="background: #95b8e7;width:160px;height:100px;position:absolute;top:50%;left:50%;margin-left:-80px;margin-top:-50px;" onclick="javascript:certiUnlockAuthBt()"> -->
                        <div style="padding:15px 25px;background-color:#ffda9c;width:200px;height:200px">
                            <input type="image" name="imageBtn" src="styles/images/certiAuthBtn2.png" />
                        </div>
                        <div class="imgBtnDesc" style="width:200px;height:50px;font-size:15px;text-align:center;vertical-align:center">添加身份证开锁</div>
                    </div>
                </div>
                <div style="float:left;width:400px;height:250px;position:relative;">
                    <div style="width:200px;height:200px;position:absolute;top:50%;left:50%;margin-top:-100px;margin-left:-100px;cursor:pointer;border:2px;" onclick="javascript:pwdUnlockAuthBt()">
                        <!-- <input class="button" type="button" value="添加开锁密码" style="background: #95b8e7;width:160px;height:100px;position:absolute;top:50%;left:50%;margin-left:-80px;margin-top:-50px;" onclick="javascript:pwdUnlockAuthBt()"> -->
                        <input type="image" name="imageBtn" src="styles/images/pwdAuthBtn.png" />
                        <div class="imgBtnDesc" style="width:200px;height:30px;font-size:25px;">添加密码开锁</div>
                    </div>
                </div>
            </div>
            <div id="certiUnlockAuth" style="border:#95b8e7 2px;width:200px;height:280px;display:none;position:absolute;top:50%;left:50%;margin-left:-100px;margin-top:-140px;">
                <table style="margin:8px auto;">
                    <tr><td><label>添加姓名:</label></td></tr>
                    <tr><td><input class="easyui-textbox" id="LockAuthName" style="width:170px;height:30px;" data-options="required:true,prompt:'请输入开锁人姓名',validType:['chineseName'],invalidMessage:'姓名输入错误(仅中文和·)'"></td></tr>
                    <tr><td><label>添加开锁身份证:</label></td></tr>
                    <!-- background-color: rgb(255,​ 243,​ 243);border: 1px solid rgb(255,​ 168,​ 168);border-radius: 5px;margin: 0;overflow: hidden;padding: 0;vertical-align: middle;white-space: nowrap;" -->
                    <tr><td><input class="easyui-textbox" id="CertAuthTxt" 	style="width:170px;height:30px;" data-options="required:true,prompt:'请输入开锁身份证',validType:['cardNumb'],invalidMessage:'身份证输入错误'"></td></tr>
                    <tr><td><label>生效时间:</label></td></tr>
                    <tr><td><input class="easyui-datetimebox" id="certiAuthDialogST" sharedCalendar="#sc" data-options="required:true,showSeconds:false" style="width:170px"></td></tr>
                    <tr><td><label>失效时间:</label></td></tr>
                    <tr><td><input class="easyui-datetimebox" id="certiAuthDialogET" sharedCalendar="#sc" data-options="required:true,showSeconds:false" value="2020-08-08 08:08" style="width:170px"></td></tr>
                    <tr><td><a style="margin-left:40px" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:certiUnlockAuthor()">提交授权</a></td></tr>
                </table>
                <div id="sc" class="easyui-calendar"></div>
            </div>
            <div id="pwdUnlockAuth" style="border:#95b8e7 2px;width:200px;height:280px;display:none;position:absolute;top:50%;left:50%;margin-left:-100px;margin-top:-140px;">
                <table style="margin:40px auto;">
                    <tr><td><label>添加开锁密码:</label></td></tr>
                    <tr><td><input class="easyui-textbox" id="PwdAuthTxt" style="width:170px;height:30px" data-options="required:true,prompt:'请输入开锁密码',validType:['lockPwd'],invalidMessage:'密码输入错误(4~11位字母、数字、下划线)'"></td></tr>
                    <tr><td><label>生效时间:</label></td></tr>
                    <tr><td><input class="easyui-datetimebox" id="pwdAuthDialogST" sharedCalendar="#sc" data-options="required:true,showSeconds:false" style="width:170px"></td></tr>
                    <tr><td><label>失效时间:</label></td></tr>
                    <tr><td><input class="easyui-datetimebox" id="pwdAuthDialogET" sharedCalendar="#sc" data-options="required:true,showSeconds:false" value="2020-08-08 08:08" style="width:170px"></td></tr>
                    <tr><td><a style="margin-left:40px" href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:pwdUnlockAuthor()">提交授权</a></td></tr>
                </table>
                <div id="sc" class="easyui-calendar"></div>
            </div>
        </div>
        <div style="clear:both;padding: 0px;margin: 0px;background: #95b8e7;width:800px;height:10px"></div>
        <div style="margin:0px;padding:0px;clear:both;width:800px;">
            <table id="cancelAuthdg" class="easyui-datagrid" title="开锁身份证密码" style="width:800px;fitColumns:true;"
                   data-options="rownumbers:true,checkOnSelect:true,singleSelect:true,toolbar:'#cancleAuthTB'">
                <thead>
                <tr>
                    <th data-options="field:'ck',checkbox:true"></th>
                    <th data-options="field:'name',width:100,align:'left'">姓名</th>
                    <th data-options="field:'cardNumb',width:160,align:'left'">身份证</th>
                    <th data-options="field:'password',width:100,align:'left'">密码</th>
                    <th data-options="field:'startTime',width:110,align:'left'">开始时间</th>
                    <th data-options="field:'endTime',width:110,align:'left'">失效时间</th>
                    <th data-options="field:'serviceNumb',width:160,align:'left'">流水号</th>
                </tr>
                </thead>
            </table>
            <div id="cancleAuthTB" style="padding:5px;height:auto">
                <div>
                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cut" onclick="javascript:cancelAuth()">删除开锁账户</a>
                </div>
            </div>
        </div>
    </div>

    <div id="dlg-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:closeDialog()">退出</a>
    </div>
</body>
</html>
