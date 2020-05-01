@Library('jenkinslib')
def tools = new org.devops.tools()    //引用org/devops/tools.groovy这个文件

pipeline {
    agent any
    stages {
        stage('Example1'){
            steps {
                timeout(time:30, unit:"MINUTES"){
                    script{
                        print("Example1")
                        tools.PrintMes("this is my lib!")
                    }
                }
            }
        }
    }
}
