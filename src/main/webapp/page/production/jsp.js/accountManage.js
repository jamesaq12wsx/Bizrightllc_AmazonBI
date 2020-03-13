 $(".delete").click(function(){ $(".cover").hide();})
var edit_userId="";   
function modifyPassword(userid,username){
	edit_userId=userid;
	 	$("#ziAccount").val(username)
	 	$("#modifyPassword").show();
	 }
   function addAccount(){$("#addAccount").show();}

$(document).ready(function(){
	getUserAccount();
})

function getUserAccount(){
	$.ajax({
		type:"get",
		url:path+"/user/getUserAccount.htm",
		success:function(value){
			var data=JSON.parse(value);
			if(data.status==1){
				$("#userAccount tbody").html("")
				$.each(data.data,function(index){
					if(this.account_type==1){
						$("#userAccount tbody").append('<tr> <td style="text-align: left;">'+this.username+'</td> <td style="text-align: left;">'+this.untreated_password+'</td> <td> <span class="btn amazonBtn" style="width: 76px;" onclick="modifyPassword(\''+this.userid+'\',\''+this.username+'\')">修改密码</span></td> </tr>')
					}else{
						$("#userAccount tbody").append('<tr> <td style="text-align: left;">'+this.username+'</td> <td style="text-align: left;">'+this.untreated_password+'</td> <td> <span class="btn amazonBtn" style="width: 76px;" onclick="modifyPassword(\''+this.userid+'\',\''+this.username+'\')">修改密码</span> <span class="btn amazonBtn grayBtn" style="width: 76px;" onclick="delUserAccount(\''+this.userid+'\')">账号销毁</span> </td> </tr>')
					}
					
				})
			}
		}
	})
}

function addUserAccount(){
	$.ajax({
		type:"post",
		data:{loginname:$("#userName").val(),
			password:$("#password").val(),
			accountType:$("#contentSelect").val()
		},
		url:path+"/user/addUserAccount.htm",
		success:function(value){
			var data=JSON.parse(value);
			if(data.status==1){
				$("#userAccount tbody").html("")
				$.each(data.data,function(index){
					if(this.account_type==1){
						$("#userAccount tbody").append('<tr> <td style="text-align: left;">'+this.username+'</td> <td style="text-align: left;">'+this.untreated_password+'</td> <td> <span class="btn amazonBtn" style="width: 76px;" onclick="modifyPassword(\''+this.userid+'\',\''+this.username+'\')">修改密码</span></td> </tr>')
					}else{
						$("#userAccount tbody").append('<tr> <td style="text-align: left;">'+this.username+'</td> <td style="text-align: left;">'+this.untreated_password+'</td> <td> <span class="btn amazonBtn" style="width: 76px;" onclick="modifyPassword(\''+this.userid+'\',\''+this.username+'\')">修改密码</span> <span class="btn amazonBtn grayBtn" style="width: 76px;" onclick="delUserAccount(\''+this.userid+'\')">账号销毁</span> </td> </tr>')
					}
					
				})
				$("#addAccount").hide()
			}
		}
	})
}

function delUserAccount(userid){
	$.ajax({
		type:"post",
		data:{userid:userid},
		url:path+"/user/delUserAccount.htm",
		success:function(value){
			var data=JSON.parse(value);
			if(data.status==1){
				$("#userAccount tbody").html("")
				$.each(data.data,function(index){
					if(this.account_type==1){
						$("#userAccount tbody").append('<tr> <td style="text-align: left;">'+this.username+'</td> <td style="text-align: left;">'+this.untreated_password+'</td> <td> <span class="btn amazonBtn" style="width: 76px;" onclick="modifyPassword(\''+this.userid+'\',\''+this.username+'\')">修改密码</span></td> </tr>')
					}else{
						$("#userAccount tbody").append('<tr> <td style="text-align: left;">'+this.username+'</td> <td style="text-align: left;">'+this.untreated_password+'</td> <td> <span class="btn amazonBtn" style="width: 76px;" onclick="modifyPassword(\''+this.userid+'\',\''+this.username+'\')">修改密码</span> <span class="btn amazonBtn grayBtn" style="width: 76px;" onclick="delUserAccount(\''+this.userid+'\')">账号销毁</span> </td> </tr>')
					}
					
				})
			}
		}
	})
}

function editUserAccount(){
	$.ajax({
		type:"post",
		data:{userid:edit_userId,password:$("#ziPassword").val()},
		url:path+"/user/editUserAccount.htm",
		success:function(value){
			var data=JSON.parse(value);
			if(data.status==1){
				$("#userAccount tbody").html("")
				$.each(data.data,function(index){
					if(this.account_type==1){
						$("#userAccount tbody").append('<tr> <td style="text-align: left;">'+this.username+'</td> <td style="text-align: left;">'+this.untreated_password+'</td> <td> <span class="btn amazonBtn" style="width: 76px;" onclick="modifyPassword(\''+this.userid+'\',\''+this.username+'\')">修改密码</span></td> </tr>')
					}else{
						$("#userAccount tbody").append('<tr> <td style="text-align: left;">'+this.username+'</td> <td style="text-align: left;">'+this.untreated_password+'</td> <td> <span class="btn amazonBtn" style="width: 76px;" onclick="modifyPassword(\''+this.userid+'\',\''+this.username+'\')">修改密码</span> <span class="btn amazonBtn grayBtn" style="width: 76px;" onclick="delUserAccount(\''+this.userid+'\')">账号销毁</span> </td> </tr>')
					}
					
				})
				$("#modifyPassword").hide()
			}
		}
	})
}