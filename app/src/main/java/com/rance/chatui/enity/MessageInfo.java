package com.rance.chatui.enity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：Rance on 2016/12/14 14:13
 * 邮箱：rance935@163.com
 */
@Entity
public class MessageInfo  {
    @Id(autoincrement = true)
    private Long id;
    private int type;
    private String content;
    private String filepath;
    private String fileUri;//适配10.0问题的
    private int sendState;
    private String time;
    private String header;
    private long voiceTime;
    private String msgId;
    private String fileType;
    @Transient
    private Object object;
    private String mimeType;

    @Generated(hash = 1272845438)
    public MessageInfo(Long id, int type, String content, String filepath,
            String fileUri, int sendState, String time, String header,
            long voiceTime, String msgId, String fileType, String mimeType) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.filepath = filepath;
        this.fileUri = fileUri;
        this.sendState = sendState;
        this.time = time;
        this.header = header;
        this.voiceTime = voiceTime;
        this.msgId = msgId;
        this.fileType = fileType;
        this.mimeType = mimeType;
    }

    @Generated(hash = 1292770546)
    public MessageInfo() {
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object)     {
        this.object = object;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public int getSendState() {
        return sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public long getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(long voiceTime) {
        this.voiceTime = voiceTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", filepath='" + filepath + '\'' +
                ", sendState=" + sendState +
                ", time='" + time + '\'' +
                ", header='" + header + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", voiceTime=" + voiceTime +
                ", msgId='" + msgId + '\'' +
                ", fileType='" + fileType + '\'' +
                ", object=" + object +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
