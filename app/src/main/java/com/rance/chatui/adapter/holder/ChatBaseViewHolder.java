package com.rance.chatui.adapter.holder;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.bumptech.glide.Glide;
import com.rance.chatui.R;
import com.rance.chatui.adapter.ChatAdapter;
import com.rance.chatui.enity.IMContact;
import com.rance.chatui.enity.Link;
import com.rance.chatui.enity.MessageInfo;
import com.rance.chatui.util.Constants;
import com.rance.chatui.util.FileUtils;
import com.rance.chatui.util.Utils;
import com.rance.chatui.widget.GifTextView;

/**
 * 作者：zipper on 2021/2/20 10:47
 * 邮箱：709847739@qq.com
 */
public abstract class ChatBaseViewHolder extends BaseViewHolder<MessageInfo> {

    private static final String TAG = "ChatBaseViewHolder";

    protected TextView chatItemDate;//发送日期
    protected ImageView chatItemHeader;//头像
    protected GifTextView chatItemContentText;//文本内容
    protected LinearLayout chatItemLayoutContent;//文本类型的

    protected ImageView chatItemContentImage;//图片类型的

    protected ImageView chatItemVoice;//语音的图片
    protected TextView chatItemVoiceTime;//语音的时间
    protected LinearLayout chatItemLinearVoice;//语音类型的

    protected LinearLayout chatItemLayoutFile;//文件类型
    protected ImageView ivFileType;//文件的类型
    protected TextView tvFileName;//文件的名字
    protected TextView tvFileSize;//文件的大小

    protected LinearLayout chatItemLayoutContact;//联系人类型
    protected TextView tvContactSurname;//联系人的sur
    protected TextView tvContactName;//联系人名字
    protected TextView tvContactPhone;//联系人的电话号码

    protected LinearLayout chatItemLayoutLink;//链接类型
    protected TextView tvLinkSubject;//链接主题
    protected TextView tvLinkText;//链接内容
    protected ImageView ivLinkPicture;//链接图片


    /**
     * 针对发送的时候才有
     */
    protected ImageView chatItemFail;
    protected ProgressBar chatItemProgress;

    private ChatAdapter.onItemClickListener onItemClickListener;

    private Handler handler;

    private Context mContext;

    public ChatBaseViewHolder(ViewGroup parent, @LayoutRes int resourceLayout, ChatAdapter.onItemClickListener onItemClickListener, Handler handler) {
        super(LayoutInflater.from(parent.getContext()).inflate(resourceLayout, parent, false));
        findViewByIds(itemView);
        setItemClick();
        setItemLongClick();
        this.mContext = parent.getContext();
        this.onItemClickListener = onItemClickListener;
        this.handler = handler;
    }


    private void findViewByIds(View itemView) {

        findViewByItemView(itemView);

        chatItemDate = itemView.findViewById(R.id.chat_item_date);

        chatItemHeader = itemView.findViewById(R.id.chat_item_header);

        chatItemContentText = itemView.findViewById(R.id.chat_item_content_text);
        chatItemLayoutContent = itemView.findViewById(R.id.chat_item_layout_content);

        chatItemContentImage = itemView.findViewById(R.id.chat_item_content_image);

        chatItemVoice = itemView.findViewById(R.id.chat_item_voice);
        chatItemVoiceTime = itemView.findViewById(R.id.chat_item_voice_time);
        chatItemLinearVoice = itemView.findViewById(R.id.chat_item_layout_voice);

        chatItemLayoutFile = itemView.findViewById(R.id.chat_item_layout_file);
        ivFileType = itemView.findViewById(R.id.iv_file_type);
        tvFileName = itemView.findViewById(R.id.tv_file_name);
        tvFileSize = itemView.findViewById(R.id.tv_file_size);

        chatItemLayoutContact = itemView.findViewById(R.id.chat_item_layout_contact);
        tvContactSurname = itemView.findViewById(R.id.tv_contact_surname);
        tvContactPhone = itemView.findViewById(R.id.tv_contact_phone);

        chatItemLayoutLink = itemView.findViewById(R.id.chat_item_layout_link);
        tvLinkSubject = itemView.findViewById(R.id.tv_link_subject);
        tvLinkText = itemView.findViewById(R.id.tv_link_text);
        ivLinkPicture = itemView.findViewById(R.id.iv_link_picture);
    }

    abstract void findViewByItemView(View itemView);

