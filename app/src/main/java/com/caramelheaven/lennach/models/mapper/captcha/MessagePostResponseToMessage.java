package com.caramelheaven.lennach.models.mapper.captcha;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import com.caramelheaven.lennach.models.mapper.Mapper;
import com.caramelheaven.lennach.models.model.common.Message;
import com.caramelheaven.lennach.models.network.MessagePostResponse;
import com.caramelheaven.lennach.utils.Constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MessagePostResponseToMessage extends Mapper<MessagePostResponse, Message> {

    @Override
    public Message map(MessagePostResponse value) {
        Message message = new Message();
        fillMessage(message, value);
        return message;
    }

    public Map<String, RequestBody> map(String board, String thread, String message, String filePath,
                                        String captchaId, String captchaValue) {
        Map<String, RequestBody> data = new HashMap<>();
        fillData(data, board, thread, message, filePath,captchaId, captchaValue);
        return data;
    }

    private void fillMessage(Message message, MessagePostResponse response) {
        message.setError(response.getError());
        message.setNum(response.getNum());
        message.setStatus(response.getStatus());
    }

    private void fillData(Map<String, RequestBody> data, String board, String thread, String message, String filePath,
                          String captchaId, String captchaValue) {
        data.put("board", RequestBody.create(MediaType.parse("text/plain"), board));
        data.put("thread", RequestBody.create(MediaType.parse("text/plain"), thread));
        data.put("comment", RequestBody.create(MediaType.parse("text/plain"), message));
        data.put("image", getFileFromPath(filePath));
        data.put("captcha_type", RequestBody.create(MediaType.parse("text/plain"), Constants.CAPTCHA_TYPE));
        data.put("2chaptcha_id", RequestBody.create(MediaType.parse("text/plain"), captchaId));
        data.put("2chaptcha_value", RequestBody.create(MediaType.parse("text/plain"), captchaValue));
    }

    private RequestBody getFileFromPath(String filePath) {
        System.out.println("!!!!!!!!!!!!! filePath: " + filePath);
         File file = new File(filePath);
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/image"), file);

        // MultipartBody.Part is used to send also the actual file name
        // Should try to change logic of ApiService
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("ProfilePic", "dipalitest.jpg", requestFile);
        return requestFile;
    }



}