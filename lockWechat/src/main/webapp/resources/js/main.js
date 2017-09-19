/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

$(function () {
    alert(json);
//	json=eval("("+json+")");
//	json=JSON.parse(json);
    alert(json[0].gatewayName+','+json[0].gatewayComment+','+json[0].gatewayLocation);

    var node_devices = document.getElementById('devices');
    node_devices.addEventListener('click',function(ev){
        var target = ev.target || window.event.srcElement;
        while(target !== node_devices ){
            if(getAttribute('class')==='card-content'){
                //href内联页面,网关操作
            }
            if(getAttribute('class')==='card-footer'){
                //向下扩展DOM,门锁card
            }
//          if(target.tagName.toLowerCase() == 'li'){
//              console.log('li click~');
//              break;
//          }
            target = target.parentNode;
        }
    })
});

//设备信息JSON对象
var json=
    [
        {
            "gatewayCode": "888888",
            "gatewayComment": "格林豪泰",
            "gatewayLocation": "江苏省.南京市.雨花台区",
            "gatewayName": "天字号",
            "gatewayStatus": "4",
            "lockLists": [
                {
                    "lockCode": "001007",
                    "lockComment": "离镜",
                    "lockLocation": "魔域",
                    "lockName": "大紫明宫",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "001005",
                    "lockComment": "夜华",
                    "lockLocation": "仙界",
                    "lockName": "九重天",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "001006",
                    "lockComment": "白浅",
                    "lockLocation": "青丘",
                    "lockName": "狐狸洞",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "001003",
                    "lockComment": "墨渊",
                    "lockLocation": "昆仑山下",
                    "lockName": "昆仑墟",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "001004",
                    "lockComment": "折颜",
                    "lockLocation": "东海之东",
                    "lockName": "十里桃林",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "001002",
                    "lockComment": "紫霞仙子",
                    "lockLocation": "虎丘",
                    "lockName": "盘丝洞",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "001001",
                    "lockComment": "猴王大圣",
                    "lockLocation": "花果山",
                    "lockName": "水帘洞",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "001008",
                    "lockComment": "",
                    "lockLocation": "鼓楼区",
                    "lockName": "301",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "851153",
                    "lockComment": "",
                    "lockLocation": "",
                    "lockName": "生态",
                    "lockPower": "0",
                    "lockStatus": "0"
                }
            ]
        },
        {
            "gatewayCode": "777777",
            "gatewayComment": "七天HOTEL",
            "gatewayLocation": "浙江省.杭州市.萧山区",
            "gatewayName": "地字号",
            "gatewayStatus": "4",
            "lockLists": [
                {
                    "lockCode": "002010",
                    "lockComment": "巍巍",
                    "lockLocation": "钟山",
                    "lockName": "中山陵",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "002009",
                    "lockComment": "好游当春",
                    "lockLocation": "鼓楼",
                    "lockName": "清凉山",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "002008",
                    "lockComment": "古趣",
                    "lockLocation": "钟山",
                    "lockName": "明孝陵",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "002007",
                    "lockComment": "上元灯会",
                    "lockLocation": "秦淮",
                    "lockName": "夫子庙",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "002006",
                    "lockComment": "座断东南，折戟沉沙",
                    "lockLocation": "江渚",
                    "lockName": "燕子矶",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "002005",
                    "lockComment": "法梧之链",
                    "lockLocation": "钟山",
                    "lockName": "美龄宫",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "002003",
                    "lockComment": "佛骨舍利",
                    "lockLocation": "雨花台区",
                    "lockName": "大报恩寺",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "002004",
                    "lockComment": "雨雾朦朦，星星点点",
                    "lockLocation": "江畔",
                    "lockName": "阅江楼",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "002002",
                    "lockComment": "小家子气",
                    "lockLocation": "雨花台区",
                    "lockName": "瞻园",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "002001",
                    "lockComment": "我佛隐居过",
                    "lockLocation": "钟山",
                    "lockName": "灵谷寺",
                    "lockPower": "0",
                    "lockStatus": "0"
                },
                {
                    "lockCode": "lock_7557",
                    "lockComment": "面具",
                    "lockLocation": "太空舱",
                    "lockName": "莫里斯",
                    "lockPower": "0",
                    "lockStatus": "0"
                }
            ]
        }
    ];