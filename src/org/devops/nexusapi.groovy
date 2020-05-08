package org.devops

def HttpReq(reqType,reqUrl,reqBody){
    def nexusServer = "http://10.208.3.247:8881/service/rest"
    result = httpRequest authentication: 'nexus-user-admin',
	    httpMode: reqType,
        contentType: "APPLICATION_JSON",
        consoleLogResponseBody: true,
        ignoreSslErrors: true,
        requestBody: reqBody,
        //quiet: true,
        url: "${nexusServer}/${reqUrl}"
    return result
}

def GetComponentsList(repository) {
	apiUrl = "v1/components?repository=${repository}"
    response = HttpReq("GET",apiUrl,'')
	result = readJSON text: """${response.content}"""
	result = result["items"]
	for (component in result) {
		if (component["group"] == "com.mycompany.app" && 
			component["name"] == "my-app" && 
			component["version"] == "1.0-20200507.074626-1") {
			println(component["id"])
			return component["id"]
		}
	}
	//println("${result}")
}

def GetComponentDownloadUrl(componentId) {
	apiUrl = "v1/components/${componentId}"
    response = HttpReq("GET",apiUrl,'')
	result = readJSON text: """${response.content}"""
	result = result["assets"]["downloadUrl"]
	result.each{
		 println it
	}
}



