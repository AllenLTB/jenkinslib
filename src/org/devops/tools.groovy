package org.devops

//d打印内容
def PrintMes(content){
    println(content)
}

def PrintMes(value,color){
	colors = ['red'    :  "\033[40;31m >>>>>>>>>>${value}<<<<<<<<<<\003[0m",
			  'bule'   :  "\033[47;34m >>>>>>>>>>${value}<<<<<<<<<<\003[0m",
			  'green'  :  "[1;32m >>>>>>>>>>${value}<<<<<<<<<<[0m",
			  'green1' :  "\033[40;32m >>>>>>>>>>${value}<<<<<<<<<<\003[0m"]
	ansiColor('xterm') {
		println(colors[color])
	}
}