    @Override
    public void setData(MessageInfo data) {
        chatItemDate.setText(data.getTime() != null ? data.getTime() : "");
        Glide.with(mContext).load(data.getHeader()).into(chatItemHeader);

        chatItemVoice.setVisibility(View.GONE);
        chatItemContentText.setVisibility(View.GONE);
        chatItemLayoutContent.setVisibility(View.GONE);
        chatItemLinearVoice.setVisibility(View.GONE);
        chatItemVoiceTime.setVisibility(View.GONE);
        chatItemContentImage.setVisibility(View.GONE);
        chatItemLayoutContact.setVisibility(View.GONE);
        chatItemLayoutFile.setVisibility(View.GONE);
        chatItemLayoutLink.setVisibility(View.GONE);

        switch (data.getFileType()) {
            case Constants.CHAT_FILE_TYPE_TEXT:
                chatItemContentText.setVisibility(View.VISIBLE);
                chatItemLayoutContent.setVisibility(View.VISIBLE);

                chatItemContentText.setSpanText(handler, data.getContent(), true);
                break;
            case Constants.CHAT_FILE_TYPE_IMAGE:
                chatItemContentImage.setVisibility(View.VISIBLE);

                Glide.with(mContext).load(data.getFilepath()).into(chatItemContentImage);
                break;
            case Constants.CHAT_FILE_TYPE_VOICE:
                chatItemVoice.setVisibility(View.VISIBLE);
                chatItemLinearVoice.setVisibility(View.VISIBLE);
                chatItemVoiceTime.setVisibility(View.VISIBLE);

                chatItemVoiceTime.setText(Utils.formatTime(data.getVoiceTime()));
                break;
            case Constants.CHAT_FILE_TYPE_FILE:
                chatItemLayoutFile.setVisibility(View.VISIBLE);

                tvFileName.setText(FileUtils.getFileName(data.getFilepath()));
                try {
                    tvFileSize.setText(FileUtils.getFileSize(data.getFilepath()));
                    switch (FileUtils.getExtensionName(data.getFilepath())) {
                        case "doc":
                        case "docx":
                            ivFileType.setImageResource(R.mipmap.icon_file_word);
                            break;
                        case "ppt":
                        case "pptx":
                            ivFileType.setImageResource(R.mipmap.icon_file_ppt);
                            break;
                        case "xls":
                        case "xlsx":
                            ivFileType.setImageResource(R.mipmap.icon_file_excel);
                            break;
                        case "pdf":
                            ivFileType.setImageResource(R.mipmap.icon_file_pdf);
                            break;
                        default:
                            ivFileType.setImageResource(R.mipmap.icon_file_other);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Constants.CHAT_FILE_TYPE_CONTACT:
                chatItemLayoutContact.setVisibility(View.VISIBLE);

                IMContact imContact = (IMContact) data.getObject();
                tvContactSurname.setText(imContact.getSurname());
                tvContactName.setText(imContact.getName());
                tvContactPhone.setText(imContact.getPhonenumber());
                break;
            case Constants.CHAT_FILE_TYPE_LINK:
                chatItemLayoutLink.setVisibility(View.VISIBLE);

                Link link = (Link) data.getObject();
                tvLinkSubject.setText(link.getSubject());
                tvLinkText.setText(link.getText());
                Glide.with(mContext).load(link.getStream()).into(ivLinkPicture);
                break;
        }
        if (Constants.CHAT_ITEM_TYPE_RIGHT == data.getType()) {
            setSendState(data);
        }
    }

    private void setSendState(MessageInfo data) {
        if (chatItemProgress == null || chatItemFail == null) {
            return;
        }
        chatItemProgress.setVisibility(View.GONE);
        chatItemFail.setVisibility(View.GONE);
        switch (data.getSendState()) {
            case Constants.CHAT_ITEM_SENDING:
                chatItemProgress.setVisibility(View.VISIBLE);
                break;
            case Constants.CHAT_ITEM_SEND_ERROR:
                chatItemFail.setVisibility(View.VISIBLE);
                break;
            case Constants.CHAT_ITEM_SEND_SUCCESS:
                break;
        }
    }

    private void setItemLongClick() {
        chatItemContentImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onLongClickImage(v, (Integer) itemView.getTag());
                return true;
            }
        });
        chatItemContentText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onLongClickText(v, (Integer) itemView.getTag());
                return true;
            }
        });
        chatItemLayoutContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onLongClickItem(v, (Integer) itemView.getTag());
                return true;
            }
        });
        chatItemLayoutFile.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onLongClickFile(v, (Integer) itemView.getTag());
                return true;
            }
        });
    }

    private void setItemClick() {
        chatItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onHeaderClick((Integer) itemView.getTag());
            }
        });
        chatItemLinearVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onVoiceClick(chatItemVoice, (Integer) itemView.getTag());
            }
        });
        chatItemContentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onImageClick(chatItemContentImage, (Integer) itemView.getTag());
            }
        });
        chatItemLayoutFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onFileClick(v, (Integer) itemView.getTag());
            }
        });
        chatItemLayoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onLinkClick(v, (Integer) itemView.getTag());
            }
        });
    }
}
