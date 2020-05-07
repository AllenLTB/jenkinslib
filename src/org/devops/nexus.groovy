package org.devops

def GetParameter(){
	env.nexusServer = '10.208.3.247:8881'
	env.jarName = sh(script: "cd target ; ls *.jar",returnStdout: true).trim()
	env.repoName = "maven-hostd"
	env.filePath = "target/${jarName}"
	pom = readMavenPom file: 'pom.xml'
	println("${pom}")
	pomVersion = "${pom.version}"
	pomArtifact = "${pom.artifactId}"
	pomPackaging = "${pom.packaging}"
	pomGroupId = "${pom.groupId}"
	println("${pomGroupId}-${pomArtifact}-${pomVersion}-${pomPackaging}")
}

def Main(uploadMethod){
	if("${uploadMethod}" == "Nexus"){
		NexusUpload()
	} else if("${uploadMethod}" == "Maven"){
		MavenUpload()
	}
}

def NexusUpload(){
	GetParameter()
	//上传制品
	nexusArtifactUploader artifacts: [[artifactId: "${pomArtifact}",
	                                   classifier: '',
	                                   file: "${filePath}",
	                                   type: "${pomPackaging}"]],
	                      credentialsId: 'nexus-user-admin',
	                      groupId: "${pomGroupId}",
	                      nexusUrl: "${nexusServer}",
	                      nexusVersion: 'nexus3',
	                      protocol: 'http',
	                      repository: "${repoName}",
	                      version: "${pomVersion}"
	
}

def MavenUpload(){
	GetParameter()
	//上传制品
	sh """
	    export PATH=/usr/java/jdk1.8.0_212-amd64/bin:"$PATH"
	    cd target/
	    "${buildHome}"/bin/mvn deploy:deploy-file -Dmaven.test.skip=true \
	    -Dfile="${jarName}" -DgroupId="${pomGroupId}" \
	    -DartifactId="${pomArtifact}" -Dversion="${pomVersion}" \
	    -Dpackaging="${pomPackaging}" -DrepositoryId="${repoName}" \
	    -Durl=http://"${nexusServer}"/repository/"${repoName}"
	"""
}
