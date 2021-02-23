package com.rance.chatui.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rance.chatui.R;
import com.rance.chatui.base.BaseFragment;
import com.rance.chatui.base.ImApplication;
import com.rance.chatui.enity.MessageInfo;
import com.rance.chatui.ui.activity.ContactActivity;
import com.rance.chatui.util.Constants;
import com.rance.chatui.util.FileUtils;
import com.rance.chatui.util.PhotoUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;


/**
 * 作者：Rance on 2016/12/13 16:01
 * 邮箱：rance935@163.com
 */
public class ChatFunctionFragment extends BaseFragment {

    private static final String TAG = "ChatFunctionFragment";

    private View rootView;
    private static final int CODE_TAKE_PHOTO = 0x111;
    private static final int CODE_CROP_PHOTO = 0xa2;
    private static final int REQUEST_CODE_PICK_IMAGE = 0xa3;
    private static final int REQUEST_CODE_PICK_FILE = 0xa4;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE_CODE = 0xa7;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE_ALBUM_CODE = 0xa8;
    private static final int MY_PERMISSIONS_REQUEST_CAMERACODE = 0xa9;

    private int output_X = 480;
    private int output_Y = 480;

    @Nullable
    private File fileUri;
    private Uri imageUri;
    @Nullable
    private File fileCropUri;
    private Uri cropImageUri;

    TextView tvCapture, tvAlbum, tvContact, tvCloud, tvFile, tvLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_chat_function, container, false);
            findViewByIds(rootView);
            setItemClick();
        }
        return rootView;
    }

    private void findViewByIds(View rootView) {
        tvCapture = (TextView) rootView.findViewById(R.id.chat_function_capture);
        tvAlbum = (TextView) rootView.findViewById(R.id.chat_function_album);
        tvContact = (TextView) rootView.findViewById(R.id.chat_function_contact);
        tvCloud = (TextView) rootView.findViewById(R.id.chat_function_cloud);
        tvFile = (TextView) rootView.findViewById(R.id.chat_function_file);
        tvLocation = (TextView) rootView.findViewById(R.id.chat_function_location);
    }

    private void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERACODE);
        } else {
            takePhoto();
        }
    }

    public void setItemClick() {
        tvCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoObtainCameraPermission();
            }
        });
        tvAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE_ALBUM_CODE);
                } else {
                    choosePhoto();
                }
            }
        });

        tvFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE_CODE);
                } else {
                    chooseFile();
                }
            }
        });

        tvCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContact();
            }
        });
    }

    private void showContact() {
        Intent intent = new Intent(mActivity, ContactActivity.class);
        startActivity(intent);
    }

    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        String fileName = System.currentTimeMillis() + ".png";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            fileUri = new File(ImApplication.getInstance().getExternalFilesDir(Constants.PATH_PHOTO).getPath() + File.separator + fileName);
        } else {
            fileUri = new File(Environment.getExternalStorageDirectory().getPath() + Constants.PATH_PHOTO + File.separator + fileName);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(mActivity, Constants.AUTHORITY, fileUri);
        } else {
            imageUri = Uri.fromFile(fileUri);
        }
        PhotoUtils.takePicture(this, imageUri, CODE_TAKE_PHOTO);
    }

    /**
     * 从相册选取图片
     */
    private void choosePhoto() {
        /**
         * 打开选择图片的界面
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            case CODE_TAKE_PHOTO:
                if (res == Activity.RESULT_OK) {
                    MessageInfo messageInfo = new MessageInfo();
                    if (fileUri != null) {
                        messageInfo.setFilepath(fileUri.getAbsolutePath());
                    }
                    messageInfo.setFileType(Constants.CHAT_FILE_TYPE_IMAGE);
                    EventBus.getDefault().post(messageInfo);
                }
                break;
            case CODE_CROP_PHOTO:
                if (res == Activity.RESULT_OK) {
                    try {
                        MessageInfo messageInfo = new MessageInfo();
                        messageInfo.setFilepath(cropImageUri.getPath());
                        messageInfo.setFileType(Constants.CHAT_FILE_TYPE_IMAGE);
                        EventBus.getDefault().post(messageInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(Constants.TAG, "失败");
                }
                break;
            case REQUEST_CODE_PICK_IMAGE:
                if (res == Activity.RESULT_OK) {
                    try {
                        Uri uri = data.getData();
                        MessageInfo messageInfo = new MessageInfo();
                        messageInfo.setFilepath(getImageRealPathFromURI(uri));
                        messageInfo.setFileType(Constants.CHAT_FILE_TYPE_IMAGE);
                        EventBus.getDefault().post(messageInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(Constants.TAG, "失败");
                }

                break;
            case REQUEST_CODE_PICK_FILE:
                if (res == Activity.RESULT_OK) {
                    try {
                        Uri uri = data.getData();
                        if (uri != null) {
                            Log.e(TAG, "onActivityResult: ->" + uri.getPath());
                        }
                        MessageInfo messageInfo = new MessageInfo();
                        messageInfo.setFilepath(FileUtils.getFileAbsolutePath(mActivity, uri));
                        messageInfo.setFileType(Constants.CHAT_FILE_TYPE_FILE);
                        EventBus.getDefault().post(messageInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(Constants.TAG, "失败");
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseFile();
                } else {
                    toastShow("请同意系统权限后继续");
                }
                break;
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE_ALBUM_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePhoto();
                } else {
                    toastShow("请同意系统权限后继续");
                }
                break;
            case MY_PERMISSIONS_REQUEST_CAMERACODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    toastShow("请同意系统权限后继续");
                }
                break;
        }
    }

    public String getImageRealPathFromURI(Uri contentUri) {

        //TODO upload file、image、voice then return url;
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
