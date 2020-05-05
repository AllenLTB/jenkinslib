package org.devops
def Scan(projectNmae,projectDesc,projectPath,projectLanguage,SonarServer,Coding,extraAgruments){
	def scannerHome = "/usr/local/sonarscanner"
	def sonarData = sh(script: "date +%Y%m%d%H%M%S", returnStdout: true).trim()
	withSonarQubeEnv("${SonarServer}") {
		sh """
			${scannerHome}/bin/sonar-scanner \
			-Dsonar.host.url="${SONAR_HOST_URL}" \
			-Dsonar.projectKey="${projectNmae}" \
			-Dsonar.projectName="${projectNmae}" \
			-Dsonar.projectVersion="${sonarData}" \
			-Dsonar.sources="${projectPath}" \
			-Dsonar.language="${projectLanguage}" \
        	-Dsonar.sourceEncoding="${Coding}" \
        	-Dsonar.projectVersion="${sonarData}" \
        	-Dsonar.login="${SONAR_AUTH_TOKEN}" "${extraAgruments}"
		"""
	}
}
