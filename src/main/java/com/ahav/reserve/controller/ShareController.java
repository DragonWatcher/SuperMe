package com.ahav.reserve.controller;

import com.ahav.reserve.utils.SpeechTranscriberWithMicrophoneDemo;
import io.swagger.annotations.Api;
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

    @RequestMapping("/share/summaryShare/asrStop")
    public void stop(){
        SpeechTranscriberWithMicrophoneDemo.setStop();
    }

    @RequestMapping("/share/summaryShare/asrStart")
    public void start(){
        SpeechTranscriberWithMicrophoneDemo.setStart();
    }

}
