/**
* @Author: 杜绍彬
* @Date:   2016-05-12:10-02-47
* @Email:  shaobin.du@zymobi.com
* @Project: Rope
* @Last modified by:   杜绍彬
* @Last modified time: 2016-05-20:02-20-19
*/



﻿var fs = require('fs');
var path = require('path');
var readline = require('readline');

var distDir = './data/origin';
var jsonDir = './data/json';

function d2json(data,fileName){
	if(!data){
		return;
	}
	//console.log(data);
	var savePath = path.join(jsonDir,fileName);
	var tmpData = data.split(";");
	var jsonData = tmpData.map(function(v){
		return v.split(',')
	});

	fs.writeFileSync(savePath,JSON.stringify(jsonData));

	//console.log('tmpData',jsonData);

}


function processFile(filePath){
	console.log('正在转换文件：',filePath);
	var fileInfo = path.parse(filePath);

	var data = fs.readFileSync(filePath,{encoding:'utf-8'});
	d2json(data,fileInfo.name+'.json');

}


function processData(dir){
	try{
		fs.accessSync(dir,fs.F_OK);
	}catch(e){
		console.log('file is not exist!');
		return;
	}

	var fileList = fs.readdirSync(dir);

	fileList = fileList.map(function(v,i){
		return path.join(dir,v);
	});

	while(1){
		if(fileList.length < 1){
			console.log('完成！');
			break;
		}
		var filePath = fileList.shift();

		console.log(filePath,fileList);

		var fStat = fs.statSync(filePath);

		if(fStat.isDirectory()){
			processData(filePath);

		}else if(fStat.isFile()){
			processFile(filePath);
		}

	}



}

processData(distDir);
