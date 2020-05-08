package org.devops

def HttpReq(reqType,reqUrl,reqBody){
    def nexusServer = "https://10.208.3.247:8881/service/rest"
    result = httpRequest authentication: 'nexus-user-admin',
		httpMode: reqType,
        contentType: "APPLICATION_JSON",
        consoleLogResponseBody: true,
        ignoreSslErrors: true,
        requestBody: reqBody,
    	//quiet: true,
        url: "${sonarServer}/${reqUrl}"
    return result
}

def GetComponentsList(repository) {
	apiUrl = "v1/components?repository=${repository}"
    response = HttpReq("GET",apiUrl,'')
	result = readJSON text: """${response.content}"""
	println("${result}")
}
