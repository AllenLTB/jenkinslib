package org.devops

//HTTP封装
def HttpReq(reqType,reqUrl,reqBody){
    def sonarServer = "https://sonarqube-netadm.leju.com/api"
    result = httpRequest authentication: 'sonarqube-tianbao1',
		httpMode: reqType,
        contentType: "APPLICATION_JSON",
        consoleLogResponseBody: true,
        ignoreSslErrors: true,
        requestBody: reqBody,
    	//quiet: true,
        url: "${sonarServer}/${reqUrl}"
    return result
}

//获取扫描结果
def GetProjectStatus(projectName){
    apiUrl = "project_branches/list?project=${projectName}"
    response = HttpReq("GET",apiUrl,'')
    response = readJSON text: """${response.content}"""
    result = response["branches"][0]["status"]["qualityGateStatus"]
    return result
}

//搜索项目
def SearchProject(projectName){
	apiUrl = "projects/search?projects=${projectName}"
	response = HttpReq("GET",apiUrl,'')
	response = readJSON text: """${response.content}"""
	result = response["paging"]["total"]
	if (result.toString() == "0") {
		return 'false'
	} else {
		return 'true'
	}
}
	

//创建项目
def CreateProject(projectName){
	apiUrl = "projects/create?name=${projectName}&project=${projectName}"
	response = HttpReq("POST",apiUrl,'')
	result = SearchProject("${projectName}")
	if (result == "false"){
		println("${projectName}项目创建失败")
		return "false"
	} else {
		println("${projectName}项目创建成功")
	}
}

//创建质量配置
def CreateQualityProfile(lang,qualityProfileName){
	qualityProfileName = qualityProfileName.split('-')[0]
	apiUrl = "qualityprofiles/create?language=${lang}&name=${qualityProfileName}"
	response = HttpReq("POST",apiUrl,'')
}

//将项目与质量配置关联
def projectAssociateQP(lang,projectName,qualityProfileName){
	qualityProfileName = qualityProfileName.split('-')[0]
	apiUrl = "qualityprofiles/add_project?language=${lang}&project=${projectName}&qualityProfile=${qualityProfileName}"
	response = HttpReq("POST",apiUrl,'')
}


//创建质量阈

//让项目使用指定质量规则和质量阈

