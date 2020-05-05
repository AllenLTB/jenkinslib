package org.devops
def SonarScan(projectNmae,projectDesc,projectPath,projectLanguage,SonarServer,Coding){
	def scannerHome = "/usr/local/sonarscanner"
	//def sonarServer = "https::/sonarqube-netadm.leju.com"
	def sonarData = sh(script: "data +%Y%m%d%H%M%S", returnStdout: true).trim()
	withSonarQubeEnv("${SonarServer}") {
		sh """
			${scannerHome}/bin/sonar-scanner \
			-Dsonar.host.url=${SONAR_HOST_URL} \
			-Dsonar.projectKey="${projectNmae}" \
			-Dsonar.projectName="${projectNmae}" \
			-Dsonar.sources="${projectPath}" \
			-Dsonar.language="${projectLanguage}" \
        	-Dsonar.sourceEncoding="${Coding}" \
        	-Dsonar.projectVersion="${sonarData}"\
        	-Dsonar.login=${SONAR_AUTH_TOKEN}
        	"${extraAgruments}"
		"""
	}
}
