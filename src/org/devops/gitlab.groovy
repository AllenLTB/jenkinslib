package org.devops

//封装HTTP
def HttpReq(reqType,reqUrl,reqBody){
	def gitServer = "https://gitlab-netadm.leju.com/api/v4"
	def tools = new org.devops.tools()
	withCredentials([string(credentialsId: 'gitlab-root-token2', variable: 'gitlabToken')]) {
		result = httpRequest(
					customHeaders: [[maskValue: true, name: 'PRIVATE-TOKEN', value: "${gitlabToken}"]],
					httpMode: reqType,
					contentType: "APPLICATION_JSON",
					consoleLogResponseBody: true,
					ignoreSslErrors: true,
					requestBody: reqBody,
					//quiet: true,
					url: "${gitServer}/${reqUrl}")
	}
	return result
}

//更改提交状态
def ChangeCommitStatus(projectId,commitSha,status){
	def tools = new org.devops.tools()
	commitApi = "projects/${projectId}/statuses/${commitSha}?state=${status}"
	response = HttpReq('POST',commitApi,'')
	tools.PrintMes("Status: ${response.status}","green")
	tools.PrintMes("Content: ${response.content}","green")
	return response
}

//获取项目ID
def GetProjectId(projectName) {
	apiUrl = "projects?search=${projectName}"
	response = HttpReq('GET',apiUrl,'')
    response = readJSON text: """${response.content}"""
	result = response[0]["id"]
	println(result)
	return result
}

//列出分支
def ListRepositoryBranch(projectId){
	apiUrl = "projects/${projectId}/repository/branches"
	response = HttpReq('GET',apiUrl,'')
	response = readJSON text: """${response.content}"""
	result = response["name"]
	println("分支ID: ${projectId}, 当前包含: ${result}分支")
	return result
}


//搜索分支
def SearchRepositoryBranch(projectId,searchKey){
	apiUrl = "projects/${projectId}/repository/branches?search=${searchKey}"
	response = HttpReq('GET',apiUrl,'')
	response = readJSON text: """${response.content}"""
	if (response.size() == 0) {
		println("不存在匹配${searchKey}关键字的分支")
		return 'false'
	} else {
		result = response["name"]
		println("匹配${searchKey}关键字的分支有: ${result}")
		return 'true'
	}	
}

//创建分支
def CreateBranch(projectId,newBranchName,refBranchName) {
	apiUrl = "projects/${projectId}/repository/branches?branch=${newBranchName}&ref=${refBranchName}"
	response = HttpReq('POST',apiUrl,'')
	response = readJSON text: """${response.content}"""
	branchList = ListRepositoryBranch(projectId)
	for (name in branchList) {
		if (name == newBranchName) {
			println("Create ${newBranchName} success.")
		}
	}
}

//创建合并请求MR
def CreateMergeRequest(projectId,sourceBranch,target_Branch,title) {
	apiUrl = "projects/${projectId}/merge_requests?source_branch=${sourceBranch}&target_branch=${target_Branch}&title=${title}"
	//reqBody = """{"source_branch": "${sourceBranch}",
	//			  "target_branch": "${target_Branch}",
	//			  "title: "${title}"}"""
					//"assignee_id: "${assigneeUser}"}"""
	response = HttpReq('POST',apiUrl,'')
	response = readJSON text: """${response.content}"""
}

//获取用户ID
def GetUserId(username) {
	apiUrl = "users?username${username}"
	response = HttpReq('POST',apiUrl,'')
	response = readJSON text: """${response.content}"""
	println(response["id"])
	return response["id"]
}
