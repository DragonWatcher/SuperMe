package com.ahav.reserve.controller;

import com.ahav.reserve.utils.SpeechTranscriberDemo;
import com.ahav.reserve.utils.SpeechTranscriberWithMicrophoneDemo;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(value = "分享|纪要分享，语音识别功能")
public class ShareController {
    //语音识别的类
    SpeechTranscriberWithMicrophoneDemo SpeechTranscriberWithMicrophoneDemo = new SpeechTranscriberWithMicrophoneDemo();

    @RequestMapping("/share/summaryShare/asr")
    public void shishi(){
        SpeechTranscriberWithMicrophoneDemo.realtimeTranscribeStart();
    }

    @RequestMapping("/share/summaryShare/asrDown")
    public void down(){
        SpeechTranscriberWithMicrophoneDemo.realtimeTranscribeDown();
    }

    @RequestMapping("/share/yinpin")
    public void yinpin(){
        SpeechTranscriberDemo SpeechTranscriberDemo = new SpeechTranscriberDemo();
        SpeechTranscriberDemo.main();
    }

/*    @RequestMapping("/share/kaish")
    public void sdsad1(){
        myRecord.capture();
    }
    @RequestMapping("/share/baocun")
    public void sdsad2(){
        myRecord.stop();
    }
    @RequestMapping("/share/guanbi")
    public void sdsad3(){
        myRecord.save();
    }*/

}
