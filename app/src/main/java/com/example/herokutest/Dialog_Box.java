package com.example.herokutest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public  final class  Dialog_Box{


    public static void ShowDialog(Context context, String Title,String url, String Buttonurl) throws IOException {

        ImageLoadTask.MyCustomTask(context,Title,Buttonurl);
        ImageLoadTask imageLoadTask=new ImageLoadTask(url,null);
        imageLoadTask.execute();


    }



    public static class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;
        private static Context mContext;
        private static String Title;
        private static String Button_url;

        public static  void MyCustomTask (Context context,String title,String button_url){
            mContext = context;
            Title=title;
            Button_url=button_url;
        }
        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);



            SwipeDismissDialog dialog = new SwipeDismissDialog.Builder(mContext)
                    .setLayoutResId(R.layout.dialog_success_booking)
                    .build()
                    .show();
            TextView textView=(TextView)dialog.findViewById(R.id.text);
            textView.setText(Title);
            Button button=(Button)dialog.findViewById(R.id.button);
            ImageView img = (ImageView) dialog.findViewById(R.id.img2);
            img.setImageBitmap(result);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Button_url));
                    mContext.startActivity(intent);
                }
            });


        }

    }

}