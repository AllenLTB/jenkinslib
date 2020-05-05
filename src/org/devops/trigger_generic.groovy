package org.devops

def GenTrigger(tokenValue) {
	GenericTrigger(
	    genericVariables: [
	        [key: "branch", value: "\$.ref", expressionType: "JSONPath", regexpFilter: "", defaultValue: ""],
	        [key: "buildUser", value: "\$.user_username", expressionType: "JSONPath", regexpFilter: "", defaultValue: ""],
	        [key: "srcUrl", value: "\$.project.git_ssh_url", expressionType: "JSONPath", regexpFilter: "", defaultValue: ""],
	        [key: "projectId", value: "\$.project.id", expressionType: "JSONPath", regexpFilter: "", defaultValue: ""],
	        [key: "commitSha", value: "\$.checkout_sha", expressionType: "JSONPath", regexpFilter: "", defaultValue: ""],
	        [key: "before", value: "\$.before", expressionType: "JSONPath", regexpFilter: "", defaultValue: ""],
	        [key: "after", value: "\$.after", expressionType: "JSONPath", regexpFilter: "", defaultValue: ""],
	        [key: "object_kind", value: "\$.object_kind", expressionType: "JSONPath", regexpFilter: "", defaultValue: ""],
	        [key: "mailUser", value: "\$.user_email", expressionType: "JSONPath", regexpFilter: "", defaultValue: ""]
	    ],
	    genericRequestVariables: [
	        [key: "runOpts", regexpFilter: ""]
	    ],
	    genericHeaderVariables: [
	    ],
	    token: "${tokenValue}",
	    causeString: 'Triggered on $branch',
	    printContributedVariables: true,
	    printPostContent: true,
	    silentResponse: true,
	    regexpFilterText: '$object_kind $before $after',
	    regexpFilterExpression: '^push\\s(?!0{40}).{40}\\s(?!0{40}).{40}$'
	)
}
def aaa(){
	if ( "${runOpts}" =="GitlabPush" ) {
		branchName = branch - '/refs/heads/'
		currentBuild.description = "Trigger by ${buildUser} - ${branch}"
	}
}
