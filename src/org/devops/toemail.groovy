package org.devops
def toemail(emailUser){
	emailext body: """
		<!DOCTYPE html>
		<html>
		<head>
		<meta charset="UTF-8">
		<title>${env.JOB_NAME}-第${env.BUILD_NUMBER}次构建日志</title>
		</head>
		<body leftmargin="8" marginwidth="0" topmargin="8" marginheight="4" offset="0">
			<table width="95%" cellpadding="0" cellspacing="0"  style="font-size: 11pt; font-family: Tahoma, Arial, Helvetica, sans-serif">
				<tr>
					<td>各位同事，大家好，以下为${env.JOB_NAME}项目构建信息</td>
				</tr>
				<tr>
					<td><br />
					<b><font color="#0B610B">构建信息</font></b>
					<hr size="2" width="100%" align="center" /></td>
				</tr>
				<tr>
					<td>
						<ul>
							<li>项目名称： ${env.JOB_NAME}</li>
							<li>构建编号： 第${env.BUILD_NUMBER}次构建</li>
							<li>持续时间： ${currentBuild.durationString}</li>
							<li>构建状态： ${currentBuild.result}</li>
							<li>构建日志： <a href="${env.BUILD_URL}console">${env.BUILD_URL}console</a></li>
							<li>构建 URL： <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></li>
							<li>项目 URL： <a href="${env.JOB_URL}">${env.JOB_URL}</a></li>
						</ul>
					</td>
				</tr>
			</table>
		</body>
		</html>""",
	compressLog: true,
	attachLog: true,
	recipientProviders: [culprits(), developers(), requester(), brokenBuildSuspects()],
	replyTo: 'do-not-reply@leju.com',
	subject: "Status: ${currentBuild.result?:'SUCCESS'} - Job \'${env.JOB_NAME}: #${env.BUILD_NUMBER}\'",
	to: emailUser
}
