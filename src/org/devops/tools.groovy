package org.devops

def PrintMes(value,color){
	colors = ['red'    :  "\033[31m >>>>>>>>>>${value}<<<<<<<<<< \033[0m",
			  'bule'   :  "\033[34m >>>>>>>>>>${value}<<<<<<<<<< \033[0m",
			  'green' :  "\033[32m >>>>>>>>>>${value}<<<<<<<<<< \033[0m"]
	ansiColor('xterm') {
		println(colors[color])
	}
}
