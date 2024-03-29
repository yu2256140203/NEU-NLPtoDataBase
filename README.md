# NEU-NLPtoDataBase
### 项目详细介绍视频：https://github.com/yu2256140203/NEU-NLPtoDataBase/blob/main/NLPtoDataBase_Display.mp4
<br/>基于Aliyun通义千问的自然语言交互数据库<br/>
项目基础介绍：<br/>
前端开发：使用Vue+Vuetify框架，实现用户界面的设计和交互，以及Azure语音转文字的功能。<br/>
后端开发：使用Springboot框架，实现用户需求的接收和处理，以及数据库的调用和返回。<br/>
大语言模型：使用阿里云通义千问的API，实现用户自然语言和SQL语句的相互转换，以及数据库结果的自然语言生成。<br/>
Azure语言服务：使用语音转文字和文字转语音的功能，将用户和项目之间的交互更加具有便捷性。<br/>

## 1. Vue前端开发
### 1.1  技术方案
&ensp;&ensp;使用Vue框架，利用其组件化、响应式和路由等特性，构建一个单页面应用，提高用户体验和开发效率。使用Vuetify框架，利用其提供的丰富的UI组件，如按钮、输入框、对话框、表格、图标等，快速搭建一个美观和符合Material Design规范的界面。使用Azure语音服务，利用其提供的语音转文字的功能，实现用户通过语音输入自然语言需求，以及通过语音播放自然语言结果的功能。  <br/>
&ensp;&ensp;本页面通过语音或文字输入自然语言需求，然后查询数据库的数据，并返回结果。它使用了Vue.js框架和Vuetify组件库，以及微软的语音服务和阿里云的通义千问API。可以在输入框中输入你想要查询的内容，或者点击麦克风按钮，使用语音输入。可以点击提交按钮，发送你的查询，然后看到表格中的结果，以及听到语音播放的结果。通过导入microsoft-cognitiveservices-speech-sdk和axios库，使用了微软的语音服务和axios的HTTP请求功能。在data属性中，定义了一些变量，用来存储用户的输入、输出、语音配置、语音识别器和语音合成器等对象。在methods属性中，定义了两个方法，分别是toggleSpeechRecognition和submitQuery。<br/>
&ensp;&ensp;toggleSpeechRecognition方法用来切换语音识别的状态，如果是关闭的，就开启语音识别，并将识别到的结果显示在输入框中；如果是开启的，就关闭语音识别，并保留输入框中的文字。<br/>
&ensp;&ensp;submitQuery方法用来提交查询，它会使用axios发送一个get请求，将输入框中的文本作为参数，向一个本地服务器请求数据。如果请求成功，就将返回的结果赋值给result变量，并调用语音合成器的speakTextAsync方法，将result_database_nlp转换为语音并播放出来；如果请求失败，就将错误信息赋值给result变量。在created钩子函数中，设置了语音配置的语言为中文，并创建了一个语音合成器对象，用来将文本转换为语音。<br/>
### 1.2实验结果分析 
&ensp;&ensp;通过浏览器访问前端页面，可以看到一个简洁和友好的用户界面，包括一个标题栏、一个输入框、一个按钮和一个输出表格。<br/>
&ensp;&ensp;在输入框中，用户可以通过键盘输入自然语言需求，也可以通过点击按钮，启动语音转文字的功能，通过麦克风输入自然语言需求。<br/>
&ensp;&ensp;在输出框中，用户可以看到自然语言需求对应的数据库返回的结果，同时通过扬声器播放自然语言结果。<br/>
&ensp;&ensp;通过测试不同的自然语言需求，可以看到前端页面能够正常地与后端和大语言模型进行交互，实现用户需求的转换和查询，以及结果的展示和播放。<br/>

![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/1f9af5495d134afbbfaac599259dd93a.png)


