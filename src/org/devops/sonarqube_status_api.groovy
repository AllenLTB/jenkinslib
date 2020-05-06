package org.devops

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

def GetProjectStatus(projectName){
    apiUrl = "project_branches/list?project=${projectName}"
    response = HttpReq("GET",apiUrl,'')
    response = readJSON text: """${response.content}"""
	println(${response.content})
	println(response)
    result = response["branches"][0]["status"]["qualityGateStatus"]
    return result
}
