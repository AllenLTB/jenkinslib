package org.devops

//封装HTTP请求
def HttpReq(reqType,reqUrl,reqBody){
    def jiraServer = "https://10.208.3.19:8090/rest/api/2"
    result = httpRequest authentication: 'jira-user-admin',
		httpMode: reqType,
        contentType: "APPLICATION_JSON",
        consoleLogResponseBody: true,
        ignoreSslErrors: true,
        requestBody: reqBody,
    	//quiet: true,
        url: "${jiraServer}/${reqUrl}"
    return result
}

def RunJql(jqlCoutent){
	apiUrl = "search?jpl=${jqlCoutent}"
	response = HttpReq("GET",apiUrl,'')
	return response
}

