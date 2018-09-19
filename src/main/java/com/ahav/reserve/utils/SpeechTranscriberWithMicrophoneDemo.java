/* * Copyright 2015 Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.*/

package com.ahav.reserve.utils;


import com.ahav.reserve.config.Common;
import com.alibaba.nls.client.protocol.InputFormatEnum;
import com.alibaba.nls.client.protocol.NlsClient;
import com.alibaba.nls.client.protocol.SampleRateEnum;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriber;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberListener;
import com.alibaba.nls.client.protocol.asr.SpeechTranscriberResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.validation.constraints.Pattern;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * SpeechTranscriberWithMicrophoneDemo class
 *
 * 使用麦克风音频流的实时音频流识别
 * @author siwei
 * @date 2018/6/25*/


public class SpeechTranscriberWithMicrophoneDemo {
    private String appKey;
    private String accessToken;
    NlsClient client;
    ArrayList<byte[]> list = new ArrayList();
    boolean flag = true;

    public void SpeechTranscriberWithMicrophoneDemo1(String appKey, String token) {
        this.appKey = appKey;
        this.accessToken = token;
        // Step0 创建NlsClient实例,应用全局创建一个即可,默认服务地址为阿里云线上服务地址
        client = new NlsClient(accessToken);
    }

    public SpeechTranscriberListener getTranscriberListener() {
        SpeechTranscriberListener listener = new SpeechTranscriberListener() {
            // 识别出中间结果.服务端识别出一个字或词时会返回此消息.仅当setEnableIntermediateResult(true)时,才会有此类消息返回
            @Override
            public void onTranscriptionResultChange(SpeechTranscriberResponse response) {
                System.out.println("name: " + response.getName() +
                        // 状态码 20000000 表示正常识别
                        ", status: " + response.getStatus() +
                        // 句子编号，从1开始递增
                        ", index: " + response.getTransSentenceIndex() +
                        // 当前句子的中间识别结果
                        ", result: " + response.getTransSentenceText() +
                        // 当前已处理的音频时长，单位是毫秒
                        ", time: " + response.getTransSentenceTime());
            }
            // 识别出一句话.服务端会智能断句,当识别到一句话结束时会返回此消息
            @Override
            public void onSentenceEnd(SpeechTranscriberResponse response) {
                System.out.println("name: " + response.getName() +
                        // 状态码 20000000 表示正常识别
                        ", status: " + response.getStatus() +
                        // 句子编号，从1开始递增
                        ", index: " + response.getTransSentenceIndex() +
                        // 当前句子的完整识别结果
                        ", result: " + response.getTransSentenceText() +
                        // 当前已处理的音频时长，单位是毫秒
                        ", time: " + response.getTransSentenceTime());
            }
            // 识别完毕
            @Override
            public void onTranscriptionComplete(SpeechTranscriberResponse response) {
                System.out.println("name: " + response.getName() +
                        ", status: " + response.getStatus());
            }
        };

        return listener;
    }

    public void process() {
        SpeechTranscriber transcriber = null;
        try {
            // Step1 创建实例,建立连接
            transcriber = new SpeechTranscriber(client, getTranscriberListener());
            transcriber.setAppKey(appKey);
            // 输入音频编码方式
            transcriber.setFormat(InputFormatEnum.PCM);
            // 输入音频采样率
            transcriber.setSampleRate(SampleRateEnum.SAMPLE_RATE_16K);
            // 是否返回中间识别结果
            transcriber.setEnableIntermediateResult(false);
            // 是否生成并返回标点符号
            transcriber.setEnablePunctuation(true);
            // 是否将返回结果规整化,比如将一百返回为100
            transcriber.setEnableITN(false);

            // Step2 此方法将以上参数设置序列化为json发送给服务端,并等待服务端确认
            transcriber.start();

            // Step3 读取麦克风数据
            AudioFormat audioFormat = new AudioFormat(16000.0F, 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
            TargetDataLine targetDataLine = (TargetDataLine)AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);
            targetDataLine.start();
            System.out.println("You can speak now!");
            int nByte = 0;
            final int bufSize = 6400;
            byte[] buffer = new byte[bufSize];

            while ((nByte = targetDataLine.read(buffer, 0, bufSize)) > 0) {

                if(flag){

                }else{
                    // Step4 直接发送麦克风数据流
                    transcriber.send(buffer);
                    /*System.out.println("16进制："   + binary(buffer, 16));*/
                    list.add(buffer);
                    buffer= new byte[bufSize];
                }

            }

            // Step5 通知服务端语音数据发送完毕,等待服务端处理完成
            transcriber.stop();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            // Step6 关闭连接
            if (null != transcriber) {
                transcriber.close();
            }
        }
    }

    public void shutdown() {
        client.shutdown();
    }
    //实时转录开始
    public void realtimeTranscribeStart() {
        String appKey = Common.appKey;
        String token = Common.token;

        SpeechTranscriberWithMicrophoneDemo1(appKey, token);
        process();  //开启
    }

    //实时转录结束
    public void realtimeTranscribeDown() {
        String uuidRecordingName = meetingUtils.getUUID32();  //通过uuid生成录音名称
        //保存录音--（将btye数组写入成一个文件）
        for (int o = 0; o < list.size(); o++) {
            BufferedOutputStream bos = null;
            FileOutputStream fos = null;
            File file = null;
            try {
                File dir = new File("F:" + File.separator + "recording" + uuidRecordingName+".pcm"); //判断是否存在这个目录
                if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                    dir.mkdirs();
                }
                file = new File("F:" + File.separator + "recording" + uuidRecordingName+".pcm");  //File.separator分割符
                fos = new FileOutputStream(file, true);  //append为true时会在这个文件后面继续追加内容，否则会覆盖
                bos = new BufferedOutputStream(fos);
                bos.write(list.get(o));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.flush();
                        bos.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }


        try {
            //pcm转wav     recordingPcmPath1等属性说明在类头处查看
            PcmToWav.convertAudioFiles("F:" + File.separator + "recording" + uuidRecordingName+".pcm","F:" + File.separator + "recording" + uuidRecordingName+".wav");
        } catch (Exception e) {
            e.printStackTrace();
        }

        shutdown();  //关闭
    }

   /* //转16进制
    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }*/

    //保存pcm文件
    public void setAccessToken(){

    }

    //暂停
    public void setStop(){
        flag = true;
    }
    //开始
    public void setStart(){
        flag = false;
    }
}
