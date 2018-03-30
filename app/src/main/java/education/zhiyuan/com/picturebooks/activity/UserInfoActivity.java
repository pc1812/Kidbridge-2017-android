package education.zhiyuan.com.picturebooks.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import education.zhiyuan.com.picturebooks.R;
import education.zhiyuan.com.picturebooks.bean.HeadBean;
import education.zhiyuan.com.picturebooks.bean.MyInfo;
import education.zhiyuan.com.picturebooks.bean.QnToken;
import education.zhiyuan.com.picturebooks.bean.UserInfo;
import education.zhiyuan.com.picturebooks.http.Api;
import education.zhiyuan.com.picturebooks.http.HttpCallBackN;
import education.zhiyuan.com.picturebooks.http.MyAsyncTaskN;
import education.zhiyuan.com.picturebooks.utils.ChooseDateUtil;
import education.zhiyuan.com.picturebooks.utils.GlideUtils;
import education.zhiyuan.com.picturebooks.utils.QnUtils;
import education.zhiyuan.com.picturebooks.utils.SharedPreferencesUtil;
import education.zhiyuan.com.picturebooks.utils.TextViewUtils;
import education.zhiyuan.com.picturebooks.utils.TimeTools;
import education.zhiyuan.com.picturebooks.utils.ToastUtil;
import education.zhiyuan.com.picturebooks.view.commodity.custom.PermissionDialog;
import education.zhiyuan.com.picturebooks.view.commodity.custom.SignDialog;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.yalantis.ucrop.UCrop.REQUEST_CROP;

/**
 * Created by Lance on 2017/6/20.
 * Email : COCOINUT@163.com
 * Introduce : 我的资料
 */

