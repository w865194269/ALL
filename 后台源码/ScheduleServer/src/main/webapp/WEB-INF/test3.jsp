<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("basePath",basePath);
%>

<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>班级课表</title>
    <script src="http://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	
	<!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
	<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
	
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	
	<script type="text/javascript">
		var List = [];
		$(function(){
			$("#showAllClasses").on("click",function(){
				$("div[name=classtitle]").show();
				var data={
						"Sel_XNXQ":$("select[name=Sel_XNXQ]").val(),
						"Sel_XZBJ":$("select[name=Sel_XZBJ]").val(),
						};
				var obj={
						type:"POST",
						url:"${basePath}clazz/local/path",
						data:JSON.stringify(data),
						dataTpye:"json",
						contentType:"application/json"
						};
				$.ajax(obj).done(function(result){
					$("#classList").empty();
					$('#img_class').empty();
					if(result.code == -1){
						var data={
								"Sel_XNXQ":$("select[name=Sel_XNXQ]").val(),
								"Sel_XZBJ":$("select[name=Sel_XZBJ]").val(),
								"txt_yzm":$("input[name=txt_yzm]").val()
								};
						var obj={
								type:"POST",
								url:"${basePath}clazz/net/path",
								data:JSON.stringify(data),
								dataTpye:"json",
								contentType:"application/json"
								};
						$.ajax(obj).done(function(res){
							$("#classList").empty();
							$('#img_class').empty();
							createTableImg(res);
						});
					}else{
						$("div[name=div_yzm]").hide();
						$("span").hide();
						var list = result.result;	
						$('#img_class').append("<img src=\"${basePath}clazz/schedule/"+list.path+"\">");
					}
				});
			});
			function createTableImg(res){
				if(res.code == -1){
					reImg();
					$("div[name=div_yzm]").show();
					$("span").text("请输入验证码").show();
				}else{
					$("div[name=div_yzm]").hide();
					$("span").hide();
					var list=res.result;
					$('#img_class').append("<img src=\"${basePath}clazz/schedule/"+list.path+"\">");
				}
			}
		});
		
		function selectClass() {
			$("div[name=div_yzm]").hide();
			$.ajax({
				type:"get",
				url:"${basePath}clazz/list",
				cache : false,
				async : false,
				success : function(data) {
					var list=data.result;
					 $('#select').empty();
			         $('#select').append("<option >--请选择班级信息--</option>");  
			            for(var i in list){
			                $('#select').append("<option value='"+list[i].cId+"'>"+list[i].name+"</option>");  
			            }
			            var selType = document.getElementById("select").options;
			            for (var i = 0; i < selType.length; i++) {
			                List[i] = selType[i].value + ":" + selType[i].text;
			            }	
				}
			});
		}
		
		function reImg(){
            var img = document.getElementById("img_yzm");
            img.src = "verification?t=" + Math.floor(Math.random()*100);
        }
		
		function Search() {
        	var txtSearch = document.getElementById("txtSearch");
            var selectContent = document.getElementById("select").options;
        	var Html = "";
            if (!(txtSearch.value.length < 1)) {
            	selectContent.length = 0;
                for (var i = 0; i < List.length; i++) {
                    if (List[i].indexOf(txtSearch.value) > -1) {
                    	selectContent.add(new Option(List[i].split(":")[1], List[i].split(":")[0]));
                    }
                }
            }else{
            	selectClass();
            }
        };
		
	</script>
  </head>
  
  <body onload="selectClass()";>

		<div width="100%" align="center"><h3>行政班级课表</h3><hr></div>
		
		<div width="100%" align="center">学年学期 &nbsp;
			<select name="Sel_XNXQ">
				<option value="20160">2016-2017学年第一学期</option>
			</select>
			行政班级
			<input type="text" id="txtSearch" onchange="Search()"/>
			<select name="Sel_XZBJ" id="select">
			</select>
			<button class="btn btn-primary" type="submit" id="showAllClasses">检索</button>
			<button class="btn btn-primary">打印</button>
		</div>
		<div name="div_yzm" width="100%" align="center" style="display:none;">
			<span style="color:red"> </span>
			验证码
			<input type="text" name="txt_yzm"/>
			<img id="img_yzm" src="verification">
			<a href="#" onclick="reImg();">看不清？换一张</a>
		</div>
		<hr>
		<div style="width:100%; height: 100%;">
	
			<div name="classtitle" width="100%" align="center" style="display:none;">
				<div style="font-size: 40px">重庆工程学院课程课表</div>
				<div style="font-size: 20px">2016-2017学年第一学期</div>
				<div align="left"></div>
				<div id="img_class">
					
				</div>
			</div>
		</div>
  </body>
</html>
