package com.huanjing.iotapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.huanjing.iotapp.base.BaseActivity;
import com.huanjing.iotapp.bean.ConfigBean;
import com.huanjing.iotapp.databinding.ActivityConfigBinding;
import com.huanjing.iotapp.databinding.ActivityMainBinding;
import com.huanjing.iotapp.utils.Out;

/**
 * 阈值配置
 */
public class ConfigActivity extends BaseActivity {

    ActivityConfigBinding binding;
    ConfigBean configBean;

    @Override
    protected void initView() {
   binding.tvSave.setOnClickListener(v -> {
       if (isAllOk()) {
           if (!isAllRight()){
               return;
           }
           if (configBean == null) {
               configBean = new ConfigBean();
           }
           configBean.data1Max = binding.etData1Max.getText().toString().trim();
           configBean.data1Min = binding.etData1Min.getText().toString().trim();

           configBean.data2Max = binding.etData2Max.getText().toString().trim();
           configBean.data2Min = binding.etData2Min.getText().toString().trim();

           configBean.data4Max = binding.etData4Max.getText().toString().trim();
           configBean.data4Min = binding.etData4Min.getText().toString().trim();



           SharedPreferences.Editor editor = sharedPreferences.edit();
           editor.putString("config", JSON.toJSONString(configBean));
           editor.commit();
           Intent intent = new Intent();
           intent.putExtra("data", configBean);
           setResult(100, intent);
           Out.toast(context, "阈值保存成功");
           finish();
       } else {
           Out.toast(context, "请填写完整数据~");
       }
   });

    }

    private boolean isAllRight() {
        double wdMin=Double.parseDouble(binding.etData1Min.getText().toString().trim());
        double wdMax=Double.parseDouble(binding.etData1Max.getText().toString().trim());
        double sdMin=Double.parseDouble(binding.etData2Min.getText().toString().trim());
        double sdMax=Double.parseDouble(binding.etData2Max.getText().toString().trim());
        double ywMin=Double.parseDouble(binding.etData4Min.getText().toString().trim());
        double ywMax=Double.parseDouble(binding.etData4Max.getText().toString().trim());
        if (wdMin>wdMax){
            Out.toast(context,"您设置‘数据1’:‘最小值’-大于-‘最大值’，请修改~");
            return false;
        }
        if (sdMin>sdMax){
            Out.toast(context,"您设置‘数据2’:‘最小值’-大于-‘最大值’，请修改~");
            return false;
        }

        if (ywMin>ywMax){
            Out.toast(context,"您设置‘数据4’:‘最小值’-大于-‘最大值’，请修改~");
            return false;
        }



        return true;
    }

    private boolean isAllOk() {
        boolean isok=false;

            isok=true;
        return isok;
    }

    @Override
    protected View bindView() {
        binding= ActivityConfigBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void loadData() {
        try {
            configBean=(ConfigBean) (getIntent().getSerializableExtra("configdata"));
        }catch (Exception exception){}
        if (configBean!=null){

        }else{
            configBean = new ConfigBean();
        }

        setData();
    }

    private void setData() {
        binding.etData1Max.setText(configBean.data1Max);
        binding.etData1Min.setText(configBean.data1Min);

        binding.etData2Max.setText(configBean.data2Max);
        binding.etData2Min.setText(configBean.data2Min);


        binding.etData4Max.setText(configBean.data4Max);
        binding.etData4Min.setText(configBean.data4Min);


    }
}