public class UserInfoActivity extends AppCompatActivity implements HttpCallBackN, QnUtils.QnCallBack {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_birthDay)
    TextView tvBirthDay;
    @BindView(R.id.et_nickName)
    TextView etNickName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_address)
    ImageView ivAddress;

    private static final int CODE_CAEMER = 100;
    private static final int CODE_ALBUM = 200;
    private static final int PERMISSION_CAMERA = 101; //相机权限
    private static final int PERMISSION_WRITER = 201; //相机读写权限
    private static final int PERMISSION_ALBUN = 301; //相册读写权限
    private boolean allPermission = true;
    private MyInfo myInfo;
    private String ivQn;
    private String ivPath, ivName;
    private QnToken qnToken;
    private String newBirthDay, newBirth, newNkName;
    private String filePath;
    private File file;
    private QnUtils qnUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_userinfo);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        tvRight.setVisibility(View.GONE);
        tvTitle.setText("我的资料");
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/huib/pic/";
        qnUtils = new QnUtils(this);
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(String msg) {
        if (msg.equals("address_success") || msg.equals("update_address")) {
            getData();
        }
    }

    private void getData() {
        String url = "/user/info";
        final Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        new MyAsyncTaskN(UserInfoActivity.this, 0, url, this).execute(param);
    }

    @OnClick({R.id.iv_back, R.id.iv_head, R.id.et_nickName, R.id.tv_birthDay, R.id.re_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_head:
                //选择头像
                showDialog();
                break;
            case R.id.tv_birthDay:
                chooseDateDialog();
                break;
            case R.id.et_nickName:
                initNkDialog();
                break;
            case R.id.re_address:
                if (myInfo != null) {
                    //新建地址
                    if (TextUtils.isEmpty(myInfo.getData().getReceivingContact())) {
                        Intent intent = new Intent(this, EditAddressActivity.class);
                        intent.putExtra("flag", 0);
                        startActivity(intent);
                    } else {
                        Intent intentAdd = new Intent(this, AddressActivity.class);
                        intentAdd.putExtra("contact", myInfo.getData().getReceivingContact());
                        intentAdd.putExtra("phone", myInfo.getData().getReceivingPhone());
                        intentAdd.putExtra("region", myInfo.getData().getReceivingRegion());
                        intentAdd.putExtra("street", myInfo.getData().getReceivingStreet());
                        startActivity(intentAdd);
                    }
                }
                break;
        }
    }

    /**
     * 修改昵称提示框
     */
    private void initNkDialog() {
        new SignDialog(UserInfoActivity.this, R.style.Dialog, "昵称", "请输入昵称", new SignDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, EditText et) {
                if (confirm) {
                    //修改昵称
                    String text = TextViewUtils.replaceBlank(et.getText().toString());
                    if (TextUtils.isEmpty(text)) {
                        ToastUtil.showShort(getApplicationContext(), "请输入昵称");
                    } else {
                        if (!myInfo.getData().getNickname().equals(text)) {
                            newNkName = text;
                            setChange(2);
                        }
                    }
                }
            }
        }).show();
    }

    /**
     * 修改头像对话框
     */
    private void showDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_camera, null);
        bottomSheetDialog.setContentView(dialogView);
        //取消
        dialogView.findViewById(R.id.pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        //相册
        dialogView.findViewById(R.id.tv_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlbum();
                bottomSheetDialog.dismiss();
            }
        });
        //相机
        dialogView.findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPicFromCamera();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    /**
     * Choose Date 选择日期
     */
    public void chooseDateDialog() {
        final ChooseDateUtil dateUtil = new ChooseDateUtil();
        String[] oldDate = tvBirthDay.getText().toString().split("-");
        dateUtil.createDialog(this, oldDate, new ChooseDateUtil.ChooseDateInterface() {
            @Override
            public void sure(int[] newDateArray) {
                String month = "", day = "";
                if (newDateArray[1] < 10) {
                    month = "0" + newDateArray[1];
                } else {
                    month = newDateArray[1] + "";
                }
                if (newDateArray[2] < 10) {
                    day = "0" + newDateArray[2] + "";
                } else {
                    day = newDateArray[2] + "";
                }
                newBirthDay = newDateArray[0] + month + day;
                newBirth = newDateArray[0] + "-" + month + "-" + day;
                setChange(3);
            }
        });
    }

    /**
     * 采用时间进行图片命名
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Intent.ACTION_GET_CONTENT调用图库，获取所有本地图片   Intent.ACTION_PICK是选择数据
     */
    public void getAlbum() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITER);
                return;
            }
        }
        goAlbum();
    }

    /**
     * 调用相册
     */
    private void goAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, CODE_ALBUM);
    }

    /**
     * 相机拍照  权限
     */
    public void getPicFromCamera() {
        checkPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, new int[]{PERMISSION_WRITER, PERMISSION_CAMERA});
    }

    public void checkPermission(String[] permissions, int[] requestCode) {
        allPermission = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                if (!(ContextCompat.checkSelfPermission(this, permissions[i]) == PackageManager.PERMISSION_GRANTED)) {
                    allPermission = false;
                    requestPermissions(new String[]{permissions[i]}, requestCode[i]);
                }
            }
        }
        if (allPermission) {
            useCamera();
        }
    }

    /**
     * 调用相机 7.0
     */
    // 7.0 以上的uri
    private Uri mProviderUri;
    // 7.0 以下的uri
    private Uri mUri;
    private void useCamera() {
        File f = new File(filePath);
        if (!f.exists()) {
            f.mkdirs();
        }
        file = new File(filePath + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Android7.0以上URI
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //通过FileProvider创建一个content类型的Uri
            mProviderUri = FileProvider.getUriForFile(this, "education.zhiyuan.com.picturebooks.fileprovider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mProviderUri);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            mUri = Uri.fromFile(file);
            Log.e("aoaodo", "useCamera: "+mUri );
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        }
        try {
            startActivityForResult(intent, CODE_CAEMER);
        } catch (ActivityNotFoundException anf) {
            ToastUtil.showShort(getApplicationContext(), "相机");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALBUN:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goAlbum();
                } else {
                    new PermissionDialog(this, "手机读写权限").show();
                }
                break;
            case PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        useCamera();
                    }
                } else {
                    new PermissionDialog(this, "手机相机权限").show();
                }
                break;
            case PERMISSION_WRITER:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPermission(Manifest.permission.CAMERA)) {
//                        useCamera();
                    }
                } else {
                    new PermissionDialog(this, "手机读写权限").show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // 相机图片
            case CODE_CAEMER:
                //在手机相册中显示刚拍摄的图片
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(file);
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);
                if (file.exists()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        // 调用裁剪方法
                        picCrop(mProviderUri);
                    } else {
                        picCrop(mUri);
                    }
                }
                break;
            // 图片库的图片
            case CODE_ALBUM:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        if (Build.VERSION.SDK_INT >= 19) {
                            //4.0以上使用这个方法处理图片
                            handlerImageOnKitKat(data);
                        } else {
                            //4.0以下处理：
                            handlerImagerBeforeKitKat(data);
                        }
                    }
                }
                break;
            case REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    Uri croppedFileUri = UCrop.getOutput(data);
                    displayImage1(croppedFileUri.getPath());
                }
                break;
            case UCrop.RESULT_ERROR:
                Toast.makeText(this, "裁切图片失败", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 4.0以上处理
     */
    @TargetApi(19)
    private void handlerImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是Document类型的uri,则通过Document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri则使用普通方法处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri直接获取图片路径
            imagePath = uri.getPath();
        }
        picCrop(Uri.fromFile(new File(imagePath)));
    }

    /**
     * 4,0以下处理
     */
    private void handlerImagerBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);

        picCrop(uri);
    }

    /**
     * 获取图片路径
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 裁剪图片
     */
    private void picCrop(Uri uri) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".jpg";
            Uri destionUri = Uri.fromFile(new File(filePath + fileName));
            UCrop uCrop = UCrop.of(uri, destionUri);
            UCrop.Options options = new UCrop.Options();
            //设置裁剪图片可操作的手势
            options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
            //是否能调整裁剪框
            uCrop.withOptions(options);
            uCrop.withAspectRatio(1, 1);
            uCrop.start(UserInfoActivity.this);
        } catch (Exception e) {
            Log.e("eee", "picCrop: " + e.getMessage());
        }
    }

    /**
     * 设置显示图片 压缩
     */
    private void displayImage1(String imagePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (imagePath != null) {
            Luban.with(this)
                    .load(imagePath)
                    .setTargetDir(filePath)
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {
                            ivPath = file.getPath();
                            getQnToken();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("aha", "onError: " + e.getMessage());
                        }
                    }).launch();    //启动压缩
        } else {
            ToastUtil.showShort(getApplicationContext(), "获取图片失败");
        }
    }

    /**
     * 修改信息
     */
    public void setChange(int type) {
        String url = "";
        Map param = new HashMap();
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", SharedPreferencesUtil.getLoginInfo(getApplicationContext()).getToken());
        switch (type) {
            case 1:
                param.put("head", ivQn); //七牛
                url = "/user/update/head"; //修改头像
                break;
            case 2:
                param.put("nickname", newNkName); //昵称
                url = "/user/update/nickname";
                break;
            case 3:
                param.put("birthday", newBirthDay); //生日
                url = "/user/update/birthday";
                break;
        }
        new MyAsyncTaskN(UserInfoActivity.this, type, url, this).execute(param);
    }

    /**
     * 传到七牛   获取七牛Token
     */
    public void getQnToken() {
        String url = "/file/upload/token";
        Map param = new HashMap();
        param.put("token", SharedPreferencesUtil.getLoginInfo(this).getToken());
        param.put("timestamp", System.currentTimeMillis());
        param.put("type", 6);
        new MyAsyncTaskN(UserInfoActivity.this, 11, url, this).execute(param);
    }

    /**
     * 网络数据请求结果
     * 通过EventBus向mineFragment传递修改成功后的数据
     */
    @Override
    public void onSuccess(int type, String str) {
        EventBus.getDefault().post(1);
        switch (type) {
            case 0:
                myInfo = new Gson().fromJson(str, MyInfo.class);
                //成功
                GlideUtils.GlideCircle(getApplicationContext(),Api.QN + myInfo.getData().getHead(),ivHead,R.mipmap.iv_login_logo);
                tvBirthDay.setText(TimeTools.getStrTime(myInfo.getData().getBirthday() + "")); //生日
                if (TextUtils.isEmpty(myInfo.getData().getNickname())) {
                    etNickName.setText("匿名用户");  //昵称
                } else {
                    etNickName.setText(myInfo.getData().getNickname());  //昵称
                }
                tvPhone.setText(myInfo.getData().getPhone());
                if (!TextUtils.isEmpty(myInfo.getData().getReceivingRegion())) {
                    ivAddress.setVisibility(View.GONE);
                    tvAddress.setText(myInfo.getData().getReceivingRegion());
                }
                break;
            case 1:
                HeadBean head = new Gson().fromJson(str, HeadBean.class);
                ToastUtil.showShort(getApplicationContext(), "修改头像成功");
                if (ivPath != null) {
                    GlideUtils.GlideCircle(getApplicationContext(),ivPath,ivHead,R.mipmap.iv_login_logo);
                }
                UserInfo userInfo = SharedPreferencesUtil.getLoginInfo(getApplicationContext());
                SharedPreferencesUtil.putLoginInfo(getApplicationContext(), userInfo.getToken(), userInfo.getPhone(), userInfo.getPwd(), head.getData().getHead(), userInfo.getNickname());
                EventBus.getDefault().post("ivHead_" + ivPath);
                break;
            case 2:
                UserInfo info = SharedPreferencesUtil.getLoginInfo(getApplicationContext());
                SharedPreferencesUtil.putLoginInfo(getApplicationContext(), info.getToken(), info.getPhone(), info.getPwd(), info.getHeadIcon(), newNkName);
                ToastUtil.showShort(getApplicationContext(), "修改昵称成功");
                EventBus.getDefault().post("nickName_" + newNkName);
                etNickName.setText(newNkName);
                break;
            case 3:
                ToastUtil.showShort(getApplicationContext(), "修改生日成功");
                tvBirthDay.setText(newBirth);
                EventBus.getDefault().post("birth");
                break;
            case 11:
                qnToken = new Gson().fromJson(str, QnToken.class);
                if (!TextUtils.isEmpty(ivPath)) {
                    qnUtils.upload(ivPath, null, qnToken.getData().getToken());
                }
                break;
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showShort(getApplicationContext(), msg);
    }

    /***
     * 七牛上传结果
     * */
    @Override
    public void uploadSuccess(String key, ResponseInfo info, JSONObject res) {
        try {
            ivQn = res.getString("key");
            setChange(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadFail(String key, ResponseInfo info, JSONObject res) {
        ToastUtil.showShort(getApplicationContext(), info.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}