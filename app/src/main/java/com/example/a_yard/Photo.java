package com.example.a_yard;
import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.JacksonMsgConvertor;
import com.ejlchina.okhttps.OkHttps;
import com.example.a_yard.data.ImgResponse;
import com.example.a_yard.data.UserInfo;
import com.example.a_yard.ui.notifications.NotificationsFragment;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Photo extends AppCompatActivity {

    private Button mbtn_choose_picture,mbtn_tuku,mbtn_photobaocun;
    private static ImageView ivCamera;
    // 拍照的requestCode
    private static final int CAMERA_REQUEST_CODE = 0x00000010;
    // 申请相机权限的requestCode
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 0x00000012;
    private static final int LOCAL_CROP = 13;// 本地图库
    /**
     * 用于保存拍照图片的uri
     */
    public static Uri mCameraUri;
    /**
     * 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
     */
    private String mCameraImagePath;

    /**
     *  是否是Android 10以上手机
     */
    private boolean isAndroidQ = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        //顶部导航栏修改
        StatusBarUtils.setWindowStatusBarColor(this, R.color.blue);
        ActionBar actionBar = getSupportActionBar();  // 获取ActionBar
        if (actionBar != null) {
            actionBar.setTitle("");  // 设置ActionBar的标题
            actionBar.setDisplayHomeAsUpEnabled(true);  // 设置返回按钮
        }
        ivCamera = findViewById(R.id.iv_show_picture);
        mbtn_choose_picture = findViewById(R.id.btn_choose_picture);
        //相机
        mbtn_choose_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionAndCamera();
            }
        });
       mbtn_tuku = (Button) findViewById(R.id.btn_tuku);
       mbtn_tuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Photo.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Photo.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //打开系统相册
                    openAlbum();
                }
            }
        });
        mbtn_photobaocun = (Button) findViewById(R.id.btn_photobaocun);
        mbtn_photobaocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo.this.finish();
            }
        });
    }
    //返回
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 检查权限并拍照。
     * 调用相机前先检查权限。
     */
    private void checkPermissionAndCamera() {
        int hasCameraPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.CAMERA);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有权限，调起相机拍照。
            openCamera();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},
                    PERMISSION_CAMERA_REQUEST_CODE);
        }
    }
    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (isAndroidQ) {
                    // Android 10 使用图片uri加载
                    ivCamera.setImageURI(mCameraUri);
                    uploadUrl(mCameraUri,null);
                } else {
                    // 使用图片路径加载
                    ivCamera.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));
                    uploadUrl(null,mCameraImagePath);
                    //NotificationsFragment.btn_phpto.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));
                }
            } else {
                Toast.makeText(this,"取消",Toast.LENGTH_LONG).show();
            }
            return;
        }
        if(resultCode == RESULT_OK){
            // 创建intent用于裁剪图片
            Intent intent1 = new Intent("com.android.camera.action.CROP");
            // 获取图库所选图片的uri
            Uri uri = data.getData();
            ivCamera.setImageURI(uri);
            //NotificationsFragment.btn_phpto.setImageURI(uri);
            uploadUrl(uri,null);
        }
    }
    public void uploadUrl(Uri uri, String filePath){
        //从Uri获取文件
        File file = null;
        if(uri != null) file = new File(getPath(uri));
        else file = new File(filePath);
        //设置图床url和需要的uid与token
        String url = "https://www.imgurl.org/api/v2/upload";
        //http client
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(30, TimeUnit.SECONDS).build();
        //构造请求
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("uid",getResources().getString(R.string.imageuid))
                .addFormDataPart("token",getResources().getString(R.string.imagetoken))
                .addFormDataPart("file",file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"),file))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        //发送请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Http response","onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Http response","onResponse");
                Response view = response;
                String reponseBody = response.body().string();
                Gson gson = new Gson();
                //gson.fromJson(reponseBody, new HashMap<String,String>().getClass());
                ImgResponse imgResponse = gson.fromJson(reponseBody,new ImgResponse().getClass());
                String imgUrl = imgResponse.getData().get("url");
                HTTP http = HTTP.builder()
                        .config( builder -> builder.addInterceptor(chain -> {
                            Response res = chain.proceed(chain.request());
                            ResponseBody body = res.body();
                            ResponseBody newBody = null;
                            if (body != null) {
                                newBody = ResponseBody.create(body.contentType(), body.bytes());
                            }
                            return res.newBuilder().body(newBody).build();
                        }))
                        .baseUrl("http://499270u7q7.51vip.biz")
                        .addMsgConvertor(new JacksonMsgConvertor())
                        .build();
                SharedPreferences sharedPreferences =
                        getSharedPreferences("userinfo",MODE_PRIVATE);

                String updateInfo = String.valueOf(sharedPreferences.getLong("id",-1)) + "," + imgUrl;
                HashMap<String,String> ret =
                        http.async("/update-uphoto")
                                .bind(this)
                                .bodyType(OkHttps.JSON)
                                .setBodyPara(updateInfo)
                                .post()
                                .getResult()
                                .getBody()
                                .toBean(new HashMap<String,String>().getClass());
                sharedPreferences.edit().putString("u_photo",imgUrl).commit();
            }
        });
    }
    /**
     * 处理权限申请的回调。
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，有调起相机拍照。
                openCamera();
            } else {
                //拒绝权限，弹出提示框。
                Toast.makeText(this, "拍照权限被拒绝", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 调起相机拍照
     */
    @SuppressWarnings("deprecation")
    private void openCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有相机
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            Uri photoUri = null;

            if (isAndroidQ) {
                // 适配android 10
                photoUri = createImageUri();
            } else {
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    mCameraImagePath = photoFile.getAbsolutePath();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                        photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                    } else {
                        photoUri = Uri.fromFile(photoFile);
                    }
                }
            }

            mCameraUri = photoUri;
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, CAMERA_REQUEST_CODE);

            }
        }
    }

    /**
     * 创建图片地址uri,用于保存拍照后的照片
     *
     * @return 图片的uri
     */
    private Uri createImageUri() {
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        } else {
            return getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        }
    }

    /**
     * 创建保存图片的文件
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File tempFile = new File(storageDir, imageName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }
//    /*
//     * 上传文件到服务器
//     */
//    public void upLoad(Context context, File file,String table_id)
//    {
//        String url = "http://117.78.3.88:8080";
//        HTTP http = HTTP.builder()
//                .config( builder -> builder.addInterceptor(chain -> {
//                    Response res = chain.proceed(chain.request());
//                    ResponseBody body = res.body();
//                    ResponseBody newBody = null;
//                    if (body != null) {
//                        newBody = ResponseBody.create(body.contentType(), body.bytes());
//                    }
//                    return res.newBuilder().body(newBody).build();
//                }))
//                .baseUrl(url)
//                .addMsgConvertor(new JacksonMsgConvertor())
//                .build();
//        String name = file.getName();
//        http.async("/img?table_id={table_id}")
//                .bind(context)
//                .bodyType("multipart/form")
//                .addFilePara("img",file)
//                .addPathPara("table_id",table_id)
//                .post();
//    }
    /**
     * 从图库选择图片
     */
    @SuppressWarnings("deprecation")
    private void openAlbum(){
        // 创建Intent，用于打开手机本地图库选择图片
        Intent intent1 = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // 启动intent打开本地图库
        startActivityForResult(intent1,LOCAL_CROP);
    }
}
