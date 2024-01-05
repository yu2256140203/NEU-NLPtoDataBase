<template>
    <div class="app">
      <!-- 使用一个div作为背景图片的容器 -->
      <div class="background">
        <br>
        <br>
        <h1 style="color: hsl(0, 0%, 100%);font-weight: bold;">东北大学-自然语言处理数据库</h1>
        <!-- 使用一个div作为白色的div框 -->
  
        <div class="overlay" style="background-color: #ffffff33; border:3px solid #fff; box-shadow: 0px 0px 20px skyblue,0px 0px 20px skyblue inset;">
          <h3 style="margin-left: 20px; color:rgb(255, 255, 255);">Azure语音文字互转+通义千问+Springboot+Vue+Vuetify</h3>
          <h3 style="margin-left: 20px; color:rgb(255, 255, 255);">当前数据库信息 表名:student,拥有的属性:学号 id,姓名  name,年龄 age,班级 class</h3>
          
          <br><br>
  
          <div style="background-color: #fff; min-height: 120px; margin-left: 20px; margin-right: 20px; width: 500px; margin: 0 auto;">
  
            <v-text-field style="background-color: white;"
              v-model="first"
              label="输入自然语言"
              outlined
            ></v-text-field>
            <!-- 添加一个圆形麦克风按钮，绑定点击事件 -->
            <v-btn color="primary" dark @click="toggleSpeechRecognition">
              <v-icon>mdi-microphone</v-icon>
            </v-btn>
            <!-- 添加一个提交按钮，绑定点击事件 -->
            <v-btn color="primary" style="margin-left: 120px;" dark @click="submitQuery">
              提交
            </v-btn>
            <br>
            <!-- 添加一个表格组件，用于展示返回的结果 -->
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <!-- 遍历表头数组，创建表头单元格 -->
                    <th v-for="header in headers" :key="header.text">
                      {{ header.text }}
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <!-- 遍历结果数组，创建表格行 -->
                  <tr v-for="item in result" :key="item.id">
                    <!-- 遍历表头数组，创建表格单元格，根据表头的值，显示对应的属性 -->
                    <td v-for="header in headers" :key="header.value">
                      {{ item[header.value] }}
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
            </div>
         
  
  
  
          
        </div>
      </div>
    </div>
  </template>
  <script>
  import { SpeechConfig, AudioConfig, SpeechSynthesizer,CancellationReason,SpeechRecognizer } from 'microsoft-cognitiveservices-speech-sdk';
  import axios from 'axios'; // 引入axios库
  
  export default {
    data: () => ({
        first: '',
        last: 'Doe',
        // 创建一个语音配置对象，使用环境变量中的密钥和区域
        speechConfig: SpeechConfig.fromSubscription('3168c01ed2de41f4b511c1c07380d280', 'eastasia'),
        // 创建一个音频配置对象，使用默认的麦克风
        audioConfig: AudioConfig.fromDefaultMicrophoneInput(),
        // 创建一个语音识别器对象，使用语音配置和音频配置
        recognizer: null,
        // 创建一个布尔变量，用来记录语音识别的状态
        isRecognizing: false,
        // 创建一个字符串变量，用来保存输入框中的文字
        savedText: '',
        // 创建一个变量，用来存储返回的结果
        result: [],
        result_database_nlp: '',
        // 创建一个数组，用来存储表头的信息
        headers: [
          { text: '学号', value: 'id' },
          { text: '姓名', value: 'name' },
          { text: '年龄', value: 'age' },
          { text: '班级', value: 'class' }
        ],
        // 创建一个语音合成器对象，使用语音配置和音频配置
        speechSynthesizer: null
      }),
    name: 'App',
    components: {
     
    },
    methods: {
      // 定义一个方法，切换语音识别的状态
      toggleSpeechRecognition() {
        // 如果语音识别器不存在，就创建一个
        if (!this.recognizer) {
          this.recognizer = new SpeechRecognizer(this.speechConfig, this.audioConfig);
        }
        // 设置语音识别的事件监听器，当识别到结果时，更新输入框的文本
        this.recognizer.recognized = (s, e) => {
          this.first = e.result.text;
        };
        // 判断语音识别的状态，如果是false，就开启语音识别，如果是true，就关闭语音识别
        if (!this.isRecognizing) {
          // 开启语音识别
          this.recognizer.startContinuousRecognitionAsync();
          // 更新语音识别的状态为true
          this.isRecognizing = true;
          // 判断输入框中的文字是否为空，如果是空，就清空输入框的文本，如果不是空，就保存输入框的文本
          if (this.first) {
            this.savedText = this.first;
            this.first = '';
          }
        } else {
          // 关闭语音识别
          this.recognizer.stopContinuousRecognitionAsync();
          // 更新语音识别的状态为false
          this.isRecognizing = false;
          // 判断输入框中的文字是否为空，如果不是空，就恢复输入框的文本，如果是空，就清空保存的文本
          if (!this.first) {
            this.first = this.savedText;
            this.savedText = '';
          }
        }
      },
  
  
      // 定义一个方法，提交查询
      submitQuery() {
        // 使用axios发送get请求，传入输入框的文本作为参数，接收返回的结果
        axios.get('http://127.0.0.1:8888/sql', {
          params: {
            order: this.first
          }
        })
        .then(response => {
          // 将返回的结果赋值给result变量
          this.result = response.data.result;
          this.result_database_nlp = response.data.database_to_nlp; 
          // 调用语音合成器的speakTextAsync方法，将this.result_database_nlp作为参数传入
          this.speechSynthesizer.speakTextAsync(this.result_database_nlp);
        })
        .catch(error => {
          // 如果出现错误，将错误信息赋值给result变量
          this.result = [error.message];
        })
      }
    },
    // 在创建组件时，设置语音配置的语言为中文
    created() {
      this.speechConfig.speechRecognitionLanguage = 'zh-CN';
      // 设置语音配置的语音名称为中文的晓燕神经语音
      this.speechConfig.speechSynthesisVoiceName = 'zh-CN-XiaoyanNeural';
      // 创建一个音频配置对象，使用默认的扬声器输出
      this.audioConfig = AudioConfig.fromDefaultSpeakerOutput();
      // 创建一个语音合成器对象，使用语音配置和音频配置
      this.speechSynthesizer = new SpeechSynthesizer(this.speechConfig, this.audioConfig);
      // 设置语音合成器的synthesized事件监听器，打印合成的语音结果和相关信息
      this.speechSynthesizer.synthesized = (s, e) => {
        console.log(`Synthesized: ${e.result.text}, audio length: ${e.result.audioLength}`);
      };
      // 设置语音合成器的canceled事件监听器，打印取消的原因和相关错误信息
      this.speechSynthesizer.canceled = (s, e) => {
        console.log(`Canceled: ${e.reason}`);
        if (e.reason === CancellationReason.Error) {
          console.log(`Error code: ${e.errorCode}`);
          console.log(`Error details: ${e.errorDetails}`);
        }
      };
    }
  }
  </script>
  
  
  <style>
  .app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin: 0;
    height: 100vh;
    width: 100vw;
  }
  html, body {
    height: 100%;
    margin: 0;
    padding: 0;
  }
  /* 设置背景图片的容器为相对定位，宽高为100% */
  .background {
    position: relative;
    width: 100%;
    height: 100%;
    /* 使用background属性设置背景图片的url，大小，位置等 */
    background: url(https://ch.img1.cf/f/M4bhL/background.png) no-repeat center center;
    background-size: cover;
  }
  /* 设置白色的div框为绝对定位，居中显示，宽高自定义 */
  .overlay {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 70%;
    height: 70%;
  
  }
  </style>
  




















  <template>
    <div class="app">
      <!-- 使用一个div作为背景图片的容器 -->
      <div class="background">
        <br>
        <br>
        <h1 style="color: hsl(0, 0%, 100%);font-weight: bold;">东北大学-自然语言处理数据库</h1>
        <!-- 使用一个div作为白色的div框 -->
  
        <div class="overlay" style="background-color: #ffffff33; border:3px solid #fff; box-shadow: 0px 0px 20px skyblue,0px 0px 20px skyblue inset;">
          <h3 style="margin-left: 20px; color:rgb(255, 255, 255);">Azure语音文字互转+通义千问+Springboot+Vue+Vuetify</h3>
          <h3 style="margin-left: 20px; color:rgb(255, 255, 255);">当前数据库信息 表名:student,拥有的属性:学号 id,姓名  name,年龄 age,班级 class</h3>
          
          <br><br>
  
          <div style="background-color: #fff; min-height: 120px; margin-left: 20px; margin-right: 20px; width: 500px; margin: 0 auto;">
  
            <v-text-field style="background-color: white;"
              v-model="first"
              label="输入自然语言"
              outlined
            ></v-text-field>
            <!-- 添加一个圆形麦克风按钮，绑定点击事件 -->
            <v-btn color="primary" dark @click="toggleSpeechRecognition">
              <v-icon>mdi-microphone</v-icon>
            </v-btn>
            <!-- 添加一个提交按钮，绑定点击事件 -->
            <v-btn color="primary" style="margin-left: 120px;" dark @click="submitQuery">
              提交
            </v-btn>
            <br>
            <!-- 添加一个表格组件，用于展示返回的结果 -->
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <!-- 遍历表头数组，创建表头单元格 -->
                    <th v-for="header in headers" :key="header.text">
                      {{ header.text }}
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <!-- 遍历结果数组，创建表格行 -->
                  <tr v-for="item in result" :key="item.id">
                    <!-- 遍历表头数组，创建表格单元格，根据表头的值，显示对应的属性 -->
                    <td v-for="header in headers" :key="header.value">
                      {{ item[header.value] }}
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
            </div>
         
  
  
  
          
        </div>
      </div>
    </div>
  </template>
  <script>
  import { SpeechConfig, AudioConfig, SpeechRecognizer, SpeechSynthesizer,CancellationReason } from 'microsoft-cognitiveservices-speech-sdk';
  import axios from 'axios'; // 引入axios库
  
  export default {
    data: () => ({
        first: '',
        last: 'Doe',
        // 创建一个语音配置对象，使用环境变量中的密钥和区域
        speechConfig: SpeechConfig.fromSubscription('3168c01ed2de41f4b511c1c07380d280', 'eastasia'),
        // 创建一个音频配置对象，使用默认的麦克风
        audioConfig: AudioConfig.fromDefaultMicrophoneInput(),
        // 创建一个语音识别器对象，使用语音配置和音频配置
        recognizer: null,
        // 创建一个布尔变量，用来记录语音识别的状态
        isRecognizing: false,
        // 创建一个字符串变量，用来保存输入框中的文字
        savedText: '',
        // 创建一个变量，用来存储返回的结果
        result: [],
        result_database_nlp: '',
        // 创建一个数组，用来存储表头的信息
        headers: [
          { text: '学号', value: 'id' },
          { text: '姓名', value: 'name' },
          { text: '年龄', value: 'age' },
          { text: '班级', value: 'class' }
        ],
        // 创建一个语音合成器对象，使用语音配置和音频配置
        speechSynthesizer: null
      }),
    name: 'App',
    components: {
     
    },
    methods: {
      // 定义一个方法，切换语音识别的状态
      toggleSpeechRecognition() {
        // 如果语音识别器不存在，就创建一个
        if (!this.recognizer) {
          this.recognizer = new SpeechRecognizer(this.speechConfig, this.audioConfig);
        }
        // 设置语音识别的事件监听器，当识别到结果时，更新输入框的文本
        this.recognizer.recognized = (s, e) => {
          this.first = e.result.text;
        };
        // 判断语音识别的状态，如果是false，就开启语音识别，如果是true，就关闭语音识别
        if (!this.isRecognizing) {
          // 开启语音识别
          this.recognizer.startContinuousRecognitionAsync();
          // 更新语音识别的状态为true
          this.isRecognizing = true;
          // 判断输入框中的文字是否为空，如果是空，就清空输入框的文本，如果不是空，就保存输入框的文本
          if (this.first) {
            this.savedText = this.first;
            this.first = '';
          }
        } else {
          // 关闭语音识别
          this.recognizer.stopContinuousRecognitionAsync();
          // 更新语音识别的状态为false
          this.isRecognizing = false;
          // 判断输入框中的文字是否为空，如果不是空，就恢复输入框的文本，如果是空，就清空保存的文本
          if (!this.first) {
            this.first = this.savedText;
            this.savedText = '';
          }
          // 关闭或释放语音识别器对象
          this.recognizer.close();
        }
      },
  
  
      // 定义一个方法，提交查询
      submitQuery() {
        // 使用axios发送get请求，传入输入框的文本作为参数，接收返回的结果
        axios.get('http://127.0.0.1:8888/sql', {
          params: {
            order: this.first
          }
        })
        .then(response => {
          // 将返回的结果赋值给result变量
          this.result = response.data.result;
          this.result_database_nlp = response.data.database_to_nlp; 
          // 调用语音合成器的speakTextAsync方法，将this.result_database_nlp作为参数传入
          this.speechSynthesizer.speakTextAsync(this.result_database_nlp);
        })
        .catch(error => {
          // 如果出现错误，将错误信息赋值给result变量
          this.result = [error.message];
        })
        // 关闭或释放语音合成器对象
        this.speechSynthesizer.close();
      }
    },
    // 在创建组件时，设置语音配置的语言为中文
    created() {
      // 设置语音配置的语言属性，用于指定语音识别和语音合成的语言
      this.speechConfig.speechRecognitionLanguage = 'zh-CN';
      this.speechConfig.speechSynthesisVoiceName = 'zh-CN-XiaoyanNeural';
      // 创建一个音频配置对象，使用默认的扬声器输出
      this.audioConfig = AudioConfig.fromDefaultSpeakerOutput();
      // 创建一个语音合成器对象，使用语音配置和音频配置
      this.speechSynthesizer = new SpeechSynthesizer(this.speechConfig, this.audioConfig);
      // 设置语音合成器的synthesized事件监听器，打印合成的语音结果和相关信息
      this.speechSynthesizer.synthesized = (s, e) => {
        console.log(`Synthesized: ${e.result.text}, audio length: ${e.result.audioLength}`);
      };
      // 设置语音合成器的canceled事件监听器，打印取消的原因和相关错误信息
      this.speechSynthesizer.canceled = (s, e) => {
        console.log(`Canceled: ${e.reason}`);
        if (e.reason === CancellationReason.Error) {
          console.log(`Error code: ${e.errorCode}`);
          console.log(`Error details: ${e.errorDetails}`);
        }
      };
    }
  }
  </script>
  
  
  <style>
  .app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin: 0;
    height: 100vh;
    width: 100vw;
  }
  html, body {
    height: 100%;
    margin: 0;
    padding: 0;
  }
  /* 设置背景图片的容器为相对定位，宽高为100% */
  .background {
    position: relative;
    width: 100%;
    height: 100%;
    /* 使用background属性设置背景图片的url，大小，位置等 */
    background: url(https://ch.img1.cf/f/M4bhL/background.png) no-repeat center center;
    background-size: cover;
  }
  /* 设置白色的div框为绝对定位，居中显示，宽高自定义 */
  .overlay {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    width: 70%;
    height: 70%;
  
  }
  </style>
  