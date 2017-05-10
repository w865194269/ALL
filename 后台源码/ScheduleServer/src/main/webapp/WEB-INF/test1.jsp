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

    <title>课程课表</title>
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
			$("#showAllCourses").on("click",function(){
				$("div[name=coursetitle]").show();
				var data={
						"Sel_XNXQ":$("select[name=Sel_XNXQ]").val(),
						"Sel_KC":$("select[name=Sel_KC]").val(),
						"txt_yzm":$("input[name=txt_yzm]").val()
						};
				var obj={
						type:"POST",
						url:"${basePath}course/schedule",
						data:JSON.stringify(data),
						dataTpye:"json",
						contentType:"application/json"
						};
				$.ajax(obj).done(function(res){
					$("#courseList").empty();
					createTable(res);
				});
			});
			function createTable(res){
				if(res.code == -1){
					$("div[name=div_yzm]").show();
					$("span").text("请输入验证码").show();
					reImg();
				}else{
					$("div[name=div_yzm]").hide();
					$("span").hide();
					var list=res.result;
					for(var i=0;i<list.length;i++){
						var course=list[i];
						$("<tr>").append($("<th>").text(course.name))
						.append($("<th>").text(course.classNum))
						.append($("<th>").text(course.number))
						.append($("<th>").text(course.courseType))
						.append($("<th>").text(course.credit))
						.append($("<th>").text(course.classRoom))
						.append($("<th>").text(course.weeks))
						.append($("<th>").text(course.section))
						.append($("<th>").text(course.address))
						.appendTo($("#courseList"));
					}
				}
			}
		});
		
		function selectCourse() {
			$("div[name=div_yzm]").hide();
			$.ajax({
				type:"get",
				url:"${basePath}course/list",
				cache : false,
				async : false,
				success : function(data) {
					var list=data.result;
					 $('#select').empty();
			         $('#select').append("<option >--请选择课程信息--</option>");  
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
            	selectCourse();
            }
        };

	</script>
  </head>
  
  <body onload="selectCourse()";>

		<div width="100%" align="center"><h3>课程课表</h3><hr></div>
		
		<div width="100%" align="center">学年学期 &nbsp;
			<select name="Sel_XNXQ">
				<option value="20160">2016-2017学年第一学期</option>
			</select>
			课程
			<input type="text" id="txtSearch" onchange="Search()"/>
			<select name="Sel_KC" id="select"">
			</select>
			<button class="btn btn-primary" type="submit" id="showAllCourses">检索</button>
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
	
			<div name="coursetitle" width="100%" align="center" style="display:none;">
				<div style="font-size: 40px">重庆工程学院课程课表</div>
				<div style="font-size: 20px">2016-2017学年第一学期</div>
				<div align="left"></div>
				<div>
					<table class="table table-striped" border="1">
						<thead>
							<tr>
								<th>任课教师</th>
								<th>上课班号</th>
								<th>上课人数</th>
								<th>课程类别</th>
								<th>考核方式</th>
								<th>上课班级构成</th>
								<th>周次</th>
								<th>节次</th>
								<th>地点</th>
							</tr>
						</thead>
						<tbody id="courseList"></tbody>
					</table>
				</div>
			</div>
		</div>
  </body>
</html>
