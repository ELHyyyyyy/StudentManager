<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>学生列表</title>
    <link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../easyui/css/demo.css">
    <script type="text/javascript" src="../easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../easyui/js/validateExtends.js"></script>
    <script type="text/javascript">
        var clazzList=${clazzListJson};
        $(function() {
            var table;

            //datagrid初始化
            $('#dataList').datagrid({
                title:'学生列表',
                iconCls:'icon-more',//图标
                border: true,
                collapsible:false,//是否可折叠的
                fit: true,//自动大小
                method: "post",
                url:"get_list?t="+new Date().getTime(),//防止浏览器缓存
                idField:'id',
                singleSelect:false,//是否单选
                pagination:true,//分页控件
                rownumbers:true,//行号
                sortName:'id',
                sortOrder:'DESC',
                remoteSort: false,
                columns: [[
                    {field:'chk',checkbox: true,width:50},
                    {field:'id',title:'ID',width:50, sortable: true},
                    {field:'stuname',title:'姓名',width:150,sortable: true},
                    {field:'sn',title:'学号',width:150,sortable: true},
                    {field:'sex',title:'性别',width:150,sortable: true},
                    {field:'clazzId',title:'所属班级',width:150,sortable: true,
                        formatter:function (value,index,row) {
                            for (var i=0;i<clazzList.length;i++){
                                if(clazzList[i].id==value){
                                    return clazzList[i].clazzname;
                                }
                            }
                            return value;
                        }
                    },
                    {field:'password',title:'密码',width:300},
                    {field:'remark',title:'备注',width:300},
                ]],
                toolbar: "#toolbar"
            });
            //设置分页控件
            var p = $('#dataList').datagrid('getPager');
            $(p).pagination({
                pageSize: 10,//每页显示的记录条数，默认为10
                pageList: [10,20,30,50,100],//可以设置每页记录条数的列表
                beforePageText: '第',//页数文本框前显示的汉字
                afterPageText: '页    共 {pages} 页',
                displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            });
            //设置工具类按钮
            $("#add").click(function(){
                table = $("#addTable");
                $("#addDialog").dialog("open");
            });
            //修改
            $("#edit").click(function(){
                table = $("#editTable");
                var selectRows = $("#dataList").datagrid("getSelections");
                if(selectRows.length != 1){
                    $.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
                } else{
                    $("#editDialog").dialog("open");
                }
            });
            //删除
            $("#delete").click(function(){
                var selectRows = $("#dataList").datagrid("getSelections");
                var selectLength = selectRows.length;
                if(selectLength == 0){
                    $.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
                } else{
                    var ids = [];
                    $(selectRows).each(function(i, row){
                        ids[i] = row.id;
                    });

                    $.messager.confirm("消息提醒", "将删除与学生相关的所有数据，确认继续？", function(r){
                        if(r){
                            $.ajax({
                                type: "post",
                                url: "delete",
                                data: {ids: ids},
                                dataType:'json',
                                success: function(data){
                                    if(data.type == "success"){
                                        $.messager.alert("消息提醒",data.msg,"info");
                                        //刷新表格
                                        $("#dataList").datagrid("reload");
                                        $("#dataList").datagrid("uncheckAll");
                                    } else{
                                        $.messager.alert("消息提醒",data.msg,"warning");
                                        return;
                                    }
                                }
                            });
                        }
                    });
                }
            });

            //设置添加窗口
            $("#addDialog").dialog({
                title: "添加学生",
                width: 350,
                height: 450,
                iconCls: "icon-add",
                modal: true,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                draggable: true,
                closed: true,
                buttons: [
                    {
                        text:'添加',
                        plain: true,
                        iconCls:'icon-user_add',
                        handler:function(){
                            var validate = $("#addForm").form("validate");
                            if(!validate){
                                $.messager.alert("消息提醒","请检查你输入的数据!","warning");
                                return;
                            } else{
                                var data = $("#addForm").serialize();
                                $.ajax({
                                    type: "post",
                                    url: "add",
                                    data: data,
                                    dataType:'json',
                                    success: function(data){
                                        if(data.type == "success"){
                                            $.messager.alert("消息提醒","添加成功!","info");
                                            //关闭窗口
                                            $("#addDialog").dialog("close");
                                            //清空原表格数据
                                            $("#add_stuname").textbox('setValue', "");
                                            $("#add_remark").textbox('setValue', "");
                                            //重新刷新页面数据
                                            $('#dataList').datagrid("reload");

                                        } else{
                                            $.messager.alert("消息提醒",data.msg,"warning");
                                            return;
                                        }
                                    }
                                });
                            }
                        }
                    },

                ],
                onClose: function(){
                    $("#add_stuname").textbox('setValue', "");
                    $("#add_remark").textbox('setValue', "");
                }
            });
            //编辑学生信息
            $("#editDialog").dialog({
                title: "修改学生信息",
                width: 350,
                height: 400,
                iconCls: "icon-edit",
                modal: true,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                draggable: true,
                closed: true,
                buttons: [
                    {
                        text:'提交',
                        plain: true,
                        iconCls:'icon-edit',
                        handler:function(){
                            var validate = $("#editForm").form("validate");
                            if(!validate){
                                $.messager.alert("消息提醒","请检查你输入的数据!","warning");
                                return;
                            } else{
                                var data = $("#editForm").serialize();
                                $.ajax({
                                    type: "post",
                                    url: "edit",
                                    data: data,
                                    dataType:'json',
                                    success: function(data){
                                        if(data.type == "success"){
                                            $.messager.alert("消息提醒",data.msg,"info");
                                            //关闭窗口
                                            $("#editDialog").dialog("close");
                                            //重新刷新页面数据
                                            $('#dataList').datagrid("reload");
                                            $('#dataList').datagrid("uncheckAll");

                                        } else{
                                            $.messager.alert("消息提醒",data.msg,"warning");
                                            return;
                                        }
                                    }
                                });
                            }
                        }
                    },

                ],
                onBeforeOpen: function(){
                    var selectRow = $("#dataList").datagrid("getSelected");
                    //设置值
                    $("#edit_id").val(selectRow.id);
                    $("#edit_clazzId").combobox('setValue', selectRow.clazzId);
                    $("#edit_stuname").textbox('setValue', selectRow.stuname);
                    $("#edit_password").textbox('setValue', selectRow.password);
                    $("#edit_sex").textbox('setValue', selectRow.sex);
                    $("#edit_remark").textbox('setValue', selectRow.remark);
                },

            });
            //搜索功能
            $("#search-btn").click(function () {
                $('#dataList').datagrid('reload',{
                    stuname:$("#search_stuname").textbox('getValue'),
                    clazzId:$("#search_clazz_id").combobox('getValue')
                });
            });
        });
    </script>
