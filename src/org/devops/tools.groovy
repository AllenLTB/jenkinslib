package org.devops

def PrintMes(value,color){
	colors = ['red'    :  "\033[40;31m >>>>>>>>>>${value}<<<<<<<<<< \033[0m",
			  'bule'   :  "\033[47;34m >>>>>>>>>>${value}<<<<<<<<<< \033[0m",
			  'green1' :  "\033[32m >>>>>>>>>>${value}<<<<<<<<<< \033[0m",
			  'green2' :  "\033[40;32m >>>>>>>>>>${value}<<<<<<<<<< \033[0m"]
	ansiColor('xterm') {
		println(colors[color])
	}
}
