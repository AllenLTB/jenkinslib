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
	GetParameter()
	if("${uploadMethod}" == "Nexus"){
		NexusUpload()
	} else if("${uploadMethod}" == "Maven"){
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
	                      nexusUrl: "${nexusServer}",
	                      nexusVersion: 'nexus3',
	                      protocol: 'http',
	                      repository: "${repoName}",
	                      version: "${pomVersion}"
	
}

def MavenUpload(){
	//上传制品
	buildHome = tool "mvn-3.6.3"
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


def PromoteArtifact(promoteType,artifactUrl){
	//晋级策略
	if ("${promoteType}" == "snapshot -> release") {
	    println("下载原始制品")
	    withCredentials([string(credentialsId: "nexus-user-admin-password-string",
	        variable: "nexusPassword")]){
	        println("${artifactUrl}")
	        sh """
	            wget --user=admin --password=${nexusPassword} ${artifactUrl}
	            ls -l *.jar
	        """
	    }
	    println(artifactUrl)
	    newArtificatUrl = artifactUrl - 'http://10.208.3.247:8881/repository/maven-hostd/'
	    jarName = newArtificatUrl.split('/').toList()[-1]
	    env.pomPackaging = newArtificatUrl.split('\\.').toList()[-1]
	    env.pomVersion = newArtificatUrl.split('/').toList()[-2].replace("SNAPSHOT","RELEASE")
	    env.pomArtifact = newArtificatUrl.split('/').toList()[-3]
	    env.pomGroupId = newArtificatUrl.split('/').toList()[0..2].join(".")
	    env.repoName = 'maven-releases'
		env.nexusServer = '10.208.3.247:8881'
	    println("${repoName} ${pomGroupId} ${pomArtifact} ${pomVersion} ${pomPackaging}")
	
	    //NexusUpload()
		println("上传新制品")
	    MavenUpload()
	}
}

				//	/*
				//	//晋级策略
				//	if ("${promoteType}" == "snapshot -> release") {
				//		tools.PrintMes("下载原始制品","green")
                //    	withCredentials([string(credentialsId: "nexus-user-admin-password-string", 
                //    	    variable: "nexusPassword")]){
                //    	    println("${artifactUrl}")
                //    	    sh """
                //    	        wget --user=admin --password=${nexusPassword} ${artifactUrl}
                //    	        ls -l *.jar
                //    	    """ 
				//		}
				//		println(artifactUrl)
				//		newArtificatUrl = artifactUrl - 'http://10.208.3.247:8881/repository/maven-hostd/'
				//		jarName = newArtificatUrl.split('/').toList()[-1]
				//		pomPackaging = newArtificatUrl.split('\\.').toList()[-1]
				//		pomVersion = newArtificatUrl.split('/').toList()[-2].replace("SNAPSHOT","RELEASE")
				//		pomArtifact = newArtificatUrl.split('/').toList()[-3]
				//		pomGroupId = newArtificatUrl.split('/').toList()[0..2].join(".")
				//		repoName = 'maven-releases'
				//		println("${repoName} ${pomGroupId} ${pomArtifact} ${pomVersion} ${pomPackaging}")

				//		//NexusUpload()
				//		MavenUpload()

				//		sh """
				//			"${buildHome}"/bin/mvn deploy:deploy-file -Dmaven.test.skip=true -Dfile="${jarName}" \
				//			-DgroupId="${pomGroupId}" -DartifactId="${pomArtifact}" -Dversion="${pomVersion}" \
				//			-Dpackaging="${pomPackaging}" -DrepositoryId="${repoName}" \
				//			-Durl=http://10.208.3.247:8881/repository/"${repoName}"
				//		"""
				//	}
				//	*/