</head>
<body>
<!-- 数据列表 -->
<table id="dataList" cellspacing="0" cellpadding="0">

</table>
<!-- 工具栏 -->
<div id="toolbar">
    <c:if test="${userType==1}">
    <div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
    <div style="float: left;" class="datagrid-btn-separator"></div>
    </c:if>
    <div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
    <div style="float: left;" class="datagrid-btn-separator"></div>
    <div>
        <c:if test="${userType==1}">
        <a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a>
        </c:if>
        学生名：<input id="search_stuname" class="easyui-textbox"  />
        所属班级：<select  id="search_clazz_id" class="easyui-combobox" >
            <option value="">全部</option>
            <c:forEach items="${clazzList}" var="clazz">
                <option value="${clazz.id}">${clazz.clazzname}</option>
            </c:forEach>
        </select>
        <a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
    </div>

</div>

<!-- 添加窗口 -->
<div id="addDialog" style="padding: 10px;">
    <form id="addForm" method="post">
        <table id="addTable" cellpadding="8">
            <tr>
                <td style="width:50px">姓名:</td>
                <td colspan="3">
                    <input id="add_stuname"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="stuname" data-options="required:true,  missingMessage:'请输入姓名'" />
                </td>
                <td style="width:80px"></td>
            </tr>
            <tr>
                <td style="width:50px">密码:</td>
                <td colspan="3">
                    <input id="password"  class="easyui-textbox" style="width: 200px; height: 30px;" type="password" name="password" data-options="required:true,  missingMessage:'请输入密码'" />
                </td>
                <td style="width:80px"></td>
            </tr>
            <tr>
                <td style="width:50px">所属班级:</td>
                <td colspan="3">
                    <select id="add_clazzId"  class="easyui-combobox" style="width: 200px; height: 30px;"  name="clazzId" data-options="required:true,  missingMessage:'请输入所属班级'" >
                        <c:forEach items="${clazzList}" var="clazz">
                            <option value="${clazz.id}">${clazz.clazzname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td style="width:80px"></td>
            </tr>
            <tr>
                <td style="width:50px">性别:</td>
                <td colspan="3">
                    <select id="add_sex"  class="easyui-combobox" style="width: 200px; height: 30px;"  name="sex" data-options="required:true,  missingMessage:'请输入所属班级'" >
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                </td>
                <td style="width:80px"></td>
            </tr>
            <tr>
                <td>备注:</td>
                <td colspan="4"><input id="add_remark" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="remark"  /></td>
            </tr>
        </table>
    </form>
</div>

<!-- 修改窗口 -->
<div id="editDialog" style="padding: 10px">
    <form id="editForm" method="post">
        <input type="hidden" name="id" id="edit_id">
        <table id="editTable" border=0  cellpadding="8" >
            <tr>
                <td style="width:50px">学生名:</td>
                <td colspan="3">
                    <input id="edit_stuname"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="stuname" data-options="required:true,  missingMessage:'请输入学生名'" />
                </td>
                <td style="width:80px"></td>
            </tr>
            <tr>
                <td style="width:50px">密码:</td>
                <td colspan="3">
                    <input id="edit_password"  class="easyui-textbox" style="width: 200px; height: 30px;" type="password" name="password" data-options="required:true,  missingMessage:'请输入密码'" />
                </td>
                <td style="width:80px"></td>
            </tr>
            <tr>
                <td style="width:50px">所属班级:</td>
                <td colspan="3">
                    <select id="edit_clazzId"  class="easyui-combobox" style="width: 200px; height: 30px;"  name="clazzId" data-options="required:true,  missingMessage:'请输入所属班级'" >
                        <c:forEach items="${clazzList}" var="clazz">
                            <option value="${clazz.id}">${clazz.clazzname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td style="width:80px"></td>
            </tr>
            <tr>
                <td style="width:50px">性别:</td>
                <td colspan="3">
                    <select id="edit_sex"  class="easyui-combobox" style="width: 200px; height: 30px;"  name="sex" data-options="required:true,  missingMessage:'请输入所属班级'" >
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                </td>
                <td style="width:80px"></td>
            </tr>
            <tr>
                <td>备注:</td>
                <td colspan="4"><input id="edit_remark" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="remark"  /></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>