package org.devops
def Build(buildType,buildShell){
    def buildTools = ["mvn":"mvn-3.6.3","gradle":"gradle-6.3","ant":"ant-1.10.7","npm":"npm-6.3"]
    println("当前选择的构建工具是 ${buildType}","green")
    buildHome = tool buildTools[buildType]
    sh "${buildHome}/bin/${buildType} ${buildShell}"
}
