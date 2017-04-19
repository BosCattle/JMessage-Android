```mermaid
%% 
        gantt
        dateFormat  YYYY-MM-DD
        title JMessage开发进度
    
        section 单聊群聊开发
        调研            :done,    des1, 2016-10-06,2016-11-00
        需求分析            :done,    des2, 2016-10-00,2016-11-06
        项目开发            :active,    des3, 2016-11-06,2017-05-03
        bug修改             :after des3,       des4, 5d
    	集成文档			 :after des4,		des5,2d
    	
    	section 推送开发
        推送分析               :a1, 2017-05-14,2017-05-16
        代码编写               :a2, 2017-05-16,2017-06-16
        bug修改				:a3, 2017-06-16,2017-06-20
        集成文档			   :after a3, a4,2d
       
    
```