package tech.jiangtao.support.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import tech.jiangtao.support.ui.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by jiang on 2016/12/29.
 */

public class ImageDialogManager {
    private Dialog mDialog;
    private Context mContext;
    private ImageView mPhotoImageView;
    private String mUrl;
    private PhotoViewAttacher mAttacher;

    public ImageDialogManager(Context context,String url){
        mContext = context;
        mUrl = url;
    }

    public void showDiaog(){
        mDialog = new Dialog(mContext, R.style.AppTheme_Transparent);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_image, null);
        mDialog.setContentView(view);
        mPhotoImageView = (ImageView) view.findViewById(R.id.image_photo);
        //加载
        Glide.with(mContext)
                .load(Uri.parse(mUrl))
                .error(R.mipmap.ic_mipmap_default_image)
                .placeholder(R.mipmap.ic_mipmap_default_image)
                .into(mPhotoImageView);
        mAttacher = new PhotoViewAttacher(mPhotoImageView);
        mDialog.show();
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                dismiss();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
    }

    public void dismiss(){
        if (mDialog!=null){
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
