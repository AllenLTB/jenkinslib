package org.devops

def gitServer = "https://gitlab-netadm.leju.com/api/v4"
def HttpReq(reqType,reqUrl,reqBody){
	def tools = new org.devops.tools()
	withCredentials([string(credentialsId: 'gitlab-username_and_password', variable: 'gitlabToken')]) {
		result = httpRequest(
					customHeaders: [[maskValue: true, name: 'PRIVATE-TOKEN', value: "${gitlabToken}"]],
					httpMode: reqType,
					contentType: "APPLICATION_JSON",
					consoleLogResponseBody: true,
					ignoreSslErrors: true,
					requestBody: reqBody,
					//quiet: true,
					url: "${gitServer}")
	}
}

def ChangeCommitStatus(projectId,commitSha,status){
	commitApi = "projects/${projectId}/statuses/${commitSha}?state=${status}"
	response = HttpReq('POST',commitApi,'')
	tools.PrintMes("Status: ${response}","green")
	tools.PrintMes("Content: ${response}","green")
	return response
}
