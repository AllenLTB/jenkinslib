package org.devops

def GetParameter(){
	def nexusServer = '10.208.3.247:8881'
	def jarName = sh(script: "cd target ; ls *.jar",returnStdout: true).trim()
	def pom = readMavenPom file: 'pom.xml'
	def repoName = "maven-hostd"
	def filePath = "target/${jarName}"
	pomVersion = "${pom.version}"
	pomArtifact = "${pom.artifactId}"
	pomPackaging = "${pom.packaging}"
	pomGroupId = "${pom.groupId}"
	println("${pomGroupId}-${pomArtifact}-${pomVersion}-${pomPackaging}")
}

def Main(uploadMethod){
	if("${uploadMethod}" == "Nexus"){
		NexusUpload()
	} else if("${uploadMethod}" == "Nexus"){
		MavenUpload()
	}
}

def NexusUpload(){
	//上传制品
	nexusArtifactUploader artifacts: [[artifactId: "${pomArtifact}",
	                                   classifier: '',
	                                   file: "${filePath}",
	                                   type: "${pomPackaging}"]],
	                      credentialsId: 'nexus-user-admin',
	                      groupId: "${pomGroupId}",
	                      nexusUrl: "${nexusServer},
	                      nexusVersion: 'nexus3',
	                      protocol: 'http',
	                      repository: "${repoName}",
	                      version: "${pomVersion}"
	
}

def MavenUpload(){
	//开始构建
	buildHome = tool "mvn-3.6.3"
	build.Build("mvn","clean install")
	
	
	//上传制品
	sh """
	    export PATH=/usr/java/jdk1.8.0_212-amd64/bin:$PATH
	    cd target/
	    ${buildHome}/bin/mvn deploy:deploy-file -Dmaven.test.skip=true \
	    -Dfile=${jarName} -DgroupId=${pomGroupId} \
	    -DartifactId=${pomArtifact} -Dversion=${pomVersion} \
	    -Dpackaging=${pomPackaging} -DrepositoryId="${maven-hostd}" \
	    -Durl=http://${nexusServer}/repository/"${repoName}"
	"""
}
