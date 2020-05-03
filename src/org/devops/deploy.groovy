package org.devops
def SlatDeploy(hosts,func){
	sh " salt -L \"${hosts}\" \"${func}\""
}
