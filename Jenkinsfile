@Library('jenkinslib')		//加载jenkinslib库，这个库就是GlobalPipeline Libraries定义的
def tools = new org.devops.tools()    //引用org/devops/tools.groovy这个文件
hello()		//引用的是var/hello.groovy里面的call方法
pipeline {
    agent any
    stages {
        stage('Example1'){
            steps {
                timeout(time:30, unit:"MINUTES"){
                    script{
                        print("Example1")
                        tools.PrintMes("this is my lib!",'green2')		//我上面定义了一个tools，tools引用的就是org/devops/tools.groovy文件。
                    }											//此时使用tools.PrintMes就是使用tools.groovy文件里面的printMe
                }
            }
        }
    }
}