## 2.Springboot后端开发
### 2.1  技术方案
&ensp;&ensp;使用Springboot框架，利用其提供的依赖注入、自动配置、嵌入式服务器等特性，快速搭建一个轻量级的后端服务，简化开发和部署的过程。使用Spring MVC模块，利用其提供的注解、拦截器、异常处理等机制，实现用户需求的接收和处理，以及数据库的调用和返回。<br/>
&ensp;&ensp;使用MyBatis框架，利用其提供的映射器、动态SQL、缓存等功能，实现数据库的连接和操作，以及数据的封装和传输。<br/>
&ensp;&ensp;qwenConfig类：设置配置类，用于定义一些公共的变量和方法，以便在其他地方调用。<br/>
&ensp;&ensp;callWithMessage方法：静态方法，用于调用阿里云通义千问的API，实现自然语言和SQL语句的相互转换，以及数据库结果的自然语言生成。该方法接收一个字符串参数，即用户的自然语言需求，返回一个字符串，即系统的自然语言结果。<br/>
&ensp;&ensp;question方法：用于生成一句SQL语句，根据用户的自然语言需求，查询数据库表student的结构和数据。该方法接收一个字符串参数，即用户的自然语言需求，返回一个字符串，即对应的SQL语句。<br/>
&ensp;&ensp;database_to_nlp方法：生成一句自然语言，根据数据库返回的查询结果，解释数据库表student的结构和数据。该方法接收一个字符串参数，即数据库的查询结果，返回一个字符串，即对应的自然语言。<br/>
### 2.2实验结果分析
&ensp;&ensp;通过Postman工具，模拟前端发送不同的自然语言需求，可以看到后端服务能够正常地接收和处理请求，以及返回响应。<br/>
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/bb728a69f25d4043b5abe936b2221e1c.png)


&ensp;&ensp;通过查看后端日志，可以看到后端服务能够正常地调用阿里云通义千问的API，实现用户自然语言和SQL语句的相互转换，以及数据库结果的自然语言生成。<br/>
&ensp;&ensp;通过查看数据库，可以看到后端服务能够正常地执行SQL语句，实现数据库的查询和更新，以及数据的返回,以下是“查询所有学生”得到的查询结果：<br/>
[<br/>
{id=2301988, name=余德华, age=22, class=计硕2305}, <br/>
{id=2301234, name=赵毅, age=20, class=计硕2305}, <br/>
{id=2301987, name=于金垒, age=22, class=计硕2305}, <br/>
{id=2301999, name=张校铭, age=21, class=计硕2305}<br/>
]<br/>
## 3.Aliyun通义千问大语言模型
### 3.1  技术方案
&ensp;&ensp;使用阿里云通义千问的API，利用其提供的大语言模型，实现用户自然语言和SQL语句的相互转换，以及数据库结果的自然语言生成。<br/>
&ensp;&ensp;使用messages参数，传入用户的自然语言需求，以及系统的角色和内容，调用通义千问的大语言模型，获取返回的message对象，其中包含系统的角色和内容，即对应的SQL语句。<br/>
&ensp;&ensp;使用prompt参数，传入数据库的结果，调用通义千问的大语言模型，获取返回的text对象，即对应的自然语言结果。<br/>
### 3.2  实验结果分析
&ensp;&ensp;通过阿里云DashScope控制台，可以看到通义千问的API的调用情况，包括调用次数、输入输出的令牌数、响应时间等。<br/>



![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/a62657ec042248c5bf8f17d5516e6b87.png)


&ensp;&ensp;通过比较不同的自然语言需求和SQL语句，可以看到通义千问的大语言模型能够准确地理解用户的意图，生成合理的SQL语句，实现用户需求的转换。<br/>
&ensp;&ensp;通过比较不同的数据库结果和自然语言结果，可以看到通义千问的大语言模型能够流畅地生成自然语言，表达数据库的结果，实现结果的生成。<br/>

![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/fe5b884d83f94febaf9104b29039f87f.png)


