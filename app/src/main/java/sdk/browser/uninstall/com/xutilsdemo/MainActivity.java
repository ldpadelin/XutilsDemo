package sdk.browser.uninstall.com.xutilsdemo;

import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import sdk.browser.uninstall.com.library.UploadUtil;


public class MainActivity extends Activity {
    TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (TextView) findViewById(R.id.upload);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                xutilsUpload();
                final Map<String ,String> map = new HashMap<String, String>();
                map.put("email","987333069@qq.com");
                map.put("username","927fb76f");
                map.put("sign","a92cfd66469776018809bd94fa004773");
                File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/P70818-104334(1).jpg");
                final Map<String ,File> fileMap = new HashMap<String, File>();
                fileMap.put("image",file);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String  result = UploadUtil.post("http://bbs.rjs.com/app.php?mod=upload", map, fileMap);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,result,Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }).start();
            }
        });
    }


    private void xutilsUpload() {
        RequestParams params = new RequestParams();
        params.addHeader("uid", "123456");
        params.addHeader("Content-Disposition", "form-data; name=\"image\"");
        params.addBodyParameter("email", "987333069@qq.com");
        params.addBodyParameter("username", "927fb76f");
        params.addBodyParameter("sign", "a92cfd66469776018809bd94fa004773");

        params.addBodyParameter("image", new File(Environment.getExternalStorageDirectory() + "/DCIM/P70818-104334(1).jpg"));

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                "http://bbs.rjs.com/app.php?mod=upload",
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        Toast.makeText(MainActivity.this,"开始上传",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        Log.e("progress","progress : " + current / total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Toast.makeText(MainActivity.this,"上传成功",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
                    }
                });
    }
}
