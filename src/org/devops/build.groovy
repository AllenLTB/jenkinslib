package org.devops
def Build(buildType,buildShell){
	def tools = new org.devops.tools()
    def buildTools = ["mvn":"mvn-3.6.3","gradle":"gradle-6.3","ant":"ant-1.10.7","npm":"npm-6.3"]
    tools.PrintMes("当前选择的构建工具是 ${buildType}","green")
    tools.PrintMes("当前执行的构建命令是 ${buildType} ${buildShell}","green1")
    buildHome = tool buildTools[buildType]
	sh """
    	export PATH=${buildHome}/bin:$PATH
		export PATH=/usr/java/jdk1.8.0_212-amd64/bin:$PATH
    	${buildType} ${buildShell}
   	"""
}